import customFetch from "../util/axios";

export default class colorService{
    getColors(token){
        try {const resp = customFetch.get(`/colors`, {
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