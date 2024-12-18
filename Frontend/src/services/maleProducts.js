import api from './axiosConfig';

export const maleProducts = async () => {
    try {
        const response = await api.get('/perfumes/collections', {
            params: { gender: 'Nam' },
        });
        return response.data; // Trả về dữ liệu từ response
    } catch (error) {
        console.error('Error fetching products:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
};
