import React, { useContext, useEffect, useState } from 'react'
import { DestinationContext } from '../Context/DestinationContext'
import { useParams } from 'react-router-dom';
import Breadcrum from '../Components/Breadcrum/Breadcrum';
import DestinationDisplay from '../Components/DestinationDisplay/DestinationDisplay';
import DescriptionBox from '../Components/DescriptionBox/DescriptionBox';
import RelatedDestination from '../Components/RelatedDestination/RelatedDestination';
import { jwtDecode } from "jwt-decode";



const Destination = () => {

  const { alldestination } = useContext(DestinationContext);
  const { destinationId } = useParams();
  const destination = alldestination.find((e) => e.id === Number(destinationId));
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
    <div>
      {/* Check if destination is not null before passing it to components */}
      {destination && (
        <>
          <Breadcrum destination={destination} />
          <DestinationDisplay destination={destination} />
          {decodedToken?.roles === 'CLIENT' && <DescriptionBox />}
          {/* <DescriptionBox /> */}
          <RelatedDestination />
        </>
      )}
    </div>
  );
}

export default Destination;
