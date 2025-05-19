import React from "react";

const SidebarComponent = () => {
  return (
    <div
      className="bg-dark text-white vh-100 p-3 mt-5"
      style={{ width: "250px", position: "fixed" }}
    >
      <h4 className="text-center mb-4">ADMIN Dashboard</h4>
      <ul className="nav flex-column">
        <li className="nav-item mb-2">
          <a href="/dashboard" className="nav-link text-white">
            📊 Dashboard
          </a>
        </li>
        <li className="nav-item mb-2">
          <a href="/users" className="nav-link text-white">
            👥 Quản lý người dùng
          </a>
        </li>
      </ul>
    </div>
  );
};

export default SidebarComponent;
