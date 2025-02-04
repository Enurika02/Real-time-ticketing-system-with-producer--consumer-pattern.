import React, { useState, useEffect } from "react";
import { ticketingApi } from "../api/ticketingApi";

const CustomerPanel = ({ customers }) => {
  const [customerId, setCustomerId] = useState("");
  const [activeCustomers, setActiveCustomers] = useState(new Set(customers));

  useEffect(() => {
    setActiveCustomers(new Set(customers));
  }, [customers]);

  const handleStartCustomer = async () => {
    if (!customerId) return;

    try {
      await ticketingApi.startCustomer(customerId);
      setActiveCustomers((prev) => new Set([...prev, customerId]));
      setCustomerId("");
    } catch (error) {
      console.error("Error starting customer:", error);
    }
  };

  const handleStopCustomer = async (id) => {
    try {
      await ticketingApi.stopCustomer(id);
      setActiveCustomers((prev) => {
        const newSet = new Set(prev);
        newSet.delete(id);
        return newSet;
      });
    } catch (error) {
      console.error("Error stopping customer:", error);
    }
  };

  return (
    <div className="border rounded-lg p-4 bg-white h-64 overflow-y-auto">
      <h2 className="text-xl font-semibold mb-4">
        Ticket Customers{" "}
        <span className="text-gray-400">({activeCustomers.size})</span>
      </h2>

      <div className="flex gap-2 mb-4">
        <input
          type="text"
          value={customerId}
          onChange={(e) => setCustomerId(e.target.value)}
          placeholder="Enter customer ID"
          className="border rounded px-2 py-1 flex-1"
        />
        <button
          onClick={handleStartCustomer}
          className="bg-blue-600 text-white px-4 py-1 rounded hover:bg-blue-500"
        >
          Start Customer
        </button>
      </div>

      <div className="space-y-2">
        {[...activeCustomers].map((id) => (
          <div
            key={id}
            className="flex justify-between items-center border rounded p-2"
          >
            <span>Customer: {id}</span>
            <button
              onClick={() => handleStopCustomer(id)}
              className="bg-blue-400 text-white px-3 py-1 rounded hover:bg-blue-600"
            >
              Stop
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CustomerPanel;
