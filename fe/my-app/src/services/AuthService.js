import axios from "axios";
const REST_API_BASE_URL = "http://localhost:8080";
const instance = axios.create({
  baseURL: REST_API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});
export const login = (username, password) => {
  return instance.post("/auth/token", { username, password });
};
