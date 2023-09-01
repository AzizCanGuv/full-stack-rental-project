import customFetch, {checkForUnauthorizedResponse} from '../../util/axios';
import {getUserFromLocalStorage} from "../../util/localStorage";


export const getAllCarsThunk = async (_, thunkAPI) => {
    const { page, search, transmissionType, sort, pageSize } =
        thunkAPI.getState().allCars;

    let url = `/cars/getAll/test?transmissionType=${transmissionType}&sortBy=${sort}&pageNo=${page}&pageSize=${pageSize}`;
    if (search) {
        url = url + `&search=${search}`;
    }
    const headers = {
        Authorization: 'Bearer ' + getUserFromLocalStorage().token,
    };
    try {
        const resp = await customFetch.get(url, { headers });
        const { content, totalElements, totalPages } = resp.data;
        return {
            cars: content.map((car) => ({
                carId: car.carId,
                brandName: car.brandName,
                colorName: car.colorName,
                description: car.description,
                dailyRentPrice: car.dailyRentPrice,
                enginePower: car.enginePower,
                year: car.year,
                location: car.location,
                createdAt: car.createdAt,
                transmissionType: car.transmissionType,
                isBooked: car.isBooked,
                carImageId: car.carImageId
            })),
            isLoading: false,
            totalCars: totalElements,
            totalPages: totalPages,
            page: page,
            pageSize: pageSize,
            stats: {},
        };
    } catch (error) {
        return checkForUnauthorizedResponse(error, thunkAPI);
    }
};

export const showStatsThunk = async (_, thunkAPI) => {

    const headers = {
        Authorization: 'Bearer ' + getUserFromLocalStorage().token,
    };

    try {
        const resp = await customFetch.get('/result', {headers});
        return resp.data;
    } catch (error) {
        return checkForUnauthorizedResponse(error, thunkAPI);
    }
};