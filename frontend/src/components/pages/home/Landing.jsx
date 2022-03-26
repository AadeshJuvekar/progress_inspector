import React from "react";
import "./landing.css";
import startup from "../../../static/images/startup.svg";

function Landing() {
  return (
    <div className="landing">
      <section className="landingContainer">
        <header className="landingHeader">Progress Inspector</header>
        <span className="landingText">
          <u>- A Project Management Tool </u>
        </span>
      </section>
      <aside className="landingImage">
        <img src={startup} alt="Landing Page" className="landingStartup" />
      </aside>
    </div>
  );
}

export default Landing;
