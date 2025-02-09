import React, { useState, useEffect } from "react";
import { ticketingApi } from "../api/ticketingApi";

const VendorPanel = ({ vendors }) => {
  console.log("vendors:", vendors);
  const [vendorId, setVendorId] = useState("");
  const [activeVendors, setActiveVendors] = useState(new Set(vendors));

  useEffect(() => {
    setActiveVendors(new Set(vendors));
  }, [vendors]);

  const handleStartVendor = async () => {
    if (!vendorId) return;

    try {
      await ticketingApi.startVendor(vendorId);
      setActiveVendors((prev) => new Set([...prev, vendorId]));
      setVendorId("");
    } catch (error) {
      console.error("Error starting vendor:", error);
    }
  };

  const handleStopVendor = async (id) => {
    try {
      await ticketingApi.stopVendor(id);
      setActiveVendors((prev) => {
        const newSet = new Set(prev);
        newSet.delete(id);
        return newSet;
      });
    } catch (error) {
      console.error("Error stopping vendor:", error);
    }
  };

  return (
    <div className="border rounded-lg p-4 bg-white h-64 overflow-y-auto">
      <h2 className="text-xl font-semibold mb-4">
        Ticket Vendors{" "}
        <span className="text-gray-400">({activeVendors.size})</span>
      </h2>

      <div className="flex gap-2 mb-4">
        <input
          type="text"
          value={vendorId}
          onChange={(e) => setVendorId(e.target.value)}
          placeholder="Enter vendor ID"
          className="border rounded px-2 py-1 flex-1"
        />
        <button
          onClick={handleStartVendor}
          className="bg-blue-600 text-white px-4 py-1 rounded hover:bg-blue-500"
        >
          Start Vendor
        </button>
      </div>

      <div className="space-y-2">
        {[...activeVendors].map((id) => (
          <div
            key={id}
            className="flex justify-between items-center border rounded p-2"
          >
            <span>Vendor: {id}</span>
            <button
              onClick={() => handleStopVendor(id)}
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

export default VendorPanel;
