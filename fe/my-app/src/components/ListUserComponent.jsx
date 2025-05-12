import React, { useEffect,useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { listUsers  } from '../services/UserService';
import { useNavigate } from 'react-router-dom';
const ListUserComponent = () => {
    
    const [users, setUSers] = useState([]);
    const navigate = useNavigate();
    useEffect(() => {
        listUsers().then((response) => {
            setUSers(response.data.data);
            
        }).catch(error => {
            console.log(error);
            navigate("/login");
        })
    },[]);
    
  return (

    <div>
        <h1 className='text-center'>Welcome to User Management</h1>
        <h2 className='text-center'>List Users</h2>
        <div style={{margin: "10px 0"}}>
            <button className='btn btn-primary mt-3'>Add users</button>
        </div>
        <table className='table table-striped table-bordered'>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                </tr>
            </thead>
            <tbody>
                {users.map(user => (
                    <tr key={user.id}>
                        <td>{user.id}</td>
                        <td>{user.username}</td>
                        <td>{user.email}</td>
                    </tr>

                ))}
            </tbody>
        </table>
    </div>
  )
}

export default ListUserComponent