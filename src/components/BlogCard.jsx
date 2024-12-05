import React from "react";
import "./BlogCard.css";

function BlogCard({ title, date, category, image }) {
  return (
    <div className="blog-card">
      <img src={image} alt={title} className="blog-card-image" />
      <div className="blog-card-content">
        <h2 className="blog-card-title">{title}</h2>
        <p className="blog-card-meta">
          {date} | {category}
        </p>
      </div>
    </div>
  );
}

export default BlogCard;