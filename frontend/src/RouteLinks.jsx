import React, { Component } from "react";
import { connect } from "react-redux";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { createBrowserHistory } from "history";
import PrivateRoute from "./PrivateRoute";
import PropTypes from "prop-types";
import { getSession, destroySession } from "./actions/userSession";
import Profile from "./components/profile/Profile";
import Signup from "./components/signup/Signup";
import Footer from "./components/footer/Footer";
import Header from "./components/header/header";
import Landing from "./components/landing/Landing";
import Signin from "./components/signin/Signin";
import Dashboard from "./components/Dashboard/Dashboard";
import history from "./history";
import CreateTask from "./components/Task/CreateTask";
import TaskRoutes from "./components/Task/TaskRoutes";
class RouteLinks extends Component {
  componentDidMount() {
    this.props.getSession();
  }
  onLogout = (event) => {
    this.props.destroySession();
  };
  render() {
    const { userSession } = this.props;
    return (
      <>
        <Router history={history}>
          <Header userSession={userSession} logoutFunction={this.onLogout} />
          <Routes>
            <Route
              exact
              path="/dashboard"
              element={<Dashboard userSession={userSession} />}
            />
            <Route exact path="/login" element={<Signin />} />
            <Route exact path="/register" element={<Signup />} />
            <Route
              exact
              path="/profile"
              element={<Profile userSession={userSession} />}
            />
            <Route
              exact
              path="/profile/update"
              element={<Profile userSession={userSession} />}
            />
            <Route exact path="/" element={<Landing />} />

            {/* LoggedInUser Private Links Start */}
            <Route
              exact
              path="/task/create"
              element={<CreateTask />}
              userSession={userSession}
              permittedUser="TeamLeader"
            />
            {/* <Route
              exact
              path="/task/:taskIdentifier"
              element={<Task />}
              userSession={userSession}
            /> */}
            <Route
              exact
              path="/task/:taskIdentifier"
              element={<TaskRoutes action={"View"} />}
              userSession={userSession}
              permittedUser="TeamLeader"
            />
            <Route
              exact
              path="/task/update/:taskIdentifier"
              element={<TaskRoutes action={"Update"} />}
              userSession={userSession}
              permittedUser="TeamLeader"
            />
            <Route
              exact
              path="/task/assignDeveloper/:taskIdentifier"
              element={<TaskRoutes action={"AssignDeveloper"} />}
              userSession={userSession}
              permittedUser="TeamLeader"
            />
          </Routes>
          {/* <PrivateRoute
              exact
              path="/teamLeader/assigndev/:taskIdentifier"
              component={AssignTaskDev}
              userSession={userSession}
              permittedUser="TeamLeader"
            /> */}
          {/* loggedInUser Private Links End */}
          <Footer />
        </Router>
      </>
    );
  }
}
Routes.propTypes = {
  userSession: PropTypes.object.isRequired,
  getSession: PropTypes.func.isRequired,
  destroySession: PropTypes.func.isRequired,
};
const mapStateToProps = (state) => ({
  userSession: state.userSession,
});

export default connect(mapStateToProps, { getSession, destroySession })(
  RouteLinks
);
