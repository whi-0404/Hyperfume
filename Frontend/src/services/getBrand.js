import api from './axiosConfig';

export const brands = async () => {
    try {
        const response = await api.get('/brands');
        return response; // Trả về dữ liệu từ response
    } catch (error) {
        console.error('Error fetching brands:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
};
