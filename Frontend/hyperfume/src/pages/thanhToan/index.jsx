import { memo, useState } from "react";
import './style.scss';
import dior_mini from "../../assets/product_img/dior_sauvage.png";

const ThanhToan = () => {
    const [personalInfo, setPersonalInfo] = useState({
        fullName: '',
        email: '',
        phoneNumber: '',
        specific_address: '',
        city: '',
        district: '',
        ward: ''
    });

    const [products, setProducts] = useState([{ name: 'Dior Savage EDP', capacity: '100ml', price: 5100000, quantity: 1 }, 
        { name: 'Dior Savage EDP', capacity: '100ml', price: 5100000, quantity: 1 }
    ]);
    const [discountCode, setDiscountCode] = useState('');
    const [shippingUnit, setShippingUnit] = useState('Standard');
    const [shippingFee, setShippingFee] = useState(0);
    const [discount, setDiscount] = useState(0);
    const [orderSuccess, setOrderSuccess] = useState(false);
    const [paymentMethod, setPaymentMethod] = useState('1');
    const [creditCardNumber, setCreditCardNumber] = useState('');

    const totalAmount = products.reduce((total, product) => total + (product.price * product.quantity), 0);
    const discountedAmount = totalAmount - discount + shippingFee;

    const formatCurrency = (amount) => {
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND',
        }).format(amount);
    };

    const applyVoucher = () => {
        if (discountCode.length === 6) {
            const discountValue = totalAmount * 0.10; // Giảm giá 10%
            setDiscount(discountValue);
        } else {
            alert("Vui lòng nhập mã giảm giá hợp lệ (6 ký tự).");
        }
    };

    const handleOrder = () => {
        setOrderSuccess(true);
    };

    // Sample data for provinces, districts, and wards
    const provinces = [
        { name: "Hà Nội", districts: [
            { name: "Quận Hoàn Kiếm", wards: ["Phường Đồng Xuân", "Phường Hàng Mã"] },
            { name: "Quận Đống Đa", wards: ["Phường Nam Đồng", "Phường Văn Chương"] }
        ]},
        { name: "Hồ Chí Minh", districts: [
            { name: "Quận 1", wards: ["Phường Bến Nghé", "Phường Bến Thành"] },
            { name: "Quận 2", wards: ["Phường Thạnh Mỹ Lợi", "Phường An Khánh"] }
        ]},
        { name: "Đà Nẵng", districts: [
            { name: "Quận Hải Châu", wards: ["Phường Thạch Thang", "Phường Nam Dương"] },
            { name: "Quận Sơn Trà", wards: ["Phường Thọ Quang", "Phường An Hải Bắc"] }
        ]}
    ];

    const [districts, setDistricts] = useState([]);
    const [wards, setWards] = useState([]);

    const handleCityChange = (e) => {
        const selectedCity = e.target.value;
        const cityData = provinces.find(province => province.name === selectedCity);
        setDistricts(cityData ? cityData.districts : []);
        setWards([]);
        setPersonalInfo({ ...personalInfo, city: selectedCity, district: '', ward: '' });
    };

    const handleDistrictChange = (e) => {
        const selectedDistrict = e.target.value;
        const districtData = districts.find(district => district.name === selectedDistrict);
        setWards(districtData ? districtData.wards : []);
        setPersonalInfo({ ...personalInfo, district: selectedDistrict, ward: '' });
    };

    const handleShippingUnitChange = (e) => {
        const selectedUnit = e.target.value;
        setShippingUnit(selectedUnit);

        const shippingFees = {
            tietkiem: 15000,
            nhanh: 30000,
            hoatoc: 50000
        };
        setShippingFee(shippingFees[selectedUnit]);
    };

    return (
        <div className="thanhtoan">
            {orderSuccess ? (
                <div className="success-message">
                    <h2>Đặt hàng thành công!</h2>
                    <table>
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
                                    <p><strong>Email:</strong> {personalInfo.email}</p>
                                    <p><strong>Điện thoại:</strong> {personalInfo.phoneNumber}</p>
                                    <p><strong>Địa chỉ:</strong> {personalInfo.specific_address}, {personalInfo.ward}, {personalInfo.district}, {personalInfo.city}</p>
                                </td>
                                <td>
                                    {products.map((product, index) => (
                                        <p key={index}>{product.name} - {product.capacity} - {formatCurrency(product.price)} x {product.quantity}</p>
                                    ))}
                                </td>
                                <td>{formatCurrency(discountedAmount)}</td>
                                <td>{paymentMethod === '1' ? 'Chuyển khoản' : paymentMethod === '2' ? 'Thanh toán khi nhận hàng' : 'Thẻ tín dụng'}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            ) : (
                <div className="grid1">
                    {/* Box 1: Personal Information */}
                    <div className="box1">
                        <h2>Thông tin thanh toán</h2>
                        <input className="text_box_info"
                            type="text" 
                            placeholder="Họ và tên" 
                            value={personalInfo.fullName} 
                            onChange={(e) => setPersonalInfo({ ...personalInfo, fullName: e.target.value })} 
                        />
                        <input className="text_box_info"
                            type="email" 
                            placeholder="Email" 
                            value={personalInfo.email} 
                            onChange={(e) => setPersonalInfo({ ...personalInfo, email: e.target.value })} 
                        />
                        <input className="text_box_info"
                            type="tel" 
                            placeholder="Điện thoại" 
                            value={personalInfo.phoneNumber} 
                            onChange={(e) => setPersonalInfo({ ...personalInfo, phoneNumber: e.target.value })} 
                        />
                        <input className="text_box_info"
                            type="text" 
                            placeholder="Địa chỉ" 
                            value={personalInfo.specific_address} 
                            onChange={(e) => setPersonalInfo({ ...personalInfo, specific_address: e.target.value })} 
                        />
                        <div className="address-dropdowns">
                            <select value={personalInfo.city} onChange={handleCityChange}>
                                <option value="">Tỉnh / Thành phố</option>
                                {provinces.map((province, index) => (
                                    <option key={index} value={province.name}>{province.name}</option>
                                ))}
                            </select>
                            <select value={personalInfo.district} onChange={handleDistrictChange}>
                                <option value="">Quận / Huyện</option>
                                {districts.map((district, index) => (
                                    <option key={index} value={district.name}>{district.name}</option>
                                ))}
                            </select>
                            <select value={personalInfo.ward} onChange={(e) => setPersonalInfo({ ...personalInfo, ward: e.target.value })}>
                                <option value="">Phường / Xã</option>
                                {wards.map((ward, index) => (
                                    <option key={index} value={ward}>{ward}</option>
                                ))}
                            </select>
                        </div>
                    </div>

                    {/* Box 2: Products */}
                    <div className="box1">
                        <div className="hop2">
                            <div className="lable_sp">
                                <h2 className="l1">Sản phẩm</h2>
                                <h2 className="l2">Tên sản phẩm</h2>
                                <h2 className="l3">Dung tích</h2>
                                <h2 className="l4">Đơn giá</h2>
                                <h2 className="l5">Số lượng</h2>
                            </div>
                            {products.map((product, index) => (
                                <div key={index} className="product1">
                                    <img src={dior_mini} alt="product_mini" className="thumbnail1" />
                                    <div className="name1">{product.name}</div>
                                    <div className="ml">{product.capacity}</div>
                                    <div className="giatien1">{formatCurrency(product.price)}</div>

                                    <input className="soluong1"
                                        type="number"
                                        value={product.quantity}
                                        onChange={(e) => {
                                            const newProducts = [...products];
                                            newProducts[index].quantity = e.target.value;
                                            setProducts(newProducts);
                                        }}
                                    />

                                    <input className="note_seller" type="text" placeholder="Ghi chú cho người bán..." />
                                </div>
                            ))}
                        </div>
                    </div>

                    {/* Box 3: Shipping Method */}
                    <div className="box1">
                        <h2>Đơn vị vận chuyển</h2>
                        <select className="hop3_box" value={shippingUnit} onChange={handleShippingUnitChange}>
                            <option value="tietkiem">Tiết kiệm</option>
                            <option value="nhanh">Nhanh</option>
                            <option value="hoatoc">Hỏa tốc</option>
                        </select>
                        <div className="hop3_phi">Phí vận chuyển: {formatCurrency(shippingFee)}</div>
                    </div>

                    {/* Box 4: Discount Voucher */}
                    <div className="box1">
                        <h2>Voucher</h2>
                        <input className="hop4_box"
                            type="text"
                            placeholder="Nhập mã giảm giá..."
                            value={discountCode}
                            onChange={(e) => setDiscountCode(e.target.value)}
                        />
                        <button className="hop4_button" onClick={applyVoucher}>Áp dụng</button>
                    </div>

                    {/* Box 5: Payment Method */}
                    <div className="box1">
                        <h2>Phương thức thanh toán</h2>
                        <select className="hop5_box" value={paymentMethod} onChange={(e) => setPaymentMethod(e.target.value)}>
                            <option value="1">Chuyển khoản</option>
                            <option value="2">Thanh toán khi nhận hàng</option>
                            <option value="3">Thẻ tín dụng</option>
                        </select>

                        {/* Hiển thị thông tin tài khoản ngân hàng nếu chọn "Chuyển khoản" */}
                        {paymentMethod === "1" && (
                            <div className="bank-info">
                                <h3>Thông tin tài khoản ngân hàng</h3>
                                <p>Tên ngân hàng: ABC Bank</p>
                                <p>Số tài khoản: 2427686868</p>
                                <p>Tên chủ tài khoản: HyperfumeShop</p>
                            </div>
                        )}

                        {/* Hiển thị ô nhập số thẻ tín dụng nếu chọn "Thẻ tín dụng" */}
                        {paymentMethod === "3" && (
                            <input className="tindung"
                                type="text" 
                                placeholder="Nhập số thẻ tín dụng (9 chữ số)" 
                                value={creditCardNumber} 
                                onChange={(e) => setCreditCardNumber(e.target.value)} 
                                maxLength={9} 
                            />
                        )}

                        <div className="hop5_text">
                            <div>Tổng tiền: {formatCurrency(totalAmount)}</div>
                            {discount > 0 && <div>Giảm giá: {formatCurrency(discount)}</div>}
                            <div>Phí vận chuyển: {formatCurrency(shippingFee)}</div>
                            <div>Tổng thanh toán: {formatCurrency(discountedAmount)}</div>
                            <button className="hop5_button" onClick={handleOrder}>Đặt hàng</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default memo(ThanhToan);