import "./App.css";
import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import AppRoute from "./components/routes/AppRoute";
import { AuthProvider } from "./context/AuthProvider";

const App = () => {
  return (
    <>
      <AuthProvider>
        <AppRoute />
      </AuthProvider>
    </>
  );
};

export default App;
