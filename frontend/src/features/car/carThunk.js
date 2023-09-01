import {showLoading, hideLoading, getAllCars} from './allCarsSlice';
import customFetch, {checkForUnauthorizedResponse} from '../../util/axios';
import {clearValues} from './carSlice';
import {getUserFromLocalStorage} from "../../util/localStorage";
import {logoutUser} from "../user/userSlice";

export const createCarThunk = async (car, thunkAPI) => {
    try {
        const resp = await customFetch.post('/cars', car);
        thunkAPI.dispatch(clearValues());
        return resp.data.msg;
    } catch (error) {
        return checkForUnauthorizedResponse(error, thunkAPI);
    }
};
export const updateCarImageThunk = async (url, formData, thunkAPI) => {
    try {
        const response = await customFetch.post(url, formData, {
            headers: {
                Authorization: `Bearer ${thunkAPI.getState().user.user.token}`,
                'Content-Type': 'multipart/form-data',
            },
        });
        window.location.reload();
        return response;
    } catch (error) {
        thunkAPI.dispatch(hideLoading());
        return checkForUnauthorizedResponse(error, thunkAPI);
    }
};

export const deleteCarThunk = async (carId, thunkAPI) => {
    thunkAPI.dispatch(showLoading());

    const headers = {Authorization: `Bearer ` + getUserFromLocalStorage().token};
    try {
        const resp = await customFetch.delete(`/cars/delete/${carId}`, {headers});
        thunkAPI.dispatch(getAllCars());
        return resp.data.msg;
    } catch (error) {
        if (error.response.status === 400) {
            thunkAPI.dispatch(hideLoading());
            return thunkAPI.rejectWithValue(error.response.data.message);
        }
        thunkAPI.dispatch(hideLoading());
        return checkForUnauthorizedResponse(error, thunkAPI);
    }
};
export const editCarThunk = async ({carId, car}, thunkAPI) => {
    try {
        const resp = await customFetch.put(`/cars/edit/${carId}`, car);
        thunkAPI.dispatch(clearValues());
        return resp.data;
    } catch (error) {
        return checkForUnauthorizedResponse(error, thunkAPI);
    }
};
