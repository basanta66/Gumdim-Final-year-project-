import React, { useState, useEffect } from 'react';
import Axios from 'axios';
import './UserProfile.css';
import { jwtDecode } from "jwt-decode";


const UserProfile = () => {
    const [decodedToken, setDecodedToken] = useState(null);
    const [userData, setUserData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

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
        }
    }, []); // Only run once when component mounts

    useEffect(() => {
        if (decodedToken) { // Check if decodedToken is not null
            const fetchUserData = async () => {
                try {
                    const response = await Axios.get(`http://localhost:8080/ghumdim/user/${decodedToken.userId}`);
                    setUserData(response.data);
                    setLoading(false); // Set loading to false once data is fetched
                } catch (error) {
                    console.error('Error fetching user data:', error);
                    setError(error); // Set error state if request fails
                    setLoading(false); // Set loading to false even if there's an error
                }
            };

            fetchUserData();
        }
    }, [decodedToken]); // Run whenever decodedToken changes

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    return (
        <div className="user-profile">
            <h2>User Profile</h2>
            {userData && (
                <div className='userprofile-container'>
                    {/* <div>
                        <strong>User:</strong> {decodedToken.userId}
                    </div> */}
                    <div>
                        <strong>Name:</strong> {userData.firstname}
                    </div>
                    {/* <div>
                        <strong>Last Name:</strong> {userData.lastname}
                    </div> */}
                    <div>
                        <strong>Email:</strong> {userData.email}
                    </div>
                    <div>
                        <strong>Location:</strong> {userData.location}
                    </div>
                </div>
            )}
        </div>
    );
};

export default UserProfile;

