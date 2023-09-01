import "./App.css";

import {BrowserRouter, Route, Routes} from "react-router-dom";
import 'react-toastify/dist/ReactToastify.css'
import {ToastContainer} from "react-toastify";
import {
    Landing,
    Error,
    Register,
    ForgotPassword,
    ChangePassword,
    ProtectedRoute,
    Stats,
    SharedLayout,
    UserSharedLayout,
    About,
    Contact,
    UserHome,
    Payment,
    CarDetail,
    CarList,
    NewCar,
    Profile,
    UserList,
    AdminReservationList,
    ReservationList,
    Faq
} from "./pages"
import {useSelector} from "react-redux";


function App() {


    const {isLoading, user} = useSelector((store) => store.user);
    return (

        <BrowserRouter>

            <Routes>

                {user && user.role === 'USER' && <Route
                    path='/'
                    element={
                        <ProtectedRoute>
                            <UserSharedLayout/>
                        </ProtectedRoute>
                    }
                >
                    <Route index element={<CarList/>}/>
                    <Route path='all-cars' element={<CarList/>}/>
                    <Route path='test' element={<CarList/>}/>
                    <Route path="car-details/:carId" element={<CarDetail/>}></Route>
                    <Route path='reservation-list' element={<ReservationList/>}/>
                    <Route path='profile' element={<Profile/>}/>
                    <Route path='payment/:reservationId' element={<Payment/>}/>
                </Route>}
                <Route path='/' element={<Landing/>}/>
                <Route path='landing' element={<Landing/>}/>
                <Route path='about' element={<About/>}/>
                <Route path='contact' element={<Contact/>}/>
                <Route path='faq' element={<Faq/>}/>
                <Route path='register' element={<Register/>}/>
                <Route path='*' element={<Error/>}/>
                <Route path="cars/:carId" element={<CarDetail/>}></Route>
                <Route path="forgot-password" element={<ForgotPassword/>}></Route>
                <Route path="change-password/:email" element={<ChangePassword/>}></Route>
                {user && user.role === 'MANAGER' && <Route
                    path='/'
                    element={
                        <ProtectedRoute>
                            <SharedLayout/>
                        </ProtectedRoute>
                    }
                >
                    <Route index element={<Stats/>}/>
                    <Route path='all-cars' element={<CarList/>}/>
                    <Route path='test' element={<CarList/>}/>
                    <Route path="car-details/:carId" element={<CarDetail/>}></Route>
                    <Route path='all-reservations' element={<AdminReservationList/>}/>
                    <Route path="add-car" element={<NewCar/>}></Route>
                    <Route path='profile' element={<Profile/>}/>
                </Route>}
                {user && user.role === 'ADMIN' && <Route
                    path='/'
                    element={
                        <ProtectedRoute>
                            <SharedLayout/>
                        </ProtectedRoute>
                    }
                >
                    <Route index element={<Stats/>}/>
                    <Route path='all-reservations' element={<AdminReservationList/>}/>
                    <Route path='all-users' element={<UserList/>}/>
                    <Route path='all-cars' element={<CarList/>}/>
                    <Route path='test' element={<CarList/>}/>
                    <Route path="car-details/:carId" element={<CarDetail/>}></Route>
                    <Route path="add-car" element={<NewCar/>}></Route>
                    <Route path='profile' element={<Profile/>}/>
                </Route>}
            </Routes>
            <ToastContainer position='top-center'/>
        </BrowserRouter>
    )
        ;
}

export default App;