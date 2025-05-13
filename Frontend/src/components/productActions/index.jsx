import React, { memo, useState } from "react";
import { FaShoppingCart, FaPlus, FaMinus } from "react-icons/fa";
import "./style.scss";
import { addToCart } from "../../services/handleCart"; // Import hàm addToCart từ export có tên

const ProductActions = ({ price, variantId }) => {
    // Sử dụng một đối tượng data để lưu trữ variantId và quantity
    const [data, setData] = useState({
        variantId: variantId || null,
        quantity: 1
    });

    // Cập nhật data khi variantId thay đổi từ props
    React.useEffect(() => {
        if (variantId !== data.variantId) {
            setData(prevData => ({
                ...prevData,
                variantId: variantId
            }));
        }
    }, [variantId]);

    const increaseQuantity = () => {
        setData(prevData => ({
            ...prevData,
            quantity: prevData.quantity + 1
        }));
    };

    const decreaseQuantity = () => {
        if (data.quantity > 1) {
            setData(prevData => ({
                ...prevData,
                quantity: prevData.quantity - 1
            }));
        }
    };

    const handleAddToCart = async () => {
        if (!data.variantId) {
            alert("Vui lòng chọn phiên bản sản phẩm trước khi thêm vào giỏ hàng.");
            return;
        }

        try {
            // Truyền trực tiếp đối tượng data cho API
            const response = await addToCart(data);
            console.log("Add to cart response:", response);
            alert("Thêm vào giỏ hàng thành công!");
          } catch (error) {
            console.error("Failed to add to cart:", error);
            // Giả sử server trả về lỗi 401 nếu người dùng chưa đăng nhập
            if (error.response && error.response.status === 401) {
              alert("Bạn cần đăng nhập tài khoản để thêm vào giỏ hàng!");
            } 
            else if(error.response && error.response.data.code === 1048) {
              alert("Sản phẩm này đã có trong giỏ hàng!");
            }
            else {
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
                <span className="quantity">{data.quantity}</span>
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