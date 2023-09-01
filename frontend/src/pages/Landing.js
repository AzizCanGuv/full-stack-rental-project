import React from 'react';
import main from '../assets/images/landing-page.svg'
import Wrapper from '../assets/wrappers/LandingPage'
import {Logo} from "../components";
import {Link} from "react-router-dom";


const Landing = () => {
    return (
        <Wrapper>
            <nav>
                <Logo/>
            </nav>
            <div className='container page'>
                <div className='info'>
                    <h1>
                        Eski <span>Çamoluk</span> Otomotiv
                    </h1>
                    <p>Experience the ultimate car rental service at Eski Çamoluk Otomotiv. We provide a wide range of vehicles to meet your rental needs. Whether you are an individual or a corporate customer, we offer exceptional services to ensure your satisfaction.</p>
                    <Link to={"/register"} className='btn'>Login/Register</Link>
                    <Link to={"/about"} className='btn'>About</Link>
                    <Link to={"/contact"} className='btn'>Contact</Link>
                    <Link to={"/faq"} className='btn'>Faq</Link>
                </div>
                <img src={main} alt='eskicamolug hunt' className='img main-img'/>
            </div>
        </Wrapper>
    );
};


export default Landing;