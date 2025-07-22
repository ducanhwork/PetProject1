import React, { useContext, useEffect, useState } from "react";
import { login } from "../../services/auth/AuthService";
import { useNavigate, useLocation } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
import { AuthContext } from "../../context/AuthProvider";

const LoginComponent = (props) => {
  const { auth, setAuth } = useContext(AuthContext);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const location = useLocation();
  const from = location?.state?.from?.pathname || "/home";
  console.log("LoginComponent mounted, from:", from);

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };
  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };
  const handleRegister = () => {
    // Chuyển hướng đến trang đăng ký
    navigate("/register", { replace: true });
  };

  const handleForgotPassword = () => {
    // Chuyển hướng đến trang quên mật khẩu
    navigate("/forgot-password", { replace: true });
  };
  const handleLogin = async (event) => {
    event.preventDefault();
    try {
      const response = await login(username, password);
      console.log("Login successful:", response);
      setAuth(response.data);
      console.log(auth, "Auth context after login");

      console.log("Setting auth context with:", response.data.data);
    } catch (error) {
      console.error("Login failed:", error);
      if (error.response && error.response.status === 401) {
        toast.error("Tên đăng nhập hoặc mật khẩu không đúng");
      }
    }
  };
  useEffect(() => {
    if (from === "/login") {
      navigate("/home");
    } else {
      if (auth?.userResponse) {
        const roles = auth.userResponse.roles.map((role) => role.name);
        console.log("User roles:", roles);
        if (roles.includes("ADMIN")) {
          navigate("/users");
        } else if (roles.includes("TEACHER")) {
          navigate("/teacher/home");
        } else if (roles.includes("STUDENT")) {
          navigate("/student/home");
        } else {
          navigate(from); // fallback về from nếu có
        }
      }
    }
  }, [auth?.userResponse, navigate, from]);

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
