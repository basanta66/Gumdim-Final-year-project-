import React, { useState, useEffect } from 'react';
import Item from '../Items/Item';
import Axios from 'axios';

const RatedPlacesAll = () => {


    const [ratingdestination, setRatingDestination] = useState([]);
    useEffect(() => {
        Axios.get("http://localhost:8080/ghumdim/viewAllDestination/sort/rating")
            .then((res) => {
                console.log(res.data);
                setRatingDestination(res.data);
            })
            .catch((error) => {
                console.error('error fetching data', error);
            });
    }, []);

    return (
        <div className='popular'>
            <h1>Destinations By Rating</h1>
            <hr />
            <div className='popular-destination'>
                {ratingdestination.map((place, index) => (
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

export default RatedPlacesAll;
