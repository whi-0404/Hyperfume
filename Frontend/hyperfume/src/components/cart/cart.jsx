import React, { memo } from "react";
import CartItem from "./cartItem";
import "./style.scss";

const Cart = ({ initialCartItems }) => {
    const [cartItems, setCartItems] = React.useState(initialCartItems);

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
    const shippingCost = (totalPrice <= 600000) ? 30000 : 0;
    const sale = totalPrice * 10 / 100;

    return (
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

                <div className="sale-code">
                    <p>(*) Phụ thu 30.000 vnđ phí giao hàng đối với đơn hàng {"<"}= 600.000đ.</p>
                    <input type="text" placeholder="Nhập mã giảm giá" />
                    <button>Áp dụng</button>
                </div>

                <div className="cart-summary">
                    <h3>Tạm tính (Tổng giá sản phẩm): {(totalPrice).toLocaleString("vi-VN")} đồng</h3>
                    <h3>Phí vận chuyển: {(shippingCost).toLocaleString("vi-VN")} đồng</h3>
                    <h3>Giảm giá: {(sale).toLocaleString("vi-VN")} đồng</h3>
                    <hr />
                    <h3 className="total">Tổng thanh toán: {(totalPrice - sale + shippingCost).toLocaleString("vi-VN")} đồng</h3>
                    <button className="checkout-btn">Tiến hành thanh toán</button>
                </div>
            </div>
        </div>
    );
};

export default memo(Cart);
