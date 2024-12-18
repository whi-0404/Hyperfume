import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/hyperfume',
    headers: {
        'Content-Type': 'application/json',
    },
});

export default api;