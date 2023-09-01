import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { toast } from 'react-toastify';
import { getAllCarsThunk, showStatsThunk } from './allCarsThunk';

const initialFiltersState = {
    search: '',
    transmissionType: 'AUTOMATIC',
    sort: 'createdAt',
    sortOptions: ['createdAt', 'dailyRentPrice'],
};

const initialState = {
    isLoading: true,
    cars: [],
    totalCars: 0,
    totalPages: 1,
    page: 0,
    pageSize: 10,
    stats: {},
    results: [],
    ...initialFiltersState,
};

export const getAllCars = createAsyncThunk('allCars/getCars', getAllCarsThunk);

export const showStats = createAsyncThunk('allCars/showStats', showStatsThunk);

const allCarsSlice = createSlice({
    name: 'allCars',
    initialState,
    reducers: {
        showLoading: (state) => {
            state.isLoading = true;
        },
        hideLoading: (state) => {
            state.isLoading = false;
        },
        handleChange: (state, { payload: { name, value } }) => {
            state.page = 0;
            state[name] = value;
        },
        clearFilters: (state) => {
            return { ...state, ...initialFiltersState };
        },
        changePage: (state, { payload }) => {
            state.page = payload;
        },
        clearAllCarsState: (state) => initialState,
    },
    extraReducers: (builder) => {
        builder
            .addCase(getAllCars.pending, (state) => {
                state.isLoading = true;
            })
            .addCase(getAllCars.fulfilled, (state, { payload }) => {
                state.isLoading = false;
                state.cars = payload.cars;
                state.totalPages = payload.totalPages;
                state.totalCars = payload.totalCars;
            })
            .addCase(getAllCars.rejected, (state, { payload }) => {
                state.isLoading = false;
                toast.error(payload);
            })
            .addCase(showStats.pending, (state) => {
                state.isLoading = true;
            })
            .addCase(showStats.fulfilled, (state, { payload }) => {
                state.isLoading = false;
                state.stats = payload.defaultStats;
                state.results = payload.results;
            })
            .addCase(showStats.rejected, (state, { payload }) => {
                state.isLoading = false;
                toast.error(payload);
            });
    },
});

export const {
    showLoading,
    hideLoading,
    handleChange,
    clearFilters,
    changePage,
    clearAllCarsState,
} = allCarsSlice.actions;

export default allCarsSlice.reducer;