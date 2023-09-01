import customFetch from "../util/axios";
import {toast} from "react-toastify";

export default class userService {
    getAllUsers(token) {
        const resp = customFetch.get(`/auth`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return resp;
    }

    changeUserRole(id, requestBody, token) {
        return customFetch.put(`/auth/role/${id}`, requestBody, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then(resp => {
                return resp;
            })
            .catch(error => {
                if (error.response && error.response.status === 400) {
                    console.log(error.response.data.message);
                    toast.error(error.response.data.message);
                } else if (error.response && error.response.status === 403) {
                    toast.error("You have authorization problem");
                }
            });
    }

    deleteUser(id, token) {
        customFetch.delete(`/auth/delete/${id}`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then(resp => {
                return resp;
            })
            .catch(error => {
                if (error.response && error.response.status === 400) {
                    console.log(error.response.data.message);
                    toast.error(error.response.data.message);
                } else if (error.response && error.response.status === 403) {
                    toast.error("You have authorization problem");
                }
            });
    }
}