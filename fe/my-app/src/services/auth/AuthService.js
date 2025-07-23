import noAuthInstance from "utils/noAuthAxiosCustomize";
import instance from "utils/axiosCustomize";
export const login = (username, password) => {
  return noAuthInstance.post("/auth/token", { username, password });
};
export const logout = (token) => {
  return instance(token).post("/auth/logout", {
    token: token,
  });
};
