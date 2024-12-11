import React, { memo } from 'react';
import Carousel from 'react-multi-carousel';
import 'react-multi-carousel/lib/styles.css';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import './style.scss';
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
        img: require("../../assets/productImages/maison-crivelli-oud-maracuja.png"),
        name: "Oud Maracuja",
        brandName: "Maison Criveli",
        price: "650,000đ - 6,100,000đ"
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
            <div className="cardSlider-container">
                {/* <Carousel responsive={responsive}>
                    {product}
                </Carousel> */}
                <Swiper
                    spaceBetween={50}
                    slidesPerView={4}
                    onSlideChange={() => console.log('slide change')}
                    onSwiper={(swiper) => console.log(swiper)}>

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
