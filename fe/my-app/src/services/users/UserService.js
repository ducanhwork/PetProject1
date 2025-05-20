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
export const getUserById = (id) => {
  const instance = axios.create({
    baseURL: REST_API_BASE_URL,
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("access_token")}`,
    },
  });
  return instance.get("/users/get/" + id);
};
export const updateUser = (id, user) => {
  const instance = axios.create({
    baseURL: REST_API_BASE_URL,
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("access_token")}`,
    },
  });
  return instance.put("/users/update/" + id, user);
};
export const deleteUser = (id) => {
  const instance = axios.create({
    baseURL: REST_API_BASE_URL,
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("access_token")}`,
    },
  });
  return instance.delete("/users/delete/" + id);
};
export const getMyProfile = () => {
  const instance = axios.create({
    baseURL: REST_API_BASE_URL,
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("access_token")}`,
    },
  });
  return instance.get("/users/myProfile");
};
