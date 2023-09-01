
import axios from "axios"
import customFetch from "../util/axios";
import {logoutUser} from "../features/user/userSlice";

export default class carService{
    getCars(token, currentPage, pageSize){
        const resp = customFetch.get(`/cars/getAll?pageNo=${currentPage}&pageSize=${pageSize}`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return resp;
    }
    getCarById(id, token){
        try {const resp = customFetch.get(`/cars/getById/${id}`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        return resp;
        } catch (error){
            if (error.response.status === 403) {
               // thunkAPI.dispatch(logoutUser());

            }

        }
    }
    deleteCar(id, token){
        try {const resp = customFetch.delete(`/cars/delete/${id}`, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
            return resp;
        } catch (error){
            if (error.response.status === 403) {
                // thunkAPI.dispatch(logoutUser());

            }

        }
    }
    updateCar(id, token, body){
        try {const resp = customFetch.put(`/cars/edit/${id}`, body, {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
            console.log("Normal "+JSON.stringify(resp.data))
            return resp;
        } catch (error){
            if (error.response.status === 403) {
                // thunkAPI.dispatch(logoutUser());

            }
            console.log("Normal "+JSON.stringify(error.response))
        }

    }
}