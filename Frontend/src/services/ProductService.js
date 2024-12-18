import axios from 'axios';

export const listProducts = () => {
    return axios.get('http://localhost:8080/hyperfume/perfumes');
}