import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { listUsers } from "../../services/users/UserService";
import SidebarComponent from "../common/SidebarComponent";
import { useNavigate } from "react-router-dom";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import { ToastContainer, toast } from "react-toastify";
const ListUserComponent = () => {
  const [stompClient, setStompClient] = useState(null);
  const [messages, setMessages] = useState([]);
  const [users, setUSers] = useState([]);
  const navigate = useNavigate();
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
      <SidebarComponent />
      <div className="container col-8" style={{ marginTop: "70px" }}>
        <h2 className="text-center">List Users</h2>
        <table className="table table-striped table-bordered w-100">
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
                    onClick={() => {
                      // Handle delete action
                      console.log("Delete user with ID:", user.id);
                    }}
                  >
                    Delete
                  </button>
                  <button
                    className="btn btn-primary"
                    onClick={() => {
                      // Handle edit action
                      console.log("Edit user with ID:", user.id);
                    }}
                  >
                    Edit
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <ToastContainer />
    </div>
  );
};

export default ListUserComponent;
