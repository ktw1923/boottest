import { useEffect, useState } from "react";
import { API_SERVER, getOne } from "../../api/itemApi";
import useCustomMove from "../../hook/useCustomMove";
import FetchModal from "../../modal/FetchModal";

const initstate={
    pnum:0,
    pname:"",
    pdesc:"",
    price:0,
    uploadFileNames:[],
}

const host=API_SERVER;

const ReadComponent = ({pnum}) => {

    const [item, setItem] =useState(initstate);
    const {moveToList, moveToModify, page, size} =useCustomMove();
    const [fetch, setFetch] = useState(false);

    useEffect(() =>{
        setFetch(true);

        getOne(pnum).then((data) =>{
            setItem(data);
            setFetch(false);
        });
    },[pnum]);
    return (
        <div className="border-2 border-sky-200 mt-10 mr-3 ml-3">
            {fetch ? <FetchModal /> : <></>}

            <div className="flex flex-wrap mx-auto p-5">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-left">pnum</div>
                    <div className="w-4/5 p-6 rounded-r border border-solid">
                        {item.pnum}
                    </div>
                </div>
            </div>

            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-left">pname</div>
                    <div className="w-4/5 p-6 rounded-r border border-solid">
                        {item.pname}
                    </div>
                </div>
            </div>

            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-left">pdesc</div>
                    <div className="w-4/5 p-6 rounded-r border border-solid">
                        {item.pdesc}
                    </div>
                </div>
            </div>


            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-left">price</div>
                    <div className="w-4/5 p-6 rounded-r border border-solid">
                        {item.price}
                    </div>
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
                        onClick={() => moveToModify(pnum)}
                >
                    Modify
                </button>
                <button type="button"
                        className="inline-block rounded p-4 m-3 text-white bg-blue-400"
                        onClick={() => moveToList(page, size)}
                >
                    List
                </button>
            </div>
        </div>


    );
}

export default ReadComponent;