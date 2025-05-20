import React, { useEffect } from "react";
import { getMyProfile } from "../../services/users/UserService";
import { ToastContainer, toast } from "react-toastify";
const UserProfileComponent = () => {
  const [user, setUser] = React.useState(null);
  useEffect(() => {
    getMyProfile()
      .then((response) => {
        console.log(response.data.data);
        setUser(response.data.data);
      })
      .catch((error) => {
        console.error("Error fetching user profile:", error);
        toast.error("Failed to fetch user profile");
      });
  }, []);
  return (
    <div>
      <ToastContainer />
      <h1>User Profile</h1>
      {user ? (
        <div>
          <p>Username: {user.username}</p>
          <p>Email: {user.email}</p>
          <p>Status: {user.isActive ? "Active" : "Inactive"}</p>
          <p>Role : {user.roles}</p>
        </div>
      ) : (
        <p>Loading...</p>
      )}
    </div>
  );
};

export default UserProfileComponent;
