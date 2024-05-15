import React, { useState, useEffect } from 'react';
import './Add.css';
import axios from 'axios';

const AddDestinationForm = () => {
    const [destinationdata, setDestinationData] = useState({
        name: '',
        address: '',
        category: 'RELIGIOUS',
        latitude: '',
        status: 'PENDING',
        contactNumber: '',
        rating: 1,
        description: '',
        author: 1,
        longitude: ''
    });

    const [multiFile, setMultiFile] = useState(null); // State to store file

    const [isCurrentLocation, setIsCurrentLocation] = useState(false); //state for checkbox

    useEffect(() => {
        if (isCurrentLocation && "geolocation" in navigator) {
            navigator.geolocation.getCurrentPosition(function (position) {
                setDestinationData({
                    ...destinationdata,
                    latitude: position.coords.latitude,
                    longitude: position.coords.longitude
                });

            });
        } else {
            setDestinationData({
                ...destinationdata,
                latitude: '',
                longitude: ''
            })

        }
    }, [isCurrentLocation]);

    const handleChange = (e) => {
        const { name, value, type, files } = e.target;
        const newValue = type === 'file' ? files[0] : value;

        setDestinationData({ ...destinationdata, [name]: newValue });
        console.log('formData', destinationdata);
    };


    const handleCurrentLocationChange = (e) => {
        setIsCurrentLocation(e.target.checked);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {

            const formData = new FormData();

            formData.append('placeRequestDto', new Blob([JSON.stringify(destinationdata)], { type: 'application/json' }));
            formData.append('multipartFile', multiFile);

            for (var pair of formData.entries()) {
                if (pair[1] instanceof File) {
                    console.log(pair[0] + ' is a File.');
                    console.log('File name: ' + pair[1].name);
                    console.log('File size: ' + pair[1].size + ' bytes');
                    console.log('File type: ' + pair[1].type);
                } else {
                    console.log(pair[0] + ' is not a File.');
                }
            }

            const response = await fetch('http://127.0.0.1:8080/ghumdim/createDestination', {
                method: "POST",
                body: formData,

            });

            console.log(response.data);
            setDestinationData({
                name: '',
                address: '',
                category: '',
                latitude: '',
                status: 'PENDING', // Reset status field to PENDING
                contactNumber: '',
                rating: 1,
                description: '',
                author: 1,
                longitude: '',

            })
            window.location.href = '/add-destination';
        } catch (error) {
            console.error(error);
        }

    };


    console.log("testing multipart");
    console.log(multiFile);

    return (
        <div className="add-des-container">
            <form onSubmit={handleSubmit} className="add-destination-form">
                <label htmlFor="name">Name:</label>
                <input
                    type="text"
                    id="name"
                    name='name'
                    value={destinationdata.name}
                    onChange={handleChange}
                    required
                />

                <label htmlFor="address">Address:</label>
                <input
                    type="text"
                    id="address"
                    name='address'
                    value={destinationdata.address}
                    onChange={handleChange}
                    required
                />

                <label htmlFor="category">Category:</label>
                <select id="category" name='category' value={destinationdata.category} onChange={handleChange}>
                    <option value="RELIGIOUS">Religious</option>
                    <option value="HIKE">Hike</option>
                    <option value="PARKS">Parks</option>
                    <option value="TREKKING">Trekking</option>
                    <option value="FOOD">Food</option>
                </select>

                <label htmlFor="contactNumber">Contact Number:</label>
                <input
                    type="tel"
                    id="contactNumber"
                    name='contactNumber'
                    value={destinationdata.contactNumber}
                    onChange={handleChange}
                />

                <label htmlFor="status">Status:</label>
                <input
                    type="text"
                    id="status"
                    name='status'
                    value={destinationdata.status}
                    onChange={handleChange}
                    disabled
                />

                <label htmlFor="image">Upload Image:</label>
                <input
                    type="file"
                    id="image"
                    name='multiFile'
                    onChange={(e) => setMultiFile(e.target.files[0])} // Store selected file
                    required
                />
                {multiFile && <img src={URL.createObjectURL(multiFile)} style={{ height: "200px", width: "200px", objectFit: "contain" }}  ></img>}

                <label htmlFor="rating">Ratings:</label>
                <select id="rating" name='rating' value={destinationdata.rating} onChange={handleChange}>
                    {[1, 2, 3, 4, 5].map((num) => (
                        <option key={num} value={num}>
                            {num}
                        </option>
                    ))}
                </select>

                <label htmlFor="description">Description:</label>
                <textarea
                    id="description"
                    name='description'
                    value={destinationdata.description}
                    onChange={handleChange}
                ></textarea>

                <label htmlFor="currentLocation">Is this your current location?</label>
                <input
                    type="checkbox"
                    id="currentLocation"
                    name='currentLocation'
                    checked={isCurrentLocation}
                    onChange={handleCurrentLocationChange}
                /> <br />

                <label htmlFor="latitude">Latitude:</label>
                <input
                    type="text"
                    id="latitude"
                    name='latitude'
                    value={destinationdata.latitude}
                    onChange={handleChange}
                    disabled={isCurrentLocation}
                />

                <label htmlFor="longitude">Longitude:</label>
                <input
                    type="text"
                    id="longitude"
                    name='longitude'
                    value={destinationdata.longitude}
                    onChange={handleChange}
                    disabled={isCurrentLocation}
                />

                <button type="submit">Submit</button>
            </form>
        </div>
    );
};

export default AddDestinationForm;
