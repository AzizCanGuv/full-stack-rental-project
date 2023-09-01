import {useState, useEffect} from 'react';
import {Logo, FormRow} from '../components';
import Wrapper from '../assets/wrappers/RegisterPage';
import {toast} from "react-toastify";
import {useNavigate, useParams} from "react-router-dom";
import {changePassword} from "../features/user/userSlice";
import {useDispatch} from "react-redux";

const initialState = {
    password: '',
    confirmPassword: '',

};
function ChangePassword() {
    const [values, setValues] = useState(initialState);
    const dispatch = useDispatch();



    const {email} = useParams();
    const navigate = useNavigate();

    const handleChange = (e) => {
        const name = e.target.name;
        const value = e.target.value;
        console.log(`${name}: ${value}`)
        setValues({...values, [name]: value})
    };
    const onSubmit = (e) => {
        e.preventDefault();
        const {password,confirmPassword} = values;

        if (confirmPassword != password) {
            toast.error('Your passwords do not match. Please try again !')
            return;
        }
        dispatch(changePassword({password,email}))
        navigate("/register")
    };


    return (

        <Wrapper>
            <form className='form' onSubmit={onSubmit}>
                <Logo/>
                <p>
                    Please type the password you want to change. Make sure the passwords match.
                </p>

                <FormRow type={'password'}
                         name={'password'}
                         value={values.password}
                         handleChange={handleChange}/>
                <FormRow type={'password'}
                         name={'confirmPassword'}
                         value={values.confirmPassword}
                         handleChange={handleChange}/>

                <button type='submit' className='btn btn-block'>
                    submit
                </button>

            </form>

        </Wrapper>


    );

}
export default ChangePassword