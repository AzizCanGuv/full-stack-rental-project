import "./newCar.css";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {toast} from "react-toastify";
import {FormRow} from "../../../components";
import customFetch from "../../../util/axios";
import BrandService from "../../../services/brandService";
import colorService from "../../../services/colorService";

export default function NewCar() {

    const [brandName, setBrandName] = useState('');
    const [colorName, setColorName] = useState('');
    const [description, setDescription] = useState('');
    const [dailyRentPrice, setDailyRentPrice] = useState(1);
    const [enginePower, setEnginePower] = useState('');
    const [year, setYear] = useState('');
    const [location, setLocation] = useState('');
    const [transmissionType, setTransmissionType] = useState('');
    const [file, setFile] = useState(null);

    const onOptionChange = e => {
        setTransmissionType(e.target.value)
    }
    const navigate = useNavigate();
    const {isLoading, user} = useSelector((store) => store.user);

    const [brandList, setBrandList] = useState([]);
    const loadBrands = async () => {
        const brands = [
            {
                key: "",
                value: "",
            }
        ]
        const {data} = await new BrandService().getBrands(user.token);
        data.forEach((value) => {
            brands.push({
                key: value.id,
                value: value.brandName,
            });
        });
        setBrandList(brands);
    };
    useEffect(() => {
        loadBrands();
        loadColors();
    }, []);

    const [colorList, setColorList] = useState([]);
    const loadColors = async () => {
        const colors = [
            {
                key: "",
                value: "",
            }
        ]
        const {data} = await new colorService().getColors(user.token);
        data.forEach((v) => {
            colors.push({
                key: v.id,
                value: v.colorName,
            });
        });
        setColorList(colors);
    };


    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!brandName || !colorName || !dailyRentPrice || !enginePower || !year || !location || !transmissionType || !file) {
            toast.error('Please Fill Out All Fields');
            return;
        }

        if (
            !brandName ||
            !colorName ||
            isNaN(parseFloat(dailyRentPrice)) ||
            isNaN(parseInt(enginePower)) ||
            isNaN(parseInt(year)) ||
            !location ||
            !transmissionType ||
            !file
        ) {
            toast.error('Please Fill Out All Fields Correctly');
            return;
        }
        try {
            const formData = new FormData();
            formData.append('car', new Blob([JSON.stringify({
                car: {
                    brandName: brandName,
                    colorName: colorName,
                    description: description,
                    dailyRentPrice: dailyRentPrice,
                    enginePower: enginePower,
                    year: year,
                    location: location,
                    transmissionType: transmissionType
                }
            })], {type: 'application/json'}));
            formData.append('file', file);

            const res = await customFetch.post('/cars/add', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                    'Authorization': `Bearer ${user.token}`,
                },
            });

            if (res.status === 201) {
                navigate('/all-cars');
            } else {
                // Handle the response when the status code is not 201
            }
        } catch (err) {
            console.log('Catch', err);
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
            setDailyRentPrice("");
        }
        else {
            setDailyRentPrice(formattedValue);
        }
    };

    const currentYear = new Date().getFullYear();

    const handleYearChange = (e) => {
        const inputYear = parseInt(e.target.value);
        const yearString = e.target.value;
        if (isNaN(inputYear) ) {
            setYear("");
            toast.error("Year must be a valid 4-digit number");
        }else if(yearString.length === 4 && inputYear < 1930){
            setYear("");
            toast.error("Year must be number greater than or equal to 1930");
        } else if (inputYear > currentYear) {
            setYear("");
            toast.error("Year cannot be greater than the current year");
        }  else{
            setYear(inputYear.toString());
        }
    };


    return (
        <div className="newCar">
            <h1 className="newCarTitle">New Car</h1>
            <form className="newCarForm">
                <div className="newCarItem">

                    <label>Brand</label>
                    <select onChange={(e) => setBrandName(e.target.value)}>
                        { brandList.map((brand) => {
                            return (
                                <option key={brand.key} value={brand.value}>{brand.value}</option>
                            );

                        })}
                    </select>
                </div>
                <div className="newCarItem">
                    <label>Color</label>
                    <select onChange={(e) => setColorName(e.target.value)}>
                        { colorList.map((color) => {
                            return (
                                <option key={color.key} value={color.value}>{color.value}</option>
                            );

                        })}
                    </select>

                </div>
                <div className="newCarItem">
                    <label>Daily Price</label>
                    <input
                        value={dailyRentPrice}
                        onChange={(e) => setDailyRentPriceFormatted(e.target.value)}
                        type="text"
                        placeholder="150"
                        maxLength={7}
                    />
                </div>
                <div className="newCarItem">
                    <label>Engine Power</label>
                    <input
                        value={enginePower}
                        onChange={(e) => setEnginePower(e.target.value.replace(/[^0-9]/g, '').slice(0, 4))}
                        type="text"
                        placeholder="512"
                        maxLength={4}
                    />
                </div>
                <div className="newCarItem">
                    <label>Year</label>
                    <input
                        value={year}
                        onChange={handleYearChange}
                        type="text"
                        placeholder="2021"
                        maxLength={4}
                    />
                </div>
                <div className="newCarItem">
                    <label>Location</label>
                    <input
                        value={location}
                        onChange={(e) => setLocation(e.target.value.slice(0, 25))}
                        type="text"
                        placeholder="New York | USA"
                        maxLength={25}
                    />
                </div>
                <div className="newCarItem">
                    <label>Description</label>
                    <input
                        value={description}
                        onChange={(e) => setDescription(e.target.value.slice(0, 70))}
                        type="text"
                        placeholder="Fast Red Car eg."
                        maxLength={70}
                    />
                </div>
                <div className="newCarItem">
                    <label>Transmission Type</label>
                    <div className="newCarGender">
                        <input checked={transmissionType === "AUTOMATIC"} onChange={onOptionChange} type="radio"
                               name="transmissionType" id="automatic" value="AUTOMATIC"/>
                        <label for="automatic">Automatic</label>
                        <input checked={transmissionType === "MANUAL"} onChange={onOptionChange} type="radio"
                               name="transmissionType" id="manual" value="MANUAL"/>
                        <label for="manual">Manual</label>
                    </div>
                </div>
                <div className="newCarItem">
                    <label>Car Image</label>
                    <div className="file-input">
                        <input type="file" accept="image/png, image/jpeg" id="file"
                               onChange={(e) => setFile(e.target.files[0])}/>
                    </div>
                </div>

                <div className="newCarItem">
                    <button onClick={handleSubmit} className="newCarButton">Create</button>
                </div>

            </form>
        </div>
    );
}