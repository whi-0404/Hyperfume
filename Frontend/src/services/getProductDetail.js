import api from "./axiosConfig";

const getProductDetail = async (id) => {
    try {
        const response = await api.get(`/perfumes/${id}`)
        return response.data;
    } catch (error) {
        console.error('Error fetching products:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
}

export default getProductDetail;