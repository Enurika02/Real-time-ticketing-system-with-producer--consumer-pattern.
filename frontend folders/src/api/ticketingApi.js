import axios from "axios";

const BASE_URL = "http://localhost:8080/api/ticketing";

export const ticketingApi = {
  startVendor: async (vendorId) => {
    return axios.post(`${BASE_URL}/vendors/${vendorId}/start`);
  },

  stopVendor: async (vendorId) => {
    return axios.post(`${BASE_URL}/vendors/${vendorId}/stop`);
  },

  startCustomer: async (customerId) => {
    return axios.post(`${BASE_URL}/customers/${customerId}/start`);
  },

  stopCustomer: async (customerId) => {
    return axios.post(`${BASE_URL}/customers/${customerId}/stop`);
  },

  getStatus: async () => {
    const response = await axios.get(`${BASE_URL}/status`);
    return response.data;
  },
};

