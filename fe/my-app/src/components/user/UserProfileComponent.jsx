import React, { useEffect } from "react";
import { getMyProfile } from "../../services/users/UserService";
import { ToastContainer, toast } from "react-toastify";
import useAuth from "../../hooks/useAuth";
const UserProfileComponent = () => {
  const [user, setUser] = React.useState(null);
  const { auth } = useAuth();

  useEffect(() => {
    fetchUserProfile(); // Fetch user profile when the component mounts
  }, []);
  const fetchUserProfile = async () => {
    let res = await getMyProfile(auth?.token);
    if (res && res.statusCode === 0) {
      setUser(res.data);
      console.log("User profile fetched successfully:", res.data);
    } else {
      toast.error("Failed to fetch user profile");
      console.error("Error fetching user profile:", res);
    }
  };
  return (
    <div className="container d-flex justify-content-center align-items-center min-vh-100 ">
      <ToastContainer />

      <div
        className="card shadow-lg p-4 text-center"
        style={{ width: "30rem" }}
      >
        {/* Avatar */}
        <img
          src={user?.avatarUrl || "https://placehold.co/600x400"}
          alt="User Avatar"
          className="rounded-circle mx-auto mb-3"
          style={{ width: "120px", height: "120px", objectFit: "cover" }}
        />

        <h2 className="card-title mb-4 text-primary mb-4 text-center fw-bolder">
          User Profile
        </h2>

        {user ? (
          <ul className="list-group list-group-flush text-start">
            <li className="list-group-item">
              <strong>Username:</strong> {user.username}
            </li>
            <li className="list-group-item">
              <strong>Full Name:</strong> {user.fullName || "N/A"}
            </li>
            <li className="list-group-item">
              <strong>Date of Birth:</strong> {user.dateOfBirth || "N/A"}
            </li>
            <li className="list-group-item">
              <strong>Address:</strong> {user.address || "N/A"}
            </li>
            <li className="list-group-item">
              <strong>Phone Number:</strong> {user.phoneNumber || "N/A"}
            </li>
            <li className="list-group-item">
              <strong>Email:</strong> {user.email}
            </li>
            <li className="list-group-item">
              <strong>Status:</strong> {user.isActive ? "Active" : "Inactive"}
            </li>
          </ul>
        ) : (
          <p className="text-muted">Loading...</p>
        )}
      </div>
    </div>
  );
};

export default UserProfileComponent;
