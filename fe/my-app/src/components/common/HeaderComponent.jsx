import React, { useContext, useState } from "react";
import { logout } from "../../services/auth/AuthService";
import { Link, useNavigate } from "react-router-dom";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faUser,
  faRightFromBracket,
  faCircleUser,
  faRightToBracket,
  faUserPlus,
} from "@fortawesome/free-solid-svg-icons";
import useAuth from "../../hooks/useAuth";

const HeaderComponent = (props) => {
  const navigate = useNavigate();
  const [menuOpen, setMenuOpen] = useState(false);
  const { auth, setAuth } = useAuth();
  const avatarUrl = auth?.userResponse?.avatarUrl;
  const handleLogout = () => {
    logout(auth?.token)
      .then((response) => {
        console.log(response.data);
        setAuth({});
        localStorage.removeItem("access_token");
        localStorage.removeItem("user");
        navigate("/login");
      })
      .catch((error) => {
        console.log(error);
      });
  };
  const handleProfileClick = () => {
    navigate("/profile");
  };
  const toggleMenu = () => {
    setMenuOpen(!menuOpen);
  };

  return (
    <div>
      <header className="header fixed-top">
        <nav className="navbar navbar-light bg-light justify-content-between px-3">
          <a href="/home" className="navbar-brand">
            EduKationCentor
          </a>

          {auth?.userResponse ? (
            <div className="position-relative">
              <FontAwesomeIcon
                icon={faCircleUser}
                size="2x"
                className="text-primary"
                style={{ cursor: "pointer" }}
                onClick={toggleMenu}
              />
              {menuOpen && (
                <ul
                  className="position-absolute bg-white shadow p-2 rounded"
                  style={{
                    top: "45px",
                    right: "0",
                    minWidth: "180px",
                    listStyleType: "none",
                    zIndex: "1000",
                  }}
                >
                  <li className="nav-item my-1">
                    <Link
                      to={"/profile"}
                      className="nav-link d-flex align-items-center"
                    >
                      {avatarUrl ? (
                        <img
                          src={avatarUrl}
                          alt="User Avatar"
                          className="rounded-circle me-2"
                          style={{ width: "30px", height: "30px" }}
                        />
                      ) : (
                        <FontAwesomeIcon
                          icon={faUser}
                          className="me-2 text-secondary"
                        />
                      )}
                      My Profile
                    </Link>
                  </li>
                  <li className="nav-item my-1">
                    <a
                      className="nav-link d-flex align-items-center"
                      onClick={handleLogout}
                    >
                      <FontAwesomeIcon
                        icon={faRightFromBracket}
                        className="me-2 text-danger"
                      />
                      Logout
                    </a>
                  </li>
                </ul>
              )}
            </div>
          ) : (
            <div className=" d-flex justify-content-end">
              <a
                href="/login"
                className="btn btn-info text-white mx-2 d-flex align-items-center"
              >
                <FontAwesomeIcon icon={faRightToBracket} className="me-2" />
                Login
              </a>
              <a
                href="/register"
                className="btn btn-dark text-white d-flex align-items-center"
              >
                <FontAwesomeIcon icon={faUserPlus} className="me-2" />
                Register
              </a>
            </div>
          )}
        </nav>
      </header>
    </div>
  );
};

export default HeaderComponent;
