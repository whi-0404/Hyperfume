import api from "./axiosConfig";
import { getToken } from "./authToken";

const getCart = async () => {
    let token = getToken();

    if (!token) {
        alert("Vui lòng đăng nhập!");
        return;
    }
    try {
        const response = await api.get('/cart', {
            headers: {
                Authorization: `Bearer ${token}`,
            }
        })
        console.log('Giỏ hàng của người dùng:', response.data);
        return response;
    } catch (error) {
        console.error('Error signing up:', error);
        throw error; // quăng lỗi để xử lý ở component gọi service
    }
};

export default getCart;