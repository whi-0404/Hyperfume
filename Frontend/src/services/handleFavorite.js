import api from './axiosConfig';

export const listFavorites = async (page = 1, size = 10) => {
    try {
        const response = await api.get(`/users/favorites?page=${page}&size=${size}`);
        return response; // Trả về dữ liệu từ response
    } catch (error) {
        console.error('Error fetching favorite products:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn nếu cần
    }
}


export const addToFavorite = async (perfumeId) => {
    try {
        console.log(`Sending API request to add perfume ${perfumeId} to favorites`);
        const response = await api.post(`/users/favorites/${perfumeId}`);
        console.log("API addToFavorite response:", response);
        
        if (response && response.data) {
            return response.data;
        }
        return response;
    } catch (error) {
        console.error('Error adding to favorites:', error);
        throw error;
    }
}

export const removeFromFavorite = async (perfumeId) => {
    try {
        console.log(`Sending API request to remove perfume ${perfumeId} from favorites`);
        const response = await api.delete(`/users/favorites/${perfumeId}`);
        console.log("API removeFromFavorite response:", response);
        
        if (response && response.data) {
            return response.data;
        }
        return response;
    } catch (error) {
        console.error('Error removing from favorites:', error);
        throw error;
    }
}

export const checkFavoritePerfume = async (perfumeId) => {
    try {
        console.log(`Sending API request to check if perfume ${perfumeId} is in favorites`);
        const response = await api.get(`/users/favorites/checkFavorite?perfumeId=${perfumeId}`);
        console.log("API checkFavoritePerfume response:", response);
        
        // Kiểm tra response và trả về đúng định dạng
        if (response && response.data) {
            return response.data; // Trả về dữ liệu từ response.data để dễ sử dụng
        }
        return response; // Trả về dữ liệu từ response nếu không có response.data
    } catch (error) {
        console.error('Error checking favorite:', error);
        throw error; // Ném lỗi để xử lý ở cấp cao hơn
    }
}
