import { useContext, useDebugValue } from "react";

import AuthContext from "../context/AuthProvider";

const useAuth = () => {
  const { auth } = useContext(AuthContext);
  console.log("useAuth hook called", auth);

  useDebugValue(auth, (auth) => (auth ? "Authenticated" : "Not Authenticated"));
  return useContext(AuthContext);
};
export default useAuth;
