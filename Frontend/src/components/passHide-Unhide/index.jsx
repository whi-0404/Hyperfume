import React, { useState, memo } from "react";
import "./style.scss";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye, faEyeSlash } from '@fortawesome/free-regular-svg-icons';

const PasswordToggle = ({ placeholder = "Nhập mật khẩu" }) => {
    const [passwordVisible, setPasswordVisible] = useState(false); // Khởi tạo state với giá trị false

    const togglePasswordVisibility = () => {
        setPasswordVisible(!passwordVisible); // Cập nhật giá trị ngược lại (true -> false hoặc false -> true)
    };

    return (
        <div className="password-toggle">
            <input
                type={passwordVisible ? "text" : "password"}
                placeholder={placeholder}
                className="password-toggle__input"
            />
            <span
                className="eyeIcon"
                onClick={togglePasswordVisibility}
            >
                <FontAwesomeIcon icon={passwordVisible ? faEye : faEyeSlash} />
            </span>
        </div>
    );
};

export default memo(PasswordToggle);