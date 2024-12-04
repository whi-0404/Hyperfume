import React, { useState, useEffect } from 'react';
import './style.scss'; // Import file SCSS cho styling
import ProductCard from '../productCard'; // Giả sử bạn đã có component ProductCard

const data = [
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
];

const CardSlider = () => {
    const [currentIndex, setCurrentIndex] = useState(0);

    const nextSlide = () => {
        setCurrentIndex((prevIndex) => (prevIndex + 1) % data.length);
    };

    const prevSlide = () => {
        setCurrentIndex((prevIndex) => (prevIndex - 1 + data.length) % data.length);
    };

    useEffect(() => {
        const interval = setInterval(() => {
            nextSlide(); // Tự động chuyển đến slide tiếp theo
        }, 2000); // Thời gian giữa các lần chuyển slide (2000ms = 2 giây)

        return () => clearInterval(interval); // Dọn dẹp interval khi component unmount
    }, [currentIndex]); // Chạy 1 lần khi component mount

    return (
        <div className="cardSlider-container">
            <div className="cardSlider">
                <button className="prev-btn" onClick={prevSlide}>❰</button>
                <div className="cardSlider-inner">
                    {data.map((d, index) => (
                        <div
                            key={index}
                            className={`cardSlider-item ${index === currentIndex ? 'active' : ''}`}>
                            <ProductCard
                                src={d.img}
                                brandName={d.brandName}
                                name={d.name}
                                price={d.price}
                            />
                        </div>
                    ))}
                </div>
                <button className="next-btn" onClick={nextSlide}>❱</button>
            </div>
        </div>
    );
};

export default CardSlider;
