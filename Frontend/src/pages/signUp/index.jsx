import { memo } from 'react';
import { Link } from 'react-router-dom';
import './style.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook } from "@fortawesome/free-brands-svg-icons";
import { faGoogle } from "@fortawesome/free-brands-svg-icons";
import PasswordToggle from '../../components/passHide-Unhide';

import signUpService from "../../services/signUp_service";

const signUp = () => {
    // Hàm xử lý gửi form
    const handleSubmit = async (e) => {
        e.preventDefault();

        const email = document.getElementById('email').value.trim();
        const username = document.getElementById('userName').value.trim();
        const fullname = document.getElementById('fullName').value;
        const phone = document.getElementById('phone').value.trim();

        const passwordInputs = document.querySelectorAll('.password-toggle__input');
        const password = passwordInputs[0].value.trim();
        const confirmPassword = passwordInputs[1].value.trim();

        if (!email || !fullname || !username || !phone || !password || !confirmPassword) {
            alert('Vui lòng nhập đầy đủ thông tin!');
            return;
        }

        if (password.length < 8) {
            alert('Mật khẩu phải có 8 ký tự trở lên.')
            return;
        } else if (password !== confirmPassword) {
            alert('Mật khẩu không khớp! Vui lòng kiểm tra lại.');
            return;
        }

        const userData = {
            email,
            username,
            fullname,
            phone,
            password,
        };

        try {
            const result = await signUpService.signUpRequest(userData);
            alert('Đăng ký thành công!');
            console.log(result);
        } catch (error) {
            alert('Đăng ký thất bại!');
            console.error(error);
        }
    };

    return (
        <div className="signUp">
            <div className="signUp-form">
                <h2>Đăng ký</h2>
                <form onSubmit={handleSubmit}>
                    <label htmlFor="email">
                        Địa chỉ email <span>*</span>
                    </label>
                    <input type="text" id="email" placeholder="Email " />

                    <label htmlFor="username">
                        Tên tài khoản <span>*</span>
                    </label>
                    <input type="text" id="userName" placeholder="Tên tài khoản" />

                    <label htmlFor="fullName">
                        Họ tên người dùng <span>*</span>
                    </label>
                    <input type="text" id="fullName" placeholder="Họ tên người dùng" />

                    <label htmlFor="phone">
                        Số điện thoại <span>*</span>
                    </label>
                    <input type="text" id="phone" placeholder="Số điện thoại" />

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
                            <FontAwesomeIcon icon={faFacebook} style={{ marginRight: '5px' }} /> Facebook
                        </button>
                        <button className="google-button">
                            <FontAwesomeIcon icon={faGoogle} style={{ marginRight: '5px' }} /> Google
                        </button>
                    </div>

                    <div className='signIn-text'>
                        Bạn đã có tài khoản? <Link to="/Sign-In">Đăng nhập</Link>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default memo(signUp);