import { useEffect } from 'react';
import webSocketService from '../services/WebSocketService';
import { useUser } from '../utils/userContext';

const useWebSocketNotifications = (onNotification) => {
  const { user } = useUser();

  useEffect(() => {
    if (!user) return;

    // Kết nối websocket khi có user
    webSocketService.setNotificationHandler(onNotification);
    webSocketService.connect();

    // Cleanup khi unmount hoặc user thay đổi
    return () => {
      webSocketService.disconnect();
    };
  }, [user, onNotification]);
};

export default useWebSocketNotifications; 