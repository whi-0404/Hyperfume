import React, { memo, useState, useEffect } from "react";
import { toByteArray } from 'base64-js';
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Pagination, A11y, Autoplay } from "swiper/modules";
import "./style.scss";
import "swiper/css";
import "swiper/css/bundle";
import ProductCard from "../productCard";
import { listProducts } from "../../services/ProductService";

// const productData = [
//   {
//     img: require("../../assets/productImages/creed/green-irish-tweed.png"),
//     name: "Green Irish Tweed",
//     brandName: "Creed",
//     price: "650,000đ - 6,100,000đ",
//   },
//   {
//     img: require("../../assets/productImages/creed/creed-aventus.png"),
//     name: "Aventus",
//     brandName: "Creed",
//     price: "650,000đ - 6,100,000đ",
//   },
//   {
//     img: require("../../assets/productImages/maison-crivelli-oud-maracuja.png"),
//     name: "Oud Maracuja",
//     brandName: "Maison Criveli",
//     price: "650,000đ - 6,100,000đ",
//   },
//   {
//     img: require("../../assets/productImages/creed/green-irish-tweed.png"),
//     name: "Angel's Share",
//     brandName: "By Kilian",
//     price: "900,000đ - 4,100,000đ",
//   },
//   {
//     img: require("../../assets/productImages/creed/green-irish-tweed.png"),
//     name: "Replica Sailing Day",
//     brandName: "Maison Margiela",
//     price: "350,000đ - 2,700,000đ",
//   },
//   {
//     img: require("../../assets/productImages/creed/green-irish-tweed.png"),
//     name: "Replica ",
//     brandName: "Maison Margiela",
//     price: "350,000đ - 2,700,000đ",
//   },
//   {
//     img: require("../../assets/productImages/creed/green-irish-tweed.png"),
//     name: "Apple Brandy On the Rock",
//     brandName: "By Kilian",
//     price: "900,000đ - 4,100,000đ",
//   },
// ];

const handleBase64Decode = (base64String) => {
  try {
    // Loại bỏ tiền tố nếu có
    const base64 = base64String.split(',')[1] || base64String;

    // Decode Base64 thành Uint8Array
    const byteArray = toByteArray(base64);

    // Tạo Blob từ Uint8Array
    const blob = new Blob([byteArray], { type: 'image/png' });

    // Tạo URL từ Blob
    const imageUrl = URL.createObjectURL(blob);

    return imageUrl;
  } catch (error) {
    console.error('Error decoding Base64:', error.message);
  }
};

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
