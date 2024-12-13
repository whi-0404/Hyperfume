import React, { memo, useState } from "react";
import { FaShoppingCart, FaPlus, FaMinus } from "react-icons/fa";
import "./style.scss";

const ProductActions = () => {
    const [quantity, setQuantity] = useState(1);

    const increaseQuantity = () => setQuantity(quantity + 1);
    const decreaseQuantity = () => {
        if (quantity > 1) setQuantity(quantity - 1);
    };

    return (
        <div className="product-actions">
            <div className="price">4,150,000</div>
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
            <button className="add-to-cart">
                <FaShoppingCart className="cart-icon" />
                THÊM VÀO GIỎ HÀNG
            </button>
            <button className="installment">MUA TRẢ SAU, TRẢ GÓP</button>
        </div>
    );
};

export default memo(ProductActions);
