import { useEffect, useState } from "react";
import {API_SERVER, getOne, modifyOne} from "../../api/itemApi";
import useCustomMove from "../../hook/useCustomMove";
import FetchModal from "../../modal/FetchModal";
import { useRef } from "react";

const initstate={
    pnum:0,
    pname:"",
    pdesc:"",
    price:0,
    uploadFileNames:[],
}

const host=API_SERVER;

const ModifyComponet = ({pnum}) => {

    const [item, setItem] =useState(initstate);
    const {moveToList} =useCustomMove();
    const [fetch, setFetch] = useState(false);

    const uploadFileRef=useRef();


    useEffect(() =>{
        setFetch(true);

        getOne(pnum).then((data) =>{
            setItem(data);
            setFetch(false);
        });
    },[pnum]);

    const changeItem = (e) => {
        item[e.target.name]=e.target.value;
        setItem({
           ...item
        });
    }

    const clickModify = () => {
        const files = uploadFileRef.current.files;

        const fData= new FormData();

        for(let i=0;i<files.length;i++){
            fData.append("files",files[i]);
        }
        fData.append("pname",item.pname);
        fData.append("pdesc",item.pdesc);
        fData.append("price",item.price);
        fData.append("deFlag", item.deFlag);

        for(let i=0;i<item.uploadFileNames.length;i++){
            fData.append("uploadFileNames", item.uploadFileNames[i]);
        }
        setFetch(true);

        modifyOne(pnum, fData).then((data) => {
            setFetch(false);
        })
    }

    return (
        <div className="border-2 border-sky-200 mt-10 mr-3 ml-3">
            {fetch ? <FetchModal /> : <></>}

            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-left">pname</div>
                    <input className="w-4/5 p-6 rounded-r border border-solid"
                           name="pname"
                           value={item.pname}
                           type={"text"}
                           onChange={changeItem}
                    >
                    </input>
                </div>
            </div>

            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-left">pdesc</div>
                    <textarea className="w-4/5 p-6 rounded-r border border-solid"
                    name="pdesc"
                    rows="4"
                    onChange={changeItem}
                    value={item.pdesc}
                    ></textarea>
                </div>
            </div>


            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-left">price</div>
                    <input className="w-4/5 p-6 rounded-r border border-solid"
                        name="price"
                        value={item.price}
                        type={"text"}
                        onChange={changeItem}
                    ></input>
                </div>
            </div>


            <div className="w-full justify-center flex  flex-col m-auto items-center">
                {item.uploadFileNames.map((imgFile, i) => (
                    <img alt="item"
                         key={i}
                         className="p-4 w-1/2"
                         src={`${host}/bit/items/item/${imgFile}`} />
                ))}
            </div>

            <div className="flex justify-end p-5">
                <button type="button"
                        className="inline-block rounded p-4 m-3 text-white bg-red-400"
                        onClick={clickModify}
                >
                    Modify
                </button>
                <button type="button"
                        className="inline-block rounded p-4 m-3 text-white bg-blue-400"
                        onClick={moveToList}
                >
                    List
                </button>
            </div>
        </div>


    );
}

export default ModifyComponet;