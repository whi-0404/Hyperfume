import { memo, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './style.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook } from "@fortawesome/free-brands-svg-icons";
import { faGoogle } from "@fortawesome/free-brands-svg-icons";
import PasswordToggle from '../../components/passHide-Unhide';

import SignInService from '../../services/signInService';


const HandleSubmit = async (e) => {
    e.preventDefault();

    const username = document.getElementById('username').value.trim();
    const password = document.getElementById('password').value.trim();

    if (!username || !password) {
        alert('Vui lòng nhập đầy đủ thông tin!');
        return;
    }

    console.log(username, password)

    try {
        const result = await SignInService(username, password);
        alert('Đăng nhập thành công!');
        console.log(result);
    } catch (error) {
        alert('Sai mật khẩu hoặc tên người dùng!');
        console.error(error);
    }
}

const signIn = () => {


    return (
        <>
            <div className="login">
                <div className="login-form">
                    <h2 style={{ fontSize: "40px" }}>Đăng nhập</h2>
                    <form onSubmit={HandleSubmit}>
                        <label htmlFor="username">
                            Tên tài khoản <span>*</span>
                        </label>
                        <input type="text" id="username" placeholder="Tên tài khoản" />

                        <label htmlFor="password">
                            Mật khẩu <span>*</span>
                        </label>
                        <PasswordToggle></PasswordToggle>

                        <button type="submit" className="login-button">Đăng nhập</button>

                        <p className="forgot-password">
                            <Link to="/Forgot-Password">Quên mật khẩu</Link>
                        </p>
                    </form>

                    <div className="divider">
                        <span>HOẶC</span>
                    </div>

                    <div className="social-signIn">
                        <button className="facebook-button">
                            <FontAwesomeIcon icon={faFacebook} style={{ marginRight: '5px' }} /> Facebook
                        </button>
                        <button className="google-button">
                            <FontAwesomeIcon icon={faGoogle} style={{ marginRight: '5px' }} /> Google
                        </button>
                    </div>

                    <p className="signup-text">
                        Bạn chưa có tài khoản? <Link to="/Sign-Up">Đăng ký</Link>
                    </p>
                </div>
            </div>
        </>
    );
};

export default memo(signIn);