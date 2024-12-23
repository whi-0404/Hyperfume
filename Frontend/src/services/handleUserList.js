import api from "./axiosConfig";
import { getToken } from "./authToken";

export const UserList = async () => {
    const token = getToken();

    if (!token) {
        alert("Vui lòng đăng nhập!");
        return;
    }

    try {
        const response = await api.get('/users', {
            headers: {
                Authorization: `Bearer ${token}`,
            }
        });
        console.log('Danh sách người dùng:', response.data); // trả về dữ liệu từ API
        return response;

    } catch (error) {
        console.error('Error signing up:', error);
        throw error; // quăng lỗi để xử lý ở component gọi service
    }
}