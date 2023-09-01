import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {showStats} from "../features/car/allCarsSlice";
import {ChartsContainer, StatsContainer} from "../components";
const Stats = () => {
    const { isLoading, results } = useSelector(
        (store) => store.allCars
    );

    const dispatch = useDispatch();
    useEffect(() => {
        dispatch(showStats());
    }, []);
    return (
        <>
            <StatsContainer />
            {results.length > 0 && <ChartsContainer />}
        </>
    );
};
export default Stats;