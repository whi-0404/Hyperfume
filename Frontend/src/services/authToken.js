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

export const login = async (username, password) => {
    try {
        const response = await api.post('/auth/token', {
            username,
            password
        });
        return response.data;
    } catch (error) {
        console.error('Error logging in:', error);
        throw error;
    }
}