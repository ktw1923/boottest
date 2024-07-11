const FetchModal = () => {
    return (
        <div className={`fixed top-0 left-0 place-items-center justify-center`}>
            <div className="bg-white rounded-3xl opacity-100 min-w-min h-1/4 min-2-[600px] flex justify-center items-center"></div>
            <div className="text-4xl font-extrabold text-blue-400 m-30">Load...</div>
        </div>
    )
}

export default FetchModal;