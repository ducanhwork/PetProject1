import { Navigate, Outlet, useLocation } from "react-router-dom";
import useAuth from "../../hooks/useAuth";
import React from "react";

const RequiredAuth = ({ acceptRole }) => {
  const { auth } = useAuth();
  const location = useLocation();
  console.log(
    "Checking authentication and roles",
    auth?.userResponse?.roles?.find((role) => acceptRole.includes(role.name))
  );

  return auth?.userResponse?.roles?.find((role) =>
    acceptRole.includes(role.name)
  ) ? (
    <Outlet />
  ) : auth?.userResponse ? (
    <Navigate to="/unauthorized" replace state={{ from: location }} />
  ) : (
    <Navigate to="/login" replace state={{ from: location }} />
  );
};

export default RequiredAuth;
