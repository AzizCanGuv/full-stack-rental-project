import { HiChevronDoubleLeft, HiChevronDoubleRight } from 'react-icons/hi';
import Wrapper from '../assets/wrappers/PageBtnContainer';
import { useSelector, useDispatch } from 'react-redux';
import { changePage } from '../features/car/allCarsSlice';

const PageBtnContainer = () => {
    const { totalPages , page } = useSelector((store) => store.allCars);
    const dispatch = useDispatch();



    const pages = Array.from({ length: totalPages }, (_, index) => {
        return index ;

    });

    const nextPage = () => {
        let newPage = page + 1;
        if (newPage > totalPages) {
            newPage = 0;
        }
        if (totalPages  === newPage){
            newPage = 0;
        }
        dispatch(changePage(newPage));
    };
    const prevPage = () => {
        let newPage = page - 1;
        if (newPage < 0) {
            newPage = totalPages-1;
        }
        dispatch(changePage(newPage));
    };

    return (
        <Wrapper>
            <button type='button' className='prev-btn' onClick={prevPage}>
                <HiChevronDoubleLeft />
                prev
            </button>
            <div className='btn-container'>
                {pages.map((pageNumber) => {
                    return (
                        <button
                            type='button'
                            key={pageNumber}
                            className={pageNumber === page ? 'pageBtn active' : 'pageBtn'}
                            onClick={() => dispatch(changePage(pageNumber))}
                        >
                            {pageNumber}
                        </button>
                    );
                })}
            </div>
            <button type='button' className='next-btn' onClick={nextPage}>
                next
                <HiChevronDoubleRight />
            </button>
        </Wrapper>
    );
};
export default PageBtnContainer;