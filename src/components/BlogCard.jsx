import React from "react";
import "./BlogCard.css";

const BlogCard = ({ post }) => {
  return (
    <div className="blog-card">
      <img src={post.image} alt={post.title} className="blog-card-image" />
      <div className="blog-card-content">
        <h2 className="blog-card-title">{post.title}</h2>
        <p className="blog-card-meta">
          {post.date} | {post.category}
        </p>
      </div>
    </div>
  );
};

export default BlogCard;