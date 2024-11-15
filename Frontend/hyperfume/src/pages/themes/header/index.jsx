import { memo } from 'react';
import './style.scss';
import logo from '../../../assets/logo.png';
import 'font-awesome/css/font-awesome.min.css';

const Header = () => {
    return (
        <>
            <div className='header'>
                <div className='container'>
                    <a href="#"><img src={logo} alt='Logo'></img></a>

                    <h1>Hương thơm đánh thức ký ức, định hình lựa chọn</h1>

                    <div class="search-bar">
                        <form>
                            <input type="text" name="" placeholder="Tìm kiếm nước hoa hoặc nhãn hiệu......" />
                            <button type="submit">
                                <i class="fa fa-search" aria-hidden="true"></i>
                            </button>
                        </form>
                    </div>

                    <div>
                        <nav>
                            <ul>
                                <li><a href="#">Trang chủ</a></li>
                                <li><a href="#">Sản phẩm</a></li>
                                <li><a href="#">Flash sale</a></li>
                                <li><a href="#">Tư vấn</a></li>
                                <li><a href="#">Blog</a></li>
                                <li><a href="#">Cẩm nang Nước hoa</a></li>
                                <li><a href="#">Hướng dẫn</a></li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </>
    )
};

export default memo(Header);