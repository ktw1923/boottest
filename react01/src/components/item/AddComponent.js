import { useRef, useState } from "react";
import { AsyncAdd } from "../../api/itemApi";
import FetchModal from "../../modal/FetchModal";

const initstate={
    pname:"",
    pdesc:"",
    price:0,
    files:[],
}

function AddComponent(props) {

    const [item, setItem] = useState(initstate);  // pname, pdesc, price, files

    const [fetch, setFetch] = useState(false);
    const uploadFileRef=useRef();

    const changeItem = (e)=>{
        item[e.target.name]=e.target.value;
        setItem({
            ...item
        });
    }

    const ClickAdd = (e) =>{
        console.log(item);

        const file = uploadFileRef.current.files;

        const fData= new FormData();

        for(let i=0;i<file.length;i++){
            fData.append("files",file[i]);
        }
        fData.append("pname",item.pname);
        fData.append("pdesc",item.pdesc);
        fData.append("price",item.price);

        setFetch(true); //로딩중...

        AsyncAdd(fData).then((data) => {
            setFetch(false);
        });

    }


    return(
        <div className="border-4 border-pink-400 m-3 p-4">
            <div className="flex justify-center">
                <div className="flex w-full flex-wrap">
                    <div className="w-1/5 p-5 text-left">PName</div>
                    <input className="w-4/5 p-5 rounded-r
                    border-neutral-200"
                           name="pname"
                           type={"text"}
                           value={item.pname}
                           onChange={changeItem}>
                    </input>

                </div>
            </div>


            <div className="flex justify-center">
                <div className="flex w-full flex-wrap">
                    <div className="w-1/5 p-5 text-left">PDesc</div>
                    <textarea className="w-4/5 p-5 rounded-r
                    border-neutral-200"
                              name="pdesc"
                              rows="3"
                              value={item.pdesc}
                              onChange={changeItem}
                    >
                    </textarea>

                </div>
            </div>

            <div className="flex justify-center">
                <div className="flex w-full flex-wrap">
                    <div className="w-1/5 p-5 text-left">Price</div>
                    <input className="w-4/5 p-5 rounded-r
                    border-neutral-200"
                           name="price"
                           type={"text"}
                           value={item.price}
                           onChange={changeItem}>
                    </input>

                </div>
            </div>

            <div className="flex justify-center">
                <div className="flex w-full flex-wrap">
                    <div className="w-1/5 p-5 text-left">Files</div>
                    <input className="w-4/5 p-5 rounded-r
                    border-neutral-200"
                           ref={uploadFileRef}
                           type={"file"}
                           multiple={true}
                    >
                    </input>
                </div>
            </div>

            <div className="flex justify-center">
                <div className="flex w-full flex-wrap">
                    <button type="button"
                            className="rounded bg-yellow-300 text-blue-400"
                            onClick={ClickAdd}
                    >
                        Add
                    </button>
                </div>
            </div>

            {fetch?<FetchModal />:<></>}
        </div>
    );

};

export default AddComponent;