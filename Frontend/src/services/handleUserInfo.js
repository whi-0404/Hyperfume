import api from "./axiosConfig";

export const UserInfo = async () => {
    try {
        const response = await api.get('/users/my-info');
        console.log('Thông tin người dùng:', response.data); // trả về dữ liệu từ API
        return response.data;

    } catch (error) {
        console.error('Error signing up:', error);
        throw error; // quăng lỗi để xử lý ở component gọi service
    }
}

export const updateUserInfo = async (userId, request) => {
    try {
        const response = await api.put(`/users/${userId}`, request); // trả về dữ liệu từ API
        return response.data;

    } catch (error) {
        console.error('Error update user:', error);
        throw error; // quăng lỗi để xử lý ở component gọi service
    }
}