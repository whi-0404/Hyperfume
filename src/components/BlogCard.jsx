import React from "react";
import "./BlogCard.css";

const BlogCard = ({ title, date, category, image }) => {
  return (
    <div className="blog-card">
      <img src={image} alt={title} className="blog-image" />
      <div className="blog-content">
        <h3>{title}</h3>
        <p>{date} | {category}</p>
      </div>
    </div>
  );
};

export default BlogCard;
