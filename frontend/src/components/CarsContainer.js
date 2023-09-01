import { useEffect } from 'react';
import Car from './Car';
import Wrapper from '../assets/wrappers/CarsContainer';
import { useSelector, useDispatch } from 'react-redux';
import Loading from './Loading';
import {getAllCars} from '../features/car/allCarsSlice';
import PageBtnContainer from './PageBtnContainer';
const CarsContainer = () => {
    const {
        cars,
        isLoading,
        page,
        totalCars,
        totalPages,
        search,
        transmissionType,
        sort,
    } = useSelector((store) => store.allCars);
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(getAllCars());
    }, [page, search, transmissionType, sort]);

    if (isLoading) {
        return <Loading />;
    }

    if (cars.length === 0) {
        return (
            <Wrapper>
                <h2>No cars to display...</h2>
            </Wrapper>
        );
    }

    return (
        <Wrapper>
            <h5>
                {totalCars} car{totalCars !== 1 && 's'} found
            </h5>
            <div className='cars'>
                {cars.map((car) => (
                    <Car key={car.carId} {...car} />
                ))}
            </div>
            {totalPages > 1 && <PageBtnContainer />}
        </Wrapper>
    );
};

export default CarsContainer;