

import React, { useState, useEffect } from 'react';
import './RelatedDestination.css';
import Item from '../Items/Item';
import Axios from 'axios';
import { useParams } from 'react-router-dom';

const RelatedDestination = () => {
    const { destinationId } = useParams();
    const [alldestination, setAllDestination] = useState({});
    const [nearbyPlaces, setNearbyPlaces] = useState([]);

    useEffect(() => {
        if (destinationId) {
            Axios.get(`http://localhost:8080/ghumdim/viewDestination/${destinationId}`)
                .then((res) => {
                    console.log(res.data);
                    setAllDestination(res.data);
                })
                .catch((error) => {
                    console.error('error fetching data', error);
                });
        }
    }, [destinationId]);

    useEffect(() => {
        const fetchNearbyPlaces = async () => {
            try {
                const userLatitude = alldestination.latitude;
                const userLongitude = alldestination.longitude;
                const response = await Axios.get(`http://localhost:8080/ghumdim/viewDestinationsSortedByDistance?userLatitude=${userLatitude}&userLongitude=${userLongitude}`);
                // Filter out the current destination from nearby places
                const filteredPlaces = response.data.filter(place => place.id !== destinationId);
                // Slice the filtered response data to get only the first four items
                const firstFourPlaces = filteredPlaces.slice(1, 5);
                setNearbyPlaces(firstFourPlaces);
            } catch (error) {
                console.error('Error fetching nearby places:', error);
            }
        };

        if (alldestination.latitude && alldestination.longitude) {
            fetchNearbyPlaces();
        }
    }, [alldestination]);

    return (
        <div className='relateddestinations'>
            <h1>Near Destinations</h1>
            <hr />
            <div className="relateddestination-item">
                {nearbyPlaces.map((item, i) => {
                    return <Item key={i} id={item.id} name={item.name} photo={item.photo} address={item.address} />
                })}
            </div>
        </div>
    )
}

export default RelatedDestination;

