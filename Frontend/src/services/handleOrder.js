import api from "./axiosConfig";

export const getOrders = async () => {
    try {
        const response = await api.get('/orders')
        return response.data;
    } catch (error) {
        console.error('Error fetching orders:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
}


export const createOrder = async (data) => {
    try {
        const response = await api.post('/orders', data)
        return response.data;
    } catch (error) {
        console.error('Error create order:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
}