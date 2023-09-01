import React, {useState} from "react";
import "./Popup.css";

export const Popup = ({text, closePopup, onAccept, onReject}) => {
    const [accepted, setAccepted] = useState(false);

    function handleAccept() {
        setAccepted(true);
        onAccept();
    }

    function handleReject() {
        setAccepted(false);
        onReject();
    }

    return (
        <div className="popup-container">
            <div className="popup-box">

                <p>
                    {text}
                </p>
                <div className="popup-text">

                </div>
                <div className={'popup-buttons'}>
                    <button className={"btn btn-danger"} onClick={handleReject}>Reject</button>
                    <button className={"btn alert-success"} onClick={handleAccept}>Approve</button>

                </div>

            </div>
        </div>
    );
}
