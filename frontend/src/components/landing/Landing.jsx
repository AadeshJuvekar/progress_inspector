import React from "react";
import "./landing.css";
import startup from "../../static/images/startup.svg";

function Landing() {
  return (
    <div className="landing">
      <div className="landingWrapper">
        <section className="landingContainer">
          <header className="landingHeader">Progress Inspector</header>
          <span className="landingText"> - A Project Management Tool</span>
        </section>
        <aside className="landingImage">
          <img src={startup} alt="Landing Page" className="landingStartup" />
        </aside>
      </div>
    </div>
  );
}

export default Landing;
