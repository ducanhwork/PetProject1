import React, { useState } from "react";
import { createUser } from "../../services/users/UserService";
import { useNavigate } from "react-router-dom";
import { ToastContainer, toast } from "react-toastify";
const RegisterComponent = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [dateOfBirth, setDateOfBirth] = useState("");
  const [address, setAddress] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [role, setRole] = useState("STUDENT");
  // Default role is ROLE_USER
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
    const form = require("form-data");
    const formData = new form();
    formData.append("username", username);
    formData.append("password", password);
    formData.append("email", email);
    formData.append("dateOfBirth", dateOfBirth);
    formData.append("address", address);
    formData.append("phoneNumber", phoneNumber);
    formData.append("role", role);
    // Call the createUser service to register the user
    createUser(formData)
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
      className="container w-50 card shadow"
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
        <br />
        <label htmlFor="dateOfBirth">Date of Birth:</label>
        <input
          className="form-control"
          type="date"
          id="dateOfBirth"
          value={dateOfBirth}
          onChange={(e) => setDateOfBirth(e.target.value)}
        />
        <br />
        <label htmlFor="address">Address:</label>
        <input
          className="form-control"
          type="text"
          id="address"
          value={address}
          onChange={(e) => setAddress(e.target.value)}
        />
        <br />
        <label htmlFor="phoneNumber">Phone Number:</label>
        <input
          className="form-control"
          type="text"
          id="phoneNumber"
          value={phoneNumber}
          onChange={(e) => setPhoneNumber(e.target.value)}
        />
        <br />
        <label htmlFor="role">You are:</label>
        <select
          className="form-select"
          id="role"
          value={role}
          onChange={(e) => setRole(e.target.value)}
        >
          <option value="STUDENT">Student</option>
          <option value="TEACHER">Teacher</option>
        </select>
        <br />
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
