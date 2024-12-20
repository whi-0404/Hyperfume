import api from './axiosConfig';

const signUpService = {
    // Gửi yêu cầu đăng ký
    signUpRequest: async (userData) => {
        try {
            const response = await api.post('/users', {
                "email": userData.email,
                "username": userData.username,
                "fullname": userData.fullname,
                "password": userData.password,
                "phone": userData.phone
            });
            return response.data; // trả về dữ liệu từ API
        } catch (error) {
            console.error('Error signing up:', error);
            throw error; // quăng lỗi để xử lý ở component gọi service
        }
    },
};

export default signUpService;