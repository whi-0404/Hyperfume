import React, { memo, useState, useEffect } from "react";
import { Link } from "react-router-dom";
import './style.scss';

import ImageSlider from '../../components/imageSlider';
import ProductCardSlider from '../../components/productCard_Slider';
import MaleProductCard_Slider from '../../components/maleProductCard_Slider';
import FemaleProductCard_Slider from '../../components/femaleProductCard_Slider';
import UnisexProductCard_Slider from '../../components/unisexProductCard_Slider';

import { listProducts } from "../../services/ProductService";
import { maleProducts } from "../../services/maleProducts";

function HomePage() {
    const [selectedProductType, setSelectedProductType] = useState('male');

    // const [products, setProducts] = useState([]);
    // const [loading, setLoading] = useState(true);
    // const [error, setError] = useState(null);

    // useEffect(() => {
    //     maleProducts()
    //         .then((response) => {
    //             setProducts(response.data);
    //             setLoading(false); // Kết thúc loading
    //         })
    //         .catch((error) => {
    //             console.error(error);
    //             setError('Failed to fetch products'); // Lưu lỗi vào state
    //             setLoading(false);
    //         });
    // }, []);


    // if (loading) return <div>Loading...</div>;

    // // Hiển thị lỗi nếu có
    // if (error) return <div>Error: {error}</div>;

    // for (let index in products.result) {
    //     console.log(products.result[index].name); // In ra từng đối tượng sản phẩm
    // }

    // if (Array.isArray(products)) {
    //     console.log("Data là array");
    // } else if (typeof products === "object") {
    //     console.log("Data là object");
    // } else {
    //     console.log("Data là kiểu khác");
    // }


    return (
        <>
            <div className='container'>
                <main>
                    <div className='sliderStyle'>
                        <ImageSlider />
                    </div>

                    <div class="famous-brand">
                        <h1>Thương hiệu nổi tiếng</h1>
                        <div class="first-brand-list">
                            <a href=""><img src={require("../../assets/image/Clive Christian.png")} alt="Logo Clive" /></a>
                            <a href=""><img src={require("../../assets/image/Xerjoff.png")} alt="Logo Xerjoff" /></a>
                            <a href=""><img src={require("../../assets/image/Ex Nihilo.png")} alt="Logo Ex Nihilo" /></a>
                            <a href=""><img src={require("../../assets/image/Roja.png")} alt="Logo Roja" /></a>
                            <a href=""><img style={{ objectFit: 'contain' }} src={require("../../assets/image/Tom Ford.png")} alt="Logo TF" /></a>
                        </div>
                        <div class="second-brand-list">
                            <a href=""><img src={require("../../assets/image/Dior.png")} alt="Logo Dior" /></a>
                            <a href=""><img src={require("../../assets/image/Zoologist.png")} alt="Logo Zoo" /></a>
                            <a href=""><img style={{ objectFit: 'contain' }} src={require("../../assets/image/Chanel.png")} alt="Logo Chanel" /></a>
                            <a href=""><img style={{ objectFit: 'contain' }} src={require("../../assets/image/Hermes.png")} alt="Logo Hermes" /></a>
                            <a href=""><img style={{ objectFit: 'contain' }} src={require("../../assets/image/LV.png")} alt="Logo LV" /></a>
                        </div>
                    </div>

                    <hr />


                    {/* <div className="test">
                        {products.result.map(product => (
                            <ProductCard
                                img={require("../../assets/image/Dior.png")}
                                name={product.name}
                                brandName={product.brandName}
                                price={product.price}
                            ></ProductCard> // Hiển thị tên sản phẩm
                        ))}
                    </div> */}


                    <div className="Sale-Container">
                        <div className="Flash-Sale">
                            <span><Link to="/flash-sale">Flash Sale</Link></span>
                        </div>

                        <ProductCardSlider />
                    </div>

                    <div className="famous-product">
                        <h1>Sản phẩm nổi bật</h1>

                        <div className="famous-product-type">
                            <button
                                onClick={() => setSelectedProductType('male')}
                                className={selectedProductType === 'male' ? 'active' : ''}>
                                Nước hoa nam
                            </button>

                            <button
                                onClick={() => setSelectedProductType('female')}
                                className={selectedProductType === 'female' ? 'active' : ''}>
                                Nước hoa nữ
                            </button>

                            <button
                                onClick={() => setSelectedProductType('unisex')}
                                className={selectedProductType === 'unisex' ? 'active' : ''}>
                                Unisex
                            </button>
                        </div>

                        {/* Điều kiện render dựa trên state */}
                        {selectedProductType === 'male' && (
                            <div>
                                <MaleProductCard_Slider />
                            </div>
                        )}
                        {selectedProductType === 'female' && (
                            <div>
                                <FemaleProductCard_Slider />
                            </div>
                        )}
                        {selectedProductType === 'unisex' && (
                            <div>
                                <UnisexProductCard_Slider />
                            </div>
                        )}
                    </div>

                </main>
            </div>
        </>
    );
}

export default memo(HomePage);