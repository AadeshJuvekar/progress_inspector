import React from "react";
import "./footer.css";
import { Copyright } from "@mui/icons-material";
function footer() {
  return (
    <div className="footer">
      <div className="footerCopyright">
        <Copyright className="footerIcon" />
        <span className="footerText">
          2022 by Progress Inspector. All Rights Reserved.
        </span>
      </div>
    </div>
  );
}

export default footer;
