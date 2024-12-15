import { memo } from "react";
import "./style.scss";
import logo from '../../../assets/logo.png';
import slogan from '../../../assets/slogan.png';
import { CiSearch } from "react-icons/ci";
import { FiSmartphone } from "react-icons/fi";
import { BsCart2 } from "react-icons/bs";
import { RiArrowDownWideFill } from "react-icons/ri";
import { NavLink } from 'react-router-dom';

const Header = () => {
    return (
        <div className="header">
            <div className="row">
                <div className="col logo">
                    <a href="/">
                        <img src={logo} alt="Logo" />
                    </a>
                </div>

                <div className="col mid">
                    <img src={slogan} alt="Slogan" />
                </div>

                <div className="col right">
                    <div className="others">
                        <div className="cart-login">
                            <div className="cart-icon">
                                <NavLink to="/Cart">
                                    <i className="fa-solid fa-cart-shopping"><BsCart2 /></i>
                                    <span className="cart-count">0</span>
                                </NavLink>
                            </div>

                            <span className="login-text"><NavLink to="/Sign-in">ĐĂNG NHẬP</NavLink></span>
                        </div>

                        <div className="hotline-search">
                            <div className="hotline">
                                <i className="fa-solid fa-mobile"><FiSmartphone /></i>
                                <div className="hotline-info">
                                    <span className="phone-number">0273-686-868</span>
                                    <span className="hotline-text">Hotline bán hàng</span>
                                </div>
                            </div>
                            <div className="search-bar">
                                <input type="text" placeholder="Tìm kiếm ..." />
                                <i className="fa-solid fa-magnifying-glass"><CiSearch /></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div>
                <nav>
                    <ul>
                        <li>
                            <NavLink to="/" exact activeClassName="active">Trang chủ</NavLink>
                        </li>


                        <li>
                            <a href="#products">Sản phẩm <RiArrowDownWideFill /></a>
                            <ul className="dropdown">
                                <li>
                                    <a href="#gioi-tinh">Giới Tính <RiArrowDownWideFill /></a>
                                    <ul className="dropdown-sub">
                                        <li><NavLink to="/nuoc-hoa-nam" activeClassName="active">Nam</NavLink></li>
                                        <li><NavLink to="/nuoc-hoa-nu" activeClassName="active">Nữ</NavLink></li>
                                        <li><NavLink to="/nuoc-hoa-unisex" activeClassName="active">Unisex</NavLink></li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="#nhom-huong">Nhóm Hương <RiArrowDownWideFill /></a>
                                    <ul className="dropdown-sub">
                                        <li><a href="/">Nhóm Hương 1</a></li>
                                        <li><a href="/">Nhóm Hương 2</a></li>
                                        <li><a href="/">Nhóm Hương 3</a></li>
                                    </ul>
                                </li>
                                <li>
                                    <a href="#quoc-gia">Quốc Gia <RiArrowDownWideFill /></a>
                                    <ul className="dropdown-sub">
                                        <li><a href="/">Việt Nam</a></li>
                                        <li><a href="/">Pháp</a></li>
                                        <li><a href="/">Ý</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </li>

                        <li><NavLink to="/flash-sale" activeClassName="active">Flashsale</NavLink></li>
                        <li><a href="#about">Tư vấn</a></li>
                        <li><a href="/">Blog</a></li>
                        <li><a href="#contact">Cẩm nang Nước Hoa</a></li>
                        <li>
                            <a href="#about">Hướng dẫn <i className="fa-solid fa-angle-down"><RiArrowDownWideFill /></i></a>
                            <ul className="dropdown">
                                <li><NavLink to="/Instruction" activeClassName="active">Hướng dẫn mua hàng</NavLink></li>
                                <li><NavLink to="/Instruction" activeClassName="active">Chính sách</NavLink></li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

    );

};

export default memo(Header);