import React, { useEffect, useState } from "react";
import { getUserById } from "../../services/users/UserService";

const UserDetailModalComponent = ({ userId, modalShow, setModalShow }) => {
  const [user, setUser] = useState(null);

  useEffect(() => {
    if (userId) {
      getUserById(userId)
        .then((response) => {
          setUser(response.data.data);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  }, [userId]);

  const handleClose = () => {
    setModalShow(false);
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
          </div>
          {user ? (
            <div className="modal-body">
              <p>
                <b>Email</b>: {user.email}
              </p>
              <p>
                <b>Username</b>: {user.username}
              </p>
              <p className="">
                <b>Status</b>:{" "}
                {user.isActive ? (
                  <span className="text-info">Active</span>
                ) : (
                  <span className="text-danger">Inactive</span>
                )}
              </p>

              <p>
                <b>Role</b>: {}
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
