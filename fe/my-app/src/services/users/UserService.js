import instance from "../../utils/axiosCustomize";
import noAuthInstance from "../../utils/noAuthAxiosCustomize";
export const listUsers = () => {
  return instance.get("/users/getAll");
};
export const createUser = (form) => {
  return noAuthInstance.post("/users/create", form);
};
export const getUserById = (id) => {
  return instance.get("/users/get/" + id);
};
export const updateUser = (id, user) => {
  return instance.put("/users/update/" + id, user);
};
export const deleteUser = (id) => {
  return instance.delete("/users/delete/" + id);
};
export const getMyProfile = () => {
  return instance.get("/users/myProfile");
};
export const activateUser = (id) => {
  return instance.put("/users/activate/" + id);
};
