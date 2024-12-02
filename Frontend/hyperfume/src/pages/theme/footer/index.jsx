import { memo } from 'react';
import './style.scss';
import boCongThuong from "../../../assets/image/boCongThuong.jpg";
import FrameCard from "../../../assets/image/Frame-Card.jpg";
import { CiFacebook } from "react-icons/ci";
import { FaInstagram } from "react-icons/fa";



const Footer = () => {
    return (
        <>
            <div className='footer'>
                <div className='container'>
                    <div class="Thong-tin">
                        <p>Thông tin thêm</p>
                        <ul>
                        <li><a href="/">Giới Thiệu về Hyperfume</a></li>
                        <li><a href="/">Blog</a></li>
                        <li><a href="/">Trả góp qua thẻ tín dụng</a></li>
                        <li><a href="/">Chính sách bảo hành/đổi trả</a></li>
                        <li><a href="/thanh-toan">Chính sách thanh toán</a></li>
                        <li><a href="/">Chính sách vận chuyển</a></li>
                        </ul>
                    </div>

                    <div class="Ho-tro">
                        <p>Hỗ trợ 24/7</p>
                        <ul>
                            <li>Công ty TNHH Hyperfume</li>
                            <li>Hotline: 0273 - 686 - 868</li>
                            <li>Địa chỉ: 123, đường Nam Kì Khởi Nghĩa, <br />Quận 1, TP.HCM</li>

                        </ul>
                    </div>

                    <div class="Thanh-toan">
                        <p>Phương thức thanh toán</p>
                        <img src={FrameCard} alt="Phương thức thanh toán" /> <br />
                        <a href="https://moit.gov.vn/"><img src={boCongThuong} alt="" /></a>
                    </div>

                    <div class="Follow">
                        <p>Follow us</p>
                        <div class="media icon">
                            <a href="/" class="fa fa-facebook"><CiFacebook /></a>
                            <a href="/" class="fa fa-instagram"><FaInstagram /></a>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
};

export default memo(Footer);