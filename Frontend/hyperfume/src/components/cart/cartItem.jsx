import React from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';

const CartItem = ({ item, onUpdateQuantity, onRemoveItem }) => {
    return (
        <div className="cart-item">
            <img src={item.image} alt={item.name} className="product-image" />
            <div className="product-details">
                <h4>{item.name}</h4>
                <p>Price: {item.price.toLocaleString("vi-VN")} đồng</p>
                <div className="quantity-controls">
                    <button onClick={() => onUpdateQuantity(item.id, item.quantity - 1)}>-</button>
                    <span>{item.quantity}</span>
                    <button onClick={() => onUpdateQuantity(item.id, item.quantity + 1)}>+</button>
                </div>
            </div>

            <button className="remove-btn" onClick={() => onRemoveItem(item.id)}>
                <FontAwesomeIcon icon={faTrash} />
            </button>
        </div>
    );
};

export default CartItem;
