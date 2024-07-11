import { useEffect, useState } from "react";
import useCustomMove from "../../hook/useCustomMove";
import { getList } from "../../api/itemApi";
import { API_SERVER } from "../../api/itemApi";
import FetchModal from "../../modal/FetchModal";

const host=API_SERVER;

const initstate={
    dtoList:[],
    pageNumList:[],
    pageDTO:null,
    prev:false,
    next:false,
    totalCount:0,
    prevPage:0,
    nextPage:0,
    totalPage:0,
    current:0,
};

const ListComponent = () => {

    const {page,size, refresh, moveToList, moveToRead}=useCustomMove();

    const [fetch, setFetch] = useState(false);  //데이터 가져오는 중인지 아닌지

    const [serverSave, setServerSave] = useState(initstate);
    //서버에서 받아온 데이터 저장

    useEffect(() => {  //이벤트 핸들러
        setFetch(true); // 데이터를 가져오는 중이기 때문에 true로 설정함

        getList({page, size}).then((data)=>{
            console.log(data);
            setServerSave(data); //받아온 데이터 값을 저장
            setFetch(false);
        });
    },[page,size,refresh]);  //값이 변경되었을때


    return (
        <div className="border-2 border-sky-200 mt-10 mr-3 ml-3">
            {fetch ? <FetchModal /> : <></>}

            <div className="flex flex-wrap mx-auto p-5">
                {serverSave.dtoList.map((item) => (
                    <div key={item.pnum}
                         className="w-full p-1 rounded border-2"
                         onClick={() => moveToRead(item.pnum)}
                    >
                        <div className="flex flex-col  h-full">
                            <div className="text-2xl p-2 w-full">
                                {item.pnum}
                            </div>
                            <div className="text-1xl m-1 p-2 w-full flex flex-col">
                                <div className="w-full overflow-hidden ">
                                    <img alt="item" className="m-auto rounded-md w-50"
                                         src={`${host}/bit/items/item/s_${item.uploadFileNames[0]}`}/>
                                </div>

                                <div className="bottom-0 bg-white">
                                    <div className="text-center">상품명: {item.pname} </div>
                                    <div className="text-center">상품가격: {item.price} </div>
                                </div>
                            </div>
                        </div>
                    </div>
                ))}
            </div>




        </div>

    );
};
export default ListComponent;