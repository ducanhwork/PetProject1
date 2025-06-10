import React, { useState } from "react";
import { login } from "../../services/auth/AuthService";
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import { setWithExpiry } from "utils/localStorageUtil";
const LoginComponent = (props) => {
  const { setIsLoggedIn, setUserAvatar } = props;
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };
  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };
  const handleRegister = () => {
    // Chuyển hướng đến trang đăng ký
    window.location.href = "/register";
  };

  const handleForgotPassword = () => {
    // Chuyển hướng đến trang quên mật khẩu
    window.location.href = "/forgot-password";
  };
  const navigate = useNavigate();
  const handleLogin = (event) => {
    event.preventDefault();

    login(username, password)
      .then((response) => {
        console.log(response.data);
        // Handle successful login (e.g., store token, redirect, etc.)
        const token = response.data.token; // Adjust based on your API response
        console.log("Token:", token);
        // Store the token in a cookie or local storage
        if (localStorage.getItem("access_token") !== null) {
          localStorage.removeItem("access_token");
        }
        setWithExpiry("access_token", token, 60); // Store token with 60 minutes expiry
        console.log(response.data.userResponse);
        // Optionally, set user avatar or other user data
        if (
          response.data.userResponse &&
          response.data.userResponse.avatarUrl
        ) {
          setUserAvatar(response.data.userResponse.avatarUrl);
        } else {
          console.log(">>>>>>>", response.data.userResponse);

          setUserAvatar(null); // Set to null if no avatar is provided
        }
        setIsLoggedIn(true);
        navigate("/users");
      })
      .catch((error) => {
        console.error("Login failed:", error);
        // Handle error (e.g., show error message)
        toast.error("Login failed. Please check your username and password.");
        // Handle login failure (e.g., show error message)
      });
  };
  return (
    <div
      className="container w-25 card shadow"
      style={{
        marginTop: "10%",
        padding: "20px",
      }}
    >
      <ToastContainer />
      <form onSubmit={handleLogin}>
        <div className="form-group mb-2">
          <h1 className="text-center text-info">Sign in</h1>
          <label htmlFor="username">Username</label>
          <input
            type="text"
            className="form-control"
            id="username"
            value={username}
            onChange={handleUsernameChange}
            required
          />
        </div>
        <br />
        <div className="form-group mb-2">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            className="form-control"
            id="password"
            value={password}
            onChange={handlePasswordChange}
            required
          />
        </div>
        <div className="d-grid gap-2">
          <button type="submit" className="btn btn-primary">
            Login
          </button>
          <button
            type="button"
            className="btn btn-secondary"
            onClick={handleRegister}
          >
            Đăng ký
          </button>
          <button
            type="button"
            className="btn btn-link"
            onClick={handleForgotPassword}
          >
            Quên mật khẩu?
          </button>
        </div>
      </form>
    </div>
  );
};

export default LoginComponent;
