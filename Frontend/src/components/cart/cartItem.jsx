import React, { memo } from "react";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTrash } from '@fortawesome/free-solid-svg-icons';

const CartItem = ({ item, checked, onCheckboxChange, onUpdateQuantity, onRemoveItem }) => {
    const handleCheckboxChange = (e) => {
        onCheckboxChange(item.perfumeVariant, e.target.checked);
    };

    // Calculate the discounted price
    const originalPrice = item.price * item.quantity;
    const discountedPrice = item.discount > 0 
        ? originalPrice * (100 - item.discount) / 100 
        : originalPrice;

    return (
        <div className="cart-item">
            <div className="item-left">
                <div className="checkbox-container">
                    <label className="custom-checkbox">
                        <input
                            type="checkbox"
                            checked={checked}
                            onChange={handleCheckboxChange}
                        />
                        <span className="checkbox-box">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="16" height="16">
                                <path d="M9 16.2l-3.5-3.5L4 14l5 5L20 8l-1.4-1.4L9 16.2z" />
                            </svg>
                        </span>
                    </label>
                </div>
                
                <div className="product-image-container">
                    <img src={require(`../../assets/productImages/${item.imageUrl}`)} alt={item.perfumeName} className="product-image" />
                </div>
            </div>

            <div className="product-details">
                <h4 className="product-name">{item.perfumeName}</h4>
                <p className="product-variant">Phân loại: {item.variantName}</p>
                
                <div className="price-details">
                    <p className="original-price">
                        {item.price.toLocaleString("vi-VN")} đồng
                    </p>
                    
                    {item.discount > 0 && (
                        <p className="discount-badge">-{item.discount}%</p>
                    )}
                </div>

                <div className="quantity-controls">
                    <button 
                        className="quantity-btn minus-btn"
                        onClick={() => onUpdateQuantity(item.perfumeVariant, item.quantity - 1)}
                        disabled={item.quantity <= 1}
                    >
                        -
                    </button>
                    <span className="quantity-value">{item.quantity}</span>
                    <button 
                        className="quantity-btn plus-btn"
                        onClick={() => onUpdateQuantity(item.perfumeVariant, item.quantity + 1)}
                    >
                        +
                    </button>
                </div>
            </div>

            <div className="item-right">
                <div className="total-cost">
                    <p>{discountedPrice.toLocaleString("vi-VN")} đồng</p>
                    {item.discount > 0 && (
                        <p className="original-total">{originalPrice.toLocaleString("vi-VN")} đồng</p>
                    )}
                </div>

                <button className="remove-btn" onClick={() => onRemoveItem(item.perfumeVariant)}>
                    <FontAwesomeIcon icon={faTrash} />
                </button>
            </div>
        </div>
    );
};

export default memo(CartItem);