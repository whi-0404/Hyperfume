import { memo, useState } from "react";
import ProductRating from "../../components/rating";
import ProductActions from "../../components/productActions";
import { AiFillStar, AiOutlineStar } from "react-icons/ai";
import "./style.scss";

const ProductDetail = () => {
  const [currentPage, setCurrentPage] = useState(1);
  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  return (
    <div className="container">
      <div className="body-sides">
        <section id="breadcrumb" class="bread-product">
          <ul class="row nav">
            <li>
              <a href="#">Trang chủ</a>
            </li>
            <li>
              <a href="#">Sản phẩm</a>
            </li>
            <li>Dior Sauvage Parfum</li>
          </ul>
        </section>

        <section className="sec-2">
          <div className="product-images">
            <div className="image-preview">
              <img
                id="main-image"
                src={require("../../assets/image/Dior/dior_sauvage_big_1.jpg")}
                alt="product"
              ></img>
            </div>
            <div className="image-thumbnails">
              <img
                className="thumbnail"
                src={require("../../assets/image/Dior/dior_sauvage_big_1.jpg")}
                alt="product"
              ></img>
              <img
                className="thumbnail"
                src={require("../../assets/image/Dior/dior_sauvage_small_1.jpg")}
                alt="product"
              ></img>
              <img
                className="thumbnail"
                src={require("../../assets/image/Dior/dior_sauvage_small_2.jpg")}
                alt="product"
              ></img>
              <img
                className="thumbnail"
                src={require("../../assets/image/Dior/dior_sauvage_small_3.jpg")}
                alt="product"
              ></img>
            </div>
          </div>
          <div className="product-main-info">
            <div className="text">
              <div className="row product-title-gender">
                <h2 className="product-title">Dior Sauvage Parfum</h2>
                <p className="gender">Nam</p>
              </div>
              <ProductRating rating={4} totalReviews={4} />
              <ul className="product-highlight-info">
                <li>Thương hiệu: Christian Dior</li>
                <li>Parfum 100ml</li>
                <li>Standard Size</li>
              </ul>
            </div>
            <div className="button-group">
              <button className="custom-button button1">Parfum 100ml</button>
              <button className="custom-button button2">
                Parfum 100ml Tester
              </button>
              <button className="custom-button button3">Parfum 60ml</button>
            </div>
            <ProductActions />
          </div>
        </section>

        <hr></hr>

        <section className="sec-3 row">
          <div className="col-6">
            <div className="product-description">
              <h2 className="title">MÔ TẢ SẢN PHẨM</h2>
              <p className="about-product">
                Nước hoa nam Dior Sauvage Parfum của thương hiệu Christian Dior
                được ra mắt năm 2019 là một cách giải thích mới, tập trung cao
                độ của bản gốc, lấy cảm hứng từ phong cảnh của thảo nguyên từ
                bầu trời xanh, núi đá, nóng dưới ánh mặt trời sa mạc. Chuyên gia
                nước hoa Dior François Demachy đã thiết kế bố cục để pha trộn sự
                tươi mát tột độ với tông màu phương Đông ấm áp.
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
                    <span class="value">Christian Dior</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Giới tính</span>
                    <span>:</span>
                    <span class="value">Nước hoa Nam</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Nồng độ</span>
                    <span>:</span>
                    <span class="value">Parfum</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Nhóm hương</span>
                    <span>:</span>
                    <span class="value">Hương phương đông</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Mùi hương chính</span>
                    <span>:</span>
                    <span class="value">Cam Bergamot, Hương Vanilla</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Độ lưu hương</span>
                    <span>:</span>
                    <span class="value">Rất lâu - Trên 12h</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Độ tỏa hương</span>
                    <span>:</span>
                    <span class="value">Xa - Trong vòng bán kính 2m</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Phong cách</span>
                    <span>:</span>
                    <span class="value">Lịch lãm, Nam tính, Lôi cuốn</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Thời điểm dùng</span>
                    <span>:</span>
                    <span class="value">Thu, Đông</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Năm phát hành</span>
                    <span>:</span>
                    <span class="value">2019</span>
                  </p>
                </li>
                <li>
                  <p>
                    <span class="label">Xuất xứ</span>
                    <span>:</span>
                    <span class="value">Pháp</span>
                  </p>
                </li>
              </ul>
              <div className="scent-structure">
                <h2 className="normal-title">Thành phần mùi hương</h2>
                <p>
                  <span class="label">Hương đầu: </span>
                  <span class="value">Cam Bergamot</span>
                </p>
                <p>
                  <span class="label">Hương giữa: </span>
                  <span class="value">
                    Hạt tiêu Tứ xuyên, Hoa oải hương, Nhục đậu khấu, Hoa hồi
                  </span>
                </p>
                <p>
                  <span class="label">Hương cuối: </span>
                  <span class="value">
                    Long diên hương, Hương Vanilla, Hổ phách
                  </span>
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
      </div>
    </div>
  );
};

export default memo(ProductDetail);
