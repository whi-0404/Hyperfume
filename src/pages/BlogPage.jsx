import React, { useState } from "react";
import BlogCard from "../components/BlogCard";
import Pagination from "../components/Pagination";
import "./BlogPage.css";

const BlogPage = () => {
  const blogPosts = [
    { id: 1, title: "5 Hương Nước Hoa Dành Cho Nam", date: "01/10", category: "Hyperfume", image: "https://placehold.co/300x200" },
    { id: 2, title: "Top 5 Nước Hoa Phái Đẹp", date: "01/10", category: "Hyperfume", image: "https://placehold.co/300x200" },
    { id: 3, title: "Nước Hoa Vĩnh Phong Cho Nam", date: "01/10", category: "Hyperfume", image: "https://placehold.co/300x200" },
    { id: 4, title: "So Sánh Nước Hoa Yves Saint Laurent", date: "01/10", category: "Hyperfume", image: "https://placehold.co/300x200" },
    { id: 5, title: "Tự Dự Nước Hoa Xperf", date: "01/10", category: "Hyperfume", image: "https://placehold.co/300x200" },
    { id: 6, title: "Sức Hút Nước Hoa Của Đấng Mày Râu", date: "01/10", category: "Hyperfume", image: "https://placehold.co/300x200" },
  ];

  const [currentPage, setCurrentPage] = useState(1);
  const postsPerPage = 3;

  const indexOfLastPost = currentPage * postsPerPage;
  const indexOfFirstPost = indexOfLastPost - postsPerPage;
  const currentPosts = blogPosts.slice(indexOfFirstPost, indexOfLastPost);

  const totalPages = Math.ceil(blogPosts.length / postsPerPage);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  return (
    <div className="blog-page">
      <div className="blog-grid">
        {currentPosts.map((post) => (
          <BlogCard key={post.id} post={post} />
        ))}
      </div>
      <Pagination
        totalPages={totalPages}
        currentPage={currentPage}
        onPageChange={handlePageChange}
      />
    </div>
  );
};

export default BlogPage;