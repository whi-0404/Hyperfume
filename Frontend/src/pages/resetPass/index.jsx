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
                    <h2>Đặt lại mật khẩu</h2>
                    <form>

                        <label htmlFor="ID">
                            Mã ID <span>*</span>
                        </label>
                        <input type="text" id="ID" placeholder="Mã ID" />

                        <label htmlFor="password">
                            Nhập mật khẩu mới <span>*</span>
                        </label>
                        <PasswordToggle placeholder='Nhập mật khẩu mới' />

                        <label htmlFor="password">
                            Nhập lại mật khẩu mới <span>*</span>
                        </label>
                        <PasswordToggle placeholder='Nhập lại mật khẩu mới' />

                        <button type="submit" className="ResetPass-button">Đặt lại mật khẩu</button>

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