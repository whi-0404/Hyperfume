import api from './axiosConfig';

export const genderProducts = async (gender) => {
    try {
        const response = await api.get('/perfumes/byGender',
            {
                params: { gender: gender },
            });
        return response; // Trả về dữ liệu từ response
    } catch (error) {
        console.error('Error fetching products:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
};
