// const TOKEN_KEY = 'authToken'; // Tên khóa lưu trong sessionStorage

// // Lưu token
// export const setToken = (token) => {
//     sessionStorage.setItem(TOKEN_KEY, token);
// };

// // Lấy token 
// export const getToken = () => {
//     return sessionStorage.getItem(TOKEN_KEY);
// };

// // // Xóa token (khi đăng xuất)
// // export const removeToken = () => {
// //     sessionStorage.removeItem(TOKEN_KEY);
// // };

import api from './axiosConfig';

export const logout = async () => {
    try {
        const response = await api.post('/auth/logout');
        return response.data;
    } catch (error) {
        console.error('Error logout:', error);
        throw error;
    }
}

export const refreshToken = async () => {
    try {
        const response = await api.post('/auth/refresh');
        return response.data;
    } catch (error) {
        console.error('Error refresh token:', error);
        throw error;
    }
}


