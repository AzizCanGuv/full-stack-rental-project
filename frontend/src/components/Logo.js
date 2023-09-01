import img from '../assets/images/secondLogo.svg'

import React, {Component} from 'react';
import logo from "../assets/images/firstLogo.svg";

const Logo = () => {

        return (
            <img src={logo} alt='eskicamolug logo' className='logo'/>
        )
}

export default Logo;