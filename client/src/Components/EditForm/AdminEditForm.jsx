
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './AdminEditForm.css';
import { useParams } from 'react-router-dom';

const AdminEditForm = () => {
    const { destinationId } = useParams();
    const [editdestinationdata, setEditDestinationData] = useState({
        name: '',
        address: '',
        category: '',
        latitude: '',
        status: '',
        contactNumber: '',
        rating: 1,
        description: '',
        author: 1,
        longitude: ''
    });

    useEffect(() => {
        axios.get(`http://localhost:8080/ghumdim/viewDestination/${destinationId}`)
            .then((res) => {
                console.log(res.data);
                setEditDestinationData(res.data);
            })
            .catch((error) => {
                console.error('error fetching data', error);
            });
    }, [destinationId]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setEditDestinationData({ ...editdestinationdata, [name]: value });

    };

    // const handleChangeone = (e) => {
    //     const { name, value } = e.target;
    //     setEditDestinationData({ ...editdestinationdata, [name]: value });

    // };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post(`http://localhost:8080/ghumdim/updateDestination/${destinationId}`, editdestinationdata, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            console.log(response.data);
            console.log(editdestinationdata.status);
            // Clear form fields after successful submission
            setEditDestinationData({
                name: '',
                address: '',
                category: '',
                latitude: '',
                status: '',
                contactNumber: '',
                rating: 1,
                description: '',
                author: 1,
                longitude: ''
            });
            // Reload the page
            window.location.reload();
        } catch (error) {
            console.error(error);
        }
    };
    return (
        <div className='admin-edit-form'>
            <form onSubmit={handleSubmit} className="add-edit-form">
                <label htmlFor="name">Name:</label>
                <input
                    type="text"
                    id="name"
                    name='name'
                    value={editdestinationdata.name}
                    onChange={handleChange}
                    required
                />

                <label htmlFor="address">Address:</label>
                <input
                    type="text"
                    id="address"
                    name='address'
                    value={editdestinationdata.address}
                    onChange={handleChange}
                    required
                />

                <label htmlFor="description">Description:</label>
                <textarea
                    id="description"
                    name='description'
                    value={editdestinationdata.description}
                    onChange={handleChange}
                ></textarea>

                <label htmlFor="latitude">Latitude:</label>
                <input
                    type="text"
                    id="latitude"
                    name='latitude'
                    value={editdestinationdata.latitude}
                    onChange={handleChange}
                />

                <label htmlFor="longitude">Longitude:</label>
                <input
                    type="text"
                    id="longitude"
                    name='longitude'
                    value={editdestinationdata.longitude}
                    onChange={handleChange}
                />

                <label htmlFor="status">Status:</label>
                <select
                    id="status"
                    name='status'
                    value={editdestinationdata.status}
                    onChange={handleChange}
                >
                    <option value="PENDING">PENDING</option>
                    <option value="VERIFIED">VERIFIED</option>
                    <option value="DUPLICATE">DUPLICATE</option>
                    <option value="REJECTED">REJECTED</option>
                </select>

                <label htmlFor="category">Category:</label>
                <select
                    id="category"
                    name='category'
                    value={editdestinationdata.category}
                    onChange={handleChange}
                >
                    <option value="RELIGIOUS">Religious</option>
                    <option value="HIKE">Hike</option>
                    <option value="PARKS">Parks</option>
                    <option value="FOOD">Food</option>
                </select>

                <button type="submit">Edit</button>

            </form>
        </div>
    );
};

export default AdminEditForm;
