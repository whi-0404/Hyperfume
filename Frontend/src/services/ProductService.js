import api from './axiosConfig';

export const listProducts = () => {
    return api.get('/products');
}