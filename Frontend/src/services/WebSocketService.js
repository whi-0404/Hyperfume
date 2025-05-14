import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import api from './axiosConfig';

class WebSocketService {
  constructor() {
    this.stompClient = null;
    this.subscriptions = new Map();
    this.connected = false;
    this.onNotificationReceived = null;
  }

  connect(onConnected, onError) {
    // Close existing connection if any
    this.disconnect();

    // Lấy baseURL từ axios config
    const baseURL = api.defaults.baseURL || 'http://localhost:8080/hyperfume';
    const socket = new SockJS(`${baseURL}/ws`, null, { withCredentials: true });
    
    this.stompClient = new Client({
      webSocketFactory: () => socket,
      connectHeaders: {},
      debug: function (str) {
        console.debug(str);
      },
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,
    });

    this.stompClient.onConnect = (frame) => {
      this.connected = true;
      console.log('Connected to WebSocket: ' + frame);
      
      if (onConnected) {
        onConnected(frame);
      }
      
      // Subscribe to user-specific notifications
      this.subscribeToUserNotifications();
    };

    this.stompClient.onStompError = (frame) => {
      console.error('WebSocket error: ' + frame.headers['message']);
      console.error('Additional details: ' + frame.body);
      
      if (onError) {
        onError(frame);
      }
    };

    this.stompClient.activate();
  }

  disconnect() {
    if (this.stompClient !== null && this.connected) {
      this.stompClient.deactivate();
      this.connected = false;
      console.log('Disconnected from WebSocket');
    }
  }

  subscribeToUserNotifications() {
    if (!this.connected) {
      console.warn('Cannot subscribe: WebSocket not connected');
      return null;
    }

    // Subscribe to user-specific notification topic
    const subscription = this.stompClient.subscribe('/user/topic/notifications', (message) => {
      try {
        const notification = JSON.parse(message.body);
        // Gọi callback với notification (chính là NotificationResponse)
        if (this.onNotificationReceived) {
          this.onNotificationReceived(notification);
        }
      } catch (error) {
        console.error('Error parsing notification:', error);
      }
    });

    this.subscriptions.set('userNotifications', subscription);
    return subscription;
  }

  // Set a callback function to handle incoming notifications
  setNotificationHandler(callback) {
    this.onNotificationReceived = callback;
  }

  // Check connection status
  isConnected() {
    return this.connected;
  }
}

// Create a singleton instance
const webSocketService = new WebSocketService();
export default webSocketService;