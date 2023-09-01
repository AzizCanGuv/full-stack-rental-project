import React, {useEffect, useState} from 'react';
import {FormRow} from '../components';
import Wrapper from '../assets/wrappers/DashboardFormPage';
import {useDispatch, useSelector} from 'react-redux';
import {toast} from 'react-toastify';
import { updateUser, updateUserProfilePicture} from '../features/user/userSlice';

import {LocalPhoneOutlined,
    DocumentScannerOutlined, Publish,
    EmailOutlined
} from "@mui/icons-material";
import customFetch from "../util/axios";

const Profile = () => {
    const {isLoading, user} = useSelector((store) => store.user);
    const dispatch = useDispatch();
    const [profilePictureUrl, setProfilePictureUrl] = useState('');



    const [userUpdateData, setUserUpdateDataData] = useState({
        newPassword: '',
        phoneNumber: user?.phoneNumber || '',
        driverLicense: user?.driverLicense || '',
    })
    const [userData, setUserData] = useState({
        id: user?.id || '',
        lastName: user?.lastName || '',
        name: user?.name || '',
        role: user?.role || '',
        password: user?.password || '',
        phoneNumber: user?.phoneNumber || '',
        driverLicense: user?.driverLicense || '',
        email: user?.email || ''
    })

    const handleProfilePictureSubmit = (e) => {
        e.preventDefault();
        const file = e.target.files[0];
        if (!file) {
            return;
        }
        const formData = new FormData();
        formData.append('file', file);
        dispatch(updateUserProfilePicture(formData));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const {newPassword, phoneNumber, driverLicense} = userUpdateData;

        if (!newPassword || !phoneNumber || !driverLicense) {
            toast.error('Please Fill Out All Fields');
            return;
        }
        dispatch(updateUser(userUpdateData));
    };
    const handleChange = (e) => {
        const name = e.target.name;
        let value = e.target.value;

        if (name === 'phoneNumber') {
            value = value.replace(/[^0-9]/g, '');

            if (value.length > 11) {
                value = value.slice(0, 11);
            }
        } else if (name === 'driverLicense') {
            if (value.length > 2) {
                value = value.slice(0, 2);
            }
        }

        setUserUpdateDataData({ ...userUpdateData, [name]: value });
    };

    useEffect(() => {
        async function getImage() {
            try {
                const response = await customFetch.get(`/auth/profile-image/${user.id}`, {
                    responseType: 'arraybuffer', // set response type to arraybuffer
                });
                const base64Image = btoa(
                    new Uint8Array(response.data).reduce(
                        (data, byte) => data + String.fromCharCode(byte),
                        ''
                    )
                );
                setProfilePictureUrl(`data:image/png;base64,${base64Image}`);
            } catch (error) {

                if (error.response.status === 400){
                    setProfilePictureUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSK_ipblQmN3wTBFei_PfH6_msyszErHGzIZQ&usqp=CAU");
                }
            }
        }

        getImage();
    }, [profilePictureUrl]);

    return (
        <Wrapper>
            <form className='form' onSubmit={handleSubmit}>
                <h3>profile</h3>
                <div className="carContainer">
                    <div className="carShow">
                        <div className="carShowTop">
                            <img className='carUpdateImg' src={profilePictureUrl} alt='' />
                            <div className="carShowTopTitle">
                                <span className="carShowUsername"></span>{userData.name}
                                <span className="carShowUsername"></span>{userData.lastName}
                            </div>
                        </div>
                        <div className="carShowBottom">
                            <span className="carShowTitle">Other Details</span>
                            <div className="carShowInfo">
                                <EmailOutlined className="carShowIcon"/>
                                <div>
                                    <h7>Email: </h7>
                                    <span className="carShowInfoTitle">{userData.email}
                                </span>
                                </div>
                            </div>
                            <div className="carShowInfo">
                                <LocalPhoneOutlined className="carShowIcon"/>
                                <div>
                                    <h7>Phone Number: </h7>
                                    <span className="carShowInfoTitle">{userData.phoneNumber}
                                </span>
                                </div>
                            </div>
                            <div className="carShowInfo">
                                <DocumentScannerOutlined className="carShowIcon"/>
                                <div>
                                    <h7>Driving License: </h7>
                                    <span className="carShowInfoTitle">{userData.driverLicense}
                                </span>
                                </div>
                            </div>
                        </div>
                    </div>
                     <div className="carUpdate">
                        <span className="carUpdateTitle">Edit</span>
                        <div className="carUpdateForm">
                            <div className="carUpdateLeft">
                                <div className="carUpdateItem">
                                    <FormRow
                                        type='text'
                                        labelText='phoneNumber'
                                        name='phoneNumber'
                                        value={userUpdateData.phoneNumber}
                                        handleChange={handleChange}
                                    />
                                </div>
                                <div className="carUpdateItem">
                                    <FormRow
                                        type='text'
                                        name='driverLicense'
                                        value={userUpdateData.driverLicense}
                                        handleChange={handleChange}
                                    />
                                </div>
                                <div className="carUpdateItem">
                                    <FormRow
                                        type='password'
                                        name='newPassword'
                                        value={userUpdateData.newPassword}
                                        handleChange={handleChange}
                                    />
                                </div>
                            </div>
                            <div className="carUpdateRight">
                                <div className="carUpdateUpload">
                                    <img className='carUpdateImg' src={profilePictureUrl} alt='' />
                                    <label htmlFor="file">
                                        <Publish className="carUpdateIcon"/>
                                    </label>
                                    <input onChange={handleProfilePictureSubmit} type="file" accept="image/png, image/jpeg" id="file" style={{display: "none"}}/>
                                </div>
                                <button className='btn btn-block' type='submit' disabled={isLoading}>
                                    {isLoading ? 'Please Wait...' : 'Update Information'}
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </Wrapper>
    );
};

export default Profile;

