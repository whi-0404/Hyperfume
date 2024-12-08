import React, { useState } from 'react';  
import Header from '../components/Header';  
import BlogCard from '../components/BlogCard';  
import Pagination from '../components/Pagination';  
import './BlogPage.css';  

const BlogPage = () => {  
  const [currentPage, setCurrentPage] = useState(1);  
  const blogsPerPage = 6;  

  const blogs = [  
    { id: 1, image: 'image1.jpg', title: '5 Hương Nước Hoa...', date: '07/10', category: 'Hyperfume' },  
    { id: 2, image: 'image2.jpg', title: 'Top 5 Nước Hoa...', date: '07/10', category: 'Hyperfume' },  
    // Add more blog data here...  
  ];  

  const totalPages = Math.ceil(blogs.length / blogsPerPage);  
  const displayedBlogs = blogs.slice((currentPage - 1) * blogsPerPage, currentPage * blogsPerPage);  

  return (  
    <div className="blog-page">  
      <Header />  
      <div className="blog-list">  
        {displayedBlogs.map((blog) => (  
          <BlogCard  
            key={blog.id}  
            image={blog.image}  
            title={blog.title}  
            date={blog.date}  
            category={blog.category}  
          />  
        ))}  
      </div>  
      <Pagination  
        currentPage={currentPage}  
        totalPages={totalPages}  
        onPageChange={setCurrentPage}  
      />  
    </div>  
  );  
};  

export default BlogPage;  