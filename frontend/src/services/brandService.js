import customFetch from "../util/axios";

export default class brandService{
    getBrands(token){
        try {const resp = customFetch.get(`/brands`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
            return resp;
        } catch(error){
            if (error.response.status === 403) {
            }
        }
    }
}