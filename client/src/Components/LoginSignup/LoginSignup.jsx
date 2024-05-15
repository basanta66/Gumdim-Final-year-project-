
import React, { useState } from 'react';
import './LoginSignup.css';
import user_icon from '../Assets/person.png';
import email_icon from '../Assets/email.png';
import password_icon from '../Assets/password.png';
import loginimg from '../Assets/loginimg.png';
import Axios from 'axios';
import { useNavigate } from "react-router-dom";

// import { Link } from 'react-router-dom';
// import jwt_decode from 'jwt-decode';
// import { useHistory } from 'react-router-dom';


const LoginSignup = () => {
    const navigate = useNavigate();
    const [action, setAction] = useState('Sign Up');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [age, setAge] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [loading, setLoading] = useState(false);

    const [jwt, setJwt] = useState(''); // State to hold the JWT
    const [decodedJwt, setDecodedJwt] = useState(null); // State to hold the decoded JWT



    const handleFormSubmit = async (e) => {
        e.preventDefault();

        // Perform validation
        if (action === 'Sign Up') {
            if (!firstName || !lastName || !age || !email || !password) {
                setErrorMessage('Please fill in all fields.');
                return;
            }
        } else {
            if (!email || !password) {
                setErrorMessage('Please fill in all fields.');
                return;
            }
        }

        // Reset error message
        setErrorMessage('');
        setLoading(true);

        try {
            let response;
            if (action === 'Sign Up') {
                response = await Axios.post('http://localhost:8080/ghumdim/register', {
                    firstName,
                    lastName,
                    age,
                    email,
                    password
                });
                console.log('signup successfull');
            } else {
                response = await Axios.post('http://localhost:8080/ghumdim/authenticate', {
                    email,
                    password

                });
                console.log('login successful');
                localStorage.setItem('jwt', response.data.token);

                navigate("/");
                window.location.reload();
            }
            // // Redirect to home page after successful login
            // history.push('/home');
            // Handle successful response
        } catch (error) {
            console.error('Error:', error.response.data);
            setErrorMessage(error.response.data.message || 'An error occurred.');
        }
        setLoading(false);
    };

    return (
        <div className='container'>
            <div className='container-left'>
                <img src={loginimg} alt='' />
            </div>

            <div className='container-right'>
                <form onSubmit={handleFormSubmit}>
                    <div className='header'>
                        <div className='signup'>{action}</div>
                        <div className='underline'></div>
                    </div>

                    <div className='inputs'>
                        {action === 'Login' ? (
                            <div></div>
                        ) : (
                            <>
                                <div className='input'>
                                    <img src={user_icon} alt='' />
                                    <input
                                        type='text'
                                        placeholder='First Name'
                                        value={firstName}
                                        onChange={(e) => setFirstName(e.target.value)}
                                        autoComplete='given-name' // Specify autocomplete attribute
                                    />
                                </div>
                                <div className='input'>
                                    <img src={user_icon} alt='' />
                                    <input
                                        type='text'
                                        placeholder='Last Name'
                                        value={lastName}
                                        onChange={(e) => setLastName(e.target.value)}
                                        autoComplete='family-name' // Specify autocomplete attribute
                                    />
                                </div>
                                <div className='input'>
                                    <img src={user_icon} alt='' />
                                    <input
                                        type='text'
                                        placeholder='Age'
                                        value={age}
                                        onChange={(e) => setAge(e.target.value)}
                                        autoComplete='age' // Specify autocomplete attribute
                                    />
                                </div>
                            </>
                        )}

                        <div className='input'>
                            <img src={email_icon} alt='' />
                            <input
                                type='email'
                                placeholder='Email Id'
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                autoComplete='email' // Specify autocomplete attribute
                            />
                        </div>

                        <div className='input'>
                            <img src={password_icon} alt='' />
                            <input
                                type='password'
                                placeholder='Password'
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                autoComplete='new-password' // Specify autocomplete attribute
                            />
                        </div>
                    </div>

                    {/* {action === 'Sign Up' ? (
                        <div></div>
                    ) : (
                        <div className='forgot-password'>
                            Forgot Password? <span>Click Here!</span>
                        </div>
                    )} */}

                    {errorMessage && <div className='error-message'>{errorMessage}</div>}

                    <div className='submit-container'>
                        <button
                            type='button'
                            className={action === 'Login' ? 'submit gray' : 'submit'}
                            onClick={() => {
                                setAction('Sign Up');
                                setErrorMessage('');
                            }}>
                            Sign Up
                        </button>
                        <button
                            type='button'
                            className={action === 'Sign Up' ? 'submit gray' : 'submit'}
                            onClick={() => {
                                setAction('Login');
                                setErrorMessage('');
                            }}>
                            Login
                        </button>
                    </div>
                    <div className='submit-btn'>
                        <button type='submit' className='submit  ' disabled={loading}>
                            {loading ? 'Loading...' : 'Submit'}
                        </button>
                    </div>

                </form>
            </div>
        </div>
    );
};

export default LoginSignup;

// import React, { useState } from 'react';
// import './LoginSignup.css';
// import user_icon from '../Assets/person.png';
// import email_icon from '../Assets/email.png';
// import password_icon from '../Assets/password.png';
// import loginimg from '../Assets/loginimg.png';
// import Axios from 'axios';
// import { useHistory } from 'react-router-dom'; // Import useHistory to handle redirection

// const LoginSignup = () => {
//     const [action, setAction] = useState('Sign Up');
//     const [firstName, setFirstName] = useState('');
//     const [lastName, setLastName] = useState('');
//     const [age, setAge] = useState('');
//     const [email, setEmail] = useState('');
//     const [password, setPassword] = useState('');
//     const [errorMessage, setErrorMessage] = useState('');
//     const [loading, setLoading] = useState(false);
//     const history = useHistory(); // Initialize useHistory hook

//     const handleFormSubmit = async (e) => {
//         e.preventDefault();
//         setErrorMessage('');
//         setLoading(true);

//         try {
//             let response;
//             if (action === 'Sign Up') {
//                 response = await Axios.post('http://localhost:8080/ghumdim/register', {
//                     firstName,
//                     lastName,
//                     age,
//                     email,
//                     password
//                 });
//             } else {
//                 response = await Axios.post('http://localhost:8080/ghumdim/authenticate', {
//                     email,
//                     password
//                 });

//                 // Store JWT token in session storage upon successful login
//                 sessionStorage.setItem('token', response.data.token);
//                 // Redirect to home page after successful login
//                 history.push('/');
//             }
//         } catch (error) {
//             console.error('Error:', error.response.data);
//             setErrorMessage(error.response.data.message || 'An error occurred.');
//         }
//         setLoading(false);
//     };

//     return (
//         <div className='container'>
//             <div className='container-left'>
//                 <img src={loginimg} alt='' />
//             </div>

//             <div className='container-right'>
//                 <form onSubmit={handleFormSubmit}>
//                     {/* Form fields */}

//                     <div className='submit-btn'>
//                         <button type='submit' className='submit  ' disabled={loading}>
//                             {loading ? 'Loading...' : 'Submit'}
//                         </button>
//                     </div>
//                 </form>
//             </div>
//         </div>
//     );
// };

// export default LoginSignup;

