import { memo, useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import ProductRating from "../../components/rating";
import ProductActions from "../../components/productActions";
import { AiFillStar, AiOutlineStar } from "react-icons/ai";
import "./style.scss";
import getProductDetail from "../../services/getProductDetail";
import { addRate, getRates } from "../../services/handleRate"; // Added import for getRates API

const suggestProducts = [
  {
    id: 1,
    name: "Armaf Club De Nuit Intense Man Limited Edition Parfum",
    price: "300,000đ - 2,150,000đ",
    image: require("../../assets/productImages/armaf/armaf-club-de-nuit.jpg"),
    url: "#",
  },
  {
    id: 2,
    name: "Creed Green Irish Tweed EDP",
    price: "650,000đ - 6,100,000đ",
    image: require("../../assets/productImages/creed/green-irish-tweed.png"),
    url: "#",
  },
  {
    id: 3,
    name: "Roja Elysium Pour Homme Parfum",
    price: "900,000đ - 7,600,000đ",
    image: require("../../assets/productImages/roja-dove/roja-elysium.png"),
    url: "#",
  },
  {
    id: 4,
    name: "Diesel Fuel for Life Homme EDP",
    price: "250,000đ - 1,900,000đ",
    image: require("../../assets/productImages/diesel/diesel-fuel.png"),
    url: "#",
  },
];

const ProductDetail = () => {
  const { id } = useParams();
  const [currentPage, setCurrentPage] = useState(1);
  const [products, setProducts] = useState(null);
  const [selectedPrice, setSelectedPrice] = useState(null);
  const [mainImage, setMainImage] = useState(null);
  const [selectedVariantId, setSelectedVariantId] = useState(null);
  const [ratings, setRatings] = useState([]); // Added state for ratings
  const [averageRating, setAverageRating] = useState(0); // Added state for average rating

  const [showReviewForm, setShowReviewForm] = useState(false);
  const [reviewText, setReviewText] = useState("");
  const [reviewRating, setReviewRating] = useState(0);
  const [reviewSubmitting, setReviewSubmitting] = useState(false);
  const [reviewError, setReviewError] = useState("");
  const [reviewSuccess, setReviewSuccess] = useState(false);

  // Function to determine if image is base64 or URL
  const getImageSource = (imageUrl) => {
    if (typeof imageUrl === 'string' && imageUrl.startsWith('data:image')) {
      return imageUrl; // Base64 image
    } else if (typeof imageUrl === 'string' && imageUrl.includes('/')) {
      // Image is a URL path
      try {
        return require(`../../assets/productImages/${imageUrl}`);
      } catch (e) {
        // If the require fails, might be a full URL
        return imageUrl;
      }
    } else {
      // Directly decode if it's base64 without proper formatting
      return imageUrl;
    }
  };

  const handleVariantClick = (variantId, price) => {
    setSelectedPrice(price);
    setSelectedVariantId(variantId);
  };

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const handleThumbnailClick = (imageUrl) => {
    setMainImage(imageUrl);
  };

  // Calculate the average rating from ratings data
  const calculateAverageRating = (ratingData) => {
    if (!ratingData ) return 0;
    const totalStars = ratingData.reduce((acc, rate) => acc + rate.rateStar, 0);
    return totalStars / ratingData.length;
  };
  
  const openReviewForm = () => {
    setShowReviewForm(true);
    setReviewText("");
    setReviewRating(0);
    setReviewError("");
    setReviewSuccess(false);
  };

  const closeReviewForm = () => {
    setShowReviewForm(false);
  };

  const handleStarClick = (starRating) => {
    setReviewRating(starRating);
  };

  const handleReviewTextChange = (e) => {
    setReviewText(e.target.value);
  };

  const handleReviewSubmit = async (e) => {
    e.preventDefault();
    
    // Validate form
    if (reviewRating === 0) {
      setReviewError("Vui lòng chọn số sao đánh giá");
      return;
    }
    
    if (reviewText.trim() === "") {
      setReviewError("Vui lòng nhập nội dung đánh giá");
      return;
    }
    
    setReviewSubmitting(true);
    setReviewError("");
    
    try {
      // Prepare request body
      const rateRequest = {
        perfumeId: id,
        rateStar: reviewRating,
        rateContext: reviewText
      };
      
      // Call API to add rate
      const response = await addRate(id, rateRequest);
      
      if (response && response.code === 1000) {
        // Success - update the ratings list
        setReviewSuccess(true);
        
        // Refresh ratings after successful submission
        getRates(id)
          .then((response) => {
            if (response && response.code === 1000 && response.result) {
              setRatings(response.result);
              setAverageRating(calculateAverageRating(response.result));
              
              // Reset form after short delay
              setTimeout(() => {
                setShowReviewForm(false);
                setReviewSuccess(false);
              }, 350);
            }
          });
      } 
      else {
        setReviewError("Có lỗi xảy ra khi gửi đánh giá");
      }
    } catch (error) {
       if( error.response.data.code === 1037) {
        setReviewError("Bạn chỉ được đánh giá sản phẩm một lần");
      }
      else{
      console.error("Error submitting review:", error);
       setReviewError("Có lỗi xảy ra khi gửi đánh giá");
      }
    } finally {
      setReviewSubmitting(false);
    }
  };

  useEffect(() => {
    // Fetch product details
    getProductDetail(id)
      .then((response) => {
        setProducts(response);
        
        // Initialize with first variant if available
        if (response.result.perfumeVariantResponseList && 
            response.result.perfumeVariantResponseList.length > 0) {
          const firstVariant = response.result.perfumeVariantResponseList[0];
          setSelectedVariantId(firstVariant.id);
          setSelectedPrice(firstVariant.price);
        }
        
        // Set main image
        const thumbnailImage = response.result.perfumeImageResponseList.find(
          (image) => image.thumbnail === true
        );
        
        if (thumbnailImage) {
          setMainImage(thumbnailImage.imageUrl);
        }
      })
      .catch((error) => {
        console.error("Error fetching product details:", error);
      });
      
    // Fetch ratings for this product
    getRates(id)
      .then((response) => {
        if (response && response.code === 1000 && response.result) {
          setRatings(response.result);
          setAverageRating(calculateAverageRating(response.result));
        }
      })
      .catch((error) => {
        console.error("Error fetching ratings:", error);
      });
  }, [id]);

  if (!products) {
    return <div>Product not found</div>;
  }

  // Get current page ratings
  const itemsPerPage = 2;
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentRatings = ratings.slice(indexOfFirstItem, indexOfLastItem);
  const totalPages = Math.ceil(ratings.length / itemsPerPage);

  return (
    <div className="detail-product-container">
      <div className="body-sides">
        <section className="sec-1 breadcrumb">
          <a href="/" className="breadcrumb-link">
            Trang chủ
          </a>
          <span className="arrow"> &gt; </span>
          <a href="/nuoc-hoa" className="breadcrumb-link">
            Sản phẩm
          </a>
          <span className="arrow"> &gt; </span>
          <span className="current">{products.result.name}</span>
          <hr className="divider" />
        </section>

        <section className="sec-2">
          <div className="product-images">
            <div className="image-preview">
              {mainImage && (
                <img
                  id="main-image"
                  src={getImageSource(mainImage)}
                  alt={products.result.name}
                />
              )}
            </div>

            <div className="image-thumbnails">
              {products.result.perfumeImageResponseList.map((image, index) => (
                <img
                  key={index}
                  className="thumbnail"
                  src={getImageSource(image.imageUrl)}
                  alt={`thumbnail-${index}`}
                  onClick={() => handleThumbnailClick(image.imageUrl)}
                />
              ))}
            </div>
          </div>

          <div className="product-main-info">
            <div className="text">
              <div className="row product-title-gender">
                <h2 className="product-title">{products.result.name}</h2>
                <p className="gender">{products.result.perfume_gender}</p>
              </div>
              {/* Updated to show dynamic rating */}
              <ProductRating rating={averageRating} totalReviews={ratings.length} />
              <ul className="product-highlight-info">
                <li>Thương hiệu: {products.result.brandName}</li>
                {products.result.flash_sale && (
                  <li className="flash-sale">Flash Sale!</li>
                )}
              </ul>
            </div>

            <div className="button-group">
              {products.result.perfumeVariantResponseList.map((variant) => (
                <button
                  key={variant.id}
                  className={`custom-button ${selectedVariantId === variant.id ? "selected" : ""}`}
                  onClick={() => handleVariantClick(variant.id, variant.price)}>
                  {variant.name}
                </button>
              ))}
            </div>

            <ProductActions 
              price={selectedPrice} 
              variantId={selectedVariantId} 
              discount={products.result.discount}
              discountedPrice={
                selectedVariantId && products.result.perfumeVariantResponseList
                  ? products.result.perfumeVariantResponseList.find(v => v.id === selectedVariantId)?.discountedPrice
                  : null
              }
            />
          </div>
        </section>

        <hr></hr>

        <section className="sec-3 row">
          <div className="col-6">
            <div className="product-description">
              <h2 className="title">MÔ TẢ SẢN PHẨM</h2>
              <p className="about-product">
                {products.result.perfume_description}
              </p>
              {products.result.perfumeImageResponseList.length > 1 && (
                <img
                  className="about-product-img"
                  src={getImageSource(products.result.perfumeImageResponseList[1].imageUrl)}
                  alt="product"
                />
              )}
            </div>
          </div>
          <div className="col-6">
            <div className="product-detail">
              <h2 className="title">THÔNG TIN SẢN PHẨM</h2>
              <ul>
                <li>
                  <p>
                    <span className="label">Thương hiệu</span>
                    <span>:</span>
                    <span className="value">{products.result.brandName}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span className="label">Giới tính</span>
                    <span>:</span>
                    <span className="value">{products.result.perfume_gender}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span className="label">Nồng độ</span>
                    <span>:</span>
                    <span className="value">{products.result.concentration}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span className="label">Nhóm hương</span>
                    <span>:</span>
                    <span className="value">{products.result.screntFamilyName}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span className="label">Mùi hương chính</span>
                    <span>:</span>
                    <span className="value">{products.result.main_notes}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span className="label">Độ lưu hương</span>
                    <span>:</span>
                    <span className="value">{products.result.longevity}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span className="label">Độ tỏa hương</span>
                    <span>:</span>
                    <span className="value">{products.result.sillage}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span className="label">Phong cách</span>
                    <span>:</span>
                    <span className="value">{products.result.style}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span className="label">Thời điểm dùng</span>
                    <span>:</span>
                    <span className="value">{products.result.season_usage}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span className="label">Năm phát hành</span>
                    <span>:</span>
                    <span className="value">{products.result.release_year}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span className="label">Xuất xứ</span>
                    <span>:</span>
                    <span className="value">{products.result.countryName}</span>
                  </p>
                </li>
              </ul>
              <div className="scent-structure">
                <h2 className="normal-title">Thành phần mùi hương</h2>
                <p>
                  <span className="label">Hương đầu: </span>
                  <span className="value">{products.result.top_notes}</span>
                </p>
                <p>
                  <span className="label">Hương giữa: </span>
                  <span className="value">{products.result.middle_notes}</span>
                </p>
                <p>
                  <span className="label">Hương cuối: </span>
                  <span className="value">{products.result.base_notes}</span>
                </p>
              </div>
            </div>
          </div>
        </section>

        <hr></hr>

        {/* Updated Review Section */}
        <section className="sec-4">
          <div className="review-header">
            <h2 className="review-title">Đánh giá và nhận xét</h2>
            <button className="review-button" onClick={openReviewForm}>Viết đánh giá</button>
          </div>
          <hr></hr>
          <div className="review-body">
            {currentRatings.length > 0 ? (
              currentRatings.map((rating) => (
                <div key={rating.id} className="review-article">
                  <div className="review-user-account">
                    <div className="avatar">
                      <div className="avatar-placeholder">{rating.userName ? rating.userName.charAt(0) : 'U'}</div>
                    </div>
                    <div className="user-name">{rating.userName || 'Anonymous'}</div>
                  </div>
                  <div className="review-star">
                    {Array.from({ length: 5 }, (_, index) =>
                      index < rating.rateStar ? (
                        <AiFillStar key={index} className="star filled" />
                      ) : (
                        <AiOutlineStar key={index} className="star" />
                      )
                    )}
                  </div>
                  <div className="review-time">{rating.rateDatetime}</div>
                  <div className="review-text">{rating.rateContext}</div>
                  <hr></hr>
                </div>
              ))
            ) : (
              <div className="no-reviews">Chưa có đánh giá nào cho sản phẩm này</div>
            )}
          </div>
          
          {ratings.length > 0 && (
            <div className="review-pagination">
              {Array.from({ length: totalPages }, (_, index) => (
                <button
                  key={index}
                  onClick={() => handlePageChange(index + 1)}
                  disabled={currentPage === index + 1}
                  className={currentPage === index + 1 ? "active" : ""}
                >
                  {index + 1}
                </button>
              ))}
            </div>
          )}
        </section>

        <hr></hr>

        <section className="sec-5 related-products">
          <h2 className="title">SẢN PHẨM LIÊN QUAN</h2>
          <div className="products-container">
            {suggestProducts.map((product) => (
              <a
                href={product.url}
                key={product.id}
                className="product-card"
                target="_blank"
                rel="noopener noreferrer"
              >
                <div className="image-container">
                  <img src={product.image} alt={product.name} />
                </div>
                <div className="product-info">
                  <h3 className="product-name">{product.name}</h3>
                  <p className="product-price">{product.price}</p>
                </div>
              </a>
            ))}
          </div>
        </section>
      </div>

      {/* Review Form Modal */}
      {showReviewForm && (
        <div className="review-modal-overlay">
          <div className="review-modal">
            <div className="review-modal-header">
              <h3>Viết đánh giá</h3>
              <button className="close-button" onClick={closeReviewForm}>×</button>
            </div>
            
            <form onSubmit={handleReviewSubmit} className="review-form">
              <div className="star-rating-selector">
                <p>Chọn số sao đánh giá:</p>
                <div className="star-container">
                  {[1, 2, 3, 4, 5].map((star) => (
                    <span 
                      key={star} 
                      onClick={() => handleStarClick(star)}
                      className="star-selector"
                    >
                      {star <= reviewRating ? (
                        <AiFillStar className="star filled" />
                      ) : (
                        <AiOutlineStar className="star" />
                      )}
                    </span>
                  ))}
                </div>
              </div>
              
              <div className="review-text-container">
                <label htmlFor="review-text">Nội dung đánh giá:</label>
                <textarea 
                  id="review-text"
                  value={reviewText}
                  onChange={handleReviewTextChange}
                  placeholder="Nhập nội dung đánh giá của bạn tại đây..."
                  rows={5}
                />
              </div>
              
              {reviewError && <div className="review-error">{reviewError}</div>}
              {reviewSuccess && <div className="review-success">Đánh giá của bạn đã được gửi thành công!</div>}
              
              <div className="review-submit-container">
                <button 
                  type="button" 
                  className="cancel-button"
                  onClick={closeReviewForm}
                  disabled={reviewSubmitting}
                >
                  Hủy bỏ
                </button>
                <button 
                  type="submit" 
                  className="submit-button"
                  disabled={reviewSubmitting}
                >
                  {reviewSubmitting ? 'Đang gửi...' : 'Gửi đánh giá'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default memo(ProductDetail);