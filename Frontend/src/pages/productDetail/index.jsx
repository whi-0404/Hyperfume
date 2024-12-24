import { memo, useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import ProductRating from "../../components/rating";
import ProductActions from "../../components/productActions";
import { AiFillStar, AiOutlineStar } from "react-icons/ai";
import "./style.scss";
import getProductDetail from "../../services/getProductDetail";
import handleBase64Decode from "../../components/covertBase64ToImg";
import addToCart from "../../services/handleAddToCart";

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
  const [mainImage, setMainImage] = useState(null); // Ảnh chính

  const handleVariantClick = (price) => {
    setSelectedPrice(price);
  };

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const handleThumbnailClick = (imageData) => {
    setMainImage(imageData); // Cập nhật ảnh chính khi click vào thumbnail
  };

  useEffect(() => {
    getProductDetail(id)
      .then((response) => {
        setProducts(response);
        const thumbnailImage = response.result.perfumeImageResponseList.find(
          (image) => image.thumbnail === true);
        setMainImage(thumbnailImage?.imageData); // Đặt ảnh chính ban đầu
      })
      .catch((error) => {
        console.error(error);
      });
  }, [id]);

  if (!products) {
    return <div>Product not found</div>;
  }

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
                  src={handleBase64Decode(mainImage)}
                  alt="product"
                />
              )}
            </div>

            <div className="image-thumbnails">
              {products.result.perfumeImageResponseList.map((image, index) => (
                <img
                  key={index}
                  className="thumbnail"
                  src={handleBase64Decode(image.imageData)}
                  alt={`thumbnail-${index}`}
                  onClick={() => handleThumbnailClick(image.imageData)}
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
              <ProductRating rating={4} totalReviews={4} />
              <ul className="product-highlight-info">
                <li>Thương hiệu: {products.result.brandName}</li>
              </ul>
            </div>

            <div className="button-group">
              {products.result.perfumeVariantResponseList.map((variant) => (
                <button
                  key={variant.id}
                  className="custom-button"
                  onClick={() => handleVariantClick(variant.price)}>
                  {variant.name}
                </button>
              ))}
            </div>
            <ProductActions price={selectedPrice} />
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
              <img
                className="about-product-img"
                src={require("../../assets/image/Dior/dior_motasp.jpg")}
                alt="product"
              ></img>
            </div>
          </div>
          <div className="col-6">
            <div className="product-detail">
              <h2 className="title">THÔNG TIN SẢN PHẨM</h2>
              <ul>
                <li>
                  <p>
                    <span class="label">Thương hiệu</span>
                    <span>:</span>
                    <span class="value">{products.result.brandName}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Giới tính</span>
                    <span>:</span>
                    <span class="value">{products.result.perfume_gender}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Nồng độ</span>
                    <span>:</span>
                    <span class="value">{products.result.concentration}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Nhóm hương</span>
                    <span>:</span>
                    <span class="value">{products.result.screntFamilyName}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Mùi hương chính</span>
                    <span>:</span>
                    <span class="value">{products.result.main_notes}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Độ lưu hương</span>
                    <span>:</span>
                    <span class="value">{products.result.longevity}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Độ tỏa hương</span>
                    <span>:</span>
                    <span class="value">{products.result.sillage}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Phong cách</span>
                    <span>:</span>
                    <span class="value">{products.result.style}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Thời điểm dùng</span>
                    <span>:</span>
                    <span class="value">{products.result.season_usage}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Năm phát hành</span>
                    <span>:</span>
                    <span class="value">{products.result.release_year}</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Xuất xứ</span>
                    <span>:</span>
                    <span class="value">{products.result.countryName}</span>
                  </p>
                </li>
              </ul>
              <div className="scent-structure">
                <h2 className="normal-title">Thành phần mùi hương</h2>
                <p>
                  <span class="label">Hương đầu: </span>
                  <span class="value">{products.result.top_notes}</span>
                </p>
                <p>
                  <span class="label">Hương giữa: </span>
                  <span class="value">{products.result.middle_notes}</span>
                </p>
                <p>
                  <span class="label">Hương cuối: </span>
                  <span class="value">{products.result.base_notes}</span>
                </p>
              </div>
            </div>
          </div>
        </section>

        <hr></hr>

        <section className="sec-4">
          <div className="review-header">
            <h2 className="review-title">Đánh giá và nhận xét</h2>
            <button className="review-button">Viết đánh giá</button>
          </div>
          <hr></hr>
          <div className="review-body">
            <div className="review-article">
              <div className="review-user-account">
                <div className="avatar">
                  <img
                    src={require("../../assets/image/UsersImages/henryle.jpg")}
                    alt="avatar"
                  />
                </div>
                <div className="user-name">Henry Le</div>
              </div>
              <div className="review-star">
                {Array.from({ length: 5 }, (_, index) =>
                  index < 5 ? (
                    <AiFillStar key={index} className="star filled" />
                  ) : (
                    <AiOutlineStar key={index} className="star" />
                  )
                )}
              </div>
              <div className="review-time">4 tháng trước</div>
              <div className="review-text">Mùi này thơm, mọi người nên thử</div>
            </div>
            <hr></hr>
            <div className="review-article">
              <div className="review-user-account">
                <div className="avatar">
                  <img
                    src={require("../../assets/image/UsersImages/madeline.jpg")}
                    alt="avatar"
                  />
                </div>
                <div className="user-name">Madelyne Kim</div>
              </div>
              <div className="review-star">
                {Array.from({ length: 5 }, (_, index) =>
                  index < 5 ? (
                    <AiFillStar key={index} className="star filled" />
                  ) : (
                    <AiOutlineStar key={index} className="star" />
                  )
                )}
              </div>
              <div className="review-time">7 tháng trước</div>
              <div className="review-text">
                Mùi này oke, mua liền đi mọi người ơi
              </div>
            </div>
          </div>
          <hr></hr>
          <div className="review-pagination">
            {Array.from({ length: 2 }, (_, index) => (
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
    </div>
  );
};

export default memo(ProductDetail);
