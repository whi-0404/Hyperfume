import api from './axiosConfig';

export const searchProducts = async (productName) => {
    try {
        const response = await api.get('/perfumes/search',
            {
                params: { name: productName },
            });
        return response; // Trả về dữ liệu từ response
    } catch (error) {
        console.error('Error fetching products:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
};