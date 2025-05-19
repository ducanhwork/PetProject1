// WebSocketComponent.jsx
import React, { useEffect, useRef, useState } from 'react';
import SockJS from 'sockjs-client';
import { CompatClient, Stomp } from '@stomp/stompjs';

const WebSocketComponent = () => {
  const [messages, setMessages] = useState([]);
  const stompClientRef = useRef(null);

  useEffect(() => {
    const socket = new SockJS('http://localhost:8080/ws'); // Your Spring endpoint
    const stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
      console.log('WebSocket connected');
      stompClient.subscribe('/topic/updates', (message) => {
        if (message.body) {
          setMessages(prev => [...prev, JSON.parse(message.body)]);
        }
      });
    });

    stompClientRef.current = stompClient;

    return () => {
      if (stompClientRef.current) {
        stompClientRef.current.disconnect(() => {
          console.log('WebSocket disconnected');
        });
      }
    };
  }, []);

  const sendMessage = () => {
    const payload = { text: "Hello from React!" };
    stompClientRef.current?.send("/app/send", {}, JSON.stringify(payload));
  };

  return (
    <div className="p-4">
      <button onClick={sendMessage} className="mb-4 p-2 bg-blue-500 text-white rounded">
        Send Message
      </button>
      <ul>
        {messages.map((msg, index) => (
          <li key={index} className="mb-2 border p-2 rounded">{msg.text}</li>
        ))}
      </ul>
    </div>
  );
};

export default WebSocketComponent;
