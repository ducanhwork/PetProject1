import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import {
  activateUser,
  deleteUser,
  listUsers,
  updateUser,
} from "../../services/users/UserService";
import SidebarComponent from "../common/SidebarComponent";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import { ToastContainer, toast } from "react-toastify";
import UserDetailModalComponent from "../user/UserDetailModalComponent";
import TableUser from "../common/TableUser";
import useAuth from "../../hooks/useAuth";
const ListUserComponent = () => {
  const [modalShow, setModalShow] = useState(false);
  const [userId, setUserId] = useState(null);
  const [stompClient, setStompClient] = useState(null);
  const [users, setUSers] = useState([]);
  const { auth } = useAuth();
  console.log("ListUserComponent mounted, auth:", auth);
  const handleShow = (userId) => {
    setUserId(userId);
    setModalShow(true);
  };
  const handleDelete = (userId) => {
    deleteUser(userId, auth?.token)
      .then((response) => {
        toast.success("User was banned successfully");
      })
      .catch((error) => {
        console.log(error);
        toast.error("Failed to ban user");
      });
  };
  const handleActive = (userId) => {
    // Implement the logic to activate a user
    activateUser(userId, auth?.token)
      .then((response) => {
        toast.success("User is activated successfully");
      })
      .catch((error) => {
        console.log(error);
        toast.error("Failed to activate user");
      });
  };
  useEffect(() => {
    fetchListUser(); // Fetch the user list when the component mounts
    const socket = new SockJS("http://localhost:8080/ws");
    const stompClient = Stomp.over(socket);
    stompClient.connect({}, () => {
      stompClient.subscribe("/topic/updates", (message) => {
        if (message.body) {
          // toast.success(
          //   ` An user is added or updated: ${JSON.parse(message.body).username}`
          // );
          fetchListUser(); // Refresh the user list after receiving the update
        }
      });
    });
    setStompClient(stompClient);

    return () => {
      if (stompClient) {
        stompClient.disconnect(() => {
          console.log("WebSocket disconnected");
        });
      }
    };
  }, []);
  const fetchListUser = async () => {
    let res = await listUsers(auth?.token);

    if (res && res.statusCode === 0) {
      setUSers(res.data);
      console.log("User list fetched successfully:", res.data);
    } else {
      toast.error("Failed to fetch user list");
      console.error("Error fetching user list:", res);
    }
  };
  return (
    <div className="row">
      <div className="col-2">
        <SidebarComponent />
      </div>
      <div className="container col-10" style={{ marginTop: "70px" }}>
        <TableUser
          handleDelete={handleDelete}
          handleShow={handleShow}
          users={users}
          handleActive={handleActive}
        />
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
