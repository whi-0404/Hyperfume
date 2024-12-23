import React, { memo } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination, A11y, Autoplay } from 'swiper/modules';
import './style.scss';
import 'swiper/css';
import 'swiper/css/bundle';
import ProductCard from '../productCard';

const productData = [
    {
        img: require("../../assets/productImages/Nassomatto-Narcotic-V.png"),
        name: "Narcotic V",
        brandName: "Nas",
        price1: 1100000,
        price2: 3000000
    },
    {
        img: require("../../assets/productImages/creed/creed-aventus.png"),
        name: "Aventus",
        brandName: "Creed",
        price1: 650000,
        price2: 6100000
    },
    {
        img: require("../../assets/productImages/chanel-chance-eau-fraiche.png"),
        name: "Chanel Chance Eau Fraiche",
        brandName: "Chanel",
        price1: 350000,
        price2: 3100000
    },
    {
        img: require("../../assets/productImages/creed/green-irish-tweed.png"),
        name: "Angel's Share",
        brandName: "By Kilian",
        price1: 900000,
        price2: 4100000,
    },
    {
        img: require("../../assets/productImages/creed/green-irish-tweed.png"),
        name: "Replica Sailing Day",
        brandName: "Maison Margiela",
        price1: 350000,
        price2: 2700000,
    },
    {
        img: require("../../assets/productImages/creed/green-irish-tweed.png"),
        name: "Replica ",
        brandName: "Maison Margiela",
        price1: 350000,
        price2: 2700000,
    },
    {
        img: require("../../assets/productImages/creed/green-irish-tweed.png"),
        name: "Apple Brandy On the Rock",
        brandName: "By Kilian",
        price1: 900000,
        price2: 4100000,
    },
    {
        img: require("../../assets/productImages/creed/green-irish-tweed.png"),
        name: "Apple Brandy On the Rock",
        brandName: "By Kilian",
        price1: 900000,
        price2: 4100000,
    },
    {
        img: require("../../assets/productImages/creed/green-irish-tweed.png"),
        name: "Apple Brandy On the Rock",
        brandName: "By Kilian",
        price1: 900000,
        price2: 4100000,
    },
];

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
                                price1={product.price1}
                                price2={product.price2}
                            />
                        </SwiperSlide>
                    ))}
                </Swiper>
            </div>
        </>
    )
}

export default memo(CardSlider);
