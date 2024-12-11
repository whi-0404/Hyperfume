import { memo } from 'react';
import { Link } from "react-router-dom";
import './style.scss';
import ImageSlider from '../../components/imageSlider';
import ProductCardSlider from '../../components/productCard_Slider';

function homePage() {

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

                    <div className="Sale-Container">
                        <div className="Flash-Sale">
                            <span><Link to="/">Flash Sale</Link></span>
                        </div>

                        <ProductCardSlider />
                    </div>

                    <div className="famous-product">
                        <h1>Sản phẩm nổi bật</h1>
                    </div>

                </main>
            </div>
        </>
    );
}

export default memo(homePage);