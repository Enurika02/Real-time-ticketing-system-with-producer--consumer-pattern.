import React from "react";

const SystemStatus = ({ status }) => {
  return (
    <div className="border rounded-lg p-4 bg-white">
      <h2 className="text-xl font-semibold mb-4">System Status</h2>

      <div className="grid grid-cols-4 gap-4">
        <div className="border rounded p-4 text-center">
          <div className="text-2xl font-semibold text-blue-600">
            {status.availableTickets}
          </div>
          <div className="text-sm text-gray-500">Available for Purchase</div>
        </div>

        <div className="border rounded p-4 text-center">
          <div className="text-2xl font-semibold text-blue-600">
            {Math.min(status.soldTickets, status.totalTickets)}
            <span className="text-gray-400"> / {status.totalTickets}</span>
          </div>
          <div className="text-sm text-gray-500">Total Tickets Sold</div>
        </div>

        <div className="border rounded p-4 text-center">
          <div className="text-2xl font-semibold text-blue-600">
            {status.activeVendors}
          </div>
          <div className="text-sm text-gray-500">Active Vendors</div>
        </div>

        <div className="border rounded p-4 text-center">
          <div className="text-2xl font-semibold text-blue-600">
            {status.activeCustomers}
          </div>
          <div className="text-sm text-gray-500">Active Customers</div>
        </div>
      </div>
    </div>
  );
};

export default SystemStatus;
