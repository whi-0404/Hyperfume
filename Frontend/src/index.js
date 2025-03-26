import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import RouterCustom from './router';
import './styles/style.scss';
import { UserProvider } from './utils/userContext'; // Import UserProvider

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <>
    <UserProvider> {/* Bọc toàn bộ ứng dụng trong UserProvider */}
      <BrowserRouter basename="/">
        <RouterCustom></RouterCustom>
      </BrowserRouter>
    </UserProvider>
  </>
);