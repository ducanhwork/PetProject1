import React from "react";
import SidebarTeacherComponent from "../common/SidebarTeacherComponent";
import { ToastContainer } from "react-toastify";

const TeacherHomePageComponent = () => {
  return (
    <div className="row">
      <div className="col-2">
        <SidebarTeacherComponent />
      </div>
      <div className="container col-10" style={{ marginTop: "70px" }}></div>
      <ToastContainer />
    </div>
  );
};

export default TeacherHomePageComponent;
