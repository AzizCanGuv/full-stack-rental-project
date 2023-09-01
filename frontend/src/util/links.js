import {IoBarChartSharp} from 'react-icons/io5';
import {MdQueryStats} from 'react-icons/md';
import {FaWpforms} from 'react-icons/fa';
import {ImCalendar, ImProfile} from 'react-icons/im';
import {AiOutlineCar, AiOutlineUsergroupDelete} from 'react-icons/ai';
import {useSelector} from "react-redux";


const Links = () => {
    const { isLoading, user } = useSelector((store) => store.user);

    const links = [
        {
            id: 1,
            text: 'stats',
            path: '/',
            icon: <IoBarChartSharp/>,
        },
        {
            id: 2,
            text: 'all cars',
            path: 'all-cars',
            icon: <AiOutlineCar/>,
        },
        {
            id: 3,
            text: 'add car',
            path: 'add-car',
            icon: <FaWpforms/>,
        },
        {
            id: 4,
            text: 'profile',
            path: 'profile',
            icon: <ImProfile/>,
        },
        {
            id: 6,
            text: 'Reservations',
            path: 'all-reservations',
            icon: <ImCalendar/>,
        },
        
    ];

    if (user.role === 'ADMIN') {
        console.log("calledAdmin")
        links.push({
            id: 5,
            text: 'users',
            path: 'all-users',
            icon: <AiOutlineUsergroupDelete />,
        });
    }
    return links;
}

export default Links;