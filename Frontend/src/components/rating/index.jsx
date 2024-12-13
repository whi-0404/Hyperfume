import React, { memo } from "react";
import { AiFillStar, AiOutlineStar } from "react-icons/ai";
import "./style.scss";

const ProductRating = ({ rating, totalReviews }) => {
  const maxStars = 5;

  return (
    <div className="rating">
      {Array.from({ length: maxStars }, (_, index) => (
        index < rating ? (
          <AiFillStar key={index} className="star filled" />
        ) : (
          <AiOutlineStar key={index} className="star" />
        )
      ))}
      <span className="review-text">{totalReviews} đánh giá</span>
    </div>
  );
};

export default memo(ProductRating);