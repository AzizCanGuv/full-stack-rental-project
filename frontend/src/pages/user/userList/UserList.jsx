import "./userList.css";
import ReactDOM from 'react-dom';
import {Link, useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {DeleteOutline} from "@mui/icons-material";
import {
    DataGrid,
    gridPageCountSelector,
    GridPagination, GridToolbar,
    useGridApiContext,
    useGridSelector,
} from '@mui/x-data-grid';
import Box from '@mui/material/Box';
import MuiPagination from '@mui/material/Pagination';
import { TablePaginationProps } from '@mui/material/TablePagination';
import * as React from 'react';
import UserService from "../../../services/userService";
import {useSelector} from "react-redux";
import FormRowSelect from "../../../components/FormRowSelect";
import {Popup} from "../../../components/Popup/Popup";

export default function UserList() {
    const [open, setOpen] = useState(false);
    const [accepted, setAccepted] = useState(false);
    const [selectedUserId, setSelectedUserId] = useState(0)
    const [selectedUserEmail, setSelectedUserEmail] = useState('')
    const [currentMethodToExecute, setCurrentMethodToExecute] = useState('');
    const handleAccept = async () => {
        setAccepted(true);

        if (currentMethodToExecute === 'ROLE') {
            const requestBody = {
                userEmail: selectedUserEmail,
                role: selectedRole
            };
            const result = await new UserService().changeUserRole(selectedUserId, requestBody, user.token);

        }
        if (currentMethodToExecute === 'DELETE') {
            const result = new UserService().deleteUser(selectedUserId, user.token);
        }
        setOpen(false)
        setTimeout(() => {
            loadUsers();
        }, 1000);


    }

    function handleReject() {
        setAccepted(false);
        setOpen(false)
    }

    const roleOptions = [
        "MANAGER", "USER"
    ];
    const [selectedRole, setSelectedRole] = useState("USER");
    const loadUsers = async () => {
        const result = await new UserService().getAllUsers(user.token);
        setData(result.data);
    };
    useEffect(() => {
        loadUsers();
    }, []);
    const {id: id} = useParams();
    const [users, setData] = useState([]);

    const {isLoading, user} = useSelector((store) => store.user);


    const handleRoleChange = async (userId, userEmail, selectedRole) => {

        setOpen(true);
        setSelectedRole(selectedRole);
        setSelectedUserId(userId)
        setSelectedUserEmail(userEmail)
        setCurrentMethodToExecute('ROLE')
    };

    const handleDelete = (userId) => {
        setOpen(true);
        setSelectedUserId(userId)
        setCurrentMethodToExecute('DELETE')
    };


    const columns = [
        {field: "userId", headerName: "ID", width: 90},
        {
            field: "email",
            headerName: "Email",
            width: 200,
            renderCell: (params) => {
                return (
                    <div className="userListUser">
                        <img className="userListImg"
                             src="https://cdn1.iconfinder.com/data/icons/website-internet/48/website_-_male_user-512.png"
                             alt=""/>
                        {params.row.email}
                    </div>
                );
            },
        },
        {field: "firstName", headerName: "First Name", width: 120},
        {
            field: "lastName",
            headerName: "Last Name",
            width: 120,
        },
        {
            field: "driverLicense",
            headerName: "Driver License",
            width: 120,
        },
        {
            field: "role",
            headerName: "Role",
            width: 160,
        },
        {
            field: "birthDate",
            headerName: "Birth Date",
            width: 120,
            renderCell: (params) => {
                const birthDate = new Date(params.row.birthDate);
                const formattedDate = birthDate.toLocaleDateString("en-US", {
                    month: "short",
                    day: "numeric",
                    year: "numeric",
                });

                return <div>{formattedDate}</div>;
            },
        },
        {
            field: "isEnabled",
            headerName: "Is Enabled",
            width: 160,
        },
        {
            field: "action",
            headerName: "Actions",
            width: 150,
            renderCell: (params) => {
                return (

                    <>
                        {params.row.role !== "ADMIN" && <FormRowSelect
                            value={params.row.role}
                            handleChange={(event) => {
                                setSelectedRole(event.target.value);
                                handleRoleChange(params.row.userId, params.row.email, event.target.value);
                            }}
                            list={roleOptions}

                        />}
                        {params.row.role !== "ADMIN" && <DeleteOutline
                            className="userListDelete"
                            onClick={() => handleDelete(params.row.userId)}
                        />}
                    </>
                );
            },
        },
    ];
    function Pagination({page,
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
                    onPageChange(event , newPage - 1);
                }}
            />
        );
    }
    function CustomPagination(props: any) {
        return <GridPagination ActionsComponent={Pagination} {...props} />;
    }

    return (
        <div className="userList">
            <DataGrid
                getRowId={row => row.userId}
                rows={users}
                disableSelectionOnClick
                columns={columns}
                //pageSize={users.length}
                checkboxSelection
                autoHeight
                pagination
                pageSizeOptions={[3]}
                slots={{
                    pagination: CustomPagination,
                    toolbar: GridToolbar,
                }}
                {...users}
                initialState={{
                    ...users.initialState,
                    pagination: { paginationModel: { pageSize: 3 }  } ,
                }}
            />
            {open && (
                <Popup
                    onAccept={handleAccept}
                    onReject={handleReject}
                    text={"Are you sure to handle role change operation?"}
                    closePopup={() => setOpen(false)}
                />
            )}
        </div>
    );
}