import React from "react";
import { logout } from "../../services/auth/AuthService";
import { useNavigate } from "react-router-dom";
const HeaderComponent = (props) => {
  const { isLoggedIn, setIsLoggedIn } = props;
  const navigate = useNavigate();
  const handleLogout = () => {
    logout()
      .then((response) => {
        console.log(response.data.data);
        setIsLoggedIn(false);
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
          <a href="home" className="navbar-brand mx-3">
            PDANH System
          </a>
          {isLoggedIn ? (
            <a
              href=""
              onClick={handleLogout}
              className="navbar-brand float-end"
            >
              Log out
            </a>
          ) : (
            <div>
              <a
                href="/login"
                className=" navbar-brand btn btn-info text-white text-center"
              >
                Login
              </a>
              <a
                href="/register"
                className="navbar-brand btn btn-dark text-white"
              >
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
