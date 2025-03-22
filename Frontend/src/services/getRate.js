import api from "./axiosConfig";

const getRates = async (id) => {
    try {
        const response = await api.get(`/perfumes/${id}/rates`)
        return response.data;
    } catch (error) {
        console.error('Error fetching rates:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
}

export default getRates;