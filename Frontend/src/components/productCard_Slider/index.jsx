import React, { memo, useState, useEffect } from "react";
import handleBase64Decode from "../../components/covertBase64ToImg";
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Pagination, A11y, Autoplay } from "swiper/modules";
import "./style.scss";
import "swiper/css";
import "swiper/css/bundle";
import ProductCard from "../productCard";
import { listProducts } from "../../services/AllProduct";

const CardSlider = () => {
  // Khởi tạo state products với kiểu dữ liệu object chứa key result là mảng
  const [products, setProducts] = useState({ result: [] });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    listProducts()
      .then((response) => {
        setProducts(response.data);
        setLoading(false);
      })
      .catch((error) => {
        console.error(error);
        setError("Failed to fetch products");
        setLoading(false);
      });
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error: {error}</div>;

  // Đảm bảo rằng products.result là mảng
  const productList = Array.isArray(products.result) ? products.result : [];

  return (
    <div className="cardSlider-container">
      <Swiper
        modules={[Navigation, Pagination, A11y, Autoplay]}
        spaceBetween={35}
        slidesPerView={5}
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
        onSlideChange={() => console.log("slide change")}
      >
        {productList.map((product) => (
          <SwiperSlide key={product.id}>
            <ProductCard
              id={product.id}
              img={product.ThumbnailImageUrl}
              name={product.name}
              brandName={product.brandName}
              price1={product.min_price}
              price2={product.max_price}
            />
          </SwiperSlide>
        ))}
      </Swiper>
    </div>
  );
};

export default memo(CardSlider);
