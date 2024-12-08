import React from 'react';  
import './Header.css';  

const Header = () => {  
  return (  
    <header className="header">  
      <h1>Blog</h1>  
      <nav>  
        <a href="/">Trang chủ</a>  
        <span>➤</span>  
        <a href="/blog">Blog</a>  
      </nav>  
    </header>  
  );  
};  

export default Header;  