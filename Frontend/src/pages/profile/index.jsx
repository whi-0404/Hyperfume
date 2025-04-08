import { memo, useEffect, useState } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import "./style.scss";
import { FaBell, FaTrash, FaCheck, FaTimes } from "react-icons/fa";
import { UserInfo, updateUserInfo } from "../../services/handleUserInfo";
import { logout } from "../../services/authToken";
import { listFavorites, removeFromFavorite } from "../../services/handleFavorite";
import ProductCard from "../../components/productCard";
import MyShippingAddress from "../../components/myAddress";

const Profile = () => {
  const [user, setUser] = useState([]);
  const [loading, setLoading] = useState(true);
  const [isEditing, setIsEditing] = useState(false);
  const [editedUser, setEditedUser] = useState(null);
  const [updateStatus, setUpdateStatus] = useState({
    success: false,
    message: "",
  });
  const [activeTab, setActiveTab] = useState("account"); // active tab state: account, address, orders, favorites
  const [favorites, setFavorites] = useState([]);
  const [favoritesLoading, setFavoritesLoading] = useState(false);
  const [favoritesError, setFavoritesError] = useState(null);
  const [selectedProducts, setSelectedProducts] = useState([]);
  const [selectionMode, setSelectionMode] = useState(false);
  const [removeLoading, setRemoveLoading] = useState(false);
  
  // Thêm state cho phân trang
  const [currentPage, setCurrentPage] = useState(1);
  const [pageSize] = useState(10); // Giới hạn 10 sản phẩm mỗi trang
  const [totalPages, setTotalPages] = useState(1);
  const [totalElements, setTotalElements] = useState(0);
  
  const navigate = useNavigate();

  useEffect(() => {
    fetchUserInfo();
  }, []);

  useEffect(() => {
    if (activeTab === "favorites" && user && user.result) {
      fetchFavorites();
    }
  }, [activeTab, user, currentPage]);

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

  const fetchFavorites = async () => {
    if (!user.result || !user.result.id) return;

    setFavoritesLoading(true);
    try {
      const response = await listFavorites(currentPage, pageSize);
      if (response && response.data) {
        const { result } = response.data;
        setFavorites(result.data || []);
        setTotalPages(result.totalPages || 1);
        setTotalElements(result.totalElements || 0);
      }
      setFavoritesError(null);
    } catch (err) {
      setFavoritesError(
        "Không thể tải danh sách sản phẩm yêu thích. Vui lòng thử lại sau."
      );
      console.error("Error fetching favorites:", err);
    } finally {
      setFavoritesLoading(false);
    }
  };

  const handleLogout = async () => {
    try {
      await logout();
      window.location.href = "/Sign-in";
    } catch (error) {
      console.error("Đăng xuất thất bại:", error);
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
      [name]: value,
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
        result: response.data || editedUser,
      });

      setIsEditing(false);
      setUpdateStatus({
        success: true,
        message: "Thông tin đã được cập nhật thành công!",
      });

      // Refresh user data
      fetchUserInfo();
    } catch (error) {
      console.error("Cập nhật thông tin thất bại:", error);
      setUpdateStatus({
        success: false,
        message: "Cập nhật thông tin thất bại. Vui lòng thử lại sau.",
      });
    } finally {
      setLoading(false);
    }
  };

  const handleTabChange = (tab) => {
    setActiveTab(tab);
  };

  // Hàm bật/tắt chế độ chọn sản phẩm
  const toggleSelectionMode = () => {
    setSelectionMode(!selectionMode);
    if (selectionMode) {
      // Reset danh sách sản phẩm đã chọn khi tắt chế độ chọn
      setSelectedProducts([]);
    }
  };

  // Hàm xử lý chọn/bỏ chọn một sản phẩm
  const toggleProductSelection = (productId) => {
    if (selectedProducts.includes(productId)) {
      setSelectedProducts(selectedProducts.filter(id => id !== productId));
    } else {
      setSelectedProducts([...selectedProducts, productId]);
    }
  };

  // Hàm chọn tất cả sản phẩm
  const selectAllProducts = () => {
    const allProductIds = favorites.map(product => product.id);
    setSelectedProducts(allProductIds);
  };

  // Hàm bỏ chọn tất cả sản phẩm
  const unselectAllProducts = () => {
    setSelectedProducts([]);
  };

  // Hàm xóa một sản phẩm khỏi danh sách yêu thích
  const removeSingleFavorite = async (productId) => {
    try {
      await removeFromFavorite(productId);
      // Cập nhật lại danh sách sau khi xóa
      fetchFavorites();
    } catch (error) {
      console.error("Lỗi khi xóa sản phẩm khỏi danh sách yêu thích:", error);
    }
  };

  // Hàm xóa nhiều sản phẩm đã chọn
  const removeSelectedFavorites = async () => {
    if (selectedProducts.length === 0) return;
    
    setRemoveLoading(true);
    try {
      // Xóa từng sản phẩm đã chọn
      for (const productId of selectedProducts) {
        await removeFromFavorite(productId);
      }
      
      // Cập nhật lại danh sách sau khi xóa
      fetchFavorites();
      
      // Reset trạng thái
      setSelectedProducts([]);
      setSelectionMode(false);
    } catch (error) {
      console.error("Lỗi khi xóa sản phẩm khỏi danh sách yêu thích:", error);
    } finally {
      setRemoveLoading(false);
    }
  };

  // Hàm để xử lý chuyển trang 
  const handlePageChange = (newPage) => {
    if (newPage >= 1 && newPage <= totalPages) {
      setCurrentPage(newPage);
      // Reset chế độ chọn khi chuyển trang
      setSelectionMode(false);
      setSelectedProducts([]);
    }
  };

  const renderTabContent = () => {
    switch (activeTab) {
      case "account":
        return (
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
                      value={editedUser.fullname || ""}
                      onChange={handleInputChange}
                      className="edit-input"
                    />
                  </li>
                  <li className="item">
                    <label className="item-label">Số điện thoại</label>
                    <input
                      type="text"
                      name="phone"
                      value={editedUser.phone || ""}
                      onChange={handleInputChange}
                      className="edit-input"
                    />
                  </li>
                  <li className="item">
                    <label className="item-label">Email</label>
                    <input
                      type="email"
                      name="email"
                      value={editedUser.email || ""}
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
                      value={editedUser.dob || ""}
                      onChange={handleInputChange}
                      className="edit-input"
                    />
                  </li>
                  <li className="item">
                    <label className="item-label">Giới tính</label>
                    <select
                      name="gender"
                      value={editedUser.gender || ""}
                      onChange={handleInputChange}
                      className="edit-input"
                    >
                      <option value="Nam">Nam</option>
                      <option value="Nữ">Nữ</option>
                    </select>
                  </li>
                </ul>
                <div className="edit-actions">
                  <button className="save-button" onClick={handleSaveChanges}>
                    Lưu thông tin
                  </button>
                  <button className="cancel-button" onClick={handleCancelEdit}>
                    Hủy bỏ
                  </button>
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
                    Tôi đồng ý cung cấp thông tin cá nhân cho các hoạt động tạo
                    hồ sơ, nhận ưu đãi.
                  </p>
                </div>
                <p className="edit-profile">
                  <a
                    href="#"
                    onClick={(e) => {
                      e.preventDefault();
                      handleEditClick();
                    }}
                  >
                    Chỉnh sửa
                  </a>
                </p>
              </>
            )}
          </div>
        );
        case "address":
          return (
            <div className="detail-section">
              <MyShippingAddress />
            </div>
          );
      case "orders":
        return (
          <div className="detail-section">
            <p className="section-placeholder">
              Chức năng đang trong quá trình phát triển
            </p>
          </div>
        );
      case "favorites":
        return (
          <div className="detail-section favorites-section">
            {favoritesLoading ? (
              <div className="favorites-loading">
                Đang tải sản phẩm yêu thích...
              </div>
            ) : favoritesError ? (
              <div className="favorites-error">{favoritesError}</div>
            ) : favorites.length === 0 ? (
              <div className="favorites-empty">
                Bạn chưa có sản phẩm yêu thích nào.
              </div>
            ) : (
              <>
                <div className="favorites-info">
                  <span className="total-favorites">
                    Tổng số: <strong>{totalElements}</strong> sản phẩm
                  </span>
                  {!selectionMode && (
                    <button className="select-mode-btn" onClick={toggleSelectionMode}>
                      Chọn sản phẩm
                    </button>
                  )}
                </div>
                
                {selectionMode && (
                  <div className="selection-controls">
                    <div className="selection-info">
                      <span>Đã chọn {selectedProducts.length}/{favorites.length} sản phẩm</span>
                    </div>
                    <div className="selection-actions">
                      <button 
                        className="select-all-btn" 
                        onClick={selectAllProducts}
                        disabled={favorites.length === selectedProducts.length}
                      >
                        Chọn tất cả
                      </button>
                      <button 
                        className="clear-selection-btn" 
                        onClick={unselectAllProducts}
                        disabled={selectedProducts.length === 0}
                      >
                        Bỏ chọn tất cả
                      </button>
                      <button 
                        className="remove-selected-btn" 
                        onClick={removeSelectedFavorites}
                        disabled={selectedProducts.length === 0 || removeLoading}
                      >
                        {removeLoading ? "Đang xóa..." : "Xóa đã chọn"} <FaTrash />
                      </button>
                      <button 
                        className="cancel-selection-btn" 
                        onClick={toggleSelectionMode}
                      >
                        Hủy <FaTimes />
                      </button>
                    </div>
                  </div>
                )}
                
                <div className="favorites-grid">
                  {favorites.map((product) => (
                    <div 
                      key={product.id} 
                      className={`product-card-wrapper ${selectionMode ? 'selection-mode' : ''} ${selectedProducts.includes(product.id) ? 'selected' : ''}`}
                    >
                      {selectionMode && (
                        <div 
                          className="product-checkbox" 
                          onClick={() => toggleProductSelection(product.id)}
                        >
                          {selectedProducts.includes(product.id) && <FaCheck />}
                        </div>
                      )}
                      {!selectionMode && (
                        <button 
                          className="remove-favorite-btn" 
                          onClick={() => removeSingleFavorite(product.id)}
                          title="Xóa khỏi yêu thích"
                        >
                          <FaTrash />
                        </button>
                      )}
                      <ProductCard
                        id={product.id}
                        img={product.thumbnailImageUrl || product.img}
                        brandName={product.brandName}
                        name={product.name}
                        price1={product.min_price || product.price1}
                        price2={product.max_price || product.price2}
                        onClick={selectionMode ? () => toggleProductSelection(product.id) : undefined}
                      />
                    </div>
                  ))}
                </div>
                
                {totalPages > 1 && (
                  <div className="pagination">
                    <button 
                      onClick={() => handlePageChange(1)} 
                      disabled={currentPage === 1}
                      className="page-btn first-page"
                    >
                      &laquo;
                    </button>
                    <button 
                      onClick={() => handlePageChange(currentPage - 1)} 
                      disabled={currentPage === 1}
                      className="page-btn prev-page"
                    >
                      &lsaquo;
                    </button>
                    
                    {[...Array(totalPages)].map((_, index) => {
                      const pageNumber = index + 1;
                      // Chỉ hiển thị các trang gần trang hiện tại
                      if (
                        pageNumber === 1 || 
                        pageNumber === totalPages || 
                        (pageNumber >= currentPage - 1 && pageNumber <= currentPage + 1)
                      ) {
                        return (
                          <button
                            key={index}
                            onClick={() => handlePageChange(pageNumber)}
                            className={`page-number ${currentPage === pageNumber ? "active" : ""}`}
                          >
                            {pageNumber}
                          </button>
                        );
                      } else if (
                        pageNumber === currentPage - 2 || 
                        pageNumber === currentPage + 2
                      ) {
                        return <span key={index} className="pagination-dots">...</span>;
                      }
                      return null;
                    })}
                    
                    <button 
                      onClick={() => handlePageChange(currentPage + 1)} 
                      disabled={currentPage === totalPages}
                      className="page-btn next-page"
                    >
                      &rsaquo;
                    </button>
                    <button 
                      onClick={() => handlePageChange(totalPages)} 
                      disabled={currentPage === totalPages}
                      className="page-btn last-page"
                    >
                      &raquo;
                    </button>
                  </div>
                )}
              </>
            )}
          </div>
        );
      default:
        return null;
    }
  };

  const getTabTitle = () => {
    switch (activeTab) {
      case "account":
        return "Tài khoản của tôi";
      case "address":
        return "Địa chỉ của tôi";
      case "orders":
        return "Đơn hàng";
      case "favorites":
        return "Sản phẩm yêu thích";
      default:
        return "";
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
              <li
                className={activeTab === "account" ? "active" : ""}
                onClick={() => handleTabChange("account")}
              >
                Tài khoản của tôi
              </li>
              <li
                className={activeTab === "address" ? "active" : ""}
                onClick={() => handleTabChange("address")}
              >
                Địa chỉ của tôi
              </li>
              <li
                className={activeTab === "orders" ? "active" : ""}
                onClick={() => handleTabChange("orders")}
              >
                Đơn hàng
              </li>
              <li
                className={activeTab === "favorites" ? "active" : ""}
                onClick={() => handleTabChange("favorites")}
              >
                Sản phẩm yêu thích
              </li>
              <li onClick={handleLogout} style={{ cursor: "pointer" }}>
                Đăng xuất
              </li>
            </ul>
          </div>
          <div className="main">
            <p className="title">{getTabTitle()}</p>
            {updateStatus.message && (
              <div
                className={`update-status ${
                  updateStatus.success ? "success" : "error"
                }`}
              >
                {updateStatus.message}
              </div>
            )}
            {renderTabContent()}
          </div>
        </div>
      </div>
    </div>
  );
};

export default memo(Profile);
