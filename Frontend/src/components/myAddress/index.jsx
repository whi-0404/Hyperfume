import { memo, useEffect, useState } from "react";
import { FaTrash, FaPencilAlt, FaPlus, FaCheck, FaStar } from "react-icons/fa";
import { 
  getShippingAddress, 
  createShippingAddress, 
  updateShippingAddress,
  deleteShippingAddress,
  setShippingAddressDefault
} from "../../services/handleMyAddress";
import "./style.scss";

// Import the location data
import provincesData from "../../assets/JSON_Tinh_Thanh_VN/tinh_tp.json";
import districtsData from "../../assets/JSON_Tinh_Thanh_VN/quan_huyen.json";
import wardsData from "../../assets/JSON_Tinh_Thanh_VN/xa_phuong.json";

const MyShippingAddress = () => {
  const [addresses, setAddresses] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [isAddingNew, setIsAddingNew] = useState(false);
  const [isEditing, setIsEditing] = useState(false);
  const [currentAddress, setCurrentAddress] = useState({
    recipientName: "",
    phone: "",
    specificAddress: "", // Changed from shipAddress to specificAddress for clarity
    shipAddress: "", // This will store the full combined address
    isDefault: false,
    provinceCode: "",
    districtCode: "",
    wardCode: ""
  });
  const [editAddressId, setEditAddressId] = useState(null);
  const [statusMessage, setStatusMessage] = useState({ type: "", message: "" });
  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 3;

  // Location data states
  const [provinces, setProvinces] = useState([]);
  const [districts, setDistricts] = useState([]);
  const [wards, setWards] = useState([]);

  useEffect(() => {
    fetchAddresses();
    prepareLocationData();
  }, []);

  // Prepare location data from JSON files
  const prepareLocationData = () => {
    // Convert JSON objects to arrays
    const provinceArray = Object.keys(provincesData).map(key => ({
      code: key,
      ...provincesData[key]
    }));
    setProvinces(provinceArray);
  };

  // Update districts when province changes
  useEffect(() => {
    if (currentAddress.provinceCode) {
      const filteredDistricts = Object.keys(districtsData)
        .filter(key => districtsData[key].parent_code === currentAddress.provinceCode)
        .map(key => ({
          code: key,
          ...districtsData[key]
        }));
      setDistricts(filteredDistricts);
      
      // Reset district and ward selection
      setCurrentAddress(prev => ({
        ...prev,
        districtCode: "",
        wardCode: ""
      }));
    } else {
      setDistricts([]);
    }
  }, [currentAddress.provinceCode]);

  // Update wards when district changes
  useEffect(() => {
    if (currentAddress.districtCode) {
      const filteredWards = Object.keys(wardsData)
        .filter(key => wardsData[key].parent_code === currentAddress.districtCode)
        .map(key => ({
          code: key,
          ...wardsData[key]
        }));
      setWards(filteredWards);
      
      // Reset ward selection
      setCurrentAddress(prev => ({
        ...prev,
        wardCode: ""
      }));
    } else {
      setWards([]);
    }
  }, [currentAddress.districtCode]);

  // Update the full address whenever location components change
  useEffect(() => {
    updateFullAddress();
  }, [currentAddress.specificAddress, currentAddress.wardCode, currentAddress.districtCode, currentAddress.provinceCode]);

  const updateFullAddress = () => {
    const selectedProvince = provinces.find(p => p.code === currentAddress.provinceCode);
    const selectedDistrict = districts.find(d => d.code === currentAddress.districtCode);
    const selectedWard = wards.find(w => w.code === currentAddress.wardCode);

    let fullAddress = currentAddress.specificAddress || "";

    if (selectedWard) {
      fullAddress = fullAddress ? `${fullAddress}, ${selectedWard.name_with_type}` : selectedWard.name_with_type;
    }
    
    if (selectedDistrict) {
      fullAddress = fullAddress ? `${fullAddress}, ${selectedDistrict.name_with_type}` : selectedDistrict.name_with_type;
    }
    
    if (selectedProvince) {
      fullAddress = fullAddress ? `${fullAddress}, ${selectedProvince.name_with_type}` : selectedProvince.name_with_type;
    }

    setCurrentAddress(prev => ({
      ...prev,
      shipAddress: fullAddress
    }));
  };

  const fetchAddresses = async () => {
    setLoading(true);
    try {
      const response = await getShippingAddress();
      if (response && response.result) {
        setAddresses(response.result);
      }
      setError(null);
    } catch (err) {
      console.error("Error fetching addresses:", err);
      setError("Không thể tải danh sách địa chỉ. Vui lòng thử lại sau.");
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setCurrentAddress({
      ...currentAddress,
      [name]: value
    });
  };

  const handleCheckboxChange = (e) => {
    const { checked } = e.target;
    setCurrentAddress({
      ...currentAddress,
      isDefault: checked 
    });
  };

  const resetForm = () => {
    setCurrentAddress({
      recipientName: "",
      phone: "",
      specificAddress: "",
      shipAddress: "",
      isDefault: false,
      provinceCode: "",
      districtCode: "",
      wardCode: ""
    });
    setIsAddingNew(false);
    setIsEditing(false);
    setEditAddressId(null);
  };

  const showSuccessMessage = (message) => {
    setStatusMessage({ type: "success", message });
    setTimeout(() => setStatusMessage({ type: "", message: "" }), 3000);
  };

  const showErrorMessage = (message) => {
    setStatusMessage({ type: "error", message });
    setTimeout(() => setStatusMessage({ type: "", message: "" }), 3000);
  };

  const handleAddNew = () => {
    resetForm();
    setIsAddingNew(true);
    setIsEditing(false);
  };

  const handleEdit = (address) => {
    setIsEditing(true);
    setIsAddingNew(false);
    setEditAddressId(address.id);

    // Extract location components from the full address if available
    // In a real application, you would probably store these separately in your database
    // This is a simplified approach that assumes the address format is standardized
    
    setCurrentAddress({
      recipientName: address.recipientName,
      phone: address.phone,
      specificAddress: "", // You'll need to handle parsing the specific address from the full address
      shipAddress: address.shipAddress,
      isDefault: address.default,
      provinceCode: "", // You'll need to determine these from the address if possible
      districtCode: "",
      wardCode: ""
    });
  };

  const handleCancel = () => {
    resetForm();
  };

  const validateForm = () => {
    if (!currentAddress.recipientName.trim()) {
      showErrorMessage("Vui lòng nhập họ tên người nhận");
      return false;
    }
    
    if (!currentAddress.phone.trim()) {
      showErrorMessage("Vui lòng nhập số điện thoại");
      return false;
    } else if (!/^[0-9]{10,11}$/.test(currentAddress.phone.trim())) {
      showErrorMessage("Số điện thoại không hợp lệ. Vui lòng nhập 10-11 chữ số");
      return false;
    }
    
    if (!currentAddress.specificAddress.trim()) {
      showErrorMessage("Vui lòng nhập địa chỉ cụ thể");
      return false;
    }
    
    if (!currentAddress.provinceCode) {
      showErrorMessage("Vui lòng chọn Tỉnh/Thành phố");
      return false;
    }
    
    if (!currentAddress.districtCode) {
      showErrorMessage("Vui lòng chọn Quận/Huyện");
      return false;
    }
    
    if (!currentAddress.wardCode) {
      showErrorMessage("Vui lòng chọn Phường/Xã");
      return false;
    }
    
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (!validateForm()) return;
    
    setLoading(true);
    try {
      const addressData = {
        recipientName: currentAddress.recipientName.trim(),
        phone: currentAddress.phone.trim(),
        shipAddress: currentAddress.shipAddress.trim(),
        isDefault: currentAddress.isDefault
      };
      
      if (isEditing) {
        await updateShippingAddress(editAddressId, addressData);
        showSuccessMessage("Cập nhật địa chỉ thành công");
      } else {
        await createShippingAddress(addressData);
        showSuccessMessage("Thêm địa chỉ mới thành công");
      }
      
      // Reset form and fetch updated addresses
      resetForm();
      await fetchAddresses();
    } catch (err) {
      console.error(isEditing ? "Error updating address:" : "Error adding address:", err);
      showErrorMessage(isEditing ? "Lỗi khi cập nhật địa chỉ" : "Lỗi khi thêm địa chỉ mới");
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (addressId) => {
    if (window.confirm("Bạn có chắc chắn muốn xóa địa chỉ này?")) {
      setLoading(true);
      try {
        await deleteShippingAddress(addressId);
        showSuccessMessage("Xóa địa chỉ thành công");
        await fetchAddresses();
        
        // Calculate if we need to adjust the current page
        const remainingAddresses = addresses.filter(address => address.id !== addressId);
        const newTotalPages = Math.ceil(remainingAddresses.length / itemsPerPage);
        
        // If current page would be empty or no longer exists, go back to first page
        if (currentPage > newTotalPages || remainingAddresses.length === 0) {
          setCurrentPage(1);
        }
        
      } catch (err) {
        console.error("Error deleting address:", err);
        showErrorMessage("Lỗi khi xóa địa chỉ");
      } finally {
        setLoading(false);
      }
    }
  };

  const handleSetDefault = async (addressId) => {
    setLoading(true);
    try {
      await setShippingAddressDefault(addressId);
      showSuccessMessage("Đã thiết lập địa chỉ mặc định");
      fetchAddresses();
    } catch (err) {
      console.error("Error setting default address:", err);
      showErrorMessage("Lỗi khi thiết lập địa chỉ mặc định");
    } finally {
      setLoading(false);
    }
  };

  // Sort addresses to put default address first
  const sortedAddresses = [...addresses].sort((a, b) => {
    if (a.default && !b.default) return -1;
    if (!a.default && b.default) return 1;
    return 0;
  });

  // Calculate pagination
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentAddresses = sortedAddresses.slice(indexOfFirstItem, indexOfLastItem);
  const totalPages = Math.ceil(sortedAddresses.length / itemsPerPage);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  return (
    <div className="shipping-address-container">
      {statusMessage.message && (
        <div className={`status-message ${statusMessage.type}`}>
          {statusMessage.message}
        </div>
      )}

      <div className="address-actions">
        <button 
          className="add-address-btn" 
          onClick={handleAddNew}
          type="button"
          disabled={isAddingNew || isEditing}
        >
          <FaPlus /> Thêm địa chỉ mới
        </button>
      </div>

      {(isAddingNew || isEditing) && (
        <div className="address-form-container">
          <h3>{isEditing ? "Chỉnh sửa địa chỉ" : "Thêm địa chỉ mới"}</h3>
          <form onSubmit={handleSubmit} className="address-form">
            <div className="form-group">
              <label htmlFor="recipientName">Họ tên người nhận <span className="required">*</span></label>
              <input
                type="text"
                id="recipientName"
                name="recipientName"
                value={currentAddress.recipientName}
                onChange={handleInputChange}
                placeholder="Nhập họ tên người nhận"
                disabled={loading}
              />
            </div>
            <div className="form-group">
              <label htmlFor="phone">Số điện thoại <span className="required">*</span></label>
              <input
                type="tel"
                id="phone"
                name="phone"
                value={currentAddress.phone}
                onChange={handleInputChange}
                placeholder="Nhập số điện thoại"
                disabled={loading}
              />
            </div>
            
            {/* Location Selection Dropdowns */}
            <div className="form-group">
              <label htmlFor="provinceCode">Tỉnh/Thành phố <span className="required">*</span></label>
              <select
                id="provinceCode"
                name="provinceCode"
                value={currentAddress.provinceCode}
                onChange={handleInputChange}
                disabled={loading}
              >
                <option value="">-- Chọn Tỉnh/Thành phố --</option>
                {provinces.map(province => (
                  <option key={province.code} value={province.code}>
                    {province.name_with_type}
                  </option>
                ))}
              </select>
            </div>
            
            <div className="form-group">
              <label htmlFor="districtCode">Quận/Huyện <span className="required">*</span></label>
              <select
                id="districtCode"
                name="districtCode"
                value={currentAddress.districtCode}
                onChange={handleInputChange}
                disabled={loading || !currentAddress.provinceCode}
              >
                <option value="">-- Chọn Quận/Huyện --</option>
                {districts.map(district => (
                  <option key={district.code} value={district.code}>
                    {district.name_with_type}
                  </option>
                ))}
              </select>
            </div>
            
            <div className="form-group">
              <label htmlFor="wardCode">Phường/Xã <span className="required">*</span></label>
              <select
                id="wardCode"
                name="wardCode"
                value={currentAddress.wardCode}
                onChange={handleInputChange}
                disabled={loading || !currentAddress.districtCode}
              >
                <option value="">-- Chọn Phường/Xã --</option>
                {wards.map(ward => (
                  <option key={ward.code} value={ward.code}>
                    {ward.name_with_type}
                  </option>
                ))}
              </select>
            </div>
            
            <div className="form-group">
              <label htmlFor="specificAddress">Địa chỉ cụ thể <span className="required">*</span></label>
              <textarea
                id="specificAddress"
                name="specificAddress"
                value={currentAddress.specificAddress}
                onChange={handleInputChange}
                placeholder="Nhập địa chỉ cụ thể (số nhà, tên đường...)"
                rows="3"
                disabled={loading}
              />
            </div>
            
            {/* Preview of full address */}
            {currentAddress.shipAddress && (
              <div className="form-group address-preview">
                <label>Địa chỉ đầy đủ:</label>
                <div className="preview-text">{currentAddress.shipAddress}</div>
              </div>
            )}
            
            <div className="form-group checkbox">
              <input
                type="checkbox"
                id="isDefault"
                name="isDefault"
                checked={currentAddress.isDefault}
                onChange={handleCheckboxChange}
                disabled={loading}
              />
              <label htmlFor="isDefault">Đặt làm địa chỉ mặc định</label>
            </div>
            <div className="form-actions">
              <button type="submit" className="submit-btn" disabled={loading}>
                {loading ? "Đang xử lý..." : isEditing ? "Cập nhật" : "Thêm mới"}
              </button>
              <button 
                type="button" 
                className="cancel-btn" 
                onClick={handleCancel}
                disabled={loading}
              >
                Hủy
              </button>
            </div>
          </form>
        </div>
      )}

      <div className="addresses-list">
        <h3>Danh sách địa chỉ nhận hàng ({addresses.length})</h3>
        
        {loading && !isAddingNew && !isEditing && (
          <div className="loading-message">Đang tải danh sách địa chỉ...</div>
        )}
        
        {error && <div className="error-message">{error}</div>}
        
        {!loading && !error && addresses.length === 0 && (
          <div className="empty-message">Bạn chưa có địa chỉ nào. Vui lòng thêm địa chỉ mới.</div>
        )}

        {currentAddresses.map((address) => (
          <div 
            key={address.id} 
            className={`address-item ${address.default ? 'default' : ''}`}
          >
            <div className="address-content">
              <div className="address-header">
                <h4 className="recipient-name">{address.recipientName}</h4>
                {address.default && (
                  <span className="default-badge">
                    <FaStar /> Mặc định
                  </span>
                )}
              </div>
              <div className="address-info">
                <p className="phone">{address.phone}</p>
                <p className="address">{address.shipAddress}</p>
              </div>
            </div>
            <div className="address-actions">
              {!address.default && (
                <button 
                  className="set-default-btn" 
                  onClick={() => handleSetDefault(address.id)}
                  title="Đặt làm địa chỉ mặc định"
                  disabled={loading}
                >
                  <FaCheck />
                </button>
              )}
              <button 
                className="edit-btn" 
                onClick={() => handleEdit(address)}
                title="Chỉnh sửa"
                disabled={loading || isAddingNew || isEditing}
              >
                <FaPencilAlt />
              </button>
              <button 
                className="delete-btn" 
                onClick={() => handleDelete(address.id)}
                title="Xóa"
                disabled={loading}
              >
                <FaTrash />
              </button>
            </div>
          </div>
        ))}

        {totalPages > 1 && (
          <div className="pagination">
            <button
              className="pagination-btn"
              onClick={() => handlePageChange(currentPage - 1)}
              disabled={currentPage === 1}
            >
              Trước
            </button>
            {Array.from({ length: totalPages }, (_, i) => i + 1).map((page) => (
              <button
                key={page}
                className={`pagination-btn ${currentPage === page ? 'active' : ''}`}
                onClick={() => handlePageChange(page)}
              >
                {page}
              </button>
            ))}
            <button
              className="pagination-btn"
              onClick={() => handlePageChange(currentPage + 1)}
              disabled={currentPage === totalPages}
            >
              Sau
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default memo(MyShippingAddress);