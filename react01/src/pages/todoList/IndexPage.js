import { Outlet, useNavigate } from "react-router-dom"
import IndexLayout from "../../layout/IndexLayout"
import { useCallback } from "react"

const IndexPage = () => {

    const nav = useNavigate()

    const onList = useCallback(() => {
        nav({
            pathname: 'list'
        })
    }, [])

    const onAdd = useCallback(() => {
        nav({
            pathname: 'add'
        })
    }, [])

    return (
        <IndexLayout>
            <div className="w-full flex m-3 p-2">
                <div className="text-xl m-2 p-3 text-center" onClick={onList}>To do List</div>
                <div className="text-xl m-2 p-3 text-center" onClick={onAdd}>Add</div>

                <div className="flex flex-wrap w-full">
                    <Outlet />
                </div>
            </div>
        </IndexLayout>

    );
}
export default IndexPage;