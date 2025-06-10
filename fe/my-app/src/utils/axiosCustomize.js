import axios from "axios";
const accessToken = localStorage.getItem("access_token");
let token = "";
if (accessToken) {
  try {
    token = JSON.parse(accessToken).value || "";
  } catch (e) {
    token = "";
  }
}
const instance = axios.create({
  baseURL: "http://localhost:8080/", // Replace with your API base URL
  timeout: 10000, // Set a timeout for requests
  headers: {
    "Content-Type": "application/json",
    // Add any other headers you need here
    Accept: "application/json",
    Authorization: `Bearer ${token}`, // Example for token-based auth
  },
});

// Add a request interceptor
instance.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    const accessToken = localStorage.getItem("access_token");
    let token = "";
    if (accessToken) {
      try {
        token = JSON.parse(accessToken).value || "";
      } catch (e) {
        token = "";
      }
    }
    config.headers.Authorization = `Bearer ${token}`; // Update the token in headers
    return config;
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error);
  }
);

// Add a response interceptor
instance.interceptors.response.use(
  function (response) {
    console.log("Response received:", response);

    // Any status code that lie within the range of 2xx cause this function to trigger
    // Do something with response data
    return response && response.data ? response.data : response;
  },
  function (error) {
    // Any status codes that falls outside the range of 2xx cause this function to trigger
    // Do something with response error
    console.log("Error response received:", error.response);
    return error && error.response.data
      ? error.response.data
      : Promise.reject(error);
  }
);
export default instance;
