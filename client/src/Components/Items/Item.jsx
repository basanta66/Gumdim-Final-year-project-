import React from 'react'
import './Item.css'
import { Link } from 'react-router-dom'

const Item = (props) => {
  // const [alldestination, setAllDestination] = useState([]);

  return (
    <div className='item'>
      <Link to={`/destination/${props.id}`}>{<img src={`https://firebasestorage.googleapis.com/v0/b/ghumdim.appspot.com/o/${props.photo}?alt=media`} alt="" />}</Link>
      <p>{props.name}</p>
      <p>{props.address}</p>
      <p>{props.status}</p>
    </div >


  )
}

export default Item;
