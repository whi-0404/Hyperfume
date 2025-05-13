import api from "./axiosConfig";

export const getRates = async (id) => {
    try {
        const response = await api.get(`/perfumes/${id}/rates`)
        return response.data;
    } catch (error) {
        console.error('Error fetching rates:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
}

export const addRate = async (id, request) => {
    try {
        const response = await api.post(`/perfumes/${id}/rates`, 
            request)
        return response.data;
    } catch (error) {
        console.error('Error add rate:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
}