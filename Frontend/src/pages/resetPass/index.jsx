import { memo } from 'react';
import './style.scss';
import { Link } from 'react-router-dom';
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