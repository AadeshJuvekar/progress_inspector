import React from "react";
import { Route, Navigate } from "react-router-dom";

const PrivateRoute = ({
  component: Component,
  userSession,
  permittedUser,
  ...otherProps
}) => (
  <Route
    {...otherProps}
    render={(props) =>
      userSession.userType !== undefined ? (
        userSession.userType === "notLoggedIn" ? (
          <Navigate to="/login" />
        ) : userSession.userType === permittedUser ? (
          <Component {...props} />
        ) : (
          <h1>unAuthorised Access</h1>
        )
      ) : (
        "404 NOT FOUND"
      )
    }
  />
);

export default PrivateRoute;
