import React, {useEffect, useRef, useState} from 'react';
import {DateRangePicker} from 'react-date-range';
import 'react-date-range/dist/styles.css';
import 'react-date-range/dist/theme/default.css';
import format from "date-fns/format";

const DateRangePickerComp = () => {
    const [dateRange, setDateRange] = useState([
        {
            startDate: new Date(),
            endDate: new Date(),
            key: 'selection'
        }
    ]);
    useEffect(() => {

        document.addEventListener("keydown", hideOnEscape, true)
        document.addEventListener("click", hideOnClickOutside, true)
    }, [])

    const hideOnEscape = (e) => {
        if (e.key === "Escape") {
            setOpen(false)
        }
    }
    const hideOnClickOutside = (e) => {
        if (refOne.current && !refOne.current.contains(e.target)) {
            setOpen(false)
        }

    }
    const [open, setOpen] = useState(false);
    const refOne = useRef(null);
    const handleSelect = (ranges) => {
        setDateRange([ranges.selection]);
    };

    return (
        <div className={'calendarWrap'}>
            <input
                value={`${format(dateRange[0].startDate, "MM/dd/yyyy")} to ${format(dateRange[0].endDate, "MM/dd/yyyy")}`}
                readOnly
                className="inputBox"
                onClick={() => setOpen(open => !open)}
            />
            <div ref={refOne}>{open && <DateRangePicker
                ranges={dateRange}
                onChange={handleSelect}
            />}
            </div>

        </div>
    );
};

export default DateRangePickerComp;
