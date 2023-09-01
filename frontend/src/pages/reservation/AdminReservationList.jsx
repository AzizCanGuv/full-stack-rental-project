import "./reservationList.css";
import ReactDOM from 'react-dom';
import {Link, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Clear, DeleteOutline} from "@mui/icons-material";
import {DataGrid, GridCheckIcon} from "@mui/x-data-grid";
import ReservationService from "../../services/reservationService";
import {useSelector} from "react-redux";
import FormRowSelect from "../../components/FormRowSelect";
import {Popup} from "../../components/Popup/Popup";
import {format} from "date-fns";
import { id } from "date-fns/locale";
import {green} from "@mui/material/colors";
import * as React from "react";

export default function AdminReservationList() {
    const [reservations, setData] = useState([]);
    const {isLoading, user} = useSelector((store) => store.user);

    const loadReservations = async () => {
        const result = await new ReservationService().getAllReservations(user.token,user.id);
        const data = result.data.map((reservation) => {
            return {
                ...reservation,

            }
        });
        console.log(data)
        setData(data);
    };
    useEffect(() => {
        loadReservations();
    }, []);


    const columns = [
        {field: "reservationId", headerName: "Reservation ID", width: 200},
        {
            field: "userId",
            headerName: "User ID",
            width: 160,
        },
        {
            field: "bookedAt",
            headerName: "Booked At",
            width: 160,
            valueFormatter: (params) => {
                const formattedDate = format(new Date(params.value), "dd-MM-yyyy");
                return formattedDate;
            },
        },
        {
            field: "pickUpDate",
            headerName: "Pickup Date",
            width: 160,
            valueFormatter: (params) => {
                const formattedDate = format(new Date(params.value), "dd-MM-yyyy");
                return formattedDate;
            },
        },
        {
            field: "dropOffDate",
            headerName: "Dropoff Date",
            width: 160,
            valueFormatter: (params) => {
                const formattedDate = format(new Date(params.value), "dd-MM-yyyy");
                return formattedDate;
            },
        }
        ,
        {
            field: "totalRentPrice",
            headerName: "Total Rent Price",
            width: 120,
        }
        ,
        {
            field: "paid",
            headerName: "Paid",
            width: 90,
            renderCell: (params) =>
                params.value ? (
                    <GridCheckIcon style={{ color: green[500] }} />
                ) : (
                    <Clear color="error" />
                ),
        }
    ];

    return (
        <div className="reservationList">
            <DataGrid
                getRowId={row => row.reservationId}
                autoHeight
                rows={reservations}
                columns={columns}
                pageSize={8}
                rowsPerPageOptions={[8]}
                checkboxSelection={false}
                disableSelectionOnClick={true}
            />

        </div>
    );
}