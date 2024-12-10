import React, { useState, useEffect } from "react";
import axios from "axios";
import './AdminPage.css';

const AdminPage = () => {
    const [customers, setCustomers] = useState([]);
    const [customerCount, setCustomerCount] = useState(0);
    const [newTicket, setNewTicket] = useState("");
    const [ticketCount, setTicketCount] = useState(0);
    const [customerEmail, setCustomerEmail] = useState("");
    const [customerDetails, setCustomerDetails] = useState(null);

    // Fetch available tickets and customers from the backend
    useEffect(() => {
        fetchTickets();
        fetchCustomers();
    }, []);

    // Function to fetch available tickets
    const fetchTickets = () => {
        axios.get("http://localhost:8080/tickets/available")
            .then(response => {
                setTicketCount(response.data); // Set the available ticket count
            })
            .catch(error => {
                console.error("Error fetching tickets", error);
            });
    };

    // Function to fetch customers
    const fetchCustomers = () => {
        axios.get("http://localhost:8080/customers/all")
            .then(response => {
                setCustomers(response.data);
                setCustomerCount(response.data.length); // Set the customer count
            })
            .catch(error => {
                console.error("Error fetching customers", error);
            });
    };

    // Fetch customer by email
    const fetchCustomerByEmail = () => {
        if (!customerEmail) {
            alert("Please enter an email.");
            return;
        }

        axios.post("http://localhost:8080/customers/find", { email: customerEmail })
            .then(response => {
                if (response.data === "Customer exists") {
                    const foundCustomer = customers.find(customer => customer.email === customerEmail);
                    setCustomerDetails(foundCustomer); // Set the customer details
                } else {
                    alert("Customer not found!");
                    setCustomerDetails(null); // Reset the customer details
                }
            })
            .catch(error => {
                console.error("Error fetching customer", error);
            });
    };

    // Delete customer by email
    const deleteCustomer = (email) => {
        axios.delete("http://localhost:8080/customers/delete", {
            data: { email }
        })
            .then(response => {
                alert(response.data);
                fetchCustomers(); // Refresh the customer list after deletion
            })
            .catch(error => {
                console.error("Error deleting customer", error);
            });
    };

    // Add tickets to the pool
    const addTicket = () => {
        const count = parseInt(newTicket, 10); // Ensure it's a number

        if (isNaN(count) || count <= 0) {
            alert("Please enter a valid number of tickets.");
            return;
        }

        axios.post("http://localhost:8080/tickets/add", { count })
            .then(response => {
                alert(`${response.data} tickets added successfully!`);
                setNewTicket(""); // Reset input field
                fetchTickets(); // Refresh the ticket count
            })
            .catch(error => {
                console.error("Error adding ticket", error);
                alert("Error adding ticket(s). Please try again.");
            });
    };

    return (
        <div className="admin-page">
            <h1>Admin Page</h1>

            {/* Tickets Section */}
            <div className="section1">
                <h3>Total Tickets Available: {ticketCount}</h3>
                <input
                    type="text"
                    placeholder="Number of Tickets"
                    value={newTicket}
                    onChange={(e) => setNewTicket(e.target.value)}
                />
                <button onClick={addTicket} className="button">Add Ticket</button>
            </div>

            {/* Customer Search by Email */}
            <div className="section2">
                <h3>Search Customer by Email</h3>
                <input
                    type="email"
                    placeholder="Enter Customer Email"
                    value={customerEmail}
                    onChange={(e) => setCustomerEmail(e.target.value)}
                />
                <button onClick={fetchCustomerByEmail} className="button">Search</button>
            </div>
            <br/>
            <br/>
            {/* Display customer details if found */}
            {customerDetails && (
                <div className="customer-details">
                    <h3>Customer Details:</h3>
                    <p><strong>ID:</strong> {customerDetails.id}</p>
                    <p><strong>Name:</strong> {customerDetails.name}</p>
                    <p><strong>Email:</strong> {customerDetails.email}</p>
                </div>
            )}

            {/* Customer List */}
            <h2>Customers</h2>
            <table className="admin-table">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Delete Customer</th>
                </tr>
                </thead>
                <tbody>
                {customers.map(customer => (
                    <tr key={customer.id}>
                        <td>{customer.id}</td>
                        <td>{customer.name}</td>
                        <td>{customer.email}</td>
                        <td>
                            <button onClick={() => deleteCustomer(customer.email)} className="button">
                                Delete
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

        </div>
    );
};

export default AdminPage;
