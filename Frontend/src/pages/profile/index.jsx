import { memo, useEffect, useState } from "react";
import { NavLink } from 'react-router-dom';
import "./style.scss";
import { FaBell } from "react-icons/fa";
import { UserInfo } from "../../services/handleUserInfo";
import { getToken } from "../../services/authToken"

const Profile = () => {
  const Token = getToken(); // Lấy Token
  const [user, setUser] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!Token) {
      setLoading(false); // Dừng trạng thái loading
      return;
    }

    UserInfo(Token)
      .then((response) => {
        setUser(response.data);
        setLoading(false); // Kết thúc loading
      })
      .catch((error) => {
        console.error(error);
        setError('Failed to fetch products'); // Lưu lỗi vào state
        setLoading(false);
      });
  }, [Token]);

  if (!Token) {
    return (
      <div className="alert">
        <h1>Bạn chưa đăng nhập tài khoản!</h1>
        <NavLink to="/Sign-in">Đăng nhập</NavLink>
      </div>
    );
  }

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
                  <label className="item-label">Họ và tên</label>
                  <p className="item-value">Lê Huy</p>
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
