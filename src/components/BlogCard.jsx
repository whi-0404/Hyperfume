import React from "react";
import "./BlogCard.css";

const BlogCard = ({ title, description, image }) => {
  return (
    <div className="blog-card">
      <img src={image} alt={title} className="blog-card-image" />
      <h2 className="blog-card-title">{title}</h2>
      <p className="blog-card-description">{description}</p>
    </div>
  );
};

export default BlogCard;