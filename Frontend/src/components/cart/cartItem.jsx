import React, { memo } from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';

const CartItem = ({ item, checked, onCheckboxChange, onUpdateQuantity, onRemoveItem }) => {
    const handleCheckboxChange = (e) => {
        onCheckboxChange(item.id, e.target.checked);
    };

    return (
        <div className="cart-item">
            <div className="checkbox-container">
                <label className="custom-checkbox">
                    <input
                        type="checkbox"
                        checked={checked}
                        onChange={handleCheckboxChange}
                    />
                    <span className="checkbox-box">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                            <path d="M9 16.2l-3.5-3.5L4 14l5 5L20 8l-1.4-1.4L9 16.2z" />
                        </svg>
                    </span>
                </label>
            </div>

            <img src={item.image} alt={item.name} className="product-image" />

            <div className="product-details">
                <h4>{item.name}</h4>
                <p>Size: {item.size}</p>
                <p>Giá: {item.price.toLocaleString("vi-VN")} đồng</p>
                <p>Khuyến mại: {item.sale}%</p>

                <div className="quantity-controls">
                    <button onClick={() => onUpdateQuantity(item.id, item.quantity - 1)}>-</button>
                    <span>{item.quantity}</span>
                    <button onClick={() => onUpdateQuantity(item.id, item.quantity + 1)}>+</button>
                </div>

            </div>

            <div className="total-cost">
                <p>{(item.price * item.quantity * (100 - item.sale) / 100).toLocaleString("vi-VN")} đồng</p>
            </div>


            <button className="remove-btn" onClick={() => onRemoveItem(item.id)}>
                <FontAwesomeIcon icon={faTrash} />
            </button>
        </div>
    );
};

export default memo(CartItem);
