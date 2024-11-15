import { memo } from 'react';
import './style.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye } from '@fortawesome/free-regular-svg-icons';


const signIn = () => {
    return (
        <>
            <div className="signUp">
                <div className="signUp-form">
                    <h2>Đăng ký</h2>
                    <form>
                        <label htmlFor="username">
                            Tên tài khoản hoặc địa chỉ email <span>*</span>
                        </label>
                        <input type="text" id="email" placeholder=" Email " />

                        <label htmlFor="username">
                            Tên tài khoản <span>*</span>
                        </label>
                        <input type="text" id="accountName" placeholder=" Tên tài khoản" />

                        <label htmlFor="username">
                            Tên người dùng <span>*</span>
                        </label>
                        <input type="text" id="userName" placeholder=" Tên người dùng" />

                        <label htmlFor="password">
                            Mật khẩu <span>*</span>
                        </label>
                        <div className="password-field">
                            <input type="password" id="password" placeholder=" Mật khẩu" />
                            <i className='icon-eye'> <FontAwesomeIcon icon={faEye} /></i>
                        </div>

                        <label htmlFor="password">
                            Nhập lại mật khẩu <span>*</span>
                        </label>
                        <div className="password-field">
                            <input type="password" id="password" placeholder=" Nhập lại mật khẩu" />
                            <i className='icon-eye'> <FontAwesomeIcon icon={faEye} /></i>
                        </div>

                        <button type="submit" className="signUp-button">Đăng ký</button>

                        <div className="divider">
                            <span>HOẶC</span>
                        </div>

                        <div className="social-login">
                            <button className="facebook-button">
                                <i className="fa fa-facebook"></i> Facebook
                            </button>
                            <button className="google-button">
                                <i className="fa fa-google"></i> Google
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </>
    );
};

export default memo(signIn);