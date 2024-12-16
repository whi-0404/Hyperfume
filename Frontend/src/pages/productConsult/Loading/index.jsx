import React from "react";
import "./style.scss"

const Loading = ({ isLoading }) => {
  return (
    <div>
      {isLoading && (
        <div className="loading-container">
          <div className="spinner" role="status">
            <span className="visually-hidden">Loading...</span>
          </div>
        </div>
      )}
    </div>
  );
};

export default Loading;
