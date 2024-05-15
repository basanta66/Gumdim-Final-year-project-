import React, { useContext, useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { DestinationContext } from '../../Context/DestinationContext'
import Axios, { all } from 'axios'
import './AdminDestinationDisplay.css'
import { FaStarHalfAlt, FaStar, FaRegStar } from "react-icons/fa";
import AdminEditForm from '../EditForm/AdminEditForm'

const AdminDestinationDisplay = (props) => {
    const { destinationId } = useParams();
    const [rating, setRating] = useState(0); // State to store the rating

    const [alldestination, setAllDestination] = useState({});

    const [modal, setmodal] = useState(false);
    const togglemodal = () => {
        setmodal(!modal)
    }



    useEffect(() => {
        if (destinationId) {
            Axios.get(`http://localhost:8080/ghumdim/viewDestination/${destinationId}`).then((res) => {
                console.log(res.data,);
                setAllDestination(res.data);
                setRating(res.data.rating); // Set the rating obtained from the API response

            })
                .catch((error) => {
                    console.error('error fetching data', error);
                });
        }
    }, []);

    const renderStars = () => {
        const stars = [];
        const roundedRating = Math.round(rating * 2) / 2;
        for (let i = 0; i < 5; i++) {
            if (i < roundedRating - 0.5) {
                stars.push(<FaStar style={{ color: '#FF4141' }} size={20} key={i} />);
            } else if (i === Math.floor(roundedRating) && roundedRating % 1 !== 0) {
                stars.push(<FaStarHalfAlt style={{ color: '#FF4141' }} size={20} key={i} />);
            } else {
                stars.push(<FaRegStar style={{ color: '#FF4141' }} size={20} key={i} />);
            }
        }
        return stars;
    };

    const { addToFavourite } = useContext(DestinationContext);

    return (
        <div className='destinationdisplay'>

            <div className="destinationdisplay-left">

                <div className="destinationdisplay-img">
                    <img className='destinationdisplay-main-img' src={`https://firebasestorage.googleapis.com/v0/b/ghumdim.appspot.com/o/${alldestination?.photo}?alt=media`} alt="" />
                </div>
            </div>

            <div className="destinationdisplay-right">
                <h1>{alldestination?.name}</h1>
                <div className='destinationdisplay-right-star'>
                    {renderStars()} {/* Render star icons dynamically */}
                </div>
                <div className='destinationdisplay-right-description'>
                    <h4>Description</h4>
                    {alldestination?.description}
                </div>
                <div className='destinationdisplay-right-location'>
                    <p>Location: {alldestination?.latitude},{alldestination?.longitude}</p>
                </div>

                {/* <button onClick={() => { addToFavourite(alldestination?.destinationId) }}>Add to Favourites</button> */}

                {/* Edit the destinations */}
                <button className='btn-modal' onClick={togglemodal} >Edit</button>

                {modal && (
                    <div className="modal">
                        <div onClick={togglemodal} className="overlay"></div>
                        <div className='modal-content'>
                            <AdminEditForm />
                            <button className='close-modal' onClick={togglemodal}>Close</button>
                        </div>
                    </div>
                )}

            </div>
        </div>
    )
}

export default AdminDestinationDisplay




