import { memo } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './style.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook } from "@fortawesome/free-brands-svg-icons";
import { faGoogle } from "@fortawesome/free-brands-svg-icons";
import PasswordToggle from '../../components/passHide-Unhide';

import SignInService from '../../services/handleSignIn';
import { setToken, removeToken } from '../../services/authToken';
import { UserInfo } from '../../services/handleUserInfo';

const SignIn = () => {
    const navigate = useNavigate();

    const HandleSubmit = async (e) => {
        e.preventDefault();

        let username = document.getElementById('username').value.trim();
        let password = document.getElementById('password').value.trim();

        if (!username || !password) {
            alert('Vui lòng nhập đầy đủ thông tin!');
            return;
        }

        try {
            removeToken()
            const signInResult = await SignInService(username, password);
            const token = signInResult.result.token;
            setToken(token); // Lưu token vào sessionStorage
            alert('Đăng nhập thành công!');

            const userInfo = await UserInfo(token);
            console.log(userInfo);

            if (userInfo.result.role.name === "USER") {
                navigate('/'); // Chuyển hướng đến trang chủ
            } else if (userInfo.result.role.name === "ADMIN") {
                navigate('/admin'); // Chuyển hướng đến trang admin
            }

        } catch (error) {
            alert('Sai mật khẩu hoặc tên người dùng!');
            console.error(error);
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
                        <button className="google-button">
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
