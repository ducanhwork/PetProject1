import "./App.css";
import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import ListUserComponent from "./components/admin/ListUserComponent";
import HeaderComponent from "./components/common/HeaderComponent";
import FooterComponent from "./components/common/FooterComponent";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginComponent from "./components/auth/LoginComponent";
import RegisterComponent from "./components/auth/RegisterComponent";
import WebSocketComponent from "./components/socket/WebSocketComponent";
import HomePageComponent from "./components/common/HomePageComponent";

const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  useEffect(() => {
    const token = localStorage.getItem("access_token");
    if (token) {
      setIsLoggedIn(true);
    }
  }, []);
  return (
    <>
      <BrowserRouter>
        <HeaderComponent
          isLoggedIn={isLoggedIn}
          setIsLoggedIn={setIsLoggedIn}
        />
        <Routes>
          <Route path="/" element={<ListUserComponent />} />
          <Route path="/socket" element={<WebSocketComponent />} />
          <Route path="/home" element={<HomePageComponent />} />
          <Route
            path="/login"
            element={<LoginComponent setIsLoggedIn={setIsLoggedIn} />}
          />
          <Route path="/users" element={<ListUserComponent />} />
          <Route path="/register" element={<RegisterComponent />} />
        </Routes>
        <FooterComponent />
      </BrowserRouter>
    </>
  );
};

export default App;
