import React, { createContext, useState, useEffect } from "react";
import Axios from 'axios'

export const DestinationContext = createContext(null);

const DestinationContextProvider = (props) => {

    const [alldestination, setAllDestination] = useState([]);
    const [favouriteItems, setFavouriteItems] = useState(getDefaultFavourite());

    // const [favouriteItems, setFavouriteItems] = useState(getDefaultFavourite(alldestination));
    useEffect(() => {
        Axios.get("http://localhost:8080/ghumdim/viewAllDestination").then((res) => {
            console.log(res.data);
            setAllDestination(res.data);
        })
            .catch((error) => {
                console.error('error fetching data', error);
            });
    }, []);

    // Function to initialize favourite items based on all destinations
    function getDefaultFavourite() {
        let favourite = {};
        alldestination.forEach(destination => {
            favourite[destination.id] = 0;
        });
        return favourite;
    }

    // add to cart
    const addToFavourite = (itemId) => {
        setFavouriteItems((prev) => {
            const updatedFavourites = { ...prev, [itemId]: prev[itemId] + 1 };
            return updatedFavourites;
        });
    }

    // remove from cart
    const removeFromFavourite = (itemId) => {
        setFavouriteItems((prev) => {
            if (prev[itemId] > 0) {
                const updatedFavourites = { ...prev, [itemId]: prev[itemId] - 1 };
                return updatedFavourites;
            }
            return prev;
        });
    }

    //Total Favourite Destinations
    const getTotalFavDestinations = () => {
        let totalDestination = 0;
        for (const item in favouriteItems) {
            if (favouriteItems[item] > 0) {
                totalDestination += favouriteItems[item];
            }
        }
        return totalDestination
    }

    const contextValue = { alldestination, favouriteItems, addToFavourite, removeFromFavourite, getTotalFavDestinations };

    return (
        <DestinationContext.Provider value={contextValue}>
            {props.children}
        </DestinationContext.Provider>
    )
}

export default DestinationContextProvider;
