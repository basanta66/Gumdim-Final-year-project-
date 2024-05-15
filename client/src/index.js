import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import DestinationContextProvider from './Context/DestinationContext';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(

  <DestinationContextProvider>
    <App />
  </DestinationContextProvider>


  // <React.StrictMode>
  //   <App />
  // </React.StrictMode>

);




