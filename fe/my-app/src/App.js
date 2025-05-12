import "./App.css";
import { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import ListUserComponent from "./components/ListUserComponent";
import HeaderComponent from "./components/HeaderComponent";
import FooterComponent from "./components/FooterComponent";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginComponent from "./components/LoginComponent";

function App() {
  return (
    <>
      <BrowserRouter>
        <HeaderComponent />
        <Routes>
          <Route path="/" element={<ListUserComponent />} />
          <Route path="/home" element={<ListUserComponent />} />
          <Route path="/login" element={<LoginComponent />} />
          <Route path="/users" element={<ListUserComponent />} />
        </Routes>
        <FooterComponent />
      </BrowserRouter>
    </>
  );
}

export default App;
