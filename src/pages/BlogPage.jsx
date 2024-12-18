import React, { useState } from "react";
import BlogCard from "../components/BlogCard";
import Pagination from "../components/Pagination";
import "./BlogPage.css";

const BlogPage = () => {
  const blogData = [
    { id: 1, title: "5 Hương Nước Hoa...", date: "01/10", category: "Hyperfume", image: "https://file.hstatic.net/1000339918/article/251._danh-gia-chi-tiet-dong-nuoc-hoa-givenchy-l-interdit_606c50e5746043d4a61c30a4d16f999e_large.jpg", },
    { id: 2, title: "Top 5 Nước Hoa...", date: "01/10", category: "Hyperfume", image: "https://file.hstatic.net/1000339918/article/251._danh-gia-chi-tiet-dong-nuoc-hoa-givenchy-l-interdit_606c50e5746043d4a61c30a4d16f999e_large.jpg" },
    { id: 3, title: "Nước Hoa Văn Phòng...", date: "01/10", category: "Hyperfume", image: "https://file.hstatic.net/1000339918/article/251._danh-gia-chi-tiet-dong-nuoc-hoa-givenchy-l-interdit_606c50e5746043d4a61c30a4d16f999e_large.jpg" },
    { id: 4, title: "5 Hương Nước Hoa...", date: "01/10", category: "Hyperfume", image: "https://file.hstatic.net/1000339918/article/251._danh-gia-chi-tiet-dong-nuoc-hoa-givenchy-l-interdit_606c50e5746043d4a61c30a4d16f999e_large.jpg" },
    { id: 5, title: "Top 5 Nước Hoa...", date: "01/10", category: "Hyperfume", image: "https://file.hstatic.net/1000339918/article/251._danh-gia-chi-tiet-dong-nuoc-hoa-givenchy-l-interdit_606c50e5746043d4a61c30a4d16f999e_large.jpg" },
    { id: 6, title: "Nước Hoa Văn Phòng...", date: "01/10", category: "Hyperfume", image: "https://file.hstatic.net/1000339918/article/251._danh-gia-chi-tiet-dong-nuoc-hoa-givenchy-l-interdit_606c50e5746043d4a61c30a4d16f999e_large.jpg" },
    { id: 7, title: "5 Hương Nước Hoa...", date: "01/10", category: "Hyperfume", image: "https://file.hstatic.net/1000339918/article/251._danh-gia-chi-tiet-dong-nuoc-hoa-givenchy-l-interdit_606c50e5746043d4a61c30a4d16f999e_large.jpg" },,
    { id: 8, title: "Top 5 Nước Hoa...", date: "01/10", category: "Hyperfume", image: "https://file.hstatic.net/1000339918/article/251._danh-gia-chi-tiet-dong-nuoc-hoa-givenchy-l-interdit_606c50e5746043d4a61c30a4d16f999e_large.jpg" },
    { id: 9, title: "Nước Hoa Văn Phòng...", date: "01/10", category: "Hyperfume", image: "https://file.hstatic.net/1000339918/article/251._danh-gia-chi-tiet-dong-nuoc-hoa-givenchy-l-interdit_606c50e5746043d4a61c30a4d16f999e_large.jpg" },
    { id: 10, title: "5 Hương Nước Hoa...", date: "01/10", category: "Hyperfume", image: "https://file.hstatic.net/1000339918/article/251._danh-gia-chi-tiet-dong-nuoc-hoa-givenchy-l-interdit_606c50e5746043d4a61c30a4d16f999e_large.jpg" },
    { id: 11, title: "Top 5 Nước Hoa...", date: "01/10", category: "Hyperfume", image: "https://file.hstatic.net/1000339918/article/251._danh-gia-chi-tiet-dong-nuoc-hoa-givenchy-l-interdit_606c50e5746043d4a61c30a4d16f999e_large.jpg" },
    { id: 12, title: "Nước Hoa Văn Phòng...", date: "01/10", category: "Hyperfume", image: "https://file.hstatic.net/1000339918/article/251._danh-gia-chi-tiet-dong-nuoc-hoa-givenchy-l-interdit_606c50e5746043d4a61c30a4d16f999e_large.jpg" },
  ];

  const [currentPage, setCurrentPage] = useState(1);
  const [selectedPost, setSelectedPost] = useState(null); // State lưu bài viết chi tiết
  const postsPerPage = 6;

  const startIndex = (currentPage - 1) * postsPerPage;
  const selectedBlogs = blogData.slice(startIndex, startIndex + postsPerPage);

  // Xử lý khi click vào bài viết
  const handlePostClick = (post) => {
    setSelectedPost(post); // Gán bài viết được chọn
  };

  // Quay lại danh sách bài viết
  const handleBackToList = () => {
    setSelectedPost(null); // Xóa bài viết đang chọn
  };

  return (
    <div className="blog-page">
      <div className="banner">
        <h1 className="banner-title">BLOG</h1>
      </div>

      {/* Kiểm tra state selectedPost */}
      {selectedPost ? (
        // Hiển thị trang chi tiết bài viết
        <div className="blog-detail">
          <button onClick={handleBackToList} className="back-button">
            Quay lại
          </button>
          <h2 className="detail-title">{selectedPost.title}</h2>
          <p className="detail-date">{selectedPost.date}</p>
          <p className="detail-category">{selectedPost.category}</p>
          <img src={selectedPost.image} alt={selectedPost.title} className="detail-image" />
          <p className="detail-content">{selectedPost.content}</p>
        </div>
      ) : (
        // Hiển thị danh sách blog
        <>
          <div className="blog-list">
            {selectedBlogs.map((blog) => (
              <div key={blog.id} onClick={() => handlePostClick(blog)} style={{ cursor: "pointer" }}>
                <BlogCard
                  title={blog.title}
                  date={blog.date}
                  category={blog.category}
                  image={blog.image}
                />
              </div>
            ))}
          </div>
          <Pagination
            totalPages={Math.ceil(blogData.length / postsPerPage)}
            currentPage={currentPage}
            onPageChange={(page) => setCurrentPage(page)}
          />
        </>
      )}
    </div>
  );
};

export default BlogPage;
