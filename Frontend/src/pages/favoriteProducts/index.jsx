import { memo, useEffect, useState } from "react";
import "./favoriteProducts.scss";
import ProductCard from "../../components/productCard";
import { listFavorites } from "../../services/handleFavorite";
import Pagination from "../Pagination";

const FavoriteProducts = () => {
  const [favorites, setFavorites] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  // Pagination state
  const [currentPage, setCurrentPage] = useState(1);
  const [productsPerPage] = useState(8); // Số sản phẩm mỗi trang

  useEffect(() => {
    const fetchFavorites = async () => {
      setLoading(true);
      try {
        const response = await listFavorites();
        if (response && response.data) {
          setFavorites(response.data.result || []);
        }
        setError(null);
      } catch (err) {
        setError("Không thể tải danh sách sản phẩm yêu thích. Vui lòng thử lại sau.");
        console.error("Error fetching favorites:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchFavorites();
  }, []);

  // Get current products for pagination
  const indexOfLastProduct = currentPage * productsPerPage;
  const indexOfFirstProduct = indexOfLastProduct - productsPerPage;
  const currentProducts = favorites.slice(indexOfFirstProduct, indexOfLastProduct);
  
  // Change page
  const paginate = (pageNumber) => {
    setCurrentPage(pageNumber);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  if (loading) {
    return <div className="favorites-loading">Đang tải sản phẩm yêu thích...</div>;
  }

  if (error) {
    return <div className="favorites-error">{error}</div>;
  }

  if (favorites.length === 0) {
    return <div className="favorites-empty">Bạn chưa có sản phẩm yêu thích nào.</div>;
  }

  return (
    <div className="favorites-container">
      <h2 className="favorites-title">Sản phẩm yêu thích</h2>
      
      <div className="favorites-info">
        <p>Tổng số: <strong>{favorites.length}</strong> sản phẩm</p>
      </div>
      
      <div className="favorites-grid">
        {currentProducts.map((product) => (
          <ProductCard
            key={product.id}
            id={product.id}
            img={product.thumbnailImageUrl || product.img}
            brandName={product.brandName}
            name={product.name}
            price1={product.min_price || product.price1}
            price2={product.max_price || product.price2}
            isFavorite={true}
          />
        ))}
      </div>
      
      {favorites.length > productsPerPage && (
        <Pagination 
          productsPerPage={productsPerPage}
          totalProducts={favorites.length}
          paginate={paginate}
          currentPage={currentPage}
        />
      )}
    </div>
  );
};

export default memo(FavoriteProducts);