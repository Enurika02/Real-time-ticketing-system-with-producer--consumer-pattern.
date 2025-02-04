import React from "react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
} from "recharts";

const TicketChart = ({ data, nextFetch }) => {
  return (
    <div className="border rounded-lg p-4 bg-white">
      <h2 className="text-xl font-semibold mb-4">Ticket Activity</h2>

      <LineChart width={800} height={400} data={data}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis
          dataKey="timestamp"
          tickFormatter={(time) => new Date(time).toLocaleTimeString()}
        />
        <YAxis />
        <Tooltip
          labelFormatter={(label) => new Date(label).toLocaleTimeString()}
        />
        <Legend />
        <Line
          type="monotone"
          dataKey="availableTickets"
          stroke="#8884d8"
          name="Available Tickets"
        />
        <Line
          type="monotone"
          dataKey="activeVendors"
          stroke="#82ca9d"
          name="Active Vendors"
        />
        <Line
          type="monotone"
          dataKey="activeCustomers"
          stroke="#ffc658"
          name="Active Customers"
        />
      </LineChart>

      <div className="text-sm text-gray-500 mt-8 mb-3">Updating in...</div>
      <div className="mb-5 h-2 overflow-hidden rounded-full bg-gray-200">
        <div
          className="h-2 transition-width duration-1000 animate-pulse rounded-full bg-blue-600"
          style={{ width: `${nextFetch}%` }}
        ></div>
      </div>
    </div>
  );
};

export default TicketChart;

