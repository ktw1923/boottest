import axios from "axios";

export const API_SERVER = 'http://localhost:8080'

const host=`${API_SERVER}/bit/items`;

export const AsyncAdd = async (item) => {
    const header = {
        headers : {
            "Content-Type": "multipart/form-data"
        }
    }

    const res = await axios.post(`${host}/`, item, header);

    return res.data;
}

export const getList= async (pageParam) => {
    const {page, size} = pageParam;

    const res = await axios.get(`${host}/list`,{
        params: {page:page, size:size},     //http://localhost:8080/bit/items/list/page=2&size=10
    })

    return res.data;
}

export const getOne = async (pnum) => {
    const res = await axios.get(`${host}/${pnum}`);
    return res.data;
}

export const modifyOne = async (pnum, item) => {

    const header = { headers: { "Content-Type": "multipart/form-data" } };
    const res = await axios.put(`${host}/${pnum}`, item, header);

    return res;
}

export const deleteOne = async (pnum) => {
    const res = await axios.delete(`${host}/${pnum}`);
    return res.data;
}