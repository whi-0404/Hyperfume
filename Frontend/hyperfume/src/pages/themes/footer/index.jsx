import { memo } from 'react';
import './style.scss';
import boCongThuong from "../../../assets/image/boCongThuong.jpg";
import FrameCard from "../../../assets/image/Frame-Card.jpg";
import { Link } from 'react-router-dom';

const Footer = () => {
    return (
        <>
            <div className='footer'>
                <div className='container'>
                    <div class="Thong-tin">
                        <p>Thông tin thêm</p>
                        <ul>
                            <li><a href="">Giới thiệu về Hyperfume</a></li>
                            <li><a href="">Blog</a></li>
                            <li><a href="">Trả góp qua thẻ tín dụng</a></li>
                            <li><Link to="/Instruction">Chính sách bảo hành/đổi trả</Link></li>
                            <li><Link to="/Instruction">Chính sách thanh toán</Link></li>
                            <li><Link to="/Instruction">Chính sách vận chuyển</Link></li>
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
                        <a href=""><img src={boCongThuong} alt="" /></a>
                    </div>

                    <div class="Follow">
                        <p>Follow us</p>
                        <div class="media icon">
                            <a href="" class="fa fa-facebook"></a>
                            <a href="" class="fa fa-instagram"></a>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
};

export default memo(Footer);