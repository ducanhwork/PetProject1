import React, {useState} from 'react'
import { MDBBtn, MDBCard, MDBCardBody, MDBCol, MDBContainer, MDBInput, MDBRow } from 'mdb-react-ui-kit';
import { login } from '../services/AuthService';
import { useNavigate } from 'react-router-dom';
const LoginComponent = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const handleUsernameChange = (event) => {
        setUsername(event.target.value);
    };
    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    }

    const navigate = useNavigate();
    const handleLogin = (event) => {
        event.preventDefault();
       
        login(username, password)
            .then((response) => {
                console.log(response.data);
                // Handle successful login (e.g., store token, redirect, etc.)
                console.log("Login successful:", response.data);
                const token = response.data.data.token; // Adjust based on your API response
                console.log("Token:", token);
                // Store the token in a cookie or local storage
                if(localStorage.getItem("access_token") !== null){
                    localStorage.removeItem("access_token");
                }
                localStorage.setItem("access_token", token);
                navigate("/users");

            })
            .catch((error) => {
                console.error("Login failed:", error);
                // Handle login failure (e.g., show error message)
                alert("Login failed. Please check your username and password.");
            });
    }
    return (
        <MDBContainer fluid>
            <form>
          <MDBRow className='d-flex justify-content-center align-items-center h-100'>
            <MDBCol col='12'>
    
              <MDBCard className='bg-dark text-white my-5 mx-auto' style={{borderRadius: '1rem', maxWidth: '400px'}}>
                <MDBCardBody className='p-5 d-flex flex-column align-items-center mx-auto w-100'>
    
                  <h2 className="fw-bold mb-2 text-uppercase">Login</h2>
                  <p className="text-white-50 mb-5">Please enter your username and password!</p>

                  <MDBInput wrapperClass='mb-4 mx-5 w-100' labelClass='text-white' label='Username' id='formControlLg' type='text' size="lg" onChange={handleUsernameChange}/>
                  <MDBInput wrapperClass='mb-4 mx-5 w-100' labelClass='text-white' label='Password' id='formControlLg' type='password' size="lg" onChange={handlePasswordChange}/>
    
                  <p className="small mb-3 pb-lg-2"><a class="text-white-50" href="#!">Forgot password?</a></p>
                  <MDBBtn outline className='mx-2 px-5' color='white' size='lg' type='submit' onClick={handleLogin}>
                    Login
                  </MDBBtn>
                      
               
                  <div>
                    <p className="mb-0">Don't have an account? <a href="#!" className="text-white-50 fw-bold">Sign Up</a></p>
    
                  </div>
                </MDBCardBody>
              </MDBCard>
    
            </MDBCol>
          </MDBRow>
        </form>
        </MDBContainer>
      );
}

export default LoginComponent