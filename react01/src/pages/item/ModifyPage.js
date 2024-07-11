import {useParams} from "react-router-dom";
import ModifyComponent from "../../components/item/ModifyComponent";

const ModifyPage = () => {

    const{pnum} = useParams();


    return (
        <div className="w-full white">
            {<ModifyComponent pnum={pnum}></ModifyComponent>}
        </div>
    )
}

export default ModifyPage;