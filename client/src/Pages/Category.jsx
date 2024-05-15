import React, { useState, useEffect } from 'react'
import './CSS/Category.css'
// import { DestinationContext } from '../Context/DestinationContext'
import dropdown_icon from '../Components/Assets/dropdown_icon.png'
import Item from '../Components/Items/Item'
import Axios from 'axios';

const Category = (props) => {

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

  return (
    <div className='destination-category'>
      <div className='destiantioncategory-indexSort'>

      </div>
      <div className="destinationcategory-places">

        {alldestination.map((item, i) => {

          if (props.category.toLowerCase() === item.category.toLowerCase()) {

            return <Item key={i} id={item.id} name={item.name} photo={item.photo} address={item.address} status={item.status} />
          }
          else {
            return null;
          }
        })}


      </div>

    </div>
  )
}

export default Category

