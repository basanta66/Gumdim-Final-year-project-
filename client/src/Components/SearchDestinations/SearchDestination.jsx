import React, { useEffect, useState } from 'react';
import Axios from 'axios';
import Item from '../Items/Item';
import { useParams } from 'react-router-dom';

const SearchDestination = () => {
    const { query } = useParams();
    const [searchResults, setSearchResults] = useState([]);

    useEffect(() => {
        const fetchSearchResults = async () => {
            try {
                const response = await Axios.get(`http://localhost:8080/ghumdim/viewDestination/cosearch/${query}`);
                setSearchResults(response.data);
            } catch (error) {
                console.error('Error fetching search results:', error);
            }
        };

        fetchSearchResults(); // Fetch search results whenever the query parameter changes
    }, [query]);

    return (
        <div className='destination-category'>
            <div className="destinationcategory-places">
                {searchResults.map((item) => (
                    <Item
                        key={item.id}
                        id={item.id}
                        name={item.name}
                        photo={item.photo}
                        address={item.address}
                        status={item.status}
                    />
                ))}
            </div>
        </div>
    );
}

export default SearchDestination;


