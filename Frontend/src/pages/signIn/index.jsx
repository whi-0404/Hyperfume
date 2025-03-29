import { memo, useEffect } from 'react';
import { Link, useNavigate,useLocation } from 'react-router-dom';
import './style.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook } from "@fortawesome/free-brands-svg-icons";
import { faGoogle } from "@fortawesome/free-brands-svg-icons";
import PasswordToggle from '../../components/passHide-Unhide';

import SignInService from '../../services/handleSignIn';
import { useUser } from '../../utils/userContext';
import { targetGoogleUrl , exchangeTokenGoogle} from '../../services/handleGoogleLogin';

const SignIn = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const { fetchUserInfo } = useUser();


    useEffect(() => {
        const handleGoogleCallback = async () => {
            try {
                // Tạo đối tượng URL để dễ dàng trích xuất tham số
                const url = new URL(window.location.href);
                
                // Lấy giá trị của tham số 'code'
                const code = url.searchParams.get('code');
                
                if (code) {
                    console.log('Đã nhận được code từ Google:', code);
                    
                    // Gửi code đến backend
                    await exchangeTokenGoogle(code);
                    
                    // Xóa tham số code khỏi URL (để tránh người dùng refresh trang)
                    navigate('/', { replace: true });
                }
            } catch (error) {
                console.error('Lỗi khi xử lý callback Google:', error);
                alert('Đã xảy ra lỗi khi xử lý đăng nhập Google. Vui lòng thử lại sau.');
            }
        };

        // Kiểm tra xem URL hiện tại có chứa code từ Google callback không
        if (location.search.includes('code=')) {
            handleGoogleCallback();
        }
    }, [location, navigate, fetchUserInfo]);


    const HandleSubmit = async (e) => {
        e.preventDefault();

        let username = document.getElementById('username').value.trim();
        let password = document.getElementById('password').value.trim();

        if (!username || !password) {
            alert('Vui lòng nhập đầy đủ thông tin!');
            return;
        }

        try {
            const signInResult = await SignInService(username, password);
            if (signInResult.code !== 1000) {
                throw new Error('Đăng nhập thất bại!');
            }
            alert('Đăng nhập thành công!');

            await fetchUserInfo();

            const userInfo = await fetchUserInfo();

            if (userInfo && userInfo.role.name === "USER") {
                navigate('/'); // Chuyển hướng đến trang chủ
            } else if (userInfo && userInfo.role.name === "ADMIN") {
                navigate('/admin'); // Chuyển hướng đến trang admin
            }

        } catch (error) {
            alert('Sai mật khẩu hoặc tên người dùng!');
            console.error(error);
        }
    };

    // Hàm xử lý đăng nhập bằng Google OAuth2
    const handleGoogleLogin = async () => {
        try {
            window.location.href = targetGoogleUrl;
        } catch (error) {
            console.error('Lỗi khi đăng nhập với Google:', error);
            alert('Không thể kết nối với Google. Vui lòng thử lại sau.');
        }
    };

    return (
        <>
            <div className="login">
                <div className="login-form">
                    <h2 style={{ fontSize: "40px" }}>Đăng nhập</h2>
                    <form onSubmit={HandleSubmit}>
                        <label htmlFor="username">
                            Tên tài khoản <span>*</span>
                        </label>
                        <input
                            type="text"
                            id="username"
                            placeholder="Tên tài khoản"
                        />
                        <label htmlFor="password">
                            Mật khẩu <span>*</span>
                        </label>
                        <PasswordToggle />
                        <button type="submit" className="login-button">Đăng nhập</button>
                        <p className="forgot-password">
                            <Link to="/Forgot-Password">Quên mật khẩu</Link>
                        </p>
                    </form>

                    <div className="divider">
                        <span>HOẶC</span>
                    </div>

                    <div className="social-signIn">
                        <button className="facebook-button">
                            <FontAwesomeIcon icon={faFacebook} style={{ marginRight: '5px' }} /> Facebook
                        </button>
                        <button className="google-button" onClick={handleGoogleLogin}>
                            <FontAwesomeIcon icon={faGoogle} style={{ marginRight: '5px' }} /> Google
                        </button>
                    </div>

                    <p className="signup-text">
                        Bạn chưa có tài khoản? <Link to="/Sign-Up">Đăng ký</Link>
                    </p>
                </div>
            </div>
        </>
    );
};

export default memo(SignIn);