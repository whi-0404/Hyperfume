import { memo } from 'react';
import { Link } from 'react-router-dom';
import './style.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook } from "@fortawesome/free-brands-svg-icons";
import { faGoogle } from "@fortawesome/free-brands-svg-icons";
import PasswordToggle from '../../components/passHide-Unhide';

const signIn = () => {
    return (
        <>
            <div className="login">
                <div className="login-form">
                    <h2>Đăng nhập</h2>
                    <form>
                        <label htmlFor="username">
                            Tên tài khoản hoặc địa chỉ email <span>*</span>
                        </label>
                        <input type="text" id="username" placeholder="Tên tài khoản hoặc email" />

                        <label htmlFor="password">
                            Mật khẩu <span>*</span>
                        </label>
                        <PasswordToggle></PasswordToggle>

                        <div className="remember-me">
                            <input type="checkbox" id="remember" />
                            <label htmlFor="remember">Ghi nhớ mật khẩu</label>
                        </div>

                        <button type="submit" className="login-button">Đăng nhập</button>

                        <p className="forgot-password">
                            <Link to="/Forgot-Password">Quên mật khẩu</Link>
                        </p>

                        <div className="divider">
                            <span>HOẶC</span>
                        </div>

                        <div className="social-signIn">
                            <button className="facebook-button">
                                <FontAwesomeIcon icon={faFacebook} /> Facebook
                            </button>
                            <button className="google-button">
                                <FontAwesomeIcon icon={faGoogle} /> Google
                            </button>
                        </div>

                        <p className="signup-text">
                            Bạn chưa có tài khoản? <Link to="/Sign-Up">Đăng ký</Link>
                        </p>
                    </form>
                </div>
            </div>
        </>
    );
};

export default memo(signIn);