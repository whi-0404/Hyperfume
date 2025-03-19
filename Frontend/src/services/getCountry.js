import api from './axiosConfig';

export const countries = async () => {
    try {
        const response = await api.get('/countries');
        return response; // Trả về dữ liệu từ response
    } catch (error) {
        console.error('Error fetching countries:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
};
