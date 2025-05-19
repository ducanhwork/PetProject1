import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { listUsers } from "../../services/users/UserService";
import SidebarComponent from "../common/SidebarComponent";
import { useNavigate } from "react-router-dom";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import { ToastContainer, toast } from "react-toastify";
import UserDetailModalComponent from "../user/UserDetailModalComponent";
const ListUserComponent = () => {
  const [modalShow, setModalShow] = useState(false);
  const [userId, setUserId] = useState(null);
  const [stompClient, setStompClient] = useState(null);
  const [messages, setMessages] = useState([]);
  const [users, setUSers] = useState([]);
  const navigate = useNavigate();
  const handleShow = (userId) => {
    setUserId(userId);
    setModalShow(true);
  };
  useEffect(() => {
    const socket = new SockJS("http://localhost:8080/ws"); // Your Spring endpoint
    const stompClient = Stomp.over(socket);
    stompClient.connect({}, () => {
      stompClient.subscribe("/topic/updates", (message) => {
        if (message.body) {
          toast.success(`New user added: ${JSON.parse(message.body).username}`);
          console.log("New user added:", JSON.parse(message.body));

          setMessages((prev) => [...prev, JSON.parse(message.body)]);
          listUsers()
            .then((response) => {
              setUSers(response.data.data);
            })
            .catch((error) => {
              console.log(error);
            });
        }
      });
    });
    setStompClient(stompClient);

    listUsers()
      .then((response) => {
        setUSers(response.data.data);
      })
      .catch((error) => {
        console.log(error);
        navigate("/login");
      });
    return () => {
      if (stompClient) {
        stompClient.disconnect(() => {
          console.log("WebSocket disconnected");
        });
      }
    };
  }, []);

  return (
    <div className="row">
      <div className="col-2">
        <SidebarComponent />
      </div>
      <div className="container col-10" style={{ marginTop: "70px" }}>
        <h2 className="text-center">List Users</h2>
        <table
          className="table table-hover table-bordered"
          style={{ width: "100%", tableLayout: "fixed" }}
        >
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr key={user.id}>
                <td>{user.id}</td>
                <td>{user.username}</td>
                <td>{user.email}</td>
                <td>
                  <button
                    className="btn btn-danger"
                    style={{ marginRight: "10px" }}
                    onClick={() => {
                      // Handle delete action
                      console.log("Delete user with ID:", user.id);
                    }}
                  >
                    Delete
                  </button>
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
      </div>
      <ToastContainer />
      <UserDetailModalComponent
        modalShow={modalShow}
        setModalShow={setModalShow}
        userId={userId}
      />
    </div>
  );
};

export default ListUserComponent;
