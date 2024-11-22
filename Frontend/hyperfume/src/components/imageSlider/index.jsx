import React, { useState, memo } from "react";
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

    const sliderStyle = {
        height: "100%",
        position: "relative"
    };

    const slideStyle = {
        width: "100%",
        height: "100%",
        backgroundPosition: "center",
        backgroundSize: "cover",
        backgroundImage: `url(${slides[currentIndex].url})`,
    };

    return (
        <>
            <div style={sliderStyle}>
                <div></div>
                <div style={slideStyle}></div>
            </div>
        </>
    );
};

export default memo(ImageSlider);