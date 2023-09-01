import { FormRow } from '.';
import Wrapper from '../assets/wrappers/SearchContainer';
import { useSelector, useDispatch } from 'react-redux';
import { handleChange, clearFilters } from '../features/car/allCarsSlice';
import { useState, useMemo } from 'react';
import FormRowSelect from "./FormRowSelect";

const SearchContainer = () => {
    const [localSearch, setLocalSearch] = useState('');

    const { isLoading, search, searchStatus, searchType, sort, sortOptions } =
        useSelector((store) => store.allCars);

    const { transmissionTypeOptions, statusOptions } = useSelector((store) => store.car);

    const dispatch = useDispatch();

    const handleSearch = (e) => {
        dispatch(handleChange({ name: e.target.name, value: e.target.value }));
    };

    const debounce = () => {
        let timeoutID;
        return (e) => {
            setLocalSearch(e.target.value);
            clearTimeout(timeoutID);
            timeoutID = setTimeout(() => {
                dispatch(handleChange({ name: e.target.name, value: e.target.value }));
            }, 1000);
        };
    };
    const optimizedDebounce = useMemo(() => debounce(), []);

    const handleSubmit = (e) => {
        e.preventDefault();
        setLocalSearch('');
        dispatch(clearFilters());
    };

    return (
        <Wrapper>
            <form className='form'>
                <h4>search form</h4>
                <div className='form-center'>
                    <FormRow
                        type='text'
                        name='search'
                        value={localSearch}
                        handleChange={optimizedDebounce}
                    />
                    <FormRowSelect
                        labelText='transmission type'
                        name='transmissionType'
                        value={searchType}
                        handleChange={handleSearch}
                        list={['AUTOMATIC', ...transmissionTypeOptions]}
                    />
                    <FormRowSelect
                        name='sort'
                        value={sort}
                        handleChange={handleSearch}
                        list={sortOptions}
                    />
                    <button
                        className='btn btn-block btn-danger'
                        disabled={isLoading}
                        onClick={handleSubmit}
                    >
                        clear filters
                    </button>
                </div>
            </form>
        </Wrapper>
    );
};
export default SearchContainer;