import api from './axiosConfig';

export const listProducts = () => {
    return api.get('/perfumes/collections', {
        params: {
            gender: 'Nam'
        }
    });
}
