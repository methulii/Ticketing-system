import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import AdminPage from "./AdminPage.jsx";
import CustomerPage from "./CustomerPage.jsx";
import './App.css';

function App() {
    return (
        <Router>
            <div className="App">
                <h1>Ticketing System</h1>
                <Routes>
                    <Route path="/admin" element={<AdminPage />} />
                    <Route path="/customer" element={<CustomerPage />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;


