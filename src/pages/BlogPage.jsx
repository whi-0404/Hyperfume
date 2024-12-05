import React, { useState } from "react";
import BlogCard from "../components/BlogCard";
import Pagination from "../components/Pagination";
import "./BlogPage.css";

const blogPosts = [
  {
    id: 1,
    title: "5 Hương Nước Hoa Công Sở Cho Nữ Được Nhiều Người Yêu Thích",
    date: "01/10",
    category: "Hyperfume",
    image: "https://placehold.co/150x150",
  },
  {
    id: 2,
    title: "Top 5 Nước Hoa Pháp Cho Nữ Được Ưa Chuộng Nhất",
    date: "01/10",
    category: "Hyperfume",
    image: "https://placehold.co/150x150",
  },
  {
    id: 3,
    title: "Nước Hoa Văn Phòng Cho Nam – Lịch Lãm Và Tự Tin Trong Mọi Tình Huống",
    date: "01/10",
    category: "Hyperfume",
    image: "https://placehold.co/150x150",
  },
];

function BlogPage() {
  const [currentPage, setCurrentPage] = useState(1);
  const postsPerPage = 2;

  const indexOfLastPost = currentPage * postsPerPage;
  const indexOfFirstPost = indexOfLastPost - postsPerPage;
  const currentPosts = blogPosts.slice(indexOfFirstPost, indexOfLastPost);

  return (
    <div className="blog-page">
      <div className="blog-list">
        {currentPosts.map((post) => (
          <BlogCard key={post.id} {...post} />
        ))}
      </div>
      <Pagination
        currentPage={currentPage}
        totalPages={Math.ceil(blogPosts.length / postsPerPage)}
        onPageChange={setCurrentPage}
      />
    </div>
  );
}

export default BlogPage;