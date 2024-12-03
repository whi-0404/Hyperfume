// import { memo } from 'react';
// import './style.scss';

// const cart = () => {
//     return (
//         <>
//             <div className='Container'>
//                 <div className='Cart'>

//                 </div>
//             </div>
//         </>
//     )
// }

// export default memo(cart);

import React, { useState, memo } from "react";
import CartItem from "./cartItem";
import "./style.scss";
import greenIrishTweed from "../../assets/productImages/green-irish-tweed.png";

const Cart = () => {
    const [cartItems, setCartItems] = useState([
        { id: 1, name: "Product 1", price: 100000, quantity: 1, image: greenIrishTweed },
        { id: 2, name: "Product 2", price: 150000, quantity: 2, image: "https://via.placeholder.com/100" },
        { id: 3, name: "Product 3", price: 200000, quantity: 1, image: "https://via.placeholder.com/100" },
    ]);

    const handleUpdateQuantity = (id, quantity) => {
        setCartItems((prevItems) =>
            prevItems.map((item) =>
                item.id === id ? { ...item, quantity: Math.max(quantity, 1) } : item
            )
        );
    };

    const handleRemoveItem = (id) => {
        setCartItems((prevItems) => prevItems.filter((item) => item.id !== id));
    };

    const totalPrice = cartItems.reduce((total, item) => total + item.price * item.quantity, 0);

    return (
        <>
            <div className="container">

                <div className="breadcrumb">
                    <a href="/" className="breadcrumb-link">Trang chủ</a>
                    <span className="arrow"> &gt; </span>
                    <a href="/Cart" className="breadcrumb-link">
                        <span className="current">Giỏ hàng</span>
                    </a>
                    <hr />
                </div>

                <div className="cart-container">
                    <h1>Shopping Cart</h1>
                    <div className="cart-content">
                        {cartItems.map((item) => (
                            <CartItem
                                key={item.id}
                                item={item}
                                onUpdateQuantity={handleUpdateQuantity}
                                onRemoveItem={handleRemoveItem}
                            />
                        ))}
                    </div>

                    <div className="cart-summary">
                        <h3>Total: {totalPrice.toLocaleString("vi-VN")} đồng</h3>
                        <button className="checkout-btn">Tiến hành thanh toán</button>
                    </div>
                </div>
            </div>
        </>
    );
};

export default memo(Cart)

