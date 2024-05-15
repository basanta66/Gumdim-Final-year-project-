import React, { useState, useEffect } from 'react';
import Item from '../Items/Item';
import Axios from 'axios';

const NearPlacesAll = () => {
    const [nearbyPlaces, setNearbyPlaces] = useState([]);

    useEffect(() => {
        // Function to fetch data from the API
        const fetchNearbyPlaces = async () => {
            try {
                // Provide user's latitude and longitude coordinates here
                const userLatitude = 27.668855;
                const userLongitude = 85.421208;
                const response = await Axios.get(`http://localhost:8080/ghumdim/viewDestinationsSortedByDistance?userLatitude=${userLatitude}&userLongitude=${userLongitude}`);
                setNearbyPlaces(response.data); // Update state with the fetched data
            } catch (error) {
                console.error('Error fetching nearby places:', error);
            }
        };

        fetchNearbyPlaces();
    }, []);

    return (
        <div className='popular'>
            <h1>Places Near You</h1>
            <hr />
            <div className='popular-destination'>
                {nearbyPlaces.map((place, index) => (
                    <Item
                        key={index}
                        id={place.id}
                        name={place.name}
                        photo={place.photo}
                        address={place.address}
                        status={place.status}
                    />
                ))}
            </div>
        </div>
    );
};

export default NearPlacesAll;
