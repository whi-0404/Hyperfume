import { memo } from 'react';
import { Link } from 'react-router-dom';
import './style.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook } from "@fortawesome/free-brands-svg-icons";
import { faGoogle } from "@fortawesome/free-brands-svg-icons";

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