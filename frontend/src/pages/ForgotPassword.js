import {useState, useEffect} from 'react';
import {Logo, FormRow} from '../components';
import Wrapper from '../assets/wrappers/RegisterPage';
import {toast} from "react-toastify";
import {useNavigate} from "react-router-dom";
import {forgotPassword, loginUser} from "../features/user/userSlice";
import {useDispatch} from "react-redux";

const initialState = {
    email: '',
};
function ForgotPassword() {
    const [values, setValues] = useState(initialState);
    const dispatch = useDispatch();




    const navigate = useNavigate();

    const handleChange = (e) => {
        const name = e.target.name;
        const value = e.target.value;
        console.log(`${name}: ${value}`)
        setValues({...values, [name]: value})
    };
    const onSubmit = (e) => {
        e.preventDefault();
        const {email} = values;
        if (!email) {
            toast.error('Please fill out all fields')
            return;
        }
        dispatch(forgotPassword({email: email}))
    };


    return (

        <Wrapper>
            <form className='form' onSubmit={onSubmit}>
                <Logo/>
                <p>
                    Please write your e-mail below. After clicking the "Submit" button, a password reset link will be
                    sent to you.
                </p>

                <FormRow type={'email'}
                         name={'email'}
                         value={values.email}
                         handleChange={handleChange}/>
                <button type='submit' className='btn btn-block'>
                    submit
                </button>

            </form>

        </Wrapper>


    );

}
export default ForgotPassword