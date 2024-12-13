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
            <div className="signUp">
                <div className="signUp-form">
                    <h2>Đăng ký</h2>
                    <form>
                        <label htmlFor="username">
                            Tên tài khoản hoặc địa chỉ email <span>*</span>
                        </label>
                        <input type="text" id="email" placeholder="Email " />

                        <label htmlFor="username">
                            Tên tài khoản <span>*</span>
                        </label>
                        <input type="text" id="accountName" placeholder="Tên tài khoản" />

                        <label htmlFor="username">
                            Tên người dùng <span>*</span>
                        </label>
                        <input type="text" id="userName" placeholder="Tên người dùng" />

                        <label htmlFor="password">
                            Mật khẩu <span>*</span>
                        </label>
                        <PasswordToggle />

                        <label htmlFor="password">
                            Nhập lại mật khẩu <span>*</span>
                        </label>
                        <PasswordToggle placeholder='Nhập lại mật khẩu' />

                        <button type="submit" className="signUp-button">Đăng ký</button>

                        <div className="divider">
                            <span>HOẶC</span>
                        </div>

                        <div className="social-signUp">
                            <button className="facebook-button">
                                <FontAwesomeIcon icon={faFacebook} /> Facebook
                            </button>
                            <button className="google-button">
                                <FontAwesomeIcon icon={faGoogle} /> Google
                            </button>
                        </div>

                        <div className='signIn-text'>
                            Bạn đã có tài khoản? <Link to="/Sign-In">Đăng nhập</Link>
                        </div>
                    </form>
                </div>
            </div>
        </>
    );
};

export default memo(signIn);