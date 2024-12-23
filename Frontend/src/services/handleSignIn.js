import api from "./axiosConfig";

const signInService = async (username, password) => {
    try {
        const response = await api.post('/auth/token', {
            "username": username,
            "password": password
        });
        return response.data; // trả về dữ liệu từ API
    } catch (error) {
        console.error('Error signing up:', error);
        throw error; // quăng lỗi để xử lý ở component gọi service
    }

};

export default signInService;