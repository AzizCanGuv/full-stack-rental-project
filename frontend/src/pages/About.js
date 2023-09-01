import React from 'react';
import Wrapper from '../assets/wrappers/LandingPage'
import '../assets/wrappers/LandingPage'
import {Logo} from "../components";
import {Link} from "react-router-dom";
import SimpleImageSlider from "react-simple-image-slider";
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';


import "leaflet/dist/leaflet.css";
import {MapContainer, TileLayer, Marker, Popup} from "react-leaflet";
import MarkerClusterGroup from "react-leaflet-cluster";

import {Icon, divIcon, point} from "leaflet";

const bull = (
    <Box
        component="span"
        sx={{display: 'inline-block', mx: '2px', transform: 'scale(0.8)'}}
    >
        •
    </Box>
);

const customIcon = new Icon({
    iconUrl: require("../assets/images/placeholder.png"),
    iconSize: [38, 38] // size of the icon
});

const createClusterCustomIcon = function (cluster) {
    return new divIcon({
        html: `<span class="cluster-icon">${cluster.getChildCount()}</span>`,
        className: "custom-marker-cluster",
        iconSize: point(33, 33, true)
    });
};


const markers = [
    {
        geocode: [48.86, 2.3522],
        popUp: "Paris 1"
    },
    {
        geocode: [48.85, 2.3522],
        popUp: "Paris 2"
    },
    {
        geocode: [48.855, 2.34],
        popUp: "Paris 3"
    }
];


const images = [
    {url: "https://i.ibb.co/mSQFYPj/IMG-7522.png"},
    {url: "https://i.ibb.co/TBt1g0y/IMG-7521.png"},
    {url: "https://i.ibb.co/p1wjd7J/IMG-7520.png"},
    {url: "https://i.ibb.co/hmFhRZk/IMG-7519.png"},
    {url: "https://i.ibb.co/n7Tctxb/IMG-7518.png"},
    {url: "https://i.ibb.co/9qztjd4/IMG-7517.png"},
    {url: "https://i.ibb.co/DVjnLfp/IMG-7516.png"},
];

const About = () => {
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
                    <p>Eski Çamoluk Otomotiv Rent A Car is a reliable company that has been providing car rental
                        services for over 20 years. We always prioritize customer satisfaction in our business
                        practices.

                        Our fleet of vehicles is always kept in top condition through strict maintenance and inspection
                        procedures, ensuring a safe and comfortable driving experience for our customers. We offer a
                        wide selection of vehicles, giving our customers the freedom to choose the car that best suits
                        their needs.

                        At Eski Çamoluk Otomotiv Rent A Car, we constantly strive to improve and renew ourselves to
                        provide our customers with the highest quality service. Our professional and experienced team is
                        always ready to assist our customers.

                        The fact that our customers are satisfied with our service and choose us repeatedly shows us
                        that we are doing our job right. As Eski Çamoluk Otomotiv Rent A Car, we will do our best to
                        provide our customers with the best service possible. </p>
                    <Link to={"/register"} className='btn'>Login/Register</Link>
                    <Link to={"/landing"} className='btn'>Landing</Link>
                    <Link to={"/contact"} className='btn'>Contact</Link>
                </div>
                <div>
                    <h1> Our Team</h1>
                    <SimpleImageSlider className={"img main-img"}
                                       width={445}
                                       height={445}
                                       images={images}
                                       showBullets={true}
                                       showNavs={true}
                    />
                </div>
                <div style={{height: "45px"}}></div>
                <div style={{height: "45px"}}></div>

                <div style={{height: "300px", width: "700px"}}>

                    <MapContainer center={[48.8566, 2.3522]} zoom={13} style={{height: "100%", width: "100%"}}>
                        <TileLayer
                            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                        />
                        <MarkerClusterGroup
                            chunkedLoading
                            iconCreateFunction={createClusterCustomIcon}
                        >
                            {markers.map((marker) => (
                                <Marker position={marker.geocode} icon={customIcon}>
                                    <Popup>{marker.popUp}</Popup>
                                </Marker>
                            ))}

                        </MarkerClusterGroup>
                    </MapContainer>


                </div>

                <div><Card sx={{minWidth: 275}}>
                    <CardContent>
                        <Typography sx={{fontSize: 18}} color="text.secondary" gutterBottom>
                            Our Offices
                        </Typography>
                        <Typography variant="h5" component="div">
                            {bull}Paris,France 1
                        </Typography>
                        <Typography sx={{mb: 1.5}} color="text.secondary">
                            19 Rue de Varenne, 75007 Paris, France
                        </Typography>
                        <Typography variant="h5" component="div">
                            {bull}Paris,France 2
                        </Typography>
                        <Typography sx={{mb: 1.5}} color="text.secondary">
                            Rue de Varenne, 75007 Paris, France
                        </Typography>
                        <Typography variant="h5" component="div">
                            {bull}Paris,France 3
                        </Typography>
                        <Typography sx={{mb: 1.5}} color="text.secondary">
                            Rue de Varenne, 75007 Paris, France
                        </Typography>
                    </CardContent>

                </Card></div>
            </div>

        </Wrapper>
    );

};

export default About;