import api from "./axiosConfig";

export const getPaymentMethods = async () => {
    try {
        const response = await api.get('/payment_method')
        return response.data;
    } catch (error) {
        console.error('Error fetching payment methods:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
}
