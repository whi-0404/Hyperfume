import React, { memo, useState } from "react";
import { FaShoppingCart, FaPlus, FaMinus } from "react-icons/fa";
import "./style.scss";
import addToCart from "../../services/handleAddToCart";

const ProductActions = ({ price, variantId }) => {
    const [quantity, setQuantity] = useState(1);

    const increaseQuantity = () => setQuantity(quantity + 1);
    const decreaseQuantity = () => {
        if (quantity > 1) setQuantity(quantity - 1);
    };

    const handleAddToCart = async () => {
        if (!variantId) {
            alert("Vui lòng chọn phiên bản sản phẩm trước khi thêm vào giỏ hàng.");
            return;
        }

        try {
            const response = await addToCart(variantId, quantity);
            console.log("Add to cart response:", response);
          } catch (error) {
            console.error("Failed to add to cart:", error);
            // Giả sử server trả về lỗi 401 nếu người dùng chưa đăng nhập
            if (error.response && error.response.status === 401) {
              alert("Bạn cần đăng nhập tài khoản để thêm vào giỏ hàng!");
            } else {
              alert("Thêm vào giỏ hàng thất bại. Vui lòng thử lại.");
            }
        }
    };

    return (
        <div className="product-actions">
            <div className="price">
                {price ? price.toLocaleString() : ""}
            </div>
            <button className="buy-now">MUA NGAY</button>

            <div className="quantity-box">
                <button className="adjust-button" onClick={decreaseQuantity}>
                    <FaMinus />
                </button>
                <span className="quantity">{quantity}</span>
                <button className="adjust-button" onClick={increaseQuantity}>
                    <FaPlus />
                </button>
            </div>

            <button className="add-to-cart" onClick={handleAddToCart}>
                <FaShoppingCart className="cart-icon" />
                THÊM VÀO GIỎ HÀNG
            </button>

            <button className="installment">MUA TRẢ SAU, TRẢ GÓP</button>
        </div>
    );
};

export default memo(ProductActions);
