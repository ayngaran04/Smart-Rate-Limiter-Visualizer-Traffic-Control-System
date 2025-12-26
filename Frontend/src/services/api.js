import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

export const sendRateLimitRequest = async (config) => {
  try {
    const response = await axios.post(`${API_BASE_URL}/sim/request`, {
      clientId: config.clientId,
      algorithm: config.algorithm,
      capacity: config.capacity,
      rate: config.rate
    });

    return {
      allowed: response.data.allowed,
      status: response.status
    };
  } catch (error) {
    // Handle 429 status (Too Many Requests) which still has a response body
    if (error.response && error.response.status === 429) {
      return {
        allowed: error.response.data.allowed || false,
        status: 429
      };
    }
    
    // For other errors, log and return denied
    console.error('API Error:', error);
    throw error;
  }
};
