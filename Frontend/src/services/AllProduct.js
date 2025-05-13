import api from './axiosConfig';

export const listProducts = async (queryParams) => {
    try {
        const response = await api.get('/perfumes',
                {
                    params: queryParams
                });
        return response; // Trả về dữ liệu từ response
    } catch (error) {
        console.error('Error fetching products:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
}