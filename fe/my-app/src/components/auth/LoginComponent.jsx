import React, {useState} from 'react'
import { login } from '../../services/auth/AuthService';
import { useNavigate } from 'react-router-dom';
const LoginComponent = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };
    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    }
    const handleRegister = () => {
    // Chuyển hướng đến trang đăng ký
    window.location.href = '/register';
    };

    const handleForgotPassword = () => {
    // Chuyển hướng đến trang quên mật khẩu
    window.location.href = '/forgot-password';
    };
    const navigate = useNavigate();
    const handleLogin = (event) => {
        event.preventDefault();
       
        login(username, password)
            .then((response) => {
                console.log(response.data);
                // Handle successful login (e.g., store token, redirect, etc.)
                const token = response.data.data.token; // Adjust based on your API response
                console.log("Token:", token);
                // Store the token in a cookie or local storage
                if(localStorage.getItem("access_token") !== null){
                    localStorage.removeItem("access_token");
                }
                localStorage.setItem("access_token", token);
                navigate("/users");

            })
            .catch((error) => {
                console.error("Login failed:", error);
                // Handle login failure (e.g., show error message)
                alert("Login failed. Please check your username and password.");
            });
    }
    return (
 <div className='container mt-5 w-50'>
      <form onSubmit={handleLogin}>
        <div className='form-group mb-2'>
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
        <div className='form-group mb-2'>
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
        <div className='d-grid gap-2'>
          <button type="submit" className="btn btn-primary">Login</button>
          <button type="button" className="btn btn-secondary" onClick={handleRegister}>
            Đăng ký
          </button>
          <button type="button" className="btn btn-link" onClick={handleForgotPassword}>
            Quên mật khẩu?
          </button>
        </div>
      </form>
    </div>
      );
}

export default LoginComponent