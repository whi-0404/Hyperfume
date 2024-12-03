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

    const [products, setProducts] = useState([{ name: 'Dior Savage EDP', capacity: '100ml', price: 51000000, quantity: 1 }, 
        { name: 'Dior Savage EDP', capacity: '100ml', price: 51000000, quantity: 1 }
    ]);
    const [discountCode, setDiscountCode] = useState('');
    const [shippingUnit, setShippingUnit] = useState('Standard');
    const [shippingFee, setShippingFee] = useState(0);
    const [discount, setDiscount] = useState(0);
    const [orderSuccess, setOrderSuccess] = useState(false);
    const [paymentMethod, setPaymentMethod] = useState('1'); // State for payment method

    const totalAmount = products.reduce((total, product) => total + (product.price * product.quantity), 0);
    const discountedAmount = totalAmount - discount + shippingFee;

    const applyVoucher = () => {
        if (discountCode.length === 6) {
            const discountValue = totalAmount * 0.10; // 10% discount
            setDiscount(discountValue);
        } else {
            alert("Please enter a valid 6-digit voucher code.");
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
            Standard: 0,
            Express: 20000,
            Overnight: 50000
        };
        setShippingFee(shippingFees[selectedUnit]);
    };

    return (
        <div className="thanhtoan">
            {orderSuccess ? (
                <div className="success-message">
                    <h2>Order Successful!</h2>
                    <p>Thank you for your order, {personalInfo.fullName}!</p>
                    <p>Your order total is: {discountedAmount} VND.</p>
                </div>
            ) : (
                <div className="grid1">
                    {/* Box 1: Personal Information */}
                    <div className="box1">
                        <h2>Thông tin thanh toán</h2>
                        <input className="text_box_info"
                            type="text" 
                            placeholder="Full Name" 
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
                            placeholder="Phone Number" 
                            value={personalInfo.phoneNumber} 
                            onChange={(e) => setPersonalInfo({ ...personalInfo, phoneNumber: e.target.value })} 
                        />
                        <input className="text_box_info"
                            type="text" 
                            placeholder="Address" 
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
                                <div className="giatien1">{product.price} VND</div>
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
                        <h2>Phương thức vận chuyển</h2>
                        <select className="hop3" value={shippingUnit} onChange={handleShippingUnitChange}>
                            <option value="Standard">Standard</option>
                            <option value="Express">Express</option>
                            <option value="Overnight">Overnight</option>
                        </select>
                        <div className="hop3">Phí vận chuyển: {shippingFee} VND</div>
                    </div>

                    {/* Box 4: Discount Voucher */}
                    <div className="box1">
                        <h2>Voucher</h2>
                        <input className="hop4_box"
                            type="text"
                            placeholder="Enter discount code"
                            value={discountCode}
                            onChange={(e) => setDiscountCode(e.target.value)}
                        />
                        <button className="hop4_button" onClick={applyVoucher}>Apply</button>
                    </div>

                    {/* Box 5: Payment Method */}
                    <div className="box1">
                        <h2>Phương thức thanh toán</h2>
                        <select className="hop5_box" value={paymentMethod} onChange={(e) => setPaymentMethod(e.target.value)}>
                            <option value="1">Credit Card</option>
                            <option value="2">PayPal</option>
                            <option value="3">Cash on Delivery</option>
                        </select>
                        <div className="hop5_text">
                        <div>Selected Payment Method: {paymentMethod === "1" ? "Credit Card" : paymentMethod === "2" ? "PayPal" : "Cash on Delivery"}</div>
                        <div>Total Amount: {totalAmount} VND</div>
                        {discount > 0 && <div>Discount: {discount} VND</div>}
                        <div>Shipping Fee: {shippingFee} VND</div>
                        <div>Total Payable: {discountedAmount} VND</div>
                        <button className="hop5_button" onClick={handleOrder}>Order</button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default memo(ThanhToan);