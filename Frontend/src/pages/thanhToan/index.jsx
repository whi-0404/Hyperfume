import { memo, useState, useEffect } from "react";
import { useLocation, useNavigate } from 'react-router-dom';
import './style.scss';
import { getShippingMethods } from "../../services/handleShippingMethod";
import { getPaymentMethods } from "../../services/handlePaymentMethod";
import { getShippingAddress } from "../../services/handleMyAddress";
import { createOrder } from "../../services/handleOrder";
import { deleteCartId } from "../../services/handleCart"; // Import thêm API xóa giỏ hàng

const ThanhToan = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    
    // Get selected products from navigation state
    const selectedProducts = location.state?.selectedProducts || [];
    
    const [personalInfo, setPersonalInfo] = useState({
        fullName: '',
        email: '',
        phoneNumber: '',
        specific_address: '',
        city: '',
        district: '',
        ward: '',
        addressId: null
    });

    const [products, setProducts] = useState([]);
    const [discountCode, setDiscountCode] = useState('');
    const [shippingMethods, setShippingMethods] = useState([]);
    const [paymentMethods, setPaymentMethods] = useState([]);
    const [selectedShippingMethod, setSelectedShippingMethod] = useState(null);
    const [selectedPaymentMethod, setSelectedPaymentMethod] = useState(null);
    const [shippingFee, setShippingFee] = useState(0);
    const [discount, setDiscount] = useState(0);
    const [orderSuccess, setOrderSuccess] = useState(false);
    const [orderDetails, setOrderDetails] = useState(null);
    const [creditCardNumber, setCreditCardNumber] = useState('');
    const [addressList, setAddressList] = useState([]);
    const [orderNotes, setOrderNotes] = useState(''); // Thêm state cho ghi chú đơn hàng

    const totalAmount = products.reduce((total, product) => total + (product.price * product.quantity), 0);
    const discountedAmount = totalAmount - discount + shippingFee;

    // Fetch all required data when component mounts
    useEffect(() => {
        const fetchInitialData = async () => {
            setLoading(true);
            try {
                // Set products from selected cart items
                if (selectedProducts.length > 0) {
                    setProducts(selectedProducts.map(item => ({
                        // Sử dụng đúng tên thuộc tính từ cartItem
                        name: item.perfumeName,
                        capacity: item.variantName,
                        price: item.price,
                        quantity: item.quantity,
                        perfumeVariantId: item.perfumeVariant,
                        discount: item.discount || 0,
                        image: item.imageUrl || null,
                        cartId: item.cartId
                    })));
                }

                // Fetch shipping methods
                const shippingMethodsData = await getShippingMethods();
                if (shippingMethodsData && shippingMethodsData.code === 1000) {
                    setShippingMethods(shippingMethodsData.result);
                    // Select first shipping method by default
                    if (shippingMethodsData.result.length > 0) {
                        setSelectedShippingMethod(shippingMethodsData.result[0]);
                        setShippingFee(shippingMethodsData.result[0].shipCost || 0); // Sử dụng shipCost từ API response
                    }
                }

                // Fetch payment methods
                const paymentMethodsData = await getPaymentMethods();
                if (paymentMethodsData && paymentMethodsData.result) {
                    setPaymentMethods(paymentMethodsData.result);
                    // Select first payment method by default
                    if (paymentMethodsData.result.length > 0) {
                        setSelectedPaymentMethod(paymentMethodsData.result[0]);
                    }
                }

                // Fetch shipping address
                const addressData = await getShippingAddress();
                if (addressData && addressData.code === 1000) {
                    setAddressList(addressData.result);
                    
                    // Find default address
                    const defaultAddress = addressData.result.find(addr => addr.default) || addressData.result[0];
                    
                    if (defaultAddress) {
                        // Sử dụng các trường theo cấu trúc API thực tế
                        setPersonalInfo({
                            fullName: defaultAddress.recipientName || '',
                            email: '', // Không có trong API, giữ trống
                            phoneNumber: defaultAddress.phone || '',
                            specific_address: defaultAddress.shipAddress || '',
                            city: '', // Địa chỉ đã là đầy đủ trong shipAddress
                            district: '',
                            ward: '',
                            addressId: defaultAddress.id
                        });
                    }
                }
            } catch (err) {
                console.error("Error fetching checkout data:", err);
                setError("Không thể tải dữ liệu thanh toán. Vui lòng thử lại sau.");
            } finally {
                setLoading(false);
            }
        };

        fetchInitialData();
    }, [selectedProducts]);

    const formatCurrency = (amount) => {
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND',
        }).format(amount);
    };

    const applyVoucher = () => {
        if (!discountCode || discountCode.trim().length === 0) {
            setError("Vui lòng nhập mã giảm giá.");
            return;
        }

        if (discountCode.length === 6) {
            const discountValue = totalAmount * 0.10; // Giảm giá 10%
            setDiscount(discountValue);
            setError(null);
        } else {
            setError("Mã giảm giá không hợp lệ. Vui lòng nhập mã có 6 ký tự.");
        }
    };

    const handleShippingMethodChange = (e) => {
        const selectedMethodId = parseInt(e.target.value);
        const method = shippingMethods.find(m => m.id === selectedMethodId);
        if (method) {
            setSelectedShippingMethod(method);
            setShippingFee(method.shipCost || 0); // Sử dụng shipCost từ API response
        }
    };

    const handlePaymentMethodChange = (e) => {
        const selectedMethodId = parseInt(e.target.value);
        const method = paymentMethods.find(m => m.id === selectedMethodId);
        if (method) {
            setSelectedPaymentMethod(method);
        }
    };

    const handleAddressChange = (e) => {
        const selectedAddressId = parseInt(e.target.value);
        const selectedAddress = addressList.find(addr => addr.id === selectedAddressId);
        if (selectedAddress) {
            setPersonalInfo({
                fullName: selectedAddress.recipientName || '',
                email: '', // Không có trong API, giữ trống
                phoneNumber: selectedAddress.phone || '',
                specific_address: selectedAddress.shipAddress || '',
                city: '', // Địa chỉ đã là đầy đủ trong shipAddress
                district: '',
                ward: '',
                addressId: selectedAddress.id
            });
        }
    };

    // Xử lý thay đổi số lượng sản phẩm
    const handleQuantityChange = (perfumeVariantId, newQuantity) => {
        // Đảm bảo số lượng không thấp hơn 1
        const updatedQuantity = Math.max(1, newQuantity);
        
        setProducts(prevProducts => 
            prevProducts.map(product => 
                product.perfumeVariantId === perfumeVariantId 
                    ? { ...product, quantity: updatedQuantity } 
                    : product
            )
        );
    };

    // Xóa sản phẩm khỏi trang thanh toán
    const handleRemoveProduct = (perfumeVariantId) => {
        setProducts(prevProducts => 
            prevProducts.filter(product => product.perfumeVariantId !== perfumeVariantId)
        );
    };

    const validateOrder = () => {
        if (!personalInfo.fullName || !personalInfo.phoneNumber || !personalInfo.specific_address) {
            setError("Vui lòng điền đầy đủ thông tin giao hàng");
            return false;
        }

        if (!selectedShippingMethod) {
            setError("Vui lòng chọn phương thức vận chuyển");
            return false;
        }

        if (!selectedPaymentMethod) {
            setError("Vui lòng chọn phương thức thanh toán");
            return false;
        }

        // If payment method is credit card, validate card number
        if (selectedPaymentMethod.id === 3 && (!creditCardNumber || creditCardNumber.length !== 9)) {
            setError("Vui lòng nhập đúng 9 chữ số thẻ tín dụng");
            return false;
        }

        if (products.length === 0) {
            setError("Giỏ hàng của bạn đang trống");
            return false;
        }

        return true;
    };

    // Xóa sản phẩm khỏi giỏ hàng sau khi đặt hàng thành công
    const removeProductsFromCart = async (cartIds) => {
        try {
            // Thực hiện xóa từng sản phẩm trong giỏ hàng
            const deletePromises = cartIds.map(cartId => deleteCartId(cartId));
            await Promise.all(deletePromises);
            console.log("Đã xóa các sản phẩm khỏi giỏ hàng");
        } catch (err) {
            console.error("Lỗi khi xóa sản phẩm khỏi giỏ hàng:", err);
            // Không hiển thị lỗi cho người dùng vì đặt hàng đã thành công
        }
    };

    const handleOrder = async () => {
        if (!validateOrder()) {
            return;
        }

        setLoading(true);
        try {
            // Prepare order request data
            const orderRequest = {
                shippingAddressId: personalInfo.addressId,
                notes: orderNotes, // Sử dụng ghi chú từ state
                shippingMethodId: selectedShippingMethod.id,
                paymentMethodId: selectedPaymentMethod.id
            };

            // Prepare order items
            const orderItems = products.map(product => ({
                perfumeVariantId: product.perfumeVariantId,
                quantity: product.quantity
            }));

            // Create order via API
            const orderResponse = await createOrder({
                orderRequest: orderRequest,
                orderItemRequests: orderItems
            });

            if (orderResponse && orderResponse.code === 1000) {
                setOrderDetails(orderResponse.result);
                setOrderSuccess(true);
                
                // Lấy danh sách cartId từ các sản phẩm để xóa khỏi giỏ hàng
                const cartIds = products.map(product => product.cartId).filter(Boolean);
                if (cartIds.length > 0) {
                    await removeProductsFromCart(cartIds);
                }
            } else {
                throw new Error("Đặt hàng không thành công");
            }
        } catch (err) {
            console.error("Error creating order:", err);
            setError("Không thể tạo đơn hàng. Vui lòng thử lại sau.");
        } finally {
            setLoading(false);
        }
    };

    // Hide error message after 3 seconds
    useEffect(() => {
        if (error) {
            const timer = setTimeout(() => {
                setError(null);
            }, 3000);
            return () => clearTimeout(timer);
        }
    }, [error]);

    return (
        <div className="thanh-toan-container">
            {loading && (
                <div className="loading-overlay">
                    <div className="loading-spinner"></div>
                </div>
            )}
            
            {error && (
                <div className="error-message">
                    {error}
                </div>
            )}

            <div className="breadcrumb">
                <a href="/" className="breadcrumb-link">Trang chủ</a>
                <span className="arrow"> &gt; </span>
                <a href="/cart" className="breadcrumb-link">Giỏ hàng</a>
                <span className="arrow"> &gt; </span>
                <span className="current">Thanh toán</span>
                <hr className="divider" />
            </div>
            
            <div className="thanhtoan">
                {orderSuccess ? (
                    <div className="success-message">
                        <h2>Đặt hàng thành công!</h2>
                        <p>Cảm ơn bạn đã đặt hàng. Mã đơn hàng của bạn là: <strong>{orderDetails?.id || "N/A"}</strong></p>
                        <table className="order-details-table">
                            <thead>
                                <tr>
                                    <th>Thông tin khách hàng</th>
                                    <th>Thông tin sản phẩm</th>
                                    <th>Tổng tiền</th>
                                    <th>Phương thức thanh toán</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <p><strong>Họ và tên:</strong> {personalInfo.fullName}</p>
                                        {personalInfo.email && <p><strong>Email:</strong> {personalInfo.email}</p>}
                                        <p><strong>Điện thoại:</strong> {personalInfo.phoneNumber}</p>
                                        <p><strong>Địa chỉ:</strong> {personalInfo.specific_address}</p>
                                        {orderNotes && <p><strong>Ghi chú:</strong> {orderNotes}</p>}
                                    </td>
                                    <td>
                                        {products.map((product, index) => (
                                            <p key={index}>{product.name} - {product.capacity} - {formatCurrency(product.price)} x {product.quantity}</p>
                                        ))}
                                    </td>
                                    <td>{formatCurrency(discountedAmount)}</td>
                                    <td>{selectedPaymentMethod?.name || "N/A"}</td>
                                </tr>
                            </tbody>
                        </table>
                        <div className="action-buttons">
                            <button className="continue-shopping-btn" onClick={() => navigate('/')}>
                                Tiếp tục mua sắm
                            </button>
                        </div>
                    </div>
                ) : (
                    <div className="checkout-grid">
                        {/* Box 1: Personal Information */}
                        <div className="checkout-box personal-info">
                            <h2>Thông tin thanh toán</h2>
                            {addressList.length > 0 && (
                                <div className="address-selection">
                                    <label>Chọn địa chỉ có sẵn:</label>
                                    <select 
                                        className="address-select"
                                        onChange={handleAddressChange}
                                        value={personalInfo.addressId || ''}
                                    >
                                        {addressList.map(address => (
                                            <option key={address.id} value={address.id}>
                                                {address.recipientName} - {address.shipAddress}
                                                {address.default ? ' (Mặc định)' : ''}
                                            </option>
                                        ))}
                                    </select>
                                    {/* Thêm ô nhập ghi chú đơn hàng */}
                                </div>
                            )}
                            <input className="text-input"
                                type="text" 
                                placeholder="Họ và tên" 
                                value={personalInfo.fullName} 
                                onChange={(e) => setPersonalInfo({ ...personalInfo, fullName: e.target.value })} 
                                required
                            />
                            <input className="text-input"
                                type="email" 
                                placeholder="Email (không bắt buộc)" 
                                value={personalInfo.email} 
                                onChange={(e) => setPersonalInfo({ ...personalInfo, email: e.target.value })} 
                            />
                            <input className="text-input"
                                type="tel" 
                                placeholder="Điện thoại" 
                                value={personalInfo.phoneNumber} 
                                onChange={(e) => setPersonalInfo({ ...personalInfo, phoneNumber: e.target.value })} 
                                required
                            />
                            <input className="text-input"
                                type="text" 
                                placeholder="Địa chỉ đầy đủ" 
                                value={personalInfo.specific_address} 
                                onChange={(e) => setPersonalInfo({ ...personalInfo, specific_address: e.target.value })} 
                                required
                            />
                            {/* Thêm ô nhập ghi chú đơn hàng */}
                            <textarea 
                                className="text-input order-notes"
                                placeholder="Ghi chú đơn hàng (không bắt buộc)"
                                value={orderNotes}
                                onChange={(e) => setOrderNotes(e.target.value)}
                                rows={3}
                            />
                        </div>

                        {/* Box 2: Products */}
                        <div className="checkout-box products">
                            <h2>Sản phẩm</h2>
                            {products.length === 0 ? (
                                <div className="empty-cart-message">
                                    <p>Không có sản phẩm nào được chọn</p>
                                    <button onClick={() => navigate('/cart')} className="return-to-cart-btn">
                                        Quay lại giỏ hàng
                                    </button>
                                </div>
                            ) : (
                                <div className="product-list">
                                    <div className="product-header">
                                        <div className="product-image-header">Sản phẩm</div>
                                        <div className="product-name-header">Tên sản phẩm</div>
                                        <div className="product-capacity-header">Dung tích</div>
                                        <div className="product-price-header">Đơn giá</div>
                                        <div className="product-quantity-header">Số lượng</div>
                                        <div className="product-total-header">Thành tiền</div>
                                        <div className="product-action-header"></div>
                                    </div>
                                    
                                    {products.map((product, index) => (
                                        <div key={index} className="product-item">
                                            <div className="product-image-container">
                                                {product.image ? (
                                                    <img src={require(`../../assets/productImages/${product.image}`)} alt={product.name} className="product-image" />
                                                ) : (
                                                    <div className="product-image-placeholder"></div>
                                                )}
                                            </div>
                                            <div className="product-name">{product.name}</div>
                                            <div className="product-capacity">{product.capacity}</div>
                                            <div className="product-price">
                                                {formatCurrency(product.price)}
                                                {product.discount > 0 && (
                                                    <span className="discount-badge">-{product.discount}%</span>
                                                )}
                                            </div>
                                            {/* Thêm control điều chỉnh số lượng */}
                                            <div className="product-quantity">
                                                <div className="quantity-controls">
                                                    <button 
                                                        className="quantity-btn" 
                                                        onClick={() => handleQuantityChange(product.perfumeVariantId, product.quantity - 1)}
                                                        disabled={product.quantity <= 1}
                                                    >
                                                        -
                                                    </button>
                                                    <span className="quantity-value">{product.quantity}</span>
                                                    <button 
                                                        className="quantity-btn"
                                                        onClick={() => handleQuantityChange(product.perfumeVariantId, product.quantity + 1)}
                                                    >
                                                        +
                                                    </button>
                                                </div>
                                            </div>
                                            <div className="product-total">
                                                {formatCurrency(product.price * product.quantity * (1 - product.discount/100))}
                                            </div>
                                            {/* Thêm nút xóa sản phẩm */}
                                            <div className="product-action">
                                                <button 
                                                    className="delete-btn"
                                                    onClick={() => handleRemoveProduct(product.perfumeVariantId)}
                                                    title="Xóa sản phẩm"
                                                >
                                                    ×
                                                </button>
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            )}
                        </div>

                        {/* Box 3: Shipping Method */}
                        <div className="checkout-box shipping-method">
                            <h2>Đơn vị vận chuyển</h2>
                            {shippingMethods.length > 0 ? (
                                <div className="shipping-options">
                                    <select 
                                        className="shipping-select"
                                        value={selectedShippingMethod?.id || ''}
                                        onChange={handleShippingMethodChange}
                                    >
                                        {shippingMethods.map(method => (
                                            <option key={method.id} value={method.id}>
                                                {method.name} - {formatCurrency(method.shipCost || 0)}
                                            </option>
                                        ))}
                                    </select>
                                    <div className="shipping-fee">
                                        Phí vận chuyển: {formatCurrency(shippingFee)}
                                    </div>
                                </div>
                            ) : (
                                <p>Đang tải phương thức vận chuyển...</p>
                            )}
                        </div>

                        {/* Box 4: Discount Voucher */}
                        <div className="checkout-box voucher">
                            <h2>Mã giảm giá</h2>
                            <div className="voucher-input-container">
                                <input 
                                    type="text"
                                    className="voucher-input"
                                    placeholder="Nhập mã giảm giá (6 ký tự)..."
                                    value={discountCode}
                                    onChange={(e) => setDiscountCode(e.target.value)}
                                    maxLength={6}
                                />
                                <button className="apply-voucher-btn" onClick={applyVoucher}>
                                    Áp dụng
                                </button>
                            </div>
                            {discount > 0 && (
                                <div className="discount-applied">
                                    <p>Đã áp dụng giảm giá: {formatCurrency(discount)}</p>
                                </div>
                            )}
                        </div>

                        {/* Box 5: Payment Method */}
                        <div className="checkout-box payment-method">
                            <h2>Phương thức thanh toán</h2>
                            {paymentMethods.length > 0 ? (
                                <div className="payment-options">
                                    <select 
                                        className="payment-select"
                                        value={selectedPaymentMethod?.id || ''}
                                        onChange={handlePaymentMethodChange}
                                    >
                                        {paymentMethods.map(method => (
                                            <option key={method.id} value={method.id}>
                                                {method.name}
                                            </option>
                                        ))}
                                    </select>

                                    {/* Display relevant payment information */}
                                    {selectedPaymentMethod?.id === 1 && (
                                        <div className="bank-info">
                                            <h3>Thông tin tài khoản ngân hàng</h3>
                                            <p>Tên ngân hàng: ABC Bank</p>
                                            <p>Số tài khoản: 2427686868</p>
                                            <p>Tên chủ tài khoản: HyperfumeShop</p>
                                            <p>Nội dung: Thanh toán đơn hàng</p>
                                        </div>
                                    )}

                                    {selectedPaymentMethod?.id === 3 && (
                                        <div className="credit-card-container">
                                            <input 
                                                type="text" 
                                                className="credit-card-input"
                                                placeholder="Nhập số thẻ tín dụng (9 chữ số)" 
                                                value={creditCardNumber} 
                                                onChange={(e) => {
                                                    // Only allow digits
                                                    const value = e.target.value.replace(/\D/g, '');
                                                    setCreditCardNumber(value);
                                                }} 
                                                maxLength={9} 
                                            />
                                        </div>
                                    )}
                                </div>
                            ) : (
                                <p>Đang tải phương thức thanh toán...</p>
                            )}

                            {/* Order Summary */}
                            <div className="order-summary">
                                <div className="summary-row">
                                    <span>Tổng tiền hàng:</span>
                                    <span>{formatCurrency(totalAmount)}</span>
                                </div>
                                {discount > 0 && (
                                    <div className="summary-row discount-row">
                                        <span>Giảm giá:</span>
                                        <span>-{formatCurrency(discount)}</span>
                                    </div>
                                )}
                                <div className="summary-row">
                                    <span>Phí vận chuyển:</span>
                                    <span>{formatCurrency(shippingFee)}</span>
                                </div>
                                <div className="summary-row total-row">
                                    <span>Tổng thanh toán:</span>
                                    <span>{formatCurrency(discountedAmount)}</span>
                                </div>
                                <button 
                                    className="place-order-btn"
                                    onClick={handleOrder}
                                    disabled={products.length === 0 || loading}
                                >
                                    Đặt hàng
                                </button>
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default memo(ThanhToan);