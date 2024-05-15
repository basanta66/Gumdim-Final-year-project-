import React, { useEffect, useState } from 'react'
import './Hero.css'
import home from '../Assets/home.png'
// import hero_right from '../Assets/hero_right.png'
import { Link } from 'react-router-dom';
import { jwtDecode } from "jwt-decode";


const Hero = () => {
    const [decodedToken, setDecodedToken] = useState(null);
    useEffect(() => {
        const token = localStorage.getItem('jwt');

        if (token) {
            try {
                const decoded = jwtDecode(token);
                setDecodedToken(decoded);
                console.log(decoded, decodedToken);
            } catch (error) {
                console.error('Error decoding token:', error);

            }
        } else {
            // Handle case where token is null or empty
        }
    }, []);


    return (
        <div className='hero'>
            <div className="hero-left">
                {/* <h2>Your Travel Companion</h2> */}
                <p>Travel, enjoy <br />and live a new<br /> and full life.</p>
                {decodedToken && decodedToken.roles === 'CLIENT' && (
                    <Link to='add-destination' className='add-des'>
                        <button className='add-des-button'>Add Destinations</button>
                    </Link>
                )}
                {/* <Link to='add-destination' className='add-des'><button className='add-des-button'  >Add Destinations</button></Link> */}
                {/* <Link to='userprofile' ><button className='add-des-button'  >userprofile</button></Link> */}

            </div>
            <div className="hero-right">
                {/* <img src={hero_right} alt="" /> */}
                <img src={home} alt="" />
            </div>
        </div>
    )

}

export default Hero
