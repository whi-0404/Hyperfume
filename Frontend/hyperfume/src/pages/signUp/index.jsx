import { memo } from 'react';
import './style.scss';
import { Link } from 'react-router-dom';
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
                                <i className="fa fa-facebook"></i> Facebook
                            </button>
                            <button className="google-button">
                                <i className="fa fa-google"></i> Google
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