import React, { useState } from "react";
  const startIndex = (currentPage - 1) * postsPerPage;
  const currentBlogs = blogs.slice(startIndex, startIndex + postsPerPage);

  const BlogPage = () => {
  return (
    <div className="blog-page">
      <Header />
      <h2>Blog Posts</h2>
      <div className="blog-list">
        {currentBlogs.map((blog, index) => (
          <BlogCard
            key={index}
            title={blog.title}
            description={blog.description}
            image={blog.image}
          />
        ))}
      </div>
      <Pagination
        currentPage={currentPage}
        totalPages={totalPages}
        onPageChange={handlePageChange}
      />
    </div>
  );
};

export default BlogPage;