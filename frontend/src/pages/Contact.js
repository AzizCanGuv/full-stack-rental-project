import React, {useState} from 'react';

import Wrapper from '../assets/wrappers/LandingPage'
import {FormRow, Logo} from "../components";
import {Link, useNavigate} from "react-router-dom";
import {toast} from "react-toastify";
import {sendTicket} from "../features/user/userSlice";
import {useDispatch, useSelector} from "react-redux";
import customFetch from "../util/axios";


const initialState = {
    firstName: '',
    lastName: '',
    email: '',
    phoneNumber:'',
    birthDate:'',
    school:'',
    gpa:'',
    content: '',
    isContact: true
};

function Contact() {

    const dispatch = useDispatch();
    const [values, setValues] = useState(initialState);
    const navigate = useNavigate();
    const [file, setFile] = useState(null);

    const handleChange = (e) => {
        const name = e.target.name;
        let value = e.target.value;

        if (name === 'gpa') {
            value = value.replace(/[^0-9.]/g, '');

            const parts = value.split('.');
            if (parts.length > 2) {
                value = `${parts[0]}.${parts[1]}`;
            }

            if (value.length > 4) {
                value = value.slice(0, 4);
            }
        } else if (name === 'phoneNumber') {
            value = value.replace(/[^0-9]/g, '');

            if (value.length > 11) {
                value = value.slice(0, 11);
            }
        } else if (name === 'school') {
            if (value.length > 25) {
                value = value.slice(0, 25);
            }
        }
        setValues({...values, [name]: value})
    };
    const toggleContact = () => {
        setValues({...values, isContact: !values.isContact})
    }

    const onSubmit = async (e) => {
        e.preventDefault();
        const {
            firstName,
            lastName,
            email,
            school,
            gpa,
            birthDate,
            phoneNumber,
            content,
            isContact,
        } = values;

        if (isContact) {
            if (!email || !firstName || !lastName || !content) {
                toast.error('Please fill out all fields')

            } else {
                dispatch(sendTicket({ firstName, lastName, email, content })).then((result) => {
                    if (result.type === "user/ticket/send/fulfilled") {
                        toast.success("Ticket Has Been Sent...");

                        setTimeout(() => {
                            window.location.reload();
                        }, 2000);
                    }
                });
            }
        } else {
            if (!birthDate || !phoneNumber || !email || !firstName || !lastName || !school || !gpa || !file) {
                toast.error('Please fill out all fields')

            } else {
                try {
                    const formData = new FormData();
                    formData.append('jobApplication', new Blob([JSON.stringify({
                        jobApplication: {
                            firstName: firstName,
                            lastName: lastName,
                            email: email,
                            school: school,
                            gpa: gpa,
                            birthDate: birthDate,
                            phoneNumber: phoneNumber
                        }
                    })], {type: 'application/json'}));
                    formData.append('file', file);

                    const res = await customFetch.post('/job-applications/add', formData, {
                        headers: {
                            'Content-Type': 'multipart/form-data'
                        },
                    });

                    if (res.status === 201) {
                        toast.success("Job Application has been sent!");

                        setTimeout(() => {
                            window.location.reload();
                        }, 2000);
                    }
                    else {
                        // Handle the response when the status code is not 201
                    }
                } catch (err) {
                    console.log('Catch', err);
                }

            }
        }

    };




    return (
        <Wrapper>
            <form onSubmit={onSubmit}>
                <nav>
                    <Logo/>
                </nav>
                <div className='container page'>
                    <div className='info'>
                        <h1>
                            Eski <span>Çamoluk</span> Otomotiv
                        </h1>
                        <p>Welcome!

                            Contact Us

                            If you would like to learn more about our company, please visit our website. Thank you. </p>
                        <Link to={"/register"} className='btn'>Login/Register</Link>
                        <Link to={"/landing"} className='btn'>Landing</Link>
                        <Link to={"/about"} className='btn'>About</Link>

                    </div>

                    <div>
                        <p>
                            {values.isContact ? 'Would you like to work with us? ' : 'Would you like to go to contact with us? '}

                            <button className={'member-btn'}
                                    type={'button'}
                                    onClick={toggleContact}

                            >{values.isContact ? 'Apply' : 'Contact'}

                            </button>
                        </p>

                        <div>
                            <br/>
                        </div>

                        <div>
                            <FormRow
                                labelText={"First Name"}
                                type={'text'}
                                name={'firstName'}
                                value={values.firstName}
                                handleChange={handleChange}

                            />

                        </div>
                        <div>
                            <FormRow
                                labelText={"Last Name"}
                                type={'text'}
                                name={'lastName'}
                                value={values.lastName}
                                handleChange={handleChange}

                            />
                        </div>
                        <div>
                            <FormRow
                                labelText={"Email"}
                                type={'text'}
                                name={'email'}
                                value={values.email}
                                handleChange={handleChange}

                            />
                            {!values.isContact &&
                                <FormRow type={'date'}
                                         labelText={"Birth Date"}
                                         name={'birthDate'}
                                         value={values.birthDate}
                                         handleChange={handleChange}/>
                            }
                            {!values.isContact &&
                                <FormRow type={'text'}
                                         labelText={"Phone Number"}
                                         name={'phoneNumber'}
                                         value={values.phoneNumber}
                                         handleChange={handleChange}/>
                            }
                            {!values.isContact &&
                                <FormRow type={'text'}
                                         labelText={"School"}
                                         name={'school'}
                                         value={values.school}
                                         handleChange={handleChange}/>
                            }
                            {!values.isContact &&
                                <FormRow type={'text'}
                                         labelText={"GPA"}
                                         name={'gpa'}
                                         value={values.gpa}
                                         handleChange={handleChange}/>
                            }
                            {!values.isContact &&
                                <div className="file-input">
                                    <input type="file" accept="application/pdf" id="file"
                                           onChange={(e) => setFile(e.target.files[0])}/>
                                </div>
                            }
                        </div>
                        {values.isContact &&
                        <div>
                            Content :

                            <textarea name="content" cols="60" rows="5" className='form-input' maxLength={250}
                                      style={{
                                          width: "100%",
                                          height: "100%",
                                          padding: "15px",
                                          borderRadius: "5px",
                                          outline: "none",
                                          resize: "none"
                                      }}
                                      value={values.content}
                                      onChange={handleChange}
                            ></textarea>

                        </div>}
                        <button className='btn btn-block' type='submit' onSubmit={onSubmit}>
                            {values.isContact ? 'Send Mail' : 'Send Job Application'}
                        </button>
                    </div>

                    <div className='social-media'>
                        <h4>Follow us on social media:</h4>
                        <div className='social-icons'>
                            <a
                                href='https://www.facebook.com/ogulturkk'
                                target='_blank'
                                rel='noopener noreferrer'
                            >
                                <img src='https://upload.wikimedia.org/wikipedia/commons/5/51/Facebook_f_logo_%282019%29.svg' alt='Facebook' style={{marginRight: '10px', width: '50px', height: '50px' }} />
                            </a>
                            <a
                                href='https://www.twitter.com/ogulturka'
                                target='_blank'
                                rel='noopener noreferrer'
                            >

                                <img src='https://upload.wikimedia.org/wikipedia/commons/6/6f/Logo_of_Twitter.svg' alt='Twitter' style={{marginRight: '10px', width: '50px', height: '50px' }}/>
                            </a>
                            <a
                                href='https://www.instagram.com/kkaangul'
                                target='_blank'
                                rel='noopener noreferrer'
                            >
                                <img src='https://upload.wikimedia.org/wikipedia/commons/9/95/Instagram_logo_2022.svg' alt='Instagram' style={{ width: '50px', height: '50px' }} />
                            </a>
                        </div>
                    </div>
                </div>
            </form>
        </Wrapper>
    );
};

export default Contact;