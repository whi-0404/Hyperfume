import React from "react";
import BlogPage from "./pages/BlogPage";
import Header from "./components/Header";
import Footer from "./components/Footer";
import "./App.css";

function App() {
  return (
    <div className="App">
      <Header />
      <BlogPage />
      <Footer />
    </div>
  );
}

export default App;
