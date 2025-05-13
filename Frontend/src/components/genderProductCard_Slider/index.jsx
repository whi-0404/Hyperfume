import React, { memo, useState, useEffect } from "react";
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation, Pagination, A11y, Autoplay } from 'swiper/modules';
import './style.scss';
import 'swiper/css';
import 'swiper/css/bundle';
import ProductCard from '../productCard';
import { listProducts } from "../../services/AllProduct";

const GenderProductCard_Slider = ({ gender }) => {
    const [products, setProducts] = useState({
        result: {
            data: [],
            totalPages: 0,
            totalElements: 0,
            pageSize: 15
        }
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
  
    useEffect(() => {
        setLoading(true);
        // Gọi API với tham số gender
        const queryParams = { gender: gender };
        
        listProducts(queryParams)
            .then((response) => {
                if (response.data && response.data.code === 1000) {
                    setProducts({
                        result: {
                            data: response.data.result.data || [],
                            totalPages: response.data.result.totalPages || 0,
                            totalElements: response.data.result.totalElements || 0,
                            pageSize: response.data.result.pageSize || 15
                        }
                    });
                } else {
                    setError('Invalid response format');
                }
                setLoading(false);
            })
            .catch((error) => {
                console.error(`Error fetching ${gender} products:`, error);
                setError('Failed to fetch products');
                setLoading(false);
            });
    }, [gender]); // Thêm gender vào dependency array để khi gender thay đổi sẽ gọi lại API
  
    if (loading) return <div className="loading-indicator">Loading {gender} products...</div>;
    if (error) return <div className="error-message">Error: {error}</div>;
    
    // Lấy danh sách sản phẩm từ state
    const productList = products.result.data || [];
    
    return (
        <div className="gender-product-slider-container">
            {productList.length === 0 ? (
                <div className="no-products-message">No {gender} products found</div>
            ) : (
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
                    breakpoints={{
                        320: {
                            slidesPerView: 1,
                            spaceBetween: 20
                        },
                        480: {
                            slidesPerView: 2,
                            spaceBetween: 30
                        },
                        768: {
                            slidesPerView: 3,
                            spaceBetween: 30
                        },
                        1024: {
                            slidesPerView: 4,
                            spaceBetween: 40
                        }
                    }}>

                    {productList.map((product) => (
                        <SwiperSlide key={product.id }>
                            <ProductCard
                                 key={product.id}
                                 id={product.id}
                                 img={product.thumbnailImageUrl}
                                 brandName={product.brandName}
                                 name={product.name}
                                 price1={product.min_price}
                                 price2={product.max_price}
                            />
                        </SwiperSlide>
                    ))}
                </Swiper>
            )}
        </div>
    );
};

export default memo(GenderProductCard_Slider);