import React, { createContext, useState, useContext, useEffect } from 'react';
import { UserInfo } from '../services/handleUserInfo';

// Tạo Context
const UserContext = createContext(null);

// Provider Component
export const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Hàm để lấy thông tin người dùng
  const fetchUserInfo = async () => {
    try {
      setLoading(true);
      const userInfo = await UserInfo();
      if (userInfo.code === 1000) {
        setUser(userInfo.result);
        return userInfo.result; 
      } else {
        setError('Không thể lấy thông tin người dùng');
      }
    } catch (err) {
      setError(err.message || 'Đã xảy ra lỗi');
    } finally {
      setLoading(false);
    }
  };

  // Hàm để cập nhật thông tin người dùng sau đăng nhập
  const updateUser = (userData) => {
    setUser(userData);
  };

  // Hàm đăng xuất
  const logout = () => {
    // Xóa cookie hoặc token ở đây nếu cần
    setUser(null);
  };

  // Lấy thông tin người dùng khi component mount (tức là khi trang web tải)
  useEffect(() => {
    // Chỉ gọi API khi chưa có thông tin người dùng
    if (!user) {
      fetchUserInfo();
    }
  }, []); // Mảng dependencies rỗng nghĩa là chỉ chạy một lần khi component mount

  return (
    <UserContext.Provider value={{ user, loading, error, updateUser, fetchUserInfo, logout }}>
      {children}
    </UserContext.Provider>
  );
};

// Hook để sử dụng context
export const useUser = () => useContext(UserContext);