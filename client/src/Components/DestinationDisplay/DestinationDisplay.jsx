

import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './DestinationDisplay.css';
import { DestinationContext } from '../../Context/DestinationContext';
import Axios from 'axios';
import { FaStarHalfAlt, FaStar, FaRegStar } from "react-icons/fa";

const DestinationDisplay = () => {
    const { destinationId } = useParams();
    const [alldestination, setAllDestination] = useState({});
    const [rating, setRating] = useState(0); // State to store the rating

    useEffect(() => {
        if (destinationId) {
            Axios.get(`http://localhost:8080/ghumdim/viewDestination/${destinationId}`).then((res) => {
                console.log(res.data);
                setAllDestination(res.data);
                setRating(res.data.rating); // Set the rating obtained from the API response
            })
                .catch((error) => {
                    console.error('error fetching data', error);
                });
        }
    }, [destinationId]);

    const { addToFavourite } = useContext(DestinationContext);

    // Function to render star icons based on rating
    const renderStars = () => {
        const stars = [];
        const roundedRating = Math.round(rating * 2) / 2; // Round the rating to the nearest half
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
                    <p>{alldestination?.description}</p>
                </div>
                <div className='destinationdisplay-right-location'>
                    <p>Location: {alldestination?.address}</p>
                    <p>Latitude: {alldestination?.latitude}</p>
                    <p>Longitude: {alldestination?.longitude}</p>
                </div>
                <a href={`https://www.google.com/maps/place/${alldestination?.latitude},${alldestination?.longitude}`} target='_blank'>Goto</a>

                <button onClick={() => { addToFavourite(alldestination?.destinationId) }}>Add to Favourites</button>
            </div>
        </div >
    );
};

export default DestinationDisplay;

