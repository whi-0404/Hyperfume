import { memo } from "react";
import "./style.scss";
import { FaBell } from "react-icons/fa";

const Profile = () => {
  return (
    <div className="container">
      <div className="body-section">
        <div className="row">
          <div className="nav">
            <ul>
              <li>Tài khoản của tôi</li>
              <li>Địa chỉ của tôi</li>
              <li>Đơn hàng</li>
              <li>Sản phẩm yêu thích</li>
              <li>Đăng xuất</li>
            </ul>
          </div>
          <div className="main">
            <p className="title">Tài khoản của tôi</p>
            <div className="detail-section">
              <ul className="detail-profile">
                <li className="item">
                  <label className="item-label">Họ</label>
                  <p className="item-value">Lê</p>
                </li>
                <li className="item">
                  <label className="item-label">Tên</label>
                  <p className="item-value">Huy</p>
                </li>
                <li className="item">
                  <label className="item-label">Số điện thoại</label>
                  <p className="item-value">0123456789</p>
                </li>
                <li className="item">
                  <label className="item-label">Email</label>
                  <p className="item-value">ledanghoanghuy@gmail.com</p>
                </li>
                <li className="item">
                  <label className="item-label">Ngày sinh</label>
                  <p className="item-value">13/02/2004</p>
                </li>
                <li className="item">
                  <label className="item-label">Giới tính</label>
                  <p className="item-value">Nam</p>
                </li>
              </ul>
              <div className="noti-profile">
                <p>
                  <FaBell></FaBell>
                  <strong> Nhận thông báo </strong>
                  <br></br>
                  Tôi đồng ý cung cấp thông tin cá nhân cho các hoạt động tạo hồ
                  sơ, nhận ưu đãi.
                </p>
              </div>
              <p className="edit-profile">
                <a href="#">Chỉnh sửa</a>
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default memo(Profile);
