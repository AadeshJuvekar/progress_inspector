import React from "react";
import "./topbar.css";
import logo from "../../static/images/logo.png";
import AccountCircle from "@mui/icons-material/AccountCircle";
import VpnKeyIcon from "@mui/icons-material/VpnKey";
import PersonIcon from "@mui/icons-material/Person";
import DashboardIcon from "@mui/icons-material/Dashboard";
import HomeIcon from "@mui/icons-material/Home";
import LogoutIcon from "@mui/icons-material/Logout";

function Topbar() {
  return (
    <div className="topbar">
      <div className="topbarWrapper">
        <div className="topbarLeft">
          <img src={logo} alt="Progress Inspector" className="topbarLogo" />
          <span className="topbarTitle">Progress Inspector</span>
        </div>
        <div className="topbarRight">
          <div className="topbarMenu">
            <HomeIcon className="topbarIcon" />
            <span className="topbarMenuItem">Home</span>
            <DashboardIcon className="topbarIcon" />
            <span className="topbarMenuItem">Dashboard</span>
            <AccountCircle className="topbarIcon" />
            <span className="topbarMenuItem">Register</span>
            <VpnKeyIcon className="topbarIcon" />
            <span className="topbarMenuItem">Login</span>
            <LogoutIcon className="topbarIcon" />
            <span className="topbarMenuItem">Logout</span>
            <PersonIcon className="topbarIcon" />
            <span className="topbarMenuItem">Profile</span>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Topbar;
