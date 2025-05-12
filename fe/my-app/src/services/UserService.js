import axios from "axios";
const REST_API_BASE_URL = "http://localhost:8080";
const ACCESS_TOKEN = "Bearer " + localStorage.getItem("access_token") || null;
const instance = axios.create({
  baseURL: REST_API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
    Authorization: ACCESS_TOKEN,
  },
});
export const listUsers = () => {
  return instance.get("/users/getAll");
};
