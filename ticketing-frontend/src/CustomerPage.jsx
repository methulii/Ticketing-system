import React, { useState } from "react";
import axios from "axios";
import './CustomerPage.css';

const CustomerPage = () => {
    const [customer, setCustomer] = useState({ name: "", email: "" });
    const [ticketStatus, setTicketStatus] = useState("");
    const [ticketCount, setTicketCount] = useState(1); // State for ticket count
    const [purchaseEmail, setPurchaseEmail] = useState(""); // State for purchase email

    // Add a new customer
    const addCustomer = () => {
        axios.post("http://localhost:8080/customers/add", customer)
            .then(response => {
                alert(response.data);
                setCustomer({ name: "", email: "" }); // Reset form
            })
            .catch(error => {
                console.error("Error adding customer", error);
            });
    };

    // Purchase tickets
    const purchaseTicket = () => {
        if (ticketCount <= 0 || !purchaseEmail) {
            alert("Please provide valid email and ticket count.");
            return;
        }

        axios.post(`http://localhost:8080/tickets/purchase`, {
            count: ticketCount,
            customerEmail: purchaseEmail
        })
            .then(response => {
                setTicketStatus("PURCHASED");
                alert(response.data);
            })
            .catch(error => {
                console.error("Error purchasing ticket", error);
            });
    };

    return (
        <div className="customer-page">
            <h1>Customer Page</h1>

            {/* Customer Registration Section */}
            <div className="section">
                <h2>Register as a New Customer</h2>
                <div className="input-group">
                    <input
                        type="text"
                        placeholder="Name"
                        value={customer.name}
                        onChange={(e) => setCustomer({ ...customer, name: e.target.value })}
                        className="input-field"
                    />
                    <input
                        type="email"
                        placeholder="Email"
                        value={customer.email}
                        onChange={(e) => setCustomer({ ...customer, email: e.target.value })}
                        className="input-field"
                    />
                    <button onClick={addCustomer} className="button">Add Customer</button>
                </div>
            </div>

            {/* Ticket Purchase Section */}
            <div className="section">
                <h2>Purchase Ticket</h2>
                <div className="input-group">
                    <input
                        type="number"
                        placeholder="Number of Tickets"
                        value={ticketCount}
                        onChange={(e) => setTicketCount(Number(e.target.value))}
                        className="input-field"
                        min="1"
                    />
                    <input
                        type="email"
                        placeholder="Enter Email for Purchase"
                        value={purchaseEmail}
                        onChange={(e) => setPurchaseEmail(e.target.value)}
                        className="input-field"
                    />
                    <button onClick={purchaseTicket} className="button">Purchase Ticket</button>
                </div>
                <h3>Ticket Status: {ticketStatus || "No tickets purchased"}</h3>
            </div>
        </div>
    );
};

export default CustomerPage;
