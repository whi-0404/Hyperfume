import React, { memo, useEffect, useState } from "react";
import { NavLink } from 'react-router-dom';
import Cart from "../../components/cart/cart";
import { getCart } from "../../services/handleCart"; // Import hàm getCart từ export có tên
import "./style.scss";

const CartPage = () => {
  const [cartData, setCartData] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchCartData = async () => {
      try {
        const response = await getCart();
        setCartData(response.data);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching cart data:", error);
        if (error.response && error.response.status === 401) {
          setError("Bạn chưa đăng nhập tài khoản!");
        } else {
          setError("Không thể tải giỏ hàng. Vui lòng thử lại sau.");
        }
        setLoading(false);
      }
    };

    fetchCartData();
  }, []);

  if (loading) {
    return (
      <div className="loading-container">
        <div className="loading-spinner"></div>
        <p>Đang tải giỏ hàng...</p>
      </div>
    );
  }

  if (error) {
    if (error === "Bạn chưa đăng nhập tài khoản!") {
      return (
        <div className="alert">
          <h1>{error}</h1>
          <NavLink to="/Sign-in">Đăng nhập</NavLink>
        </div>
      );
    }
    return (
      <div className="error-container">
        <h2>Đã xảy ra lỗi</h2>
        <p>{error}</p>
        <button onClick={() => window.location.reload()} className="retry-btn">Thử lại</button>
      </div>
    );
  }

  return <Cart initialCartItems={cartData.result || []} />;
};

export default memo(CartPage);