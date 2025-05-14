import React, { useCallback } from 'react';
import useWebSocketNotifications from '../../hooks/useWebSocketNotifications';
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const NotificationListener = () => {
  const handleNotification = useCallback((notification) => {
    // notification là object NotificationResponse gửi trực tiếp từ server
    if (notification && notification.title && notification.content) {
      toast.info(
        <div>
          <strong>{notification.title}</strong>
          <div>{notification.content}</div>
          <small>{new Date(notification.createdAt).toLocaleString()}</small>
        </div>,
        {
          position: "top-right",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        }
      );
    }
  }, []);

  useWebSocketNotifications(handleNotification);

  return <ToastContainer />;
};

export default NotificationListener; 