import api from './axiosConfig';

export const screntFamilies = async () => {
    try {
        const response = await api.get('/screntFamilies');
        return response; // Trả về dữ liệu từ response
    } catch (error) {
        console.error('Error fetching screntFamilies:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
};
