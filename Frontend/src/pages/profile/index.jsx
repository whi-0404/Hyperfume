import { memo, useEffect, useState } from "react";
import { NavLink, useNavigate } from 'react-router-dom';
import "./style.scss";
import { FaBell } from "react-icons/fa";
import { UserInfo, updateUserInfo } from "../../services/handleUserInfo";
import { logout } from "../../services/authToken";

const Profile = () => {
  const [user, setUser] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isEditing, setIsEditing] = useState(false);
  const [editedUser, setEditedUser] = useState(null);
  const [updateStatus, setUpdateStatus] = useState({ success: false, message: "" });
  const navigate = useNavigate();

  useEffect(() => {
    fetchUserInfo();
  }, []);

  const fetchUserInfo = () => {
    setLoading(true);
    UserInfo()
      .then((response) => {
        setUser(response);
        setEditedUser(response.result);
      })
      .catch((error) => {
        console.error(error);
      })
      .finally(() => {
        setLoading(false);
      });
  };

  const handleLogout = async () => {
    try {
      await logout();
      window.location.href = '/Sign-in';
    } catch (error) {
      console.error('Đăng xuất thất bại:', error);
    }
  };

  const handleEditClick = () => {
    setIsEditing(true);
  };

  const handleCancelEdit = () => {
    setIsEditing(false);
    setEditedUser(user.result); // Reset to original values
    setUpdateStatus({ success: false, message: "" });
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditedUser({
      ...editedUser,
      [name]: value
    });
  };

  const handleSaveChanges = async () => {
    try {
      setLoading(true);
      // Call the update service
      const response = await updateUserInfo(user.result.id, editedUser);
      
      // Update the user state with the updated information
      setUser({
        ...user,
        result: response.data || editedUser
      });
      
      setIsEditing(false);
      setUpdateStatus({ success: true, message: "Thông tin đã được cập nhật thành công!" });
      
      // Refresh user data
      fetchUserInfo();
    } catch (error) {
      console.error('Cập nhật thông tin thất bại:', error);
      setUpdateStatus({ success: false, message: "Cập nhật thông tin thất bại. Vui lòng thử lại sau." });
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  if (!user || !user.result) {
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
              <li onClick={handleLogout} style={{ cursor: 'pointer' }}>Đăng xuất</li>
            </ul>
          </div>
          <div className="main">
            <p className="title">Tài khoản của tôi</p>
            {updateStatus.message && (
              <div className={`update-status ${updateStatus.success ? 'success' : 'error'}`}>
                {updateStatus.message}
              </div>
            )}
            <div className="detail-section">
              {isEditing ? (
                // Editing form
                <div className="edit-form">
                  <ul className="detail-profile">
                    <li className="item">
                      <label className="item-label">Họ và tên</label>
                      <input
                        type="text"
                        name="fullname"
                        value={editedUser.fullname || ''}
                        onChange={handleInputChange}
                        className="edit-input"
                      />
                    </li>
                    <li className="item">
                      <label className="item-label">Số điện thoại</label>
                      <input
                        type="text"
                        name="phone"
                        value={editedUser.phone || ''}
                        onChange={handleInputChange}
                        className="edit-input"
                      />
                    </li>
                    <li className="item">
                      <label className="item-label">Email</label>
                      <input
                        type="email"
                        name="email"
                        value={editedUser.email || ''}
                        onChange={handleInputChange}
                        className="edit-input"
                        disabled // Email often used as an identifier, might not be editable
                      />
                    </li>
                    <li className="item">
                      <label className="item-label">Ngày sinh</label>
                      <input
                        type="date"
                        name="dob"
                        value={editedUser.dob || ''}
                        onChange={handleInputChange}
                        className="edit-input"
                      />
                    </li>
                    <li className="item">
                      <label className="item-label">Giới tính</label>
                      <select
                        name="gender"
                        value={editedUser.gender || ''}
                        onChange={handleInputChange}
                        className="edit-input"
                      >
                        <option value="Nam">Nam</option>
                        <option value="Nữ">Nữ</option>
                      </select>
                    </li>
                  </ul>
                  <div className="edit-actions">
                    <button className="save-button" onClick={handleSaveChanges}>Lưu thông tin</button>
                    <button className="cancel-button" onClick={handleCancelEdit}>Hủy bỏ</button>
                  </div>
                </div>
              ) : (
                // Display mode
                <>
                  <ul className="detail-profile">
                    <li className="item">
                      <label className="item-label">Họ và tên</label>
                      <p className="item-value">{user.result.fullname}</p>
                    </li>
                    <li className="item">
                      <label className="item-label">Số điện thoại</label>
                      <p className="item-value">{user.result.phone}</p>
                    </li>
                    <li className="item">
                      <label className="item-label">Email</label>
                      <p className="item-value">{user.result.email}</p>
                    </li>
                    <li className="item">
                      <label className="item-label">Ngày sinh</label>
                      <p className="item-value">{user.result.dob}</p>
                    </li>
                    <li className="item">
                      <label className="item-label">Giới tính</label>
                      <p className="item-value">{user.result.gender}</p>
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
                    <a href="#" onClick={(e) => { e.preventDefault(); handleEditClick(); }}>Chỉnh sửa</a>
                  </p>
                </>
              )}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default memo(Profile);