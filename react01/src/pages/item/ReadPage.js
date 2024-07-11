import ReadComponent from "../../components/item/ReadComponent";
import {useParams} from "react-router-dom";

const ReadPage = () => {

    const {pnum} = useParams();

    return (
        <div className="w-full white">
            {<ReadComponent pnum={pnum}></ReadComponent>}
        </div>
    )
}

export default ReadPage;