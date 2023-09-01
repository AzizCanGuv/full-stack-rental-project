import "./reservationList.css";
import ReactDOM from 'react-dom';
import {Link, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Clear, DeleteOutline} from "@mui/icons-material";
import {
    DataGrid,
    GridCheckIcon, gridPageCountSelector,
    GridPagination,
    GridToolbar,
    useGridApiContext,
    useGridSelector
} from "@mui/x-data-grid";
import ReservationService from "../../services/reservationService";
import {useSelector} from "react-redux";
import FormRowSelect from "../../components/FormRowSelect";
import {Popup} from "../../components/Popup/Popup";
import {format} from "date-fns";
import {id} from "date-fns/locale";
import {green} from "@mui/material/colors";
import * as React from "react";
import {TablePaginationProps} from "@mui/material/TablePagination";
import MuiPagination from "@mui/material/Pagination";

export default function ReservationList() {
    const [reservations, setData] = useState([]);
    const {isLoading, user} = useSelector((store) => store.user);

    const loadReservations = async () => {
        const result = await new ReservationService().getReservationByUserId(user.token, user.id);
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
        {field: "reservationId", headerName: "ID", width: 200},
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
            field: "colorName",
            headerName: "Color Name",
            width: 90,
        }
        ,
        {
            field: "brandName",
            headerName: "Brand Name",
            width: 90,
        }
        ,
        {
            field: "paid",
            headerName: "Paid",
            width: 90,
            renderCell: (params) =>
                params.value ? (
                    <GridCheckIcon style={{color: green[500]}}/>
                ) : (
                    <Clear color="error"/>
                ),
        },
        {
            field: "action",
            headerName: "Actions",
            width: 300,
            renderCell: (params) => {
                return (

                    <>

                        <Link to={"/car-details/" + params.row.carId}>
                            <button className="ReservationListView">Car Details</button>
                        </Link>
                        {!params.row.paid && (
                            <Link to={"/payment/" + params.row.reservationId}>
                                <button className="ReservationListContinuePayment">Continue Payment</button>
                            </Link>
                        )}
                    </>
                );
            },
        },
    ];

    function Pagination({
                            page,
                            onPageChange,
                            className,
                        }: Pick<TablePaginationProps, 'page' | 'onPageChange' | 'className'>) {
        const apiRef = useGridApiContext();
        const pageCount = useGridSelector(apiRef, gridPageCountSelector);

        return (
            <MuiPagination
                color="primary"
                className={className}
                count={pageCount}
                page={page + 1}
                onChange={(event, newPage) => {
                    onPageChange(event, newPage - 1);
                }}
            />
        );
    }

    function CustomPagination(props: any) {
        return <GridPagination ActionsComponent={Pagination} {...props} />;
    }

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
                pagination
                pageSizeOptions={[3]}
                slots={{
                    pagination: CustomPagination,
                    toolbar: GridToolbar,
                }}
                {...reservations}
                initialState={{
                    ...reservations.initialState,
                    pagination: {paginationModel: {pageSize: 3}},
                }}
            />

        </div>
    );
}