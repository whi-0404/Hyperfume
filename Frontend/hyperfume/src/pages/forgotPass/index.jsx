import { memo } from 'react';
import './style.scss';
import { Link } from 'react-router-dom';

const signIn = () => {
    return (
        <>
            <div className="login">
                <div className="login-form">
                    <h2>Quên mật khẩu? <br />
                        Vui lòng nhập tên đăng nhập hoặc địa chỉ email. Bạn sẽ nhận được một liên kết tạo mật khẩu mới qua email.</h2>
                    <form>
                        <label htmlFor="username">
                            Tên tài khoản hoặc địa chỉ email <span>*</span>
                        </label>
                        <input type="text" id="username" placeholder="Tên tài khoản hoặc email" />

                        <Link to="/Reset-Password">
                            <button type="submit" className="resetPass-button">
                                Đặt lại mật khẩu
                            </button>
                        </Link>

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