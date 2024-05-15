import React, { useState } from 'react';
import './StarRating.css';

const Star = ({ selected, onSelect }) => {
    return (
        <span className={selected ? 'star selected' : 'star'} onClick={onSelect}>
            ★
        </span>
    );
};

const StarRating = ({ onChange }) => {
    const [rating, setRating] = useState(0);

    const handleSelect = (index) => {
        setRating(index + 1);
        onChange(index + 1); // Pass the selected rating to the parent component
    };

    return (
        <div>
            {[...Array(5)].map((_, index) => (
                <Star key={index} selected={index < rating} onSelect={() => handleSelect(index)} />
            ))}
        </div>
    );
};

export default StarRating;
