import React, {useEffect, useState} from "react";
import {Elements} from "@stripe/react-stripe-js";
import {loadStripe} from "@stripe/stripe-js";
import axios from "axios";
import CheckoutForm from "./CheckoutForm";

import {useParams} from "react-router-dom";

function Payment() {
    const stripePromise = loadStripe("pk_test_51N3bXVKKy0AKHGEeqZ8gkagctgrTlXAksLOB5s7kREZUpiw41mhbJWCZmkvvVCh7BKZH1dyukCSmLpbbVuoRLd4G00Yf5uvrVm");


    return (

        <Elements stripe={stripePromise}>
            <CheckoutForm/>
        </Elements>

    );
}

export default Payment;