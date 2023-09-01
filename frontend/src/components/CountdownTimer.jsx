import React, { useEffect, useState } from "react";
import "./CountdownTimer.css";

const CountdownTimer = ({ remainingTimeToDelete, onCountdownEnd }) => {
    const [time, setTime] = useState(remainingTimeToDelete);

    useEffect(() => {
        if (remainingTimeToDelete) {
            const [initialMinutes, initialSeconds] = String(remainingTimeToDelete).split(":").map(Number);
            let minutes = initialMinutes;
            let seconds = initialSeconds;

            const timer = setInterval(() => {
                if (minutes === 0 && seconds === 0) {
                    clearInterval(timer);
                    onCountdownEnd(); // Call the callback function when the countdown reaches zero
                } else {
                    if (seconds === 0) {
                        minutes -= 1;
                        seconds = 59;
                    } else {
                        seconds -= 1;
                    }

                    setTime(`${minutes.toString().padStart(2, "0")}:${seconds.toString().padStart(2, "0")}`);
                }
            }, 1000); // Update the countdown every second (1000 milliseconds)

            return () => {
                clearInterval(timer);
            };
        }
    }, [remainingTimeToDelete, onCountdownEnd]);

    const countdownClass = time === "00:00" ? "countdown countdown-red" : "countdown";

    return (
        <div className={countdownClass}>
            Time remaining until your right to payment ends: {time}
        </div>
    );
};

export default CountdownTimer;
