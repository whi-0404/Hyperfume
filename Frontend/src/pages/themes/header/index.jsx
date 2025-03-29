import { memo, useEffect, useState } from "react";
import "./style.scss";
import logo from "../../../assets/logo.png";
import slogan from "../../../assets/slogan.png";
import { CiSearch } from "react-icons/ci";
import { FiSmartphone } from "react-icons/fi";
import { BsCart2 } from "react-icons/bs";
import { RiArrowDownWideFill } from "react-icons/ri";
import { NavLink, useNavigate } from "react-router-dom";
import { searchProducts } from "../../../services/handleSearchPerfume";
import { brands } from "../../../services/getBrand";
import { countries } from "../../../services/getCountry";
import {useUser} from "../../../utils/userContext";
import defaultAvatar from "../../../assets/image/UsersImages/avt_default.png";

const Header = () => {
  const navigate = useNavigate();
  const [brandList, setBrandList] = useState([]);
  const [countryList, setCountryList] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const { user } = useUser();

  useEffect(() => {
    const loadBrands = async () => {
      const brands = await fetchBrands();
      setBrandList(brands);
    };
    
    loadBrands();
  }, []);

  useEffect(() => {
    const loadCountries = async () => {
      const countries = await fetchCountries();
      setCountryList(countries);
    };
    
    loadCountries();
  }, []);

  const HandleSubmit = async (e) => {
    e.preventDefault();
    const searchText = document.getElementById("searchText").value;

    if (!searchText) {
      alert("Vui lòng nhập đầy đủ thông tin!");
      return;
    }
    try {
      const response = await searchProducts(searchText); // Gọi API tìm kiếm
      const products = response.data;
      console.log(products);
      navigate("/search", { state: { products } });
    } catch (error) {
      console.error("Error while searching for products:", error);
    }
  };

  const fetchBrands = async () => {
    const cachedBrands = sessionStorage.getItem("brands");

    if (cachedBrands) {
        return JSON.parse(cachedBrands);
    } else {
        try {
            setLoading(true); // Bắt đầu load dữ liệu
            const response = await brands(); // Gọi API

            if (response.data && response.data.code === 1000) {
                const brandList = response.data.result;
                
                // Lưu vào sessionStorage
                sessionStorage.setItem("brands", JSON.stringify(brandList));

                setLoading(false); // Hoàn tất load dữ liệu
                return brandList;
            } else {
                setError("Invalid response format");
                setLoading(false);
                return [];
            }
        } catch (error) {
            console.error(error);
            setError("Failed to fetch brands");
            setLoading(false);
            return [];
        }
    }
  };

  const fetchCountries = async () => {
    const cachedCountries = sessionStorage.getItem("countries");

    if (cachedCountries) {
        return JSON.parse(cachedCountries);
    } else {
        try {
            setLoading(true); // Bắt đầu load dữ liệu
            const response = await countries(); // Gọi API

            if (response.data && response.data.code === 1000) {
                const countryList = response.data.result;
                
                // Lưu vào sessionStorage
                sessionStorage.setItem("countries", JSON.stringify(countryList));

                setLoading(false); // Hoàn tất load dữ liệu
                return countryList;
            } else {
                setError("Invalid response format");
                setLoading(false);
                return [];
            }
        } catch (error) {
            console.error(error);
            setError("Failed to fetch countries");
            setLoading(false);
            return [];
        }
    }
  };

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
                  <span className="cart-count"></span>
                </NavLink>
              </div>
            { !user ? 
              (<span className="login-text">
                <NavLink to="/Sign-In">ĐĂNG NHẬP</NavLink>
              </span>
              ) : (
                <div className="user-profile">
                  <div className="avatar-container">
                  <NavLink to="/Profile">
                    <img 
                      src={user.avatar ? user.avatar : defaultAvatar} 
                      alt="Avatar" 
                      className="user-avatar" 
                    />
                    </NavLink>
                  </div>
                  <span className="login-text">
                    <NavLink to="/Profile">{user.username}</NavLink>
                  </span>
                </div>
                )}
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

              <form onSubmit={HandleSubmit}>
                <div className="search-bar">
                  <input type="text" placeholder="Tìm kiếm ..." id="searchText" required />
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
              <NavLink to="/nuoc-hoa" activeClassName="active">
                Sản phẩm{" "}
                <i className="fa-solid fa-angle-down">
                  <RiArrowDownWideFill />
                </i>
              </NavLink>
              <ul className="dropdown"> 
                <li>
                  <NavLink to="/nuoc-hoa-nam" activeClassName="active" state={{ gender: "Nam" }}>
                    Nước hoa Nam
                  </NavLink>
                </li>
                <li>
                  <NavLink to="/nuoc-hoa-nu" activeClassName="active" state={{ gender: "Nữ" }}> 
                    Nước hoa Nữ
                  </NavLink>
                </li>
                <li>
                  <NavLink to="/nuoc-hoa-unisex" activeClassName="active" state={{ gender: "Unisex" }}>
                    Nước hoa Unisex
                  </NavLink>
                </li>
              </ul>
            </li>
            <li>
            <div className="dropdown-toggle">
                Thương hiệu{" "}
                <i className="fa-solid fa-angle-down">
                  <RiArrowDownWideFill />
                </i>
              </div>
              <ul className="dropdown">
                {loading ? (
                  <li>Đang tải...</li>
                ) : error ? (
                  <li>{error}</li>
                ) : (
                  brandList.map((brand, index) => (
                    <li key={index}>
                      <NavLink
                      to={`/nuoc-hoa/thuong-hieu/${brand.name}`}
                      activeClassName="active"
                      state={{ brandId: brand.id, brandName: brand.name }}
                      >
                      {brand.name}
                    </NavLink>
                    </li>
                  ))
                )}
              </ul>
            </li>
            <li>
              <div className="dropdown-toggle">
                Xuất xứ{" "}
                <i className="fa-solid fa-angle-down">
                  <RiArrowDownWideFill />
                </i>
              </div>

              <ul className="dropdown">
                {loading ? (
                  <li>Đang tải...</li>
                ) : error ? (
                  <li>{error}</li>
                ) : (
                  countryList.map((country, index) => (
                    <li key={index}>
                      <NavLink 
                        to={`/nuoc-hoa/xuat-xu/${country.name}`} 
                        activeClassName="active" 
                        state={{ countryId: country.id, countryName: country.name }}
                      >
                        {country.name}
                      </NavLink>
                    </li>
                  ))
                )}
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
              <NavLink to="/Blog" activeClassName="active">
                Blog
              </NavLink>
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