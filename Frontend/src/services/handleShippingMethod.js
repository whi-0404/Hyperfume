import api from "./axiosConfig";

export const getShippingMethods = async () => {
    try {
        const response = await api.get('/shipping_methods')
        return response.data;
    } catch (error) {
        console.error('Error fetching shipping methods:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
}
