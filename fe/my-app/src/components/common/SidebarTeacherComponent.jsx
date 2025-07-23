import React from "react";
import { Link } from "react-router-dom";

const SidebarTeacherComponent = () => {
  return (
    <div
      className="bg-dark text-white vh-100 p-3 mt-5"
      style={{ width: "250px", position: "fixed" }}
    >
      <h4 className="text-center mb-4">TEACHER Dashboard</h4>
      <ul className="nav flex-column">
        <li className="nav-item mb-2">
          <Link to="/teacher" className="nav-link text-white">
            📊 Dashboard
          </Link>
        </li>
        <li className="nav-item mb-2">
          <Link to="/class" className="nav-link text-white">
            👥 Quản lý lớp học
          </Link>
        </li>
      </ul>
    </div>
  );
};

export default SidebarTeacherComponent;
