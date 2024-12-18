import api from './perfumeVarient';

export const listProducts = () => {
    return api.get('/perfumes');
}