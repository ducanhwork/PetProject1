import React from "react";
import { logout } from "../../services/auth/AuthService";
import { useNavigate } from "react-router-dom";
const HeaderComponent = () => {
  const navigate = useNavigate();
  const handleLogout = () => {
    logout()
      .then((response) => {
        console.log(response.data.data);
        navigate("/login");
      })
      .catch((error) => {
        console.log(error);
      });
    localStorage.removeItem("access_token");
  };
  return (
    <div>
      <header className="header fixed-top">
        <nav className="navbar navbar-light bg-light">
          <a href="#" className="navbar-brand mx-3">
            PDANH System
          </a>
          {localStorage.getItem("access_token") !== null && (
            <a
              href=""
              onClick={handleLogout}
              className="navbar-brand float-end"
            >
              Log out
            </a>
          )}
        </nav>
      </header>
    </div>
  );
};

export default HeaderComponent;
