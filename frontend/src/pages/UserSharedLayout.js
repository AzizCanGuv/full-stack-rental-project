import { Outlet } from 'react-router-dom';
import {Navbar, UserBigSidebar, UserSmallSidebar} from '../components';
import Wrapper from '../assets/wrappers/SharedLayout';
const UserSharedLayout = () => {
    return (
        <Wrapper>
            <main className='dashboard'>
                <a
                    href="https://wa.me/2348100000000?text=I%20need%20help"
                    className="whatsapp_float"
                    target="_blank"
                    rel="noopener noreferrer"
                >
                    <i className="fa fa-whatsapp whatsapp-icon"></i>
                </a>

                <UserSmallSidebar />
                <UserBigSidebar />
                <div>
                    <Navbar />
                    <div className='dashboard-page'>
                        <Outlet />
                    </div>
                </div>
            </main>
        </Wrapper>
    );
};
export default UserSharedLayout;