import React, { useState, useEffect } from 'react'
import './Breadcrum.css'
import arrow_icon from '../Assets/breadcrum_arrow.png'
import Axios from 'axios';
const Breadcrum = (props) => {
    // const [alldestination, setAllDestination] = useState({});
    // useEffect(() => {
    //     Axios.get("http://localhost:8080/ghumdim/viewAllDestination").then((res) => {
    //         console.log(res.data);
    //         setAllDestination(res.data);
    //     })
    //         .catch((error) => {
    //             console.error('error fetching data', error);
    //         });
    // }, []);

    const { destination } = props;

    return (
        <div className='breadcrum'>
            HOME <img src={arrow_icon} alt="" /> Destination <img src={arrow_icon} alt="" /> {destination?.category} <img src={arrow_icon} alt="" />  {destination?.name}
        </div>
    )
}

export default Breadcrum
