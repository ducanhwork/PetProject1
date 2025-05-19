import React, { useState } from "react";
import { createUser } from "../../services/users/UserService";
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
const RegisterComponent = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };
  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };
  const handleEmailChange = (event) => {
    setEmail(event.target.value);
  };
  const handleSubmit = (event) => {
    event.preventDefault();
    // Perform registration logic here
    console.log("Username:", username);
    console.log("Password:", password);
    console.log("Email:", email);
    createUser(username, password, email)
      .then((response) => {
        console.log("Response: ", response.data);
        // Handle successful registration (e.g., redirect to login page)
        alert("Registration successful. Please login.");
        navigate("/login");
      })
      .catch((error) => {
        console.log("Registration failed:", error.response.data.message);
        let message = error.response.data.message;
        toast.error(message);
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
      <h1 className="text-center">Register</h1>
      <form onSubmit={handleSubmit}>
        <label htmlFor="username">Username:</label>
        <input
          className="form-control"
          type="text"
          id="username"
          value={username}
          onChange={handleUsernameChange}
        />
        <br />
        <label htmlFor="password">Password:</label>
        <input
          className="form-control"
          type="password"
          id="password"
          value={password}
          onChange={handlePasswordChange}
        />
        <br />
        <label htmlFor="email">Email:</label>
        <input
          className="form-control"
          type="email"
          id="email"
          value={email}
          onChange={handleEmailChange}
        />
        <button className="btn btn-primary mt-3" type="submit">
          Register
        </button>
      </form>
      <br />
      <p className="small mb-3 pb-lg-2">
        <a className="text-black-50" href="login">
          Already have an account? Login
        </a>
      </p>
    </div>
  );
};

export default RegisterComponent;
