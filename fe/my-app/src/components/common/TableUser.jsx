import React from "react";

const TableUser = (props) => {
  const { users, handleDelete, handleShow, handleActive } = props;
  return (
    <>
      <h2 className="text-center">List Users</h2>
      <table
        className="table table-hover table-bordered"
        style={{ width: "100%", tableLayout: "fixed" }}
      >
        <thead>
          <tr className="text-center">
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Role</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => (
            <tr key={user.id}>
              <td>{user.id}</td>
              <td>{user.username}</td>
              <td>{user.email}</td>
              <td className="text-center">
                {user.roles[0].name === "ADMIN" ? (
                  <span className="badge w-75 bg-danger">Admin</span>
                ) : user.roles[0].name === "TEACHER" ? (
                  <span className="badge w-75 bg-success">Teacher</span>
                ) : (
                  <span className="badge w-75 bg-secondary">Student</span>
                )}
              </td>
              <td>
                {user.isActive ? (
                  <button
                    className="btn btn-danger"
                    style={{ marginRight: "10px" }}
                    onClick={() => {
                      handleDelete(user.id);
                    }}
                  >
                    Ban
                  </button>
                ) : (
                  <button
                    className="btn btn-success"
                    style={{ marginRight: "10px" }}
                    onClick={() => {
                      handleActive(user.id);
                    }}
                  >
                    Activate
                  </button>
                )}
                <button
                  className="btn btn-primary"
                  style={{ marginRight: "10px" }}
                  onClick={() => {
                    // Handle edit action
                    console.log("Edit user with ID:", user.id);
                  }}
                >
                  Edit
                </button>
                <button
                  className="btn btn-info"
                  onClick={() => {
                    handleShow(user.id);
                  }}
                >
                  View
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  );
};

export default TableUser;
