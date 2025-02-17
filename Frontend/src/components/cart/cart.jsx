import React, { memo, useState, useEffect } from "react";
import { NavLink } from 'react-router-dom';
import CartItem from "./cartItem";
import CartQuantityCount from "../cartQuantityCount";
import "./style.scss";


const Cart = ({ initialCartItems }) => {
    const [cartItems, setCartItems] = React.useState(initialCartItems);
    const [selectedItems, setSelectedItems] = useState([]);
    const [isAllChecked, setIsAllChecked] = useState(false);

    const handleUpdateQuantity = (id, quantity) => {
        setCartItems((prevItems) =>
            prevItems.map((item) =>
                item.perfumeVariant === id ? { ...item, quantity: Math.max(quantity, 1) } : item
            )
        );
    };

    const handleRemoveItem = (id) => {
        setCartItems((prevItems) => prevItems.filter((item) => item.perfumeVariant !== id));
    };

    // Tính toán totalPrice chỉ với những sản phẩm được chọn
    const totalPrice = cartItems
        .filter((item) => selectedItems.includes(item.perfumeVariant))  // Lọc những sản phẩm được chọn
        .reduce((total, item) => total + item.price * item.quantity, 0); // Tính tổng giá trị

    const shippingCost = (totalPrice <= 600000) ? 30000 : 0;

    // Xử lý khi checkbox của từng item thay đổi, để thành selected all
    const handleSelectAll = (checked) => {
        setIsAllChecked(checked);
        if (checked) {
            setSelectedItems(cartItems.map((item) => item.perfumeVariant));
        } else {
            setSelectedItems([]);
        }
    };

    // Xử lý khi checkbox của từng item thay đổi
    const handleItemCheckboxChange = (id, checked) => {
        setSelectedItems((prevSelected) => {
            if (checked) {
                return [...prevSelected, id];
            } else {
                return prevSelected.filter((itemId) => itemId !== id);
            }
        });
    };

    // Cập nhật trạng thái của checkbox tổng
    useEffect(() => {
        setIsAllChecked(selectedItems.length === cartItems.length && cartItems.length > 0);
    }, [selectedItems, cartItems]);

    return (
        <>
            <div className="cart-container">
                <div className="breadcrumb">
                    <a href="/" className="breadcrumb-link">Trang chủ</a>
                    <span className="arrow"> &gt; </span>
                    <a href="/Cart" className="breadcrumb-link">
                        <span className="current">Giỏ hàng</span>
                    </a>
                    <hr className="divider" />
                </div>

                <div className="cart-items-container">
                    <h1>Shopping Cart</h1>

                    <div className="total-checkbox-container">
                        <label className="custom-checkbox">
                            <input id="checkbox-1"
                                type="checkbox"
                                checked={isAllChecked}
                                onChange={(e) => handleSelectAll(e.target.checked)}
                            />
                            <span className="checkbox-box">
                                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                                    <path d="M9 16.2l-3.5-3.5L4 14l5 5L20 8l-1.4-1.4L9 16.2z" />
                                </svg>
                            </span>
                        </label>
                        <label htmlFor="checkbox-1" style={{ marginLeft: "20px", fontSize: "25px", fontWeight: "600" }}>Select All</label>
                    </div>

                    <div className="total-cost-item">
                        <p>Thành tiền</p>
                    </div>

                    <div className="cart-content">
                        {cartItems.map((item) => (
                            <CartItem
                                key={item.id}
                                item={item}
                                checked={selectedItems.includes(item.perfumeVariant)}
                                onCheckboxChange={handleItemCheckboxChange}
                                onUpdateQuantity={handleUpdateQuantity}
                                onRemoveItem={handleRemoveItem}
                            />
                        ))}
                    </div>

                    <p style={{ marginTop: "10px" }}>(*) Phụ thu 30.000 vnđ phí giao hàng đối với đơn hàng {"<"}= 600.000đ.</p>

                    <div className="cart-summary">
                        <h3>Tổng số lượng: <CartQuantityCount items={cartItems} /></h3>
                        <h3>Tạm tính (Tổng giá sản phẩm): {(totalPrice).toLocaleString("vi-VN")} đồng</h3>
                        <h3>Phí vận chuyển: {(shippingCost).toLocaleString("vi-VN")} đồng</h3>
                        <hr />
                        <h3 className="total">Tổng thanh toán: {(totalPrice + shippingCost).toLocaleString("vi-VN")} đồng</h3>
                        <button className="checkout-btn"><NavLink to="/thanh-toan" activeClassName="active">Tiến hành thanh toán</NavLink></button>
                    </div>
                </div>
            </div >
        </>
    );
};

export default memo(Cart);
