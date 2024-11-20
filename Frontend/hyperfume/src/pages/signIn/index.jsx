import { memo } from 'react';
import './style.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye } from '@fortawesome/free-regular-svg-icons';
import { Link } from 'react-router-dom';

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
                        <input type="text" id="username" placeholder=" Tên tài khoản hoặc email" />

                        <label htmlFor="password">
                            Mật khẩu <span>*</span>
                        </label>
                        <div className="password-field">
                            <input type="password" id="password" placeholder=" Mật khẩu" />
                            <i className='icon-eye'> <FontAwesomeIcon icon={faEye} /></i>
                        </div>

                        <div className="remember-me">
                            <input type="checkbox" id="remember" />
                            <label htmlFor="remember">Ghi nhớ mật khẩu</label>
                        </div>

                        <button type="submit" className="login-button">Đăng nhập</button>

                        <a href="#" className="forgot-password">Quên mật khẩu</a>

                        <div className="divider">
                            <span>HOẶC</span>
                        </div>

                        <div className="social-signIn">
                            <button className="facebook-button">
                                <i className="fa fa-facebook"></i> Facebook
                            </button>
                            <button className="google-button">
                                <i className="fa fa-google"></i> Google
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