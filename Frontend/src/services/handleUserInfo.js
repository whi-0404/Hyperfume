import api from "./axiosConfig";

export const UserInfo = async (token) => {

    if (!token) {
        alert("Vui lòng đăng nhập!");
        return;
    }

    try {
        const response = await api.get('/users/my-info', {
            headers: {
                Authorization: `Bearer ${token}`,
            }
        });
        console.log('Thông tin người dùng:', response.data); // trả về dữ liệu từ API
        return response.data;

    } catch (error) {
        console.error('Error signing up:', error);
        throw error; // quăng lỗi để xử lý ở component gọi service
    }
}