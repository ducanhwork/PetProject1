import "./App.css";
import { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import ListUserComponent from "./components/admin/ListUserComponent";
import HeaderComponent from "./components/common/HeaderComponent";
import FooterComponent from "./components/common/FooterComponent";
import SidebarComponent from "./components/common/SidebarComponent";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginComponent from "./components/auth/LoginComponent";
import RegisterComponent from "./components/auth/RegisterComponent";
import WebSocketComponent from "./components/socket/WebSocketComponent";

function App() {
  return (
    <>
      <BrowserRouter>
        <HeaderComponent />
        <Routes>
          <Route path="/" element={<ListUserComponent />} />
          <Route path="/socket" element={<WebSocketComponent />} />
          <Route path="/home" element={<ListUserComponent />} />
          <Route path="/login" element={<LoginComponent />} />
          <Route path="/users" element={<ListUserComponent />} />
          <Route path="/register" element={<RegisterComponent />} />
        </Routes>
        <FooterComponent />
      </BrowserRouter>
    </>
  );
}

export default App;
