import {configureStore} from "@reduxjs/toolkit";
import userSlice from "./features/user/userSlice";
import allCarsSlice from "./features/car/allCarsSlice";
import carSlice from "./features/car/carSlice";

export const store  = configureStore({
    reducer:{
        user:userSlice,
        car: carSlice,
        allCars: allCarsSlice

    }
})


