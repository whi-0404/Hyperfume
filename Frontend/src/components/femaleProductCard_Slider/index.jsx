import React, { memo } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination, A11y, Autoplay } from 'swiper/modules';
import './style.scss';
import 'swiper/css';
import 'swiper/css/bundle';
import ProductCard from '../productCard';

const productData = [
    {
        img: require("../../assets/productImages/green-irish-tweed.png"),
        name: "Green Irish Tweed",
        brandName: "Creed",
        price: "650,000đ - 6,100,000đ"
    },
    {
        img: require("../../assets/productImages/creed-aventus.png"),
        name: "Aventus",
        brandName: "Creed",
        price: "650,000đ - 6,100,000đ"
    },
    {
        img: require("../../assets/productImages/chanel-chance-eau-fraiche.png"),
        name: "Chanel Chance Eau Fraiche",
        brandName: "Chanel",
        price: "350,000đ - 3,100,000đ"
    },
    {
        img: require("../../assets/productImages/green-irish-tweed.png"),
        name: "Angel's Share",
        brandName: "By Kilian",
        price: "900,000đ - 4,100,000đ"
    },
    {
        img: require("../../assets/productImages/green-irish-tweed.png"),
        name: "Replica Sailing Day",
        brandName: "Maison Margiela",
        price: "350,000đ - 2,700,000đ"
    },
    {
        img: require("../../assets/productImages/green-irish-tweed.png"),
        name: "Replica ",
        brandName: "Maison Margiela",
        price: "350,000đ - 2,700,000đ"
    },
    {
        img: require("../../assets/productImages/green-irish-tweed.png"),
        name: "Apple Brandy On the Rock",
        brandName: "By Kilian",
        price: "900,000đ - 4,100,000đ"
    },
    {
        img: require("../../assets/productImages/green-irish-tweed.png"),
        name: "Apple Brandy On the Rock",
        brandName: "By Kilian",
        price: "900,000đ - 4,100,000đ"
    },
    {
        img: require("../../assets/productImages/green-irish-tweed.png"),
        name: "Apple Brandy On the Rock",
        brandName: "By Kilian",
        price: "900,000đ - 4,100,000đ"
    },
];

const product = productData.map((item) => <ProductCard
    srcImg={item.img}
    name={item.name}
    brandName={item.brandName}
    price={item.price}
/>);

const CardSlider = () => {
    return (
        <>
            <div className="femaleCardSlider-container">
                <Swiper
                    modules={[Navigation, Pagination, A11y, Autoplay]}
                    spaceBetween={35}
                    slidesPerView={7}
                    autoplay={{
                        delay: 2500,
                        disableOnInteraction: false,
                    }}
                    navigation
                    pagination={{
                        clickable: true,
                        dynamicBullets: true,
                    }}
                    onSwiper={(swiper) => console.log(swiper)}
                    onSlideChange={() => console.log('slide change')}>

                    {productData.map((product) => (
                        <SwiperSlide>
                            <ProductCard
                                img={product.img}
                                name={product.name}
                                brandName={product.brandName}
                                price={product.price}
                            />
                        </SwiperSlide>
                    ))}
                </Swiper>
            </div>
        </>
    )
}

export default memo(CardSlider);
