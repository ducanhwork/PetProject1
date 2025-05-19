import axios from "axios";
const REST_API_BASE_URL = "http://localhost:8080";

export const listUsers = () => {
  const instance = axios.create({
    baseURL: REST_API_BASE_URL,
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("access_token")}`,
    },
  });
  return instance.get("/users/getAll");
};
export const createUser = (username, password, email) => {
  return axios.post(REST_API_BASE_URL + "/users/create", {
    username: username,
    password: password,
    email: email,
  });
};
