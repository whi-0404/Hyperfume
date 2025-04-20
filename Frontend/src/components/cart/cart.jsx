import React, { memo, useState, useEffect, useRef } from "react";
import { NavLink, useLocation, useNavigate } from 'react-router-dom';
import CartItem from "./cartItem";
import CartQuantityCount from "../cartQuantityCount";
import { deleteCartId, updateQuantity } from "../../services/handleCart";
import "./style.scss";

const Cart = ({ initialCartItems }) => {
    const [cartItems, setCartItems] = useState(initialCartItems || []);
    const [selectedItems, setSelectedItems] = useState([]);
    const [isAllChecked, setIsAllChecked] = useState(false);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    
    // Lưu trữ số lượng ban đầu của mỗi sản phẩm
    const initialQuantities = useRef({});
    // Kiểm tra xem đã có sự thay đổi chưa lưu hay không
    const hasUnsavedChanges = useRef(false);
    // Sử dụng location để theo dõi sự thay đổi URL
    const location = useLocation();
    const prevLocationRef = useRef(location);
    const navigate = useNavigate();
    
    // Khởi tạo số lượng ban đầu khi component được render lần đầu
    useEffect(() => {
        const initQuantities = {};
        cartItems.forEach(item => {
            initQuantities[item.cartId] = item.quantity;
        });
        initialQuantities.current = initQuantities;
    }, []);

    // Handle quantity updates - Chỉ cập nhật UI, không gọi API
    const handleUpdateQuantity = (id, quantity) => {
        const newQuantity = Math.max(quantity, 1);
        
        // Tìm cartId tương ứng với perfumeVariant
        const cartItem = cartItems.find(item => item.perfumeVariant === id);
        if (cartItem) {
            const cartId = cartItem.cartId;
            
            // Kiểm tra xem số lượng có thay đổi so với ban đầu không
            if (initialQuantities.current[cartId] !== newQuantity) {
                hasUnsavedChanges.current = true;
            } else {
                // Kiểm tra xem có còn sản phẩm nào thay đổi không
                const stillHasChanges = cartItems.some(item => 
                    item.perfumeVariant !== id && 
                    initialQuantities.current[item.cartId] !== item.quantity
                );
                hasUnsavedChanges.current = stillHasChanges;
            }
        }
        
        // Chỉ cập nhật cartItems cho UI
        setCartItems((prevItems) =>
            prevItems.map((item) =>
                item.perfumeVariant === id ? { ...item, quantity: newQuantity } : item
            )
        );
    };

    // Hàm gửi dữ liệu cập nhật số lượng lên server
    const saveQuantityChanges = async () => {
        // Xác định các sản phẩm có số lượng thay đổi
        const itemsToUpdate = cartItems.filter(item => 
            initialQuantities.current[item.cartId] && 
            initialQuantities.current[item.cartId] !== item.quantity
        );

        if (itemsToUpdate.length === 0) {
            hasUnsavedChanges.current = false;
            return;
        }

        try {
            setLoading(true);
            
            // Gửi các request cập nhật số lượng
            const updatePromises = itemsToUpdate.map(item => {
                return updateQuantity({ 
                    cartId: item.cartId, 
                    quantity: item.quantity 
                });
            });
            
            await Promise.all(updatePromises);
            
            // Cập nhật lại số lượng ban đầu sau khi lưu thành công
            const updatedQuantities = {...initialQuantities.current};
            itemsToUpdate.forEach(item => {
                updatedQuantities[item.cartId] = item.quantity;
            });
            initialQuantities.current = updatedQuantities;
            
            // Đánh dấu không còn thay đổi chưa lưu
            hasUnsavedChanges.current = false;
            
            setLoading(false);
        } catch (err) {
            console.error("Lỗi khi cập nhật số lượng sản phẩm:", err);
            setError("Không thể cập nhật số lượng sản phẩm. Vui lòng thử lại.");
            setLoading(false);
        }
    };

    // Theo dõi việc người dùng rời khỏi trang (URL thay đổi)
    useEffect(() => {
        const handleLocationChange = () => {
            // Kiểm tra xem location có thay đổi không
            if (prevLocationRef.current.pathname !== location.pathname && hasUnsavedChanges.current) {
                // Lưu thay đổi trước khi chuyển trang
                saveQuantityChanges();
            }
            // Cập nhật reference cho vị trí hiện tại
            prevLocationRef.current = location;
        };

        handleLocationChange();
        
        // Không cần cleanup vì chúng ta đang sử dụng useEffect để theo dõi location
    }, [location]);

    // Lắng nghe sự kiện beforeunload để lưu thay đổi khi người dùng rời trang
    useEffect(() => {
        const handleBeforeUnload = (e) => {
            if (hasUnsavedChanges.current) {
                // Hiển thị thông báo xác nhận cho người dùng
                const confirmationMessage = "Bạn có thay đổi chưa được lưu. Bạn có chắc chắn muốn rời đi?";
                e.returnValue = confirmationMessage;
                
                // Lưu thay đổi trước khi thoát
                saveQuantityChanges();
                
                return confirmationMessage;
            }
        };

        window.addEventListener('beforeunload', handleBeforeUnload);
        
        return () => {
            window.removeEventListener('beforeunload', handleBeforeUnload);
            
            // Lưu các thay đổi khi component unmount
            if (hasUnsavedChanges.current) {
                saveQuantityChanges();
            }
        };
    }, []);

    // Handle item removal
    const handleRemoveItem = async (id) => {
        // Tìm cartId của sản phẩm cần xóa
        const itemToRemove = cartItems.find(item => item.perfumeVariant === id);
        if (!itemToRemove) return;

        try {
            setLoading(true);
            // Gọi API xóa sản phẩm
            await deleteCartId(itemToRemove.cartId);
            // Cập nhật state sau khi xóa thành công
            setCartItems((prevItems) => prevItems.filter((item) => item.cartId !== itemToRemove.cartId));
            // Cập nhật danh sách selected items
            setSelectedItems(prev => prev.filter(itemId => itemId !== id));
            
            // Xóa item đã xóa khỏi danh sách theo dõi số lượng ban đầu
            const updatedInitialQuantities = {...initialQuantities.current};
            delete updatedInitialQuantities[itemToRemove.cartId];
            initialQuantities.current = updatedInitialQuantities;
            
            setLoading(false);
        } catch (err) {
            console.error("Lỗi khi xóa sản phẩm:", err);
            setError("Không thể xóa sản phẩm. Vui lòng thử lại.");
            setLoading(false);
        }
    };

    // Calculate subtotal and total with discounts
    const calculateCartTotals = () => {
        const selectedProducts = cartItems.filter(item => selectedItems.includes(item.perfumeVariant));
        
        const subtotal = selectedProducts.reduce(
            (total, item) => total + item.price * item.quantity, 
            0
        );
        
        const totalWithDiscount = selectedProducts.reduce(
            (total, item) => total + (item.price * item.quantity * (100 - item.discount) / 100), 
            0
        );
        
        return { subtotal, totalWithDiscount };
    };

    const { subtotal, totalWithDiscount } = calculateCartTotals();

    // Handle select all checkbox
    const handleSelectAll = (checked) => {
        setIsAllChecked(checked);
        if (checked) {
            setSelectedItems(cartItems.map((item) => item.perfumeVariant));
        } else {
            setSelectedItems([]);
        }
    };

    // Handle individual item selection
    const handleItemCheckboxChange = (id, checked) => {
        setSelectedItems((prevSelected) => {
            if (checked) {
                return [...prevSelected, id];
            } else {
                return prevSelected.filter((itemId) => itemId !== id);
            }
        });
    };

    const handleCheckout = () => {
        if (selectedItems.length === 0) {
          return;
        }
        
        // Save any pending quantity changes before proceeding
        if (hasUnsavedChanges.current) {
          saveQuantityChanges().then(() => {
            // Filter the cart items to only include selected ones with ALL properties
            const selectedProducts = cartItems.filter(item => 
              selectedItems.includes(item.perfumeVariant)
            );
            
            // Navigate to checkout with ALL item data
            navigate('/thanh-toan', { 
              state: { 
                selectedProducts: selectedProducts 
              }
            });
          });
        } else {
          // No unsaved changes, navigate directly with ALL properties
          const selectedProducts = cartItems.filter(item => 
            selectedItems.includes(item.perfumeVariant)
          );
          
          navigate('/thanh-toan', { 
            state: { 
              selectedProducts: selectedProducts 
            }
          });
        }
      };
      

    // Update select all checkbox state when individual selections change
    useEffect(() => {
        setIsAllChecked(selectedItems.length === cartItems.length && cartItems.length > 0);
    }, [selectedItems, cartItems]);
    
    // Calculate saved amount from discounts
    const savedAmount = subtotal - totalWithDiscount;

    // Hiển thị thông báo lỗi nếu có
    useEffect(() => {
        if (error) {
            const timer = setTimeout(() => {
                setError(null);
            }, 3000);
            return () => clearTimeout(timer);
        }
    }, [error]);

    // Thêm hàm custom navigate để đảm bảo lưu thay đổi trước khi chuyển trang
    const handleNavigate = (to) => {
        if (hasUnsavedChanges.current) {
            saveQuantityChanges().then(() => {
                navigate(to);
            });
        } else {
            navigate(to);
        }
    };

    return (
        <div className="cart-container">
            {loading && (
                <div className="cart-loading-overlay">
                    <div className="loading-spinner"></div>
                </div>
            )}
            
            {error && (
                <div className="cart-error-message">
                    {error}
                </div>
            )}
            
            <div className="breadcrumb">
                <NavLink to="/" className="breadcrumb-link">Trang chủ</NavLink>
                <span className="arrow"> &gt; </span>
                <span className="current">Giỏ hàng</span>
                <hr className="divider" />
            </div>

            <div className="cart-items-container">
                <h1 className="cart-title">Giỏ hàng của bạn</h1>
                
                {cartItems.length === 0 ? (
                    <div className="empty-cart">
                        <h2>Giỏ hàng trống</h2>
                        <p>Bạn chưa có sản phẩm nào trong giỏ hàng.</p>
                        <NavLink to="/" className="continue-shopping-btn">
                            Tiếp tục mua sắm
                        </NavLink>
                    </div>
                ) : (
                    <>
                        <div className="cart-header">
                            <div className="total-checkbox-container">
                                <label className="custom-checkbox">
                                    <input
                                        id="checkbox-select-all"
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
                                <label htmlFor="checkbox-select-all" className="select-all-label">
                                    Chọn tất cả ({cartItems.length} sản phẩm)
                                </label>
                            </div>
                            <div className="total-cost-item">
                                <p>Thành tiền</p>
                            </div>
                        </div>

                        <div className="cart-content">
                            {cartItems.map((item) => (
                                <CartItem
                                    key={item.cartId}
                                    item={item}
                                    checked={selectedItems.includes(item.perfumeVariant)}
                                    onCheckboxChange={handleItemCheckboxChange}
                                    onUpdateQuantity={handleUpdateQuantity}
                                    onRemoveItem={handleRemoveItem}
                                />
                            ))}
                        </div>

                        <div className="cart-summary">
                            <div className="summary-row">
                                <span>Tổng số lượng:</span>
                                <span><CartQuantityCount items={selectedItems.length > 0 ? 
                                    cartItems.filter(item => selectedItems.includes(item.perfumeVariant)) : 
                                    []} /> sản phẩm</span>
                            </div>
                            <div className="summary-row">
                                <span>Tạm tính:</span>
                                <span>{subtotal.toLocaleString("vi-VN")} đồng</span>
                            </div>
                            {savedAmount > 0 && (
                                <div className="summary-row discount-row">
                                    <span>Giảm giá:</span>
                                    <span>-{savedAmount.toLocaleString("vi-VN")} đồng</span>
                                </div>
                            )}
                            <hr />
                            <div className="summary-row total-row">
                                <span>Tổng thanh toán:</span>
                                <span>{totalWithDiscount.toLocaleString("vi-VN")} đồng</span>
                            </div>
                            <div className="action-buttons">
                                <NavLink to="/" className="continue-btn" onClick={(e) => {
                                    e.preventDefault();
                                    if (hasUnsavedChanges.current) {
                                        saveQuantityChanges().then(() => {
                                            navigate('/');
                                        });
                                    } else {
                                        navigate('/');
                                    }
                                }}>
                                    Tiếp tục mua sắm
                                </NavLink>
                                <NavLink 
                                    to="/thanh-toan" 
                                    className={`checkout-btn ${selectedItems.length === 0 ? 'disabled' : ''}`}
                                    onClick={(e) => {
                                        e.preventDefault();
                                        handleCheckout();
                                    }}
                                    >
                                    Thanh toán
                                    </NavLink>
                            </div>
                        </div>
                    </>
                )}
            </div>
        </div>
    );
};

export default memo(Cart);