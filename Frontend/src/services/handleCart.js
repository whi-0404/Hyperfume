import api from "./axiosConfig";

export const getCart = async () => {
    try {
        const response = await api.get('/carts');
        return response;
    } catch (error) {
        throw error; 
    }
};

export const deleteCartId = async (cartId) => {
    try {
        const response = await api.delete(`/carts/${cartId}`);
        return response;
    } catch (error) {
        console.error("Error delete cartId:", error);
        throw error; 
    }
};  

export const addToCart = async (data) => {
    try {
        const response = await api.post('/carts', data);
        return response;
    } catch (error) {
        console.error("Error add cart:", error);
        throw error; 
    }
};

export const updateQuantity = async (data) => {
    try {
        const response = await api.put('/carts/update_quantity', data);
        return response;
    } catch (error) {
        console.error("Error update quantity:", error);
        throw error; 
    }
};
