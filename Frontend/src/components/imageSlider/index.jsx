import React, { useState, memo, useEffect } from "react";
import "./style.scss";

import image1 from '../../assets/productImages/image-1.jpg';
import image2 from '../../assets/productImages/image-2.jpg';
import image3 from '../../assets/productImages/image-3.jpg';
import image4 from '../../assets/productImages/image-4.jpg';
import image5 from '../../assets/productImages/image-5.jpg';

const slides = [
    { url: image1, title: "" },
    { url: image2, title: "" },
    { url: image3, title: "" },
    { url: image4, title: "" },
    { url: image5, title: "" },
];

const ImageSlider = () => {
    const [currentIndex, setCurrentIndex] = useState(0);

    const slideStyle = {
        width: "100%",
        height: "100%",
        backgroundPosition: "center",
        backgroundSize: "cover",
        backgroundImage: `url(${slides[currentIndex].url})`,
    };

    const goToPre = () => {
        const isFirstSlide = currentIndex === 0;
        const newIndex = isFirstSlide ? slides.length - 1 : currentIndex - 1;
        setCurrentIndex(newIndex)
    }

    const goToNext = () => {
        const isLastSlide = currentIndex === slides.length - 1;
        const newIndex = isLastSlide ? 0 : currentIndex + 1;
        setCurrentIndex(newIndex);
    }

    useEffect(() => {
        const intervalId = setInterval(() => {
            goToNext();
        }, 3000);

        // Cleanup interval khi component bị unmount
        return () => clearInterval(intervalId);
    }, [currentIndex]); // Mỗi khi currentIndex thay đổi, useEffect sẽ chạy lại

    return (
        <>
            <div className="sliderStyle">
                <div className="leftArrow" set onClick={goToPre}>❰</div>
                <div className="rightArrow" onClick={goToNext}>❱</div>
                <div style={slideStyle}></div>
            </div>
        </>
    );
};

export default memo(ImageSlider);