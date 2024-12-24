import api from './axiosConfig';

export const createOrder = async () => {
    try {
        const response = await api.get('/perfumes/collections',
            {
                params: { gender: 'Nữ' },
            });
        return response; // Trả về dữ liệu từ response
    } catch (error) {
        console.error('Error fetching products:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
};