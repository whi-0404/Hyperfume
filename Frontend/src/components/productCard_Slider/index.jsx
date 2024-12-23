import React, { memo, useState, useEffect } from "react";
import handleBase64Decode from "../../components/covertBase64ToImg"
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Pagination, A11y, Autoplay } from "swiper/modules";
import "./style.scss";
import "swiper/css";
import "swiper/css/bundle";
import ProductCard from "../productCard";
import { listProducts } from "../../services/ProductService";

const CardSlider = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    listProducts()
      .then((response) => {
        setProducts(response.data);
        setLoading(false); // Kết thúc loading
      })
      .catch((error) => {
        console.error(error);
        setError('Failed to fetch products'); // Lưu lỗi vào state
        setLoading(false);
      });
  }, []);

  if (loading) return <div>Loading...</div>;

  // Hiển thị lỗi nếu có
  if (error) return <div>Error: {error}</div>;
  return (
    <>
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
          {products.result.map((product) => (
            <SwiperSlide>
              <ProductCard
                img={handleBase64Decode(product.thumbnailImageData)}
                name={product.name}
                brandName={product.brandName}
                price1={product.perfumeVariantResponseList[0].price}
                price2={product.perfumeVariantResponseList[1].price}
              />
            </SwiperSlide>
          ))}
        </Swiper>
      </div>
    </>
  );
};

export default memo(CardSlider);
