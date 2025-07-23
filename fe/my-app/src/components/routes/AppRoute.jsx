import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import HeaderComponent from "../common/HeaderComponent";
import HomePageComponent from "../common/HomePageComponent";
import UserProfileComponent from "../user/UserProfileComponent";
import TeacherHomePageComponent from "../teacher/TeacherHomePageComponent";
import StudentHomePageComponent from "../student/StudentHomePageComponent";
import LoginComponent from "../auth/LoginComponent";
import ListUserComponent from "../admin/ListUserComponent";
import RegisterComponent from "../auth/RegisterComponent";
import FooterComponent from "../common/FooterComponent";
import RequiredAuth from "../common/RequiredAuth";
const AppRoute = () => {
  return (
    <>
      <BrowserRouter>
        <HeaderComponent />
        <Routes>
          {/* Public Routes */}
          <Route path="/" element={<HomePageComponent />} />
          <Route path="/home" element={<HomePageComponent />} />
          <Route path="/login" element={<LoginComponent />} />
          <Route path="/register" element={<RegisterComponent />} />

          {/* User Profile Route */}
          <Route
            element={
              <RequiredAuth acceptRole={["STUDENT", "ADMIN", "TEACHER"]} />
            }
          >
            <Route path="/profile" element={<UserProfileComponent />} />
          </Route>

          {/* Protected Routes with Role ADMIN */}
          <Route element={<RequiredAuth acceptRole={["ADMIN"]} />}>
            <Route path="/users" element={<ListUserComponent />} />
          </Route>
          {/* Protected Routes with Role TEACHER */}
          <Route element={<RequiredAuth acceptRole={["TEACHER"]} />}>
            <Route
              path="/teacher/home"
              element={<TeacherHomePageComponent />}
            />
            <Route path="/teacher" element={<TeacherHomePageComponent />} />
          </Route>
          {/* Protected Routes with Role STUDENT */}
          <Route element={<RequiredAuth acceptRole={["STUDENT"]} />}>
            <Route
              path="/student/home"
              element={<StudentHomePageComponent />}
            />
            <Route path="/student" element={<StudentHomePageComponent />} />
          </Route>
        </Routes>
        <FooterComponent />
      </BrowserRouter>
    </>
  );
};

export default AppRoute;
