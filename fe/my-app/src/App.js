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
import UserProfileComponent from "./components/user/UserProfileComponent";
import TeacherHomePageComponent from "./components/teacher/TeacherHomePageComponent";
import StudentHomePageComponent from "./components/student/StudentHomePageComponent";
import { getWithExpiry } from "utils/localStorageUtil";

const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [userAvatar, setUserAvatar] = useState(null); // Placeholder for user avatar
  useEffect(() => {
    getWithExpiry("access_token") ? setIsLoggedIn(true) : setIsLoggedIn(false);
  }, []);
  return (
    <>
      <BrowserRouter>
        <HeaderComponent
          userAvatar={userAvatar} // Replace with actual user avatar if available
          isLoggedIn={isLoggedIn}
          setIsLoggedIn={setIsLoggedIn}
        />
        <Routes>
          <Route path="/" element={<HomePageComponent />} />
          <Route path="/socket" element={<WebSocketComponent />} />
          <Route path="/home" element={<HomePageComponent />} />
          <Route path="/profile" element={<UserProfileComponent />} />
          <Route path="/teacher/home" element={<TeacherHomePageComponent />} />
          <Route path="/teacher" element={<TeacherHomePageComponent />} />
          <Route path="/student/home" element={<StudentHomePageComponent />} />
          <Route path="/student" element={<StudentHomePageComponent />} />
          <Route
            path="/login"
            element={
              <LoginComponent
                setUserAvatar={setUserAvatar}
                setIsLoggedIn={setIsLoggedIn}
              />
            }
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
