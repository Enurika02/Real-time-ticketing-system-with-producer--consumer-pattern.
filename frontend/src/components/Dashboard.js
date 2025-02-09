import React, { useState, useEffect } from "react";
import { ticketingApi } from "../api/ticketingApi";
import VendorPanel from "./VendorPanel";
import CustomerPanel from "./CustomerPanel";
import SystemStatus from "./SystemStatus";
import TicketChart from "./TicketChart";

const FETCH_INTERVAL = 5000;
const NEXT_FETCH_UPDATE_INTERVAL = 1000;

const Dashboard = () => {
  const [status, setStatus] = useState({
    availableTickets: 0,
    totalTickets: 0,
    activeVendors: 0,
    activeCustomers: 0,
  });

  const [statusHistory, setStatusHistory] = useState([]);

  const [fetchedVendors, setFetchedVendors] = useState([]);
  const [fetchedCustomers, setFetchedCustomers] = useState([]);

  const [nextFetch, setNextFetch] = useState(0);

  useEffect(() => {
    const fetchStatus = async () => {
      try {
        const statusResponse = await ticketingApi.getStatus();
        const newStatus = {
          availableTickets: statusResponse.availableTickets,
          soldTickets: statusResponse.soldTickets,
          totalTickets: statusResponse.totalTickets,
          activeVendors: statusResponse.activeVendors?.length,
          activeCustomers: statusResponse.activeCustomers?.length,
        };
        setStatus(newStatus);
        setStatusHistory((prev) =>
          [...prev, { ...newStatus, timestamp: new Date() }].slice(-20),
        );
        setNextFetch(0);

        if (fetchedCustomers.length === 0) {
          setFetchedCustomers(statusResponse.activeCustomers);
        }
        if (fetchedVendors.length === 0) {
          setFetchedVendors(statusResponse.activeVendors);
        }
      } catch (error) {
        console.error("Error fetching status:", error);
      }
    };

    const interval = setInterval(fetchStatus, FETCH_INTERVAL);

    return () => {
      console.log("clearing 1");
      clearInterval(interval);
    };
  }, [fetchedCustomers.length, fetchedVendors.length]);

  useEffect(() => {
    const updateNextFetch = () => {
      if (nextFetch < 100) {
        setNextFetch(nextFetch + 25);
      }
    };

    const nextFetchInterval = setInterval(
      updateNextFetch,
      NEXT_FETCH_UPDATE_INTERVAL,
    );

    return () => {
      console.log("clearing 2");
      clearInterval(nextFetchInterval);
    };
  }, [nextFetch]);

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold mb-4">
        Event Ticketing System Dashboard
      </h1>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <VendorPanel vendors={fetchedVendors} />
        <CustomerPanel customers={fetchedCustomers} />
      </div>

      <div className="mt-4">
        <SystemStatus status={status} />
      </div>

      <div className="mt-4">
        <TicketChart data={statusHistory} nextFetch={nextFetch} />
      </div>
    </div>
  );
};

export default Dashboard;
