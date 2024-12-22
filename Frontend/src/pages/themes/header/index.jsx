import { memo } from "react";
import "./style.scss";
import logo from "../../../assets/logo.png";
import slogan from "../../../assets/slogan.png";
import { CiSearch } from "react-icons/ci";
import { FiSmartphone } from "react-icons/fi";
import { BsCart2 } from "react-icons/bs";
import { RiArrowDownWideFill } from "react-icons/ri";
import { NavLink } from "react-router-dom";

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
                  <i className="fa-solid fa-cart-shopping">
                    <BsCart2 />
                  </i>
                  <span className="cart-count">0</span>
                </NavLink>
              </div>

              <span className="login-text">
                <NavLink to="/Sign-in">ĐĂNG NHẬP</NavLink>
              </span>
            </div>

            <div className="hotline-search">
              <div className="hotline">
                <i className="fa-solid fa-mobile">
                  <FiSmartphone />
                </i>
                <div className="hotline-info">
                  <span className="phone-number">0273-686-868</span>
                  <span className="hotline-text">Hotline bán hàng</span>
                </div>
              </div>

              <form action="">
                <div className="search-bar">
                  <input type="text" placeholder="Tìm kiếm ..." />
                  <button type="submit" className="search-button">
                    <i className="fa-solid fa-magnifying-glass">
                      <CiSearch />
                    </i>
                  </button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>

      <div>
        <nav>
          <ul>
            <li>
              <NavLink to="/" exact activeClassName="active">
                Trang chủ
              </NavLink>
            </li>
            <li>
              <a href="#news">
                Sản phẩm{" "}
                <i className="fa-solid fa-angle-down">
                  <RiArrowDownWideFill />
                </i>
              </a>
              <ul className="dropdown">
                <li>
                  <NavLink to="/nuoc-hoa-nam" activeClassName="active">
                    Nước hoa Nam
                  </NavLink>
                </li>
                <li>
                  <NavLink to="/nuoc-hoa-nu" activeClassName="active">
                    Nước hoa Nữ
                  </NavLink>
                </li>
                <li>
                  <NavLink to="/nuoc-hoa-unisex" activeClassName="active">
                    Nước hoa Unisex
                  </NavLink>
                </li>
              </ul>
            </li>
            <li>
              <NavLink to="/flash-sale" activeClassName="active">
                Flashsale
              </NavLink>
            </li>
            <li>
              <NavLink to="/consult" activeClassName="active">
                Tư vấn
              </NavLink>
            </li>
            <li>
              <a href="">Blog</a>
            </li>
            <li>
              <a href="#about">
                Hướng dẫn{" "}
                <i className="fa-solid fa-angle-down">
                  <RiArrowDownWideFill />
                </i>
              </a>
              <ul className="dropdown">
                <li>
                  <NavLink to="/Instruction" activeClassName="active">
                    Hướng dẫn mua hàng
                  </NavLink>
                </li>
                <li>
                  <NavLink to="/Instruction" activeClassName="active">
                    Chính sách
                  </NavLink>
                </li>
              </ul>
            </li>
          </ul>
        </nav>
      </div>
    </div>
  );
};

export default memo(Header);
