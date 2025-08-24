import React, { useEffect, useState } from "react";
import { getUserById } from "../../services/users/UserService";
import { toast } from "react-toastify";
import useAuth from "../../hooks/useAuth";
const UserDetailModalComponent = ({ userId, modalShow, setModalShow }) => {
  const [user, setUser] = useState(null);
  const { auth } = useAuth();
  useEffect(() => {
    if (modalShow && userId) {
      setUser(null); // Reset user state when modal opens
      loadUserDetail(userId); // Load user details when modal is shown
    }
  }, [modalShow, userId]);
  const loadUserDetail = async (userId) => {
    let res = await getUserById(userId, auth?.token);
    if (res && res.statusCode === 0) {
      setUser(res.data);
    } else {
      toast.warning("Failed to fetch user detail");
    }
  };
  if (!modalShow) return null;
  const handleClose = () => {
    setModalShow(false);
    setUser(null);
  };

  return (
    <>
      <div
        className={`modal-backdrop fade ${
          modalShow ? "d-block" : "d-none"
        } show`}
      ></div>
      {/* Modal content */}
      <div
        className={`modal ${
          modalShow ? "d-block" : "d-none"
        } w-50 postion-absolute top-50 start-50 translate-middle`}
        style={{
          zIndex: 1050,
          backgroundColor: "white",
          borderRadius: "5px",
          height: "fit-content",
          padding: "0px",
          boxShadow: "0 4px 8px rgba(0, 0, 0, 0.2)",
        }}
      >
        <div className="modal-content">
          <div className="modal-header">
            <h5 className="modal-title">User Details</h5>
            {/* Avatar */}
            <img
              src={user?.avatarUrl || "https://placehold.co/600x400"}
              alt="User Avatar"
              className="rounded-circle mx-auto mb-3"
              style={{ width: "120px", height: "120px", objectFit: "cover" }}
            />
          </div>
          {user ? (
            <div className="modal-body">
              <p>
                <b>Email</b>: {user.email}
              </p>
              <p>
                <b>Username</b>: {user.username}
              </p>
              <p>
                <b>Phone</b>: {user.phoneNumber || "N/A"}
              </p>
              <p>
                <b>Full Name</b>: {user.fullName || "N/A"}
              </p>
              <p>
                <b>Date of Birth</b>: {user.dateOfBirth || "N/A"}
              </p>
              <p>
                <b>Address</b>: {user.address || "N/A"}
              </p>
              <p>
                <b>Role</b>:{" "}
                {user.roles[0]?.name === "ADMIN" ? (
                  <span className="badge w-10 bg-danger">Admin</span>
                ) : user.roles[0]?.name === "TEACHER" ? (
                  <span className="badge w-10 bg-success">Teacher</span>
                ) : (
                  <span className="badge w-10 bg-secondary">Student</span>
                )}
              </p>
              <p className="">
                <b>Status</b>:{" "}
                {user.isActive ? (
                  <span className="badge w-10 bg-success">Active</span>
                ) : (
                  <span className="badge w-10 bg-danger">Inactive</span>
                )}
              </p>
            </div>
          ) : (
            <div className="modal-body">Loading...</div>
          )}
          <div className="modal-footer">
            <button
              type="button"
              className="btn btn-danger"
              onClick={handleClose}
            >
              Close
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

export default UserDetailModalComponent;
