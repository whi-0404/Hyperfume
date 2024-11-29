import { memo } from 'react';
import './style.scss';
import ImageSlider from '../../components/imageSlider';
import ProductCard from '../../components/productCard';


const homePage = () => {

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

                    <div class="Flash-Sale">
                        <span>Flash Sale</span>
                    </div>
                    <ProductCard
                        src={require("../../assets/productImages/green-irish-tweed.png")}
                        brandName="Creed"
                        name="Green Irish Tweed"
                        price="650,000đ - 6,100,000đ" />

                </main>
            </div>
        </>
    );
};

export default memo(homePage);