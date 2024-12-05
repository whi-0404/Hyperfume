import React from "react";
import { Link } from "react-router-dom";
import "./Navbar.css";

function Navbar() {
  return (
    <nav className="navbar">
      <h1 className="navbar-title">React Blog</h1>
      <div className="navbar-links">
        <Link to="/">Home</Link>
        <Link to="/blog">Blog</Link>
      </div>
    </nav>
  );
}

export default Navbar;