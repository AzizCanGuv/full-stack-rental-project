import StatItem from './StatItem';
import {FaCalendarCheck, FaCar, FaUsers} from 'react-icons/fa';
import Wrapper from '../assets/wrappers/StatsContainer';
import { useSelector } from 'react-redux';

const StatsContainer = () => {
    const { stats } = useSelector((store) => store.allCars);

    const defaultStats = [
        {
            title: 'Total Users',
            count: stats.totalUsers || 0,
            icon: <FaUsers />,
            color: '#e9b949',
            bcg: '#fcefc7',
        },
        {
            title: 'Total Reservations',
            count: stats.totalReservations || 0,
            icon: <FaCalendarCheck />,
            color: '#647acb',
            bcg: '#e0e8f9',
        },
        {
            title: 'Total Cars',
            count: stats.totalCars || 0,
            icon: <FaCar />,
            color: '#d66a6a',
            bcg: '#ffeeee',
        },
    ];

    return (
        <Wrapper>
            {defaultStats.map((item, index) => {
                return <StatItem key={index} {...item} />;
            })}
        </Wrapper>
    );
};
export default StatsContainer;