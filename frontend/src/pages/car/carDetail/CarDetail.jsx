import {Link, useNavigate, useParams} from "react-router-dom";
import "./carDetail.css";
import {
    BoltOutlined,
    CalendarToday, DescriptionOutlined,
    LocationSearching,
    PaidOutlined,
    Publish, ScheduleOutlined, WbAutoOutlined
} from "@mui/icons-material";
import React, {useEffect, useRef, useState} from "react";
import axios from "axios";
import {Icon} from "@mui/material";
import CarService from "../../../services/carService";
import {useDispatch, useSelector} from "react-redux";
import {format} from "date-fns";
import {DateRange} from "react-date-range";
import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';
import {toast} from "react-toastify";
import customFetch from "../../../util/axios";
import {updateUserProfilePicture} from "../../../features/user/userSlice";
import {updateCarImage} from "../../../features/car/carSlice";

export default function CarDetail() {
    const [updateLocation, setUpdateLocation] = useState('');
    const [updateDailyRentPrice, setUpdateDailyRentPrice] = useState('');
    const [updateDescription, setUpdateDescription] = useState('');


    const [car, setCar] = useState({
        brandName: "",
        colorName: "",
        transmissionType: "",
        enginePower: "",
        location: "",
        dailyRentPrice: "",
        createdAt: "",
        carImageId: ""
    });
    const onInputChange = (e) => {
        setCar({...car, [e.target.name]: e.target.value});
    };
    const {carId} = useParams();

    useEffect(() => {
        loadCar();
    }, []);

    const {brandName, colorName, transmissionType, enginePower, location, dailyRentPrice} = car;
    const loadCar = async () => {
        const result = await new CarService().getCarById(carId, user.token);
        const carData = result.data;
        carData.createdAt = format(new Date(carData.createdAt), "MMM do, yyyy");
        setCar(carData);
    };
    const {isLoading, user} = useSelector((store) => store.user);
    const dispatch = useDispatch();

    const handleCarPictureSubmit = (e) => {
        e.preventDefault();
        const file = e.target.files[0];
        if (!file) {
            return;
        }
        const formData = new FormData();
        formData.append('file', file);
        dispatch(updateCarImage({formData, carId}));
    };


    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            let res = await customFetch.put(`cars/edit/${carId}`, {
                description: updateDescription,
                dailyRentPrice: updateDailyRentPrice,
                location: updateLocation,
            }, {
                headers: {
                    'Authorization': `Bearer ${user.token}`,
                }
            });
            const status = res.status;
            if (status === 200) {
                loadCar()
                toast.success("Car Updated!")
            } else {
                // handle errors
            }
        } catch (err) {
            console.log("Catch" + err);
        }
    };
    const setDailyRentPriceFormatted = (value) => {
        const cleanedValue = value.replace(/[^0-9.]/g, "");
        const parts = cleanedValue.split(".");

        let formattedValue = "";

        if (parts.length === 1) {
            formattedValue = parts[0];
        } else if (parts.length === 2) {
            const wholeNumber = parts[0];
            const decimalPart = parts[1].slice(0, 2);
            formattedValue = `${wholeNumber}.${decimalPart}`;
        } else {
            formattedValue = `${parts[0]}.${parts[1].slice(0, 2)}`;
        }

        // Check if the formattedValue is only a decimal part
        if (formattedValue.startsWith(".")) {
            setUpdateDailyRentPrice("");
        } else {
            setUpdateDailyRentPrice(formattedValue);
        }
    };
    return (
        <div className="car">
            <div className="carTitleContainer">
                <h1 className="carTitle">{user.role === 'USER' ? <p>Car Details</p> : <p>Edit Car</p>}</h1>
                {user.role !== 'USER' && <Link to="/add-car">
                    <button className="carAddButton">Create</button>
                </Link>}
            </div>
            <div className="carContainer">
                <div className="carShow">
                    <div className="carShowTop">
                        <img
                            src={car.carImageId ? car.carImageId : 'https://img.freepik.com/free-vector/modern-blue-urban-adventure-suv-vehicle-illustration_1344-205.jpg?w=2000'}
                            alt=""
                            className="carShowImg"
                        />
                        <div className="carShowTopTitle">
                            <span className="carShowUsername">{car.brandName}</span>
                            <span className="carShowUserTitle">{car.colorName}</span>
                        </div>
                    </div>
                    <div className="carShowBottom">
                        <span className="carShowTitle">Car Details</span>
                        <div className="carShowInfo">
                            <WbAutoOutlined className="carShowIcon"/>
                            <div>
                                <h7>Transmission Type:</h7>
                                <span className="carShowInfoTitle">{car.transmissionType}
                                </span>
                            </div>
                        </div>
                        <div className="carShowInfo">
                            <CalendarToday className="carShowIcon"/>
                            <div>
                                <h7> Added :</h7>
                                <span className="carShowInfoTitle">{car.createdAt}
                                </span>
                            </div>
                        </div>
                        <span className="carShowTitle">Other Details</span>
                        <div className="carShowInfo">
                            <BoltOutlined className="carShowIcon"/>
                            <div>
                                <h7> Engine Power:</h7>
                                <span className="carShowInfoTitle">{car.enginePower}
                                </span>
                            </div>
                        </div>
                        <div className="carShowInfo">
                            <ScheduleOutlined className="carShowIcon"/>
                            <div>
                                <h7> Year:</h7>
                                <span className="carShowInfoTitle">{car.year}
                                </span>
                            </div>
                        </div>
                        <div className="carShowInfo">
                            <LocationSearching className="carShowIcon"/>
                            <div>
                                <h7> Location:</h7>
                                <span className="carShowInfoTitle">{car.location}
                                </span>
                            </div>
                        </div>
                        <div className="carShowInfo">
                            <PaidOutlined className="carShowIcon"/>
                            <div>
                                <h7> Daily Rent Price:</h7>
                                <span className="carShowInfoTitle">{car.dailyRentPrice}
                                </span>
                            </div>
                        </div>
                        <div className="carShowInfo">
                            <DescriptionOutlined className="carShowIcon"/>
                            <div>
                                <h7> Description:</h7>
                                <span className="carShowInfoTitle">{car.description}
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
                {user.role === 'USER' && <div className="carUpdate">
                    <span className="carUpdateTitle">Pick-Up and Drop-Off Dates</span>
                    <form className="carUpdateForm">
                        <div className="carUpdateLeft">
                            <div className="carUpdateItem">
                                <DateRangePickerComp/>
                            </div>
                        </div>
                    </form>
                </div>}
                {user.role !== 'USER' && <div className="carUpdate">
                    <span className="carUpdateTitle">Edit</span>
                    <form className="carUpdateForm">
                        <div className="carUpdateLeft">
                            <div className="carUpdateItem">
                                <label>Location</label>
                                <input
                                    type="text"
                                    placeholder="New York | USA"
                                    className="carUpdateInput"
                                    maxLength={25}
                                    value={updateLocation} onChange={(e) => setUpdateLocation(e.target.value)}
                                />
                            </div>
                            <div className="carUpdateItem">
                                <label>Daily Price</label>
                                <input
                                    type="text"
                                    placeholder="120.4"
                                    className="carUpdateInput"
                                    maxLength={7}
                                    value={updateDailyRentPrice}
                                    onChange={(e) => setDailyRentPriceFormatted(e.target.value)}
                                />
                            </div>
                            <div className="carUpdateItem">
                                <label>Description</label>
                                <input
                                    type="text"
                                    placeholder="Description"
                                    className="carUpdateInput"
                                    maxLength={70}
                                    value={updateDescription} onChange={(e) => setUpdateDescription(e.target.value)}
                                />
                            </div>
                        </div>
                        <div className="carUpdateRight">
                            <div className="carUpdateUpload">
                                <img
                                    className="carUpdateImg"
                                    src={car.carImageId ? car.carImageId : 'https://img.freepik.com/free-vector/modern-blue-urban-adventure-suv-vehicle-illustration_1344-205.jpg?w=2000'}
                                    alt=""
                                />
                                <label htmlFor="file">
                                    <Publish className="carUpdateIcon"/>
                                </label>
                                <input onChange={handleCarPictureSubmit} type="file" accept="image/png, image/jpeg" id="file" style={{display: "none"}}/>
                            </div>
                            <button onClick={handleSubmit} className="carUpdateButton">Update</button>
                        </div>
                    </form>
                </div>}
            </div>
        </div>
    );


}

const DateRangePickerComp = () => {
    const [dateRange, setDateRange] = useState([
        {
            startDate: new Date(),
            endDate: new Date(),
            key: 'selection'
        }
    ]);
    const {carId} = useParams();
    const [disabledDates, setDisabledDates] = useState([]);
    useEffect(() => {
        const fetchDisabledDates = async () => {
            try {
                const response = await customFetch.get(`/reservation/getNonAvailableDates/${carId}`, {
                    headers: {
                        Authorization: `Bearer ${user.token}`
                    }
                });
                const disabledDatesJson = await response.data;
                const disabledDatesArr = disabledDatesJson.map(dateStr => new Date(dateStr));
                setDisabledDates(disabledDatesArr);
            } catch (err) {
                console.log('Catch' + err);
            }
        };
        fetchDisabledDates();

        document.addEventListener('keydown', hideOnEscape, true);
        document.addEventListener('click', hideOnClickOutside, true);
        return () => {
            document.removeEventListener('keydown', hideOnEscape, true);
            document.removeEventListener('click', hideOnClickOutside, true);
        };
    }, [carId]);

    const hideOnEscape = (e) => {
        if (e.key === "Escape") {
            setOpen(false)
        }
    }
    const hideOnClickOutside = (e) => {
        if (refOne.current && !refOne.current.contains(e.target)) {
            setOpen(false)
        }

    }
    const {isLoading, user} = useSelector((store) => store.user);

    const [open, setOpen] = useState(false);
    const refOne = useRef(null);
    const navigate = useNavigate();
    const handleSelect = (ranges) => {
        setDateRange([ranges.selection]);
    };
    const handleUserReservation = async (e) => {
        e.preventDefault();
        try {
            const response = await customFetch.post(`/reservation/reserve`, {
                pickupDate: dateRange[0].startDate.getTime(),
                dropOffDate: dateRange[0].endDate.getTime(),
                carId: parseInt(carId),
                userId: user.id,
            }, {
                headers: {
                    Authorization: `Bearer ${user.token}`,
                    'Content-Type': 'application/json'
                }
            });
            const data = await response.data;
            const status = response.status;
            if (status === 201) {
                const reservationId = data.reservationId;
                toast.info("You have 5 minutes to complete payment!")
                navigate("/payment/" + reservationId)
            } else {
                toast.error("Something Went Wrong!")
            }
        } catch (err) {
            console.log("Catch" + err);
        }
    };


    const isDateDisabled = (date) => {
        const disabledDate = disabledDates.find(disabledDate => {
            return (
                disabledDate.getFullYear() === date.getFullYear() &&
                disabledDate.getMonth() === date.getMonth() &&
                disabledDate.getDate() === date.getDate()
            );
        });
        return Boolean(disabledDate);
    };

    return (
        <div className={'calendarWrap'}>
            <button
                className="carSelectDateButton"
                onClick={() => setOpen(open => !open)}
                type={'button'}
            >
                Select Date
            </button>
            <br/>
            <div ref={refOne}>{open && <DateRange
                minDate={new Date()}
                ranges={dateRange}
                onChange={handleSelect}
                showMonthAndYearPickers={false}
                disabledDates={disabledDates.map(date => new Date(date))}
            />}

            </div>
            <br/>
            {!open && <label><strong>Selected
                Dates:</strong> {`${format(dateRange[0].startDate, "MM-dd-yyyy")} to ${format(dateRange[0].endDate, "MM-dd-yyyy")}`}
            </label>}
            <br/>
            <br/>
            <button onClick={handleUserReservation} className="carAddButton">Reserve</button>
        </div>
    );
};