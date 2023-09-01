import Wrapper from '../assets/wrappers/CarInfo';

const CarInfo = ({ icon, text }) => {
    return (
        <Wrapper>
            <span className='icon'>{icon} </span>
            <span className='text'>{text} </span>
        </Wrapper>
    );
};
export default CarInfo;