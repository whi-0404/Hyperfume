const TOKEN_KEY = 'authToken'; // Tên khóa lưu trong sessionStorage

// Lưu token
export const setToken = (token) => {
    sessionStorage.setItem(TOKEN_KEY, token);
};

// Lấy token 
export const getToken = () => {
    return sessionStorage.getItem(TOKEN_KEY);
};

// Xóa token (khi đăng xuất)
export const removeToken = () => {
    sessionStorage.removeItem(TOKEN_KEY);
};
