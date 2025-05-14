import React, { useState, useRef, useEffect, useCallback } from "react";
import { FaBell } from "react-icons/fa";
import useWebSocketNotifications from "../../../hooks/useWebSocketNotifications";
import { toast } from "react-toastify";
import "./style.scss";

const NotificationBell = () => {
  const [notifications, setNotifications] = useState([]);
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const bellRef = useRef(null);

  // Helper to truncate content
  const truncate = (str, n) => (str && str.length > n ? str.slice(0, n) + "..." : str);

  // Add new notification to the top and show toast
  const handleNotification = useCallback((notification) => {
    setNotifications((prev) => [{ ...notification, isRead: false }, ...prev]);
    if (notification && notification.title && notification.content) {
      toast.info(
        <div>
          <strong>{notification.title}</strong>
          <div>{truncate(notification.content, 50)}</div>
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

  // Mark all as read when dropdown opens
  useEffect(() => {
    if (dropdownOpen) {
      setNotifications((prev) => prev.map((n) => ({ ...n, isRead: true })));
    }
  }, [dropdownOpen]);

  // Close dropdown on outside click
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (bellRef.current && !bellRef.current.contains(event.target)) {
        setDropdownOpen(false);
      }
    };
    document.addEventListener("mousedown", handleClickOutside);
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  const unreadCount = notifications.filter((n) => !n.isRead).length;

  return (
    <div className="notification-bell" ref={bellRef}>
      <div className="bell-icon" onClick={() => setDropdownOpen((open) => !open)}>
        <FaBell size={22} />
        {unreadCount > 0 && <span className="notification-badge">{unreadCount}</span>}
      </div>
      {dropdownOpen && (
        <div className="notification-dropdown">
          <div className="dropdown-title">Thông báo</div>
          {notifications.length === 0 ? (
            <div className="no-notifications">Không có thông báo mới</div>
          ) : (
            <ul className="notification-list">
              {notifications.slice(0, 10).map((n, idx) => (
                <li key={idx} className={n.isRead ? "read" : "unread"}>
                  <div className="notification-title">{n.title}</div>
                  <div className="notification-content">{n.content}</div>
                  <div className="notification-time">{new Date(n.createdAt).toLocaleString()}</div>
                </li>
              ))}
            </ul>
          )}
        </div>
      )}
    </div>
  );
};

export default NotificationBell; 