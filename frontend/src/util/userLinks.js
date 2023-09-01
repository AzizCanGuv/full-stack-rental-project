import { IoBarChartSharp } from 'react-icons/io5';
import {MdPayment, MdQueryStats} from 'react-icons/md';
import { FaWpforms } from 'react-icons/fa';
import { ImProfile } from 'react-icons/im';

const links = [

    {
        id: 1,
        text: 'all cars',
        path: 'all-cars',
        icon: <MdQueryStats />,
    },
    {
        id: 2,
        text: 'My Reservations',
        path: 'reservation-list',
        icon: <MdPayment />,
    },
    {
        id: 3,
        text: 'profile',
        path: 'profile',
        icon: <ImProfile />,
    },
];

export default links;