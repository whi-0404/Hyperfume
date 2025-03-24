import api from "./axiosConfig";

const getCart = async () => {
    try {
        const response = await api.get('/cart')
        console.log('Giỏ hàng của người dùng:', response.data);
        return response;
    } catch (error) {
        if (error.response && error.response.status === 401) {
            alert("Bạn chưa đăng nhập, vui lòng đăng nhập!");
        }
        console.error('Error signing up:', error);
        throw error; // quăng lỗi để xử lý ở component gọi service
    }
};

export default getCart;