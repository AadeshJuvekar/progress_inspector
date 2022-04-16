import React, { useEffect, useState } from "react";
import "./header.css";
import logo from "../../static/images/logo.png";
import AccountCircle from "@mui/icons-material/AccountCircle";
import VpnKeyIcon from "@mui/icons-material/VpnKey";
import PersonIcon from "@mui/icons-material/Person";
import DashboardIcon from "@mui/icons-material/Dashboard";
import HomeIcon from "@mui/icons-material/Home";
import LogoutIcon from "@mui/icons-material/Logout";
import { NavLink } from "react-router-dom";

function Header(props) {
  return (
    <div className="header">
      <div className="headerWrapper">
        <div className="headerLeft">
          <img src={logo} alt="Progress Inspector" className="headerLogo" />
          <span className="headerTitle">Progress Inspector</span>
        </div>
        <div className="headerRight">
          <div className="headerMenu">
            <NavLink to="/" className="headerLink">
              <HomeIcon className="headerIcon" />
              <span className="headerMenuItem">Home</span>
            </NavLink>

            {props.userSession.authType !== "notLoggedIn" ? (
              <>
                <NavLink to="/dashboard" className="headerLink">
                  <DashboardIcon className="headerIcon" />
                  <span className="headerMenuItem">Dashboard</span>
                </NavLink>
                <NavLink to="/profile" className="headerLink">
                  <PersonIcon className="headerIcon" />
                  <span className="headerMenuItem">Profile</span>
                </NavLink>
                <NavLink
                  to="/logout"
                  className="headerLink"
                  onClick={props.logoutFunction}
                >
                  <LogoutIcon className="headerIcon" />
                  <span className="headerMenuItem">Logout</span>
                </NavLink>
              </>
            ) : (
              <>
                <NavLink to="/register" className="headerLink">
                  <AccountCircle className="headerIcon" />
                  <span className="headerMenuItem">Register</span>
                </NavLink>
                <NavLink to="/login" className="headerLink">
                  <VpnKeyIcon className="headerIcon" />
                  <span className="headerMenuItem">Login</span>
                </NavLink>
              </>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Header;
