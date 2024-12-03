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

    const goToPre = () => {
        const isFirstSlide = currentIndex === 0;
        const newIndex = isFirstSlide ? slides.length - 1 : currentIndex - 1;
        setCurrentIndex(newIndex);
    };

    const goToNext = () => {
        const isLastSlide = currentIndex === slides.length - 1;
        const newIndex = isLastSlide ? 0 : currentIndex + 1;
        setCurrentIndex(newIndex);
    };

    useEffect(() => {
        const intervalId = setInterval(() => {
            goToNext();
        }, 3000);

        return () => clearInterval(intervalId);
    }, [currentIndex]);

    return (
        <div className="sliderStyle">
            <div className="leftArrow" onClick={goToPre}>❰</div>
            <div className="rightArrow" onClick={goToNext}>❱</div>

            {slides.map((slide, index) => {
                let slideClass = "slide";
                if (index === currentIndex) slideClass += " activeSlide";
                if (index === (currentIndex - 1 + slides.length) % slides.length) slideClass += " previousSlide";

                return (
                    <div
                        key={index}
                        className={slideClass}
                        style={{ backgroundImage: `url(${slide.url})` }}
                    ></div>
                );
            })}
        </div>
    );
};

export default memo(ImageSlider);

