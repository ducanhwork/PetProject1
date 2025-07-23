import { useState, createContext, useEffect } from "react";
const AuthContext = createContext();
export const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState({});
  useEffect(() => {
    const token = localStorage.getItem("access_token");
    const userResponse = localStorage.getItem("user");

    if (token && userResponse) {
      setAuth({
        token,
        userResponse: JSON.parse(userResponse),
      });
    }
  }, []);
  return (
    <AuthContext.Provider value={{ auth, setAuth }}>
      {children}
    </AuthContext.Provider>
  );
};
export default AuthContext;
