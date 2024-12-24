import api from "./axiosConfig";
import { getToken } from "./authToken";

const addToCart = async (variantId, quantity) => {
    const token = getToken();
    console.log(token)
    try {
        const response = await api.post('/cart', {
            "variantId": variantId,
            "quantity": quantity
        },
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                }
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