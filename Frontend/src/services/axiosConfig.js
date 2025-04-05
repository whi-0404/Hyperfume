import axios from 'axios';
import { refreshToken } from './authToken'; 

const api = axios.create({
    baseURL: 'http://localhost:8080/hyperfume',
    headers: {
        'Content-Type': 'application/json',
    },
    withCredentials: true
});
let isRefreshing = false;
// Queue of requests to retry after token refresh
let failedQueue = [];

// Helper to process the failed queue
const processQueue = (error, token = null) => {
  failedQueue.forEach(promise => {
    if (error) {
      promise.reject(error);
    } else {
      promise.resolve(token);
    }
  });
  
  failedQueue = [];
};

// Response interceptor
api.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    const originalRequest = error.config;
    
    // Check if the response has the exact structure shown in the error payload
    if (error.response && 
        error.response.data && 
        error.response.data.code === 1040 && 
        error.response.data.message === "Access token has expired" && 
        !originalRequest._retry) {
      
      // Mark as retry attempt
      originalRequest._retry = true;
      
      // If refresh is already in progress, add to queue
      if (isRefreshing) {
        return new Promise((resolve, reject) => {
          failedQueue.push({ resolve, reject });
        })
          .then(() => {
            return api(originalRequest);
          })
          .catch(err => {
            return Promise.reject(err); 
          });
      }
      
      isRefreshing = true;
      
      try {
        // Call refresh tokens endpoint
        await refreshToken();
        
        // Signal all requests in queue to continue
        processQueue(null);
        isRefreshing = false;
        
        // Retry original request
        return api(originalRequest);
      } catch (refreshError) {
        // Handle refresh token failure
        processQueue(refreshError);
        isRefreshing = false;
        
        // Redirect to login or handle as needed
        // For example: window.location.href = '/login';
        return Promise.reject(refreshError);
      }
    }
    
    // Return any other errors normally
    return Promise.reject(error);
  }
);

export default api;