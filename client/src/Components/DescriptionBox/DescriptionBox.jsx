
import React, { useState, useEffect } from 'react';
import './DescriptionBox.css';
import Axios from 'axios';
import { useParams } from 'react-router-dom';
import StarRating from '../StarRating/StarRating'; // Import the StarRating component
import { jwtDecode } from "jwt-decode";

const DescriptionBox = () => {
    const { destinationId } = useParams();
    const [reviews, setReviews] = useState([]);
    const [newReview, setNewReview] = useState('');
    const [rating, setRating] = useState(0); // State to store the selected rating

    useEffect(() => {
        fetchReviews();
    }, []);
    const fetchReviews = () => {
        Axios.get(`http://localhost:8080/ghumdim/getReview/${destinationId}`)
            .then((res) => {
                console.log('data fetched', res.data);
                setReviews(res.data);
            })
            .catch((error) => {
                console.error('error fetching data', error);
            });
    };

    const handleReviewSubmit = (e) => {
        e.preventDefault();
        if (newReview.trim() === '') {
            alert('Please enter a review comment.');
            return;
        }

        const reviewData = {
            reviewDetail: newReview,
            rating: rating, // Include the rating in the review data
            // user: { userId: decodedToken?.userId },
            user: decodedToken.userId,
            destination: destinationId
        };

        Axios.post("http://localhost:8080/ghumdim/createReview", reviewData)
            .then((res) => {
                console.log('Review posted successfully:', res.data);
                fetchReviews();
                setNewReview('');
                setRating(0); // Reset the rating after posting the review
            })
            .catch((error) => {
                console.error('Error posting review:', error);
            });
    };

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
                // Handle error if unable to decode token
            }
        } else {
            // Handle case where token is null or empty
        }
    }, []);

    return (
        <div className='descriptionbox'>
            <div className='descriptionbox-navigator'>
                <div className="descriptionbox-nav-box fade">
                    Reviews
                </div>
            </div>
            <div className="descriptionbox-description">
                {reviews.map((review) => (
                    <div key={review.name}>
                        <p>Reviewer Name: {review?.userName}</p>
                        <p>Comment: {review?.reviewDetail}</p>

                        <hr />
                    </div>
                ))}
            </div>
            <hr />
            <div className="add-review">
                <form onSubmit={handleReviewSubmit}>
                    <textarea
                        placeholder="Write your review..."
                        value={newReview}
                        onChange={(e) => setNewReview(e.target.value)}
                        required
                    ></textarea>
                    <StarRating onChange={setRating} /> {/* Include the StarRating component */}
                    <button type="submit">Post Review</button>
                </form>
            </div>
        </div>
    );
};

export default DescriptionBox;