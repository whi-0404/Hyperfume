import React, { memo, useState, useEffect } from "react";
import { Link } from "react-router-dom";
import './style.scss';

import ImageSlider from '../../components/imageSlider';
import ProductCardSlider from '../../components/productCard_Slider';
import GenderProductCard_Slider from '../../components/genderProductCard_Slider';

function HomePage() {
    const [selectedProductType, setSelectedProductType] = useState('Nam');

    return (
        <div className='container'>
            <main>
                <section className='sliderStyle'>
                    <ImageSlider />
                </section>

                <section className="famous-brand">
                    <h1>Thương hiệu nổi tiếng</h1>
                    <div className="first-brand-list">
                        <a href="/brands/clive-christian"><img src={require("../../assets/image/Clive Christian.png")} alt="Logo Clive" /></a>
                        <a href="/brands/xerjoff"><img src={require("../../assets/image/Xerjoff.png")} alt="Logo Xerjoff" /></a>
                        <a href="/brands/ex-nihilo"><img src={require("../../assets/image/Ex Nihilo.png")} alt="Logo Ex Nihilo" /></a>
                        <a href="/brands/roja"><img src={require("../../assets/image/Roja.png")} alt="Logo Roja" /></a>
                        <a href="/brands/tom-ford"><img style={{ objectFit: 'contain' }} src={require("../../assets/image/Tom Ford.png")} alt="Logo TF" /></a>
                    </div>
                    <div className="second-brand-list">
                        <a href="/brands/dior"><img src={require("../../assets/image/Dior.png")} alt="Logo Dior" /></a>
                        <a href="/brands/zoologist"><img src={require("../../assets/image/Zoologist.png")} alt="Logo Zoo" /></a>
                        <a href="/brands/chanel"><img style={{ objectFit: 'contain' }} src={require("../../assets/image/Chanel.png")} alt="Logo Chanel" /></a>
                        <a href="/brands/hermes"><img style={{ objectFit: 'contain' }} src={require("../../assets/image/Hermes.png")} alt="Logo Hermes" /></a>
                        <a href="/brands/louis-vuitton"><img style={{ objectFit: 'contain' }} src={require("../../assets/image/LV.png")} alt="Logo LV" /></a>
                    </div>
                </section>

                <hr />

                <section className="Sale-Container">
                    <div className="Flash-Sale">
                        <span><Link to="/flash-sale">Flash Sale</Link></span>
                    </div>

                    <ProductCardSlider />
                </section>

                <section className="famous-product">
                    <h1>Sản phẩm nổi bật</h1>

                    <div className="famous-product-type">
                        <button
                            onClick={() => setSelectedProductType('Nam')}
                            className={selectedProductType === 'Nam' ? 'active' : ''}>
                            Nước hoa nam
                        </button>

                        <button
                            onClick={() => setSelectedProductType('Nữ')}
                            className={selectedProductType === 'Nữ' ? 'active' : ''}>
                            Nước hoa nữ
                        </button>

                        <button
                            onClick={() => setSelectedProductType('unisex')}
                            className={selectedProductType === 'Unisex' ? 'active' : ''}>
                            Unisex
                        </button>
                    </div>

                    <GenderProductCard_Slider gender={selectedProductType} />
                </section>
            </main>
        </div>
    );
}

export default memo(HomePage);