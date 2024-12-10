import React from 'react';
import ReactDOM from 'react-dom/client'; // Importing React DOM for modern React
import App from './App'; // Importing the main App component
import './index.css'; // Global styles if needed

// Rendering the App component into the root div in index.html
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <App />
    </React.StrictMode>
);
