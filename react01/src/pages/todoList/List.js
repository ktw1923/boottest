import { useSearchParams } from "react-router-dom";

const List = () => {

    const [searchParams] = useSearchParams()
    const page = searchParams.get("page")
    const num = searchParams.get("num")

    return (
        <div className="p-5 w-full bg-yellow-200">
            <div className="text-2xl font-bold">
                {page}/{num}
            </div>

        </div>
    );
}

export default List;