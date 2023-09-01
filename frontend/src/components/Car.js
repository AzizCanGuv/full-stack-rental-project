import {FaLocationArrow, FaBriefcase, FaCalendarAlt, FaCommentDollar, FaDollarSign} from 'react-icons/fa';
import {GiGearStickPattern} from 'react-icons/gi';
import {Link, useNavigate} from 'react-router-dom';
import Wrapper from '../assets/wrappers/Car';
import {useDispatch, useSelector} from 'react-redux';
import CarInfo from './CarInfo';
import moment from 'moment';
import {deleteCar, setEditCar} from '../features/car/carSlice';

const Car = ({
                 carId,
                 brandName,
                 colorName,
                 description,
                 dailyRentPrice,
                 enginePower,
                 year,
                 location,
                 createdAt,
                 transmissionType,
                 isBooked,
                 history,
    carImageId
             }) => {
    const dispatch = useDispatch();
    const {isLoading, user} = useSelector((store) => store.user);


    const navigate = useNavigate();
    const date = moment(createdAt).format('MMM Do, YYYY');


    return (
        <Wrapper>
            <header>
                <img src={carImageId ? carImageId : 'https://img.freepik.com/free-vector/modern-blue-urban-adventure-suv-vehicle-illustration_1344-205.jpg?w=2000'} alt="Main Icon" className="main-icon" />
                <div className='info'>
                    <h5>{brandName}</h5>
                    <p>{colorName}</p>
                </div>
            </header>
            <div className='content'>
                <div className='content-center'>
                    <CarInfo icon={<FaLocationArrow/>} text={location}/>
                    <CarInfo icon={<FaCalendarAlt/>} text={date}/>
                    <CarInfo icon={<FaDollarSign/>} text={dailyRentPrice}/>
                    <CarInfo icon={<GiGearStickPattern/>} text={transmissionType}/>
                </div>
                <footer>
                    <div className='actions'>
                        {user.role !== 'USER' ? (
                            <Link className='btn edit-btn' to={`/car-details/${carId}`}>
                                Edit
                            </Link>
                        ) : (
                            <Link to={`/car-details/${carId}`} className='btn edit-btn'>
                                Car Details
                            </Link>
                        )}
                        {user.role !== 'USER' && <button
                            type='button'
                            className='btn delete-btn'
                            onClick={() => dispatch(deleteCar(carId))}
                        >
                            delete
                        </button>}
                    </div>
                </footer>
            </div>
        </Wrapper>
    );
};
export default Car;