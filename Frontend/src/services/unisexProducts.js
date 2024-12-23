import api from './axiosConfig';

export const unisexProducts = async () => {
    try {
        const response = await api.get('/perfumes/collections',
            {
                params: { gender: 'Unisex' },
            });
        return response; // Trả về dữ liệu từ response
    } catch (error) {
        console.error('Error fetching products:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
};