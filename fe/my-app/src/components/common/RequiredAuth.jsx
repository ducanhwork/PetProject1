import { Navigate, Outlet, useLocation } from "react-router-dom";
import useAuth from "../../hooks/useAuth";
import React from "react";

const RequiredAuth = (acceptRole) => {
  const auth = useAuth();
  const location = useLocation();
  return auth?.userResponse?.roles?.find((role) =>
    acceptRole.includes(role.name)
  ) ? (
    <Outlet />
  ) : auth?.userResponse ? (
    <Navigate to="/unauthorized" replace state={{ from: location }} />
  ) : (
    <>Loading ...</>
  );
};

export default RequiredAuth;
