import noAuthInstance from "utils/noAuthAxiosCustomize";
import instance from "utils/axiosCustomize";
export const login = (username, password) => {
  return noAuthInstance.post("/auth/token", { username, password });
};
export const logout = () => {
  var accessToken = JSON.parse(localStorage.getItem("access_token"))?.value;
  return instance.post("/auth/logout", {
    token: accessToken,
  });
};
