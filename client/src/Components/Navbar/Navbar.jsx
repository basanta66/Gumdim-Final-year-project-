import React, { useContext, useState, useEffect } from 'react';
import './Navbar.css';
import { Link } from 'react-router-dom';
import { DestinationContext } from '../../Context/DestinationContext';
import { AiOutlineSearch } from 'react-icons/ai';
import Axios from 'axios'; // Import Axios for making HTTP requests

const Navbar = () => {
  const [menu, setMenu] = useState("home");
  const [resmenu, setResMenu] = useState(false);
  // const [totalFavDestinations, setTotalFavDestinations] = useState(0);
  // const { getTotalFavDestinations } = useContext(DestinationContext);

  const [searchResults, setSearchResults] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [typingTimeout, setTypingTimeout] = useState(null); // Timer for delaying API requests

  useEffect(() => {
    // Function to fetch search results
    const fetchSearchResults = async () => {
      try {
        const response = await Axios.get(`http://localhost:8080/ghumdim/viewDestination/cosearch/${searchQuery}`);
        setSearchResults(response.data);
      } catch (error) {
        console.error('Error fetching search results:', error);
      }
    };

    // Fetch search results only if there's a search query
    if (searchQuery.trim() !== "") {
      fetchSearchResults();
    } else {
      setSearchResults([]);
    }
  }, [searchQuery]);

  // Handler for search input change
  const handleSearchChange = (event) => {
    const searchText = event.target.value;
    setSearchQuery(searchText);

    // Clear previous timer
    if (typingTimeout) {
      clearTimeout(typingTimeout);
    }

    // Set a new timer to delay API request
    setTypingTimeout(setTimeout(() => {
      setSearchQuery(searchText);
    }, 500)); // Adjust the delay as needed
  };

  // Toggle responsive menu
  const handleMenuToggle = () => {
    setResMenu(!resmenu);
  };

  return (
    <div className={`navbar ${resmenu ? 'responsive' : ''}`}>
      <div className="nav-logo">
        <p><span style={{ color: 'rgb(235, 178, 72)' }}>G</span>humdim</p>
      </div>

      <ul className={resmenu ? 'nav-menu-responsive' : 'nav-menu'}>
        <li onClick={() => { setMenu("home") }}><Link style={{ textDecoration: 'none' }} to='/'>Home</Link> {menu === "home" ? <hr /> : <></>} </li>
        <li onClick={() => { setMenu("religious") }}> <Link style={{ textDecoration: 'none' }} to='/religious'>Religious Place</Link>{menu === "religious" ? <hr /> : <></>} </li>
        <li onClick={() => { setMenu("parks") }}> <Link style={{ textDecoration: 'none' }} to='/parks'>Parks</Link>{menu === "parks" ? <hr /> : <></>} </li>
        <li onClick={() => { setMenu("hike") }}> <Link style={{ textDecoration: 'none' }} to='/hike'>Hike</Link>{menu === "hike" ? <hr /> : <></>} </li>
        <li onClick={() => { setMenu("food") }}> <Link style={{ textDecoration: 'none' }} to='/food'>Food</Link>{menu === "food" ? <hr /> : <></>} </li>
      </ul>

      <div className='search-bar'>
        <input type="search" placeholder='Search' value={searchQuery} onChange={handleSearchChange} />
        {/* Use Link to redirect to search page */}
        <Link to={`/search/${searchQuery}`}><button><AiOutlineSearch style={{ width: '25px', height: '27px' }} /></button></Link>
      </div>

      <div className="nav-login-fav">
        <Link to='/login'><i className="fa-solid fa-user"></i></Link>
        {/* <Link to='/favourites'>
          <i className="fa-solid fa-star">
            <span className='red'><sup>{getTotalFavDestinations()}</sup></span>
          </i>
        </Link> */}
      </div>

      <div className="mobile" onClick={handleMenuToggle}>
        <i className={resmenu ? 'fa-solid fa-xmark' : 'fa-solid fa-bars'}></i>
      </div>

    </div>
  );
};

export default Navbar;

