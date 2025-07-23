import instance from "../../utils/axiosCustomize";
import noAuthInstance from "../../utils/noAuthAxiosCustomize";
export const listUsers = (token) => {
  return instance(token).get("/users/getAll");
};
export const createUser = (form) => {
  return noAuthInstance.post("/users/create", form);
};
export const getUserById = (id, token) => {
  return instance(token).get("/users/get/" + id);
};
export const updateUser = (id, user, token) => {
  return instance(token).put("/users/update/" + id, user);
};
export const deleteUser = (id, token) => {
  return instance(token).delete("/users/delete/" + id);
};
export const getMyProfile = (token) => {
  return instance(token).get("/users/myProfile");
};
export const activateUser = (id, token) => {
  return instance(token).put("/users/activate/" + id);
};
