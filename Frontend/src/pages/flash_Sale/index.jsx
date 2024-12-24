/* eslint-disable no-unused-vars */
import { memo, useState, useEffect } from "react";
import './style.scss';
import DiorSavage from "../../assets/product_img/dior_sauvage.png";
import saleoff10 from "../../assets/image/saleof10.png";
import { CiClock2 } from "react-icons/ci";
import Flashsaleimg from "../../assets/image/flashsale.png";


const FlashSale = () => {
    const [sortOption, setSortOption] = useState("latest");
    const [filters, setFilters] = useState({
        fragrance: '',
        brand: '',
        concentration: '',
        fragranceGroup: '',
        maxPrice: ''
    });
    const [currentPage, setCurrentPage] = useState(1);
    const productsPerPage = 12; // Show 12 products per page

    // tien viet dung dau phay
    const formatCurrency = (amount) => {
      return amount.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
    };


    const perfumes = [
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        
        {
            name: 'Dior Sauvage EDP', brand: 'Dior', price: 5100000,
            oldPrice: 5670000, // Old price for discount display
            quantitySold: 50, // Example quantity sold
            image: DiorSavage, link: '/dior', fragrance: 'Lâu - 7h đến 12h', concentration: 'Eau de Parfum', group: 'Hương phương đông'
        },        


        
    ];

    

    // Filtering function
    const filteredPerfumes = perfumes.filter(perfume => {
        return (
            (filters.fragrance ? perfume.fragrance === filters.fragrance : true) &&
            (filters.brand ? perfume.brand === filters.brand : true) &&
            (filters.concentration ? perfume.concentration === filters.concentration : true) &&
            (filters.fragranceGroup ? perfume.group === filters.fragranceGroup : true) &&
            (filters.maxPrice ? perfume.price <= parseFloat(filters.maxPrice) : true) // Price filter
        );
    });

    const sortedPerfumes = () => {
        switch (sortOption) {
            case "bestSelling":
                return filteredPerfumes; // Implement best selling logic here
            case "latest":
                return filteredPerfumes; // Implement latest logic here
            case "highToLow":
                return [...filteredPerfumes].sort((a, b) => b.price - a.price);
            case "lowToHigh":
                return [...filteredPerfumes].sort((a, b) => a.price - b.price);
            default:
                return filteredPerfumes;
        }
    };


    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };

    // Calculate the products to display for the current page
    const currentProducts = sortedPerfumes();
    const indexOfLastProduct = currentPage * productsPerPage;
    const indexOfFirstProduct = indexOfLastProduct - productsPerPage;
    const displayedProducts = currentProducts.slice(indexOfFirstProduct, indexOfLastProduct);

    // Calculate total pages
    const totalPages = Math.ceil(currentProducts.length / productsPerPage);


    // countdown hh:mm:ss
    // Countdown state
    const [timeLeft, setTimeLeft] = useState(23 * 60 * 60); // 23 hours in seconds

    // Countdown timer effect
    useEffect(() => {
        const intervalId = setInterval(() => {
            setTimeLeft(prevTime => {
                if (prevTime <= 0) {
                    clearInterval(intervalId);
                    return 0; // Stop countdown at 0
                }
                return prevTime - 1; // Decrease time by 1 second
            });
        }, 1000);

        return () => clearInterval(intervalId); // Cleanup on unmount
    }, []);

    const formatTime = (seconds) => {
        const hours = String(Math.floor(seconds / 3600)).padStart(2, '0');
        const minutes = String(Math.floor((seconds % 3600) / 60)).padStart(2, '0');
        const secs = String(seconds % 60).padStart(2, '0');
        return `${hours}:${minutes}:${secs}`;
    };

    
    return (
        <div className="flash-sale">
            <div className="breadcrumb">
              <a href="/" className="breadcrumb-link">Trang chủ</a>
              <span className="arrow"> &gt; </span>
              <span className="current">Flashsale</span>
              <hr className="divider" />
            </div>

            <h1 className="product-title">
                Flash sale <span className="icon"><CiClock2 /></span> kết thúc trong <span className="countdown">{formatTime(timeLeft)}</span>
            </h1>

            <img className="poster-flashsale" src={Flashsaleimg} alt="Poster flashsale" />
            

        

            <div className="product-grid">
                {displayedProducts.map((perfume, index) => (
                    <div className="product-card" key={index}>
                        <a href={perfume.link} className="product-link">
                            <div className="image-container">
                                <img src={perfume.image} alt={perfume.name} className="product-image" />
                                <img src={saleoff10} alt="10% Off" className="discount-tag" />
                            </div>
                        </a>
                        <h2 className="product-name">{perfume.name}</h2>
                        <div className="price-container">
                            <span className="new-price">{formatCurrency(perfume.price)}</span>
                            <br />
                            <span className="old-price">{formatCurrency(perfume.oldPrice)}</span>
                        </div>
                        <div className="quantity-sold">
                            <div className="sold-bar" style={{ width: `${(perfume.quantitySold / 100) * 100}%` }}></div>
                        </div>
                        <p className="quantity-text">Đã bán {perfume.quantitySold}</p>
                    </div>
                ))}
            </div>


            {totalPages > 1 && (
                <div className="pagination">
                    {Array.from({ length: totalPages }, (_, index) => (
                        <button 
                            key={index} 
                            onClick={() => handlePageChange(index + 1)} 
                            disabled={currentPage === index + 1}
                            className={currentPage === index + 1 ? 'active' : ''}
                        >
                            {index + 1}
                        </button>
                    ))}
                </div>
            )}
        </div>
    );
};

export default memo(FlashSale);