import api from "./axiosConfig";

const addToCart = async (userId, variantId, quantity) => {
    try {
        const response = await api.post('/cart', {
            "userId": userId,
            "variantId": variantId,
            "quantity": quantity
        })
        alert('Sản phẩm đã được thêm vào giỏ hàng');
    } catch (error) {
        console.error('Error signing up:', error);
        throw error; // quăng lỗi để xử lý ở component gọi service
    }
};

export default addToCart;