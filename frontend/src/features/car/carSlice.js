import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { toast } from 'react-toastify';
import { getUserFromLocalStorage } from '../../util/localStorage';
import {createCarThunk, deleteCarThunk, editCarThunk, updateCarImageThunk} from './carThunk';
import {updateUserProfilePictureThunk} from "../user/userThunk";
import {logoutUser} from "../user/userSlice";
import {hideLoading} from "./allCarsSlice";
import {checkForUnauthorizedResponse} from "../../util/axios";
const initialState = {
    isLoading: false,
    location: '',
    transmissionTypeOptions: ['MANUAL'],
    carType: 'AUTOMATIC',
    isEditing: false,
    editCarId: '',
};

export const updateCarImage = createAsyncThunk(
    'car/updateCarImage',
    async ({formData,carId}, thunkAPI) => {
        try {
            const response = await updateCarImageThunk(
                `/cars/car-image/${carId}`,
                formData,
                thunkAPI
            );
            return response.data;
        } catch (error) {
            thunkAPI.dispatch(hideLoading());
            return checkForUnauthorizedResponse(error, thunkAPI);
        }
    }
);
export const createCar = createAsyncThunk('car/createCar', createCarThunk);

export const deleteCar = createAsyncThunk('car/deleteCar', deleteCarThunk);

export const editCar = createAsyncThunk('car/editCar', editCarThunk);

const carSlice = createSlice({
    name: 'car',
    initialState,
    reducers: {
        handleChange: (state, { payload: { name, value } }) => {
            state[name] = value;
        },
        clearValues: () => {
            return {
                ...initialState,
                carLocation: getUserFromLocalStorage()?.location || '',
            };
        },
        setEditCar: (state, { payload }) => {
            return { ...state, isEditing: true, ...payload };
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(createCar.pending, (state) => {
                state.isLoading = true;
            })
            .addCase(createCar.fulfilled, (state) => {
                state.isLoading = false;
                toast.success('CarDetail Created');
            })
            .addCase(createCar.rejected, (state, { payload }) => {
                state.isLoading = false;
                toast.error(payload);
            })
            .addCase(deleteCar.fulfilled, (state, { payload }) => {
                toast.success(payload);
            })
            .addCase(deleteCar.rejected, (state, { payload }) => {
                toast.error(payload);
            })
            .addCase(editCar.pending, (state) => {
                state.isLoading = true;
            })
            .addCase(editCar.fulfilled, (state) => {
                state.isLoading = false;
                toast.success('CarDetail Modified...');
            })
            .addCase(editCar.rejected, (state, { payload }) => {
                state.isLoading = false;
                toast.error(payload);
            });
    },
});

export const { handleChange, clearValues, setEditCar } = carSlice.actions;

export default carSlice.reducer;