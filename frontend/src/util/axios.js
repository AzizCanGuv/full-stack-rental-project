import axios from "axios";
import {clearStore} from "../features/user/userSlice";

const customFetch = axios.create({

    //Basically If you want to request to a BE service which is running on CLOUD baseURL: 'http://test-camolug.us-east-1.elasticbeanstalk.com/'
    //If you want to request to a BE service which is running on localhost:5000 paste below baseURL: 'http://localhost:5000/'
    baseURL: 'http://localhost:5000/'
    //"http://localhost:5000/" - DEV Environment
})

export const checkForUnauthorizedResponse = (error, thunkAPI) => {
    if (error.response.status === 403) {
        thunkAPI.dispatch(clearStore());
        return thunkAPI.rejectWithValue('Unauthorized! Logging Out...');
    }
    return thunkAPI.rejectWithValue(error.response.data.msg);
};

export default customFetch;