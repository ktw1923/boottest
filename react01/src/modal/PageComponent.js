const PageComponent= ({serverSave,movePage}) => {
    return (
        <div className="m-5 flex justify-center">
            {serverSave.prev ? (
                <div className="m-2 p-2 w-16 text-center font-bold text-blue-600"
                     onClick={() => movePage({page:serverSave.prevPage})}
                >
                    Prev{" "}
                </div>
            ) : (<></>) }

            {serverSave.pageNumList.map((pageNum) =>(
                <div key={pageNum}
                     className={`m-2 p-2 w-16 text-center text-white
            ${serverSave.current === pageNum? "bg-blue-200" : "bg-white"}`}

                     onClick={() => movePage({page:pageNum})}
                >
                    {pageNum}
                </div>

            ))}

            {serverSave.next ? (
                <div className="m-2 p-2 w-16 text-center font-bold text-blue-600"
                     onClick={() => movePage({page:serverSave.nextPage})}
                >
                    Next
                </div>
            ) : (<></>) }

        </div>

    );
}

export default PageComponent;