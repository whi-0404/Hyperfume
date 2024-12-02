import { memo } from 'react';
import './style.scss';
import logo from '../../../assets/logo.png';
import 'font-awesome/css/font-awesome.min.css';
import { Link, useLocation } from 'react-router-dom';

const Header = () => {
    const location = useLocation();

    const isActive = (path) => location.pathname === path;

    return (
        <>
            <div className='header'>
                <Link to="/"><img src={logo} alt='Logo'></img></Link>

                <h1>Hương thơm đánh thức ký ức, định hình lựa chọn</h1>

                <div className="search-bar">
                    <form>
                        <input type="text" name="" placeholder="Tìm kiếm nước hoa hoặc nhãn hiệu......" />
                        <button type="submit">
                            <i className="fa fa-search" aria-hidden="true"></i>
                        </button>
                    </form>
                </div>

                <div className='navigation'>
                    <nav>
                        <ul>
                            <li>
                                <Link to="/" className={isActive('/') ? 'active' : ''}>Trang chủ</Link>
                            </li>
                            <li>
                                <a href="#" >Sản phẩm</a>
                            </li>
                            <li>
                                <a href="#" >Flash sale</a>
                            </li>
                            <li>
                                <a href="#" >Tư vấn</a>
                            </li>
                            <li>
                                <a href="#" >Blog</a>
                            </li>
                            <li>
                                <a href="#" >Cẩm nang Nước hoa</a>
                            </li>
                            <li>
                                <Link to="/Instruction" className={isActive('/Instruction') ? 'active' : ''}>Hướng dẫn</Link>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </>
    );
};

export default memo(Header);
