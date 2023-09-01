import {CardElement, PaymentElement, useElements, useStripe} from "@stripe/react-stripe-js";
import React, {useEffect, useState} from "react";
import {useSelector} from "react-redux";
import {useNavigate, useParams} from "react-router-dom";
import Wrapper from "../assets/wrappers/DashboardFormPage";
import {toast} from "react-toastify";
import {Popup} from "../components/Popup/Popup";
import UserService from "../services/userService";
import customFetch from "../util/axios";
import CountdownTimer from "../components/CountdownTimer";

const CheckoutForm = () => {
    const stripe = useStripe();
    const elements = useElements();
    const [errorMessage, setErrorMessage] = useState("");
    const [succeeded, setSucceeded] = useState(false);
    const {isLoading, user} = useSelector((store) => store.user);
    const [totalPayment, setTotalPayment] = useState(null);
    const {reservationId} = useParams();
    const navigate = useNavigate();
    const [showPopup, setShowPopup] = useState(false);
    const [remainingTimeToDelete, setRemainingTimeToDelete] = useState("5:00");
    const handleCountdownEnd = () => {
        toast.error("Provided Payment Time Has Been Expired!")
        navigate("/all-cars");
    };
    const text = "Cancellation/Return Policy:\n" +
        "\n" +
        "Cancellation by Renter: If the renter cancels the rental reservation, the following refund policy will apply:\n" +
        "Cancellation made 48 hours or more prior to the scheduled pickup time will result in a full refund.\n" +
        "Cancellation made less than 48 hours prior to the scheduled pickup time will result in a 50% refund of the total rental cost.\n" +
        "Cancellation made less than 24 hours prior to the scheduled pickup time will not be refunded.\n" +
        "Return Policy: If the renter returns the vehicle before the scheduled end date and time, the rental fees for the unused portion of the rental period will not be refunded.\n" +
        "\n" +
        "Cancellation by Owner: If the owner cancels the rental reservation due to unforeseen circumstances, such as an unexpected mechanical issue with the vehicle, the renter will receive a full refund of the rental cost.\n" +
        "\n" +
        "No Show Policy: If the renter fails to show up at the scheduled pickup time without prior notice, the rental reservation will be cancelled and no refund will be provided.\n" +
        "\n" +
        "Please note that by completing the rental reservation, you agree to the above policies. If you have any questions or concerns, please contact us at [contact information]."
    useEffect(() => {
        async function fetchReservation() {
            if (reservationId) {
                try {
                    const encodedReservationId = encodeURIComponent(reservationId);
                    const response = await customFetch.get(`/reservation/getReservationByReservationId/${encodedReservationId}`, {
                        headers: {
                            Authorization: `Bearer ${user.token}`,
                        },
                    });
                    const data = response.data;
                    setTotalPayment(data.totalRentPrice);
                    setRemainingTimeToDelete(data.remainingTimeToDelete)
                } catch (error) {
                    console.error(error);
                }
            }
        }

        fetchReservation();
    }, [reservationId, user.token]);
    const handleCardFailed = async (event) => {
        try {
            const response = await customFetch.post('/payment/failed-payment-intent', {
                reservationId: reservationId,
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${user.token}`,
                },
            });
            console.log(response.data);
        } catch (error) {
            console.error(error);
        }
    };

    const handleAccept = (event) => {
        setShowPopup(false);
        console.log("handleAccept")
        handleSubmit();
    }

    const handleReject = () => {
        console.log("handleReject")
        setShowPopup(false);
    }



    const handleSubmit = async () => {
        console.log('HandleSubmit');

        if (!stripe || !elements) {
            return;
        }

        const { error, paymentMethod } = await stripe.createPaymentMethod({
            type: 'card',
            card: elements.getElement(CardElement),
        });

        if (error) {
            setErrorMessage(error.message);
            setSucceeded(false);
            return;
        }

        const { id } = paymentMethod;

        try {
            const { data: { clientSecret } } = await customFetch.post('/payment/create-payment-intent', {
                amount: totalPayment * 100,
                currency: 'USD',
                paymentMethodId: id,
                reservationId: reservationId,
            }, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${user.token}`,
                },
            });

            const { error: stripeError } = await stripe.confirmCardPayment(clientSecret, {
                payment_method: id,
            });

            if (stripeError) {
                setErrorMessage(stripeError.message);
                setSucceeded(false);
                toast.error('Payment Failed');
                await handleCardFailed();
            } else {
                setErrorMessage('');
                toast.success('Payment Successful');
                setSucceeded(true);
                navigate('/');
            }
        } catch (error) {
            setErrorMessage(error.message);
            setSucceeded(false);
        }
    };


    const appearance = {
        theme: "stripe",
    };

    const options = {
        style: {
            base: {
                color: '#32325D',
                fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
                fontSmoothing: 'antialiased',
                fontSize: '16px',
                '::placeholder': {
                    color: '#CFD7DF',
                },
            },
            invalid: {
                color: '#E25950',
                '::placeholder': {
                    color: '#FFCCA5',
                },
            },
        },
    };

    return (
        <Wrapper>
            <div className="car">
                <CountdownTimer remainingTimeToDelete={remainingTimeToDelete} onCountdownEnd={handleCountdownEnd} />
                <div className="carContainer">
                    <div className="carShow">
                        <div className="carShowBottom">
                            <h2 className="carShowTitle">Payment Details</h2>
                            <div className="carShowInfo">
                               <h5>You will pay: {totalPayment} to rent your car</h5>
                                <br/>
                            </div>
                        </div>
                    </div>
                    <div className="carUpdate">
                        <form className={"from"} onSubmit={handleSubmit}>
                            <label>
                                Card details
                                <CardElement options={options}/>
                            </label>
                            <button type={"button"} onClick={() => setShowPopup(!showPopup)} className={"btn"} disabled={!stripe}>Pay</button>
                            {errorMessage && <div>{errorMessage}</div>}
                            {succeeded && <div>Payment succeeded!</div>}
                            {showPopup ? <Popup onAccept={handleAccept} onReject={handleReject} text={text} closePopup={() => setShowPopup(false)} /> : null}
                        </form>
                    </div>
                </div>
            </div>
        </Wrapper>
    );
};




export default CheckoutForm;
