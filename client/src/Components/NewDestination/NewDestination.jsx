
import React, { useState, useEffect } from 'react'
import './NewDestination.css'
import Axios from 'axios'
import { Link } from 'react-router-dom';


import Item from '../Items/Item'

const NewDestination = () => {
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

  // Slice the first 8 items from the ratingdestination array
  const displayedItems = ratingdestination.slice(0, 8);

  return (
    <div className='new-destination'>
      <h1>Destination By Rating</h1>
      <hr />
      <p className='showmore-btn'><Link to="/ratedplaces">Show More</Link> {/* Use Link to navigate to NearPlacesAll */}</p>
      <div className="newdestinations-list">
        {displayedItems.map((item, i) => {
          return <Item key={i} id={item.id} name={item.name} photo={item.photo} address={item.address} />
        })}
      </div>
    </div>
  )
}

export default NewDestination
