import React, { memo } from 'react';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination, A11y, Autoplay } from 'swiper/modules';
import './style.scss';
import 'swiper/css';
import 'swiper/css/bundle';
import ProductCard from '../productCard';

const productData = [
    {
        img: require("../../assets/productImages/creed/green-irish-tweed.png"),
        name: "Green Irish Tweed",
        brandName: "Creed",
        price1: 1100000,
        price2: 3000000
    },
    {
        img: require("../../assets/productImages/creed/creed-aventus.png"),
        name: "Aventus",
        brandName: "Creed",
        price1: 1100000,
        price2: 3000000
    },
    {
        img: require("../../assets/productImages/maison-crivelli-oud-maracuja.png"),
        name: "Oud Maracuja",
        brandName: "Maison Criveli",
        price1: 1100000,
        price2: 3000000
    },
    {
        img: require("../../assets/productImages/dior-homme-sport.png"),
        name: "Homme Sport",
        brandName: "Dior",
        price1: 1100000,
        price2: 3000000
    },
    {
        img: require("../../assets/productImages/replica-jazz-club.png"),
        name: "Replica Jazz club",
        brandName: "Maison Margiela",
        price1: 350000,
        price2: 3100000
    },
    {
        img: require("../../assets/productImages/dior_sauvage.png"),
        name: "Sauvage EDP ",
        brandName: "Dior",
        price1: 350000,
        price2: 3100000
    },
    {
        img: require("../../assets/productImages/creed/green-irish-tweed.png"),
        name: "Ombre Leather",
        brandName: "Tom Ford",
        price1: 350000,
        price2: 3100000
    },
    {
        img: require("../../assets/productImages/creed/green-irish-tweed.png"),
        name: "Apple Brandy On the Rock",
        brandName: "By Kilian",
        price1: 350000,
        price2: 3100000
    },
    {
        img: require("../../assets/productImages/creed/green-irish-tweed.png"),
        name: "Apple Brandy On the Rock",
        brandName: "By Kilian",
        price1: 1100000,
        price2: 3000000
    },
];

const CardSlider = () => {
    return (
        <>
            <div className="maleCardSlider-container">
                <Swiper
                    modules={[Navigation, Pagination, A11y, Autoplay]}
                    spaceBetween={40}
                    slidesPerView={4}
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
