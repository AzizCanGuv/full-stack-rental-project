import {useState, useEffect} from 'react';
import {Logo, FormRow} from '../components';
import Wrapper from '../assets/wrappers/RegisterPage';
import {toast} from "react-toastify";
import {useDispatch, useSelector} from "react-redux";
import {loginUser, registerUser} from "../features/user/userSlice";
import {useNavigate} from "react-router-dom";
import {Popup} from "../components/Popup/Popup";
import "../components/Popup/Popup.css";
import "react-datepicker/dist/react-datepicker.css";

const initialState = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    isMember: true
};

function Register() {
    const [values, setValues] = useState(initialState);

    const dispatch = useDispatch();
    const {isLoading, user} = useSelector((store) => store.user);

    const navigate = useNavigate();
    const [open, setOpen] = useState(false);


    const text = "Welcome to Eski Çamoluk Otomotiv, a car rental service provider. By using our website application (\"Eski Çamoluk Otomotiv\"), you agree to the following terms of service. Please read these terms carefully before using our services.\n" +
        "\n" +
        "Rental Agreement\n" +
        "By renting a vehicle through our platform, you agree to the rental agreement provided by the car rental company. The rental agreement will include specific terms and conditions related to the rental period, pick-up and drop-off times and locations, additional fees and charges, insurance coverage, and other rental terms. Eski Çamoluk Otomotiv is not responsible for any disputes or issues that arise between you and the car rental company.\n" +
        "\n" +
        "Age and Driving Requirements\n" +
        "You must be at least 21 years old to rent a car through our platform. Additional age restrictions may apply depending on the rental location and type of vehicle. You must also have a valid driver's license that is issued by your country of residence and meets the requirements of the rental company. Eski Çamoluk Otomotiv reserves the right to deny rental services to any person who does not meet the age or driving requirements.\n" +
        "\n" +
        "Payment and Fees\n" +
        "You agree to pay all fees and charges associated with your rental, including the rental rate, taxes, additional charges for optional services and equipment, and any fines or penalties for violations of the rental agreement or traffic laws. Payment must be made through our platform using a valid credit card or other accepted payment method. Eski Çamoluk Otomotiv reserves the right to pre-authorize your payment method for the estimated rental charges and any additional fees.\n" +
        "\n" +
        "Insurance Coverage\n" +
        "Eski Çamoluk Otomotiv provides basic insurance coverage for all rental vehicles, but additional coverage options may be available through the rental company for an additional fee. Please review the rental agreement for details on the coverage provided and any additional options or restrictions.\n" +
        "\n" +
        "Cancellation and Refunds\n" +
        "You may cancel your rental reservation at any time, but cancellation fees may apply depending on the rental company and the timing of your cancellation. Refunds are subject to the terms and conditions of the rental agreement and the rental company's policies. Eski Çamoluk Otomotiv is not responsible for any refunds or cancellations.\n" +
        "\n" +
        "Prohibited Uses\n" +
        "You agree to use the rental vehicle only for lawful purposes and in accordance with the rental agreement and applicable laws and regulations. Prohibited uses include but are not limited to: using the vehicle for illegal activities, transporting hazardous materials, using the vehicle while under the influence of drugs or alcohol, and allowing unauthorized persons to drive the vehicle. Eski Çamoluk Otomotiv reserves the right to terminate your rental agreement and/or report any illegal activities to the appropriate authorities.\n" +
        "\n" +
        "Limitation of Liability\n" +
        "Eski Çamoluk Otomotiv is not liable for any damages, losses, or expenses incurred as a result of your use of the rental vehicle, including but not limited to: accidents, theft, damage to property or persons, and any fines or penalties imposed by law enforcement or other authorities. Eski Çamoluk Otomotiv is not responsible for any delays, cancellations, or other issues related to the rental agreement or the rental company's services.\n" +
        "\n" +
        "Indemnification\n" +
        "You agree to indemnify and hold Eski Çamoluk Otomotiv, its affiliates, and their respective officers, directors, employees, and agents, harmless from and against any claims, liabilities, damages, losses, and expenses, including reasonable attorneys' fees, arising out of or in any way connected with your use of the rental vehicle or your breach";
    const [accepted, setAccepted] = useState(false);

    function handleAccept() {
        setAccepted(true);
        setOpen(false)
    }

    function handleReject() {
        setAccepted(false);
        setOpen(false)
    }

    const handleChange = (e) => {
        const name = e.target.name;
        let value = e.target.type === 'date' ? new Date(e.target.value).toISOString() : e.target.value;

        if (name === 'identityNumber' || name === 'phoneNumber') {
            // Remove any non-digit characters from the value
            value = value.replace(/\D/g, '');

            const maxLength = name === 'identityNumber' ? 12 : 11;
            value = value.slice(0, maxLength);
        }
        console.log(`${name}: ${value}`);
        setValues({...values, [name]: value});
    };

    const onSubmit = (e) => {
        e.preventDefault();
        const {
            firstName,
            lastName,
            identityNumber,
            birthDate,
            phoneNumber,
            driverLicense,
            email,
            password,
            isMember
        } = values;
        if (!email || !password || (!isMember && (!firstName || !lastName))) {
            toast.error('Please fill out all fields')
            return;
        }
        if (accepted === false && !isMember) {
            toast.error('Please accept the terms of service!')
            return;
        }


        if (isMember) {
            dispatch(loginUser({email: email, password: password}))
            return;
        }
        dispatch(registerUser({
            firstName,
            lastName,
            identityNumber,
            birthDate,
            phoneNumber,
            driverLicense,
            email,
            password
        }))
    };

    const toggleMember = () => {
        setValues({...values, isMember: !values.isMember})
    }
    const navigateToForgotPassword = () => {
        navigate("/forgot-password")
    }


    useEffect(() => {
        if (user) {
            setTimeout(() => {
                navigate('/')
            }, 2000)
        }
    }, [user])


    return (
        <Wrapper className='full-page'>
            <form className='form' onSubmit={onSubmit}>
                <Logo/>
                <h3>{values.isMember ? 'Login' : 'Register'}</h3>

                {!values.isMember &&
                    <FormRow type={'text'}
                             labelText={"First Name"}
                             name={'firstName'}
                             value={values.firstName}
                             handleChange={handleChange}/>
                }
                {!values.isMember &&
                    <FormRow type={'text'}
                             labelText={"Last Name"}
                             name={'lastName'}
                             value={values.lastName}
                             handleChange={handleChange}/>
                }
                {!values.isMember &&
                    <FormRow type={'text'}
                             labelText={"Identity Number"}
                             name={'identityNumber'}
                             value={values.identityNumber}
                             handleChange={handleChange}/>
                }
                {!values.isMember &&
                    <FormRow type={'date'}
                             labelText={"Birth Date"}
                             name={'birthDate'}
                             value={values.birthDate}
                             handleChange={handleChange}/>
                }
                {!values.isMember &&
                    <FormRow type={'text'}
                             labelText={"Phone Number"}
                             name={'phoneNumber'}
                             value={values.phoneNumber}
                             handleChange={handleChange}/>
                }
                {!values.isMember &&
                    <FormRow type={'text'}
                             labelText={"Driver License"}
                             name={'driverLicense'}
                             value={values.driverLicense}
                             handleChange={handleChange}/>
                }
                <FormRow type={'email'}
                         labelText={"Email"}
                         name={'email'}
                         value={values.email}
                         handleChange={handleChange}/>
                <FormRow type={'password'}
                         labelText={"Password"}
                         name={'password'}
                         value={values.password}
                         handleChange={handleChange}/>
                {!values.isMember &&
                    <div>
                        <button className={'btn btn-danger'} type={"button"} onClick={() => setOpen(!open)}> Our Terms
                            Of Services
                        </button>
                        {open ? <Popup onAccept={handleAccept} onReject={handleReject} text={text}
                                       closePopup={() => setOpen(false)}/> : null}
                    </div>
                }

                {!values.isMember &&
                    <button type='submit' className='btn btn-block' disabled={isLoading}>
                        {isLoading ? 'loading...' : 'submit'}
                    </button>
                }
                {values.isMember &&
                    <button type='submit' className='btn btn-block' disabled={isLoading}>
                        {isLoading ? 'loading...' : 'submit'}
                    </button>

                }
                <p>
                    {values.isMember ? 'Not a member yet?' : 'Already a member?'}

                    <button className={'member-btn'}
                            type={'button'}
                            onClick={toggleMember}

                    >{values.isMember ? 'Register' : 'Login'}

                    </button>
                </p>
                {values.isMember && <p>
                    <button className={'member-btn'}
                            type={'button'}
                            onClick={navigateToForgotPassword}
                    > Forgot password
                    </button>
                </p>
                }
            </form>
        </Wrapper>
    );
}

export default Register;