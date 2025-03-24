import api from "./axiosConfig";

const addToCart = async (variantId, quantity) => {
    console.log()
    try {
        const response = await api.post('/cart', {
            "variantId": variantId,
            "quantity": quantity
        }
        )
        alert('Sản phẩm đã được thêm vào giỏ hàng');
        return response.data;
    } catch (error) {
        console.error('Error signing up:', error);
        throw error; // quăng lỗi để xử lý ở component gọi service
    }
};

export default addToCart;