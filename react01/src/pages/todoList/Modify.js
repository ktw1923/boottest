import { useNavigate } from "react-router-dom";

const Modify = ({ todonum }) => {

  const nav = useNavigate()

  const moveRead = ((todonum) => {
    nav({ pathname: `/todo/read/${todonum}` })
  })


  const moveToList = () => {

    nav({pathname:`/todo/list`})

  }
  return (

    <div className="text-2xl font-bold">
        Modify
    </div>


  );
}
export default Modify;