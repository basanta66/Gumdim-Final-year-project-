import React, { useContext, useState, useEffect } from 'react'
import './FavouriteDestination.css'
import { DestinationContext } from '../../Context/DestinationContext'
import remove_icon from '../Assets/cart_cross_icon.png'
import Axios from 'axios';

const FavouriteDestination = () => {

    const [alldestination, setAllDestination] = useState([]);
    useEffect(() => {
        Axios.get("http://localhost:8080/ghumdim/viewAllDestination").then((res) => {
            console.log(res.data);
            setAllDestination(res.data);
        })
            .catch((error) => {
                console.error('error fetching data', error);
            });
    }, []);

    const { all_destination, favouriteItems, removeFromFavourite } = useContext(DestinationContext);
    return (
        <div className='favitems'>
            <div className='favtitems-format-main'>
                <p>Destinations</p>
                <p>Name</p>
                <p>Category</p>
                <p>Address</p>
                <p>Remove</p>
            </div>
            <hr />
            {alldestination.map((e) => {
                if (favouriteItems[e.id] > 0) {
                    return <div>
                        <div className="favitems-format favtitems-format-main">
                            <img src={`https://firebasestorage.googleapis.com/v0/b/ghumdim.appspot.com/o/${e.photo}?alt=media`} alt="" className='favicon-destination-icon' />
                            <p>{e.name}</p>
                            <p>{e.category}</p>
                            <p>{e.address}</p>
                            <img className='favicon-remove-icon' src={remove_icon} onClick={() => { removeFromFavourite(e.id) }} alt="" />
                        </div>
                        <hr />
                    </div>
                }
                return null;
            })}
        </div>
    )

}

export default FavouriteDestination