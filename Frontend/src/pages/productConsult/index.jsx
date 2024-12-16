import React, { memo, useState } from "react";
import { GoogleGenerativeAI } from "@google/generative-ai";
import "./style.scss";
import ChatHistory from "./ChatHistory";
import Loading from "./Loading";

const ProductConsult = () => {
  const [userInput, setUserInput] = useState("");
  const [chatHistory, setChatHistory] = useState([]);
  const [isLoading, setIsLoading] = useState(false);

  // inislize your Gemeni Api
  const genAI = new GoogleGenerativeAI(
    "AIzaSyCqWZNwESY7NDhmLDFGfl1fxOUHQcaMoXA"
  );
  const model = genAI.getGenerativeModel({ model: "gemini-1.5-flash" });

  // Function to handle user input
  const handleUserInput = (e) => {
    setUserInput(e.target.value);
  };

  // Function to send user message to Gemini
  const sendMessage = async () => {
    if (userInput.trim() === "") return;

    setIsLoading(true);
    try {
      // call Gemini Api to get a response
      const result = await model.generateContent(userInput);
      const response = await result.response;
      console.log(response);
      // add Gemeni's response to the chat history
      setChatHistory([
        ...chatHistory,
        { type: "user", message: userInput },
        { type: "bot", message: response.text() },
      ]);
    } catch {
      console.error("Error sending message");
    } finally {
      setUserInput("");
      setIsLoading(false);
    }
  };

  // Function to clear the chat history
  const clearChat = () => {
    setChatHistory([]);
  };

  return (
    <div className="container">
      <div className="chat-container">
        <h1 className="chat-header">HYPERFUME TƯ VẤN</h1>

        <div className="chat-display">
          <ChatHistory chatHistory={chatHistory} />
          <Loading isLoading={isLoading} />
        </div>

        <div className="chat-input-container">
          <input
            type="text"
            className="chat-input"
            placeholder="Nhập chữ ở đây"
            value={userInput}
            onChange={handleUserInput}
          />
          <button
            className="send-button"
            onClick={sendMessage}
            disabled={isLoading}
          >
            Gửi
          </button>
        </div>
        <button className="clear-button" onClick={clearChat}>
          Xóa cuộc trò chuyện
        </button>
      </div>
    </div>
  );
};

export default memo(ProductConsult);
