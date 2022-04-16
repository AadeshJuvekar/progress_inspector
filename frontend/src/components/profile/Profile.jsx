import React, { Component } from "react";
import { Navigate, NavLink } from "react-router-dom";
import "./profile.css"
import PropTypes from "prop-types";
import { connect } from "react-redux";
import {
  Box,
  Button,
  Card,
  IconButton,
  InputAdornment,
  TextField,
  Typography,
} from "@mui/material";
import { Visibility, VisibilityOff } from "@mui/icons-material";
import { updateUser, getUser } from "./../../actions/userAction";
import { getSession } from "./../../actions/userSession";
/**
 * This is class component for Updating User data
 */
class Profile extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: "",
      name: "",
      userName: "",
      password: "",
      userRole: "",
      oldPassword: "",
      confirmPassword: "",
      newPassword: "",
      editPage: false,
      changePassword: false,
      showPassword: false,
      errors: {},
    };
  }
  /**
   * This function recieves the props from the nextProps
   * @param {*} nextProps
   */
  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
    const { id, name, loginName, pwd, userType } = nextProps.users.user;
    console.log(nextProps.users.user);
    this.setState({
      id,
      name,
      userName: loginName,
      password: pwd,
      userRole: userType,
    });
  }
  /**
   * This function mounts the actions on the component
   */
  componentDidMount() {
    this.props.getUser(this.props.userSession.loginName);
  }
  /**
   * This function handles the changes done on elements
   * @param {*} event
   */

  onChange = (event) => {
    this.setState({ errors: {} });
    this.setState({ [event.target.name]: event.target.value });
  };
  /**
   * This toggles profile edit option.
   */
  handleEdit = () => {
    this.setState({ editPage: !this.state.editPage });
  };
  /**
   * This toggles password change option
   */
  handleChange = () => {
    this.setState({ changePassword: !this.state.changePassword });
  };
  /**
   * This function saves the changes on the backend.
   */
  onSubmit = (event) => {
    event.preventDefault();
    if (this.state.changePassword) {
      if (this.props.user.user.pwd === this.state.oldPassword) {
        if (this.state.newPassword === this.state.confirmPassword) {
          const updatedUser = {
            id: this.state.id,
            name: this.state.name,
            loginName: this.state.userName,
            pwd: this.state.newPassword,
            userType: this.state.userRole,
          };
          this.props.getUser(this.props.userSession.loginName);
          window.alert("Password Updated Successfully");
          this.props.updateUser(updatedUser);
        } else {
          this.setState({
            errors: {
              confirmPassword: "Password did not Match",
            },
          });
        }
      } else if (this.props.user.user.pwd !== this.state.oldPassword) {
        this.setState({
          errors: {
            oldPassword: "Old Password entered is Wrong",
          },
        });
      } else if (this.state.oldPassword === "") {
        this.setState({
          errors: {
            oldPassword: "Please Enter Password",
          },
        });
      }
    } else {
      const updatedUser = {
        id: this.state.id,
        name: this.state.name,
        loginName: this.state.userName,
        pwd: this.state.password,
        userType: this.state.userRole,
      };
      console.log(updatedUser);
      this.props.getUser(this.props.userSession.loginName);
      window.alert("Profile Updated Successfully");
      this.props.updateUser(updatedUser);
    }
  };
  render() {
    const { editPage } = this.state;
    const { changePassword } = this.state;
    const { errors } = this.state;
    const { userSession } = this.props;
    const handleClickShowPassword = () => {
      this.setState({ showPassword: !this.state.showPassword });
    };
    const handleMouseDownPassword = (event) => {
      event.preventDefault();
    };

    return (
      <div className="profile">
        <div className="profileContainer">
          {userSession.userType !== undefined &&
          userSession.userType === "notLoggedIn" ? (
            <Navigate to="/login" />
          ) : (
            <Box
              style={{
                display: "flex",
                justifyContent: "center",
                marginTop: "20px",
              }}
            >
              <Card
                varient="outlined"
                maxWidth="sm"
                style={{
                  width: "50%",
                  height: "100%",
                  padding: "30px",
                }}
              >
                <Typography variant="h4" align="left" display="inline">
                  User Profile
                </Typography>

                {/* Edit Profile Button Toggle */}
                {changePassword ? (
                  ""
                ) : (
                  // <NavLink to="">
                  <Button
                    variant="outlined"
                    color="primary"
                    onClick={this.handleEdit}
                    style={{ display: "inline", float: "right" }}
                  >
                    {editPage ? "Cancel" : "Edit Profile"}
                  </Button>
                  // </NavLink>
                )}
                <hr />
                <form noValidate onSubmit={this.onSubmit}>
                  {/* Edit Profile or Change Password form toggle */}
                  {changePassword ? (
                    ""
                  ) : (
                    <Box
                      style={{
                        justifyContent: "center",
                        width: "100%",
                        height: "100%",
                      }}
                    >
                      {/* <form noValidate onSubmit={this.onSubmit}> */}
                      <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="name"
                        label="Name"
                        name="name"
                        value={this.state.name}
                        onChange={this.onChange}
                        error={editPage ? this.state.errors.name : ""}
                        helperText={editPage ? this.state.errors.name : ""}
                        disabled={editPage ? false : true}
                      />
                      <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="loginName"
                        label="User Name"
                        name="userName"
                        value={this.state.userName}
                        onChange={this.onChange}
                        disabled
                      />

                      {/* </form> */}
                    </Box>
                  )}
                  {/* Change password Button Toggle */}
                  {editPage ? (
                    ""
                  ) : (
                    <Button
                      onClick={this.handleChange}
                      variant="contained"
                      color="primary"
                      style={{ float: "right", marginBottom: "10px" }}
                    >
                      {changePassword ? "Cancel" : "Change Password"}
                    </Button>
                  )}
                  {changePassword !== undefined ? (
                    changePassword ? (
                      <Box
                        style={{
                          justifyContent: "center",
                          height: "100%",
                        }}
                      >
                        {/* <form noValidate onSubmit={this.onSubmit}> */}
                        <TextField
                          variant="outlined"
                          required
                          fullWidth
                          error={
                            this.state.errors.oldPassword !== "" ? false : true
                          }
                          // helperText={this.state.errors.oldPassword}
                          name="oldPassword"
                          label="Old Password"
                          value={this.state.oldPassword}
                          onChange={this.onChange}
                          type={this.state.showPassword ? "text" : "password"}
                          id="oldPassword"
                          endAdornment={
                            <InputAdornment position="end">
                              <IconButton
                                aria-label="toggle password visibility"
                                onClick={handleClickShowPassword}
                                onMouseDown={handleMouseDownPassword}
                                edge="end"
                              >
                                {this.state.showPassword ? (
                                  <Visibility />
                                ) : (
                                  <VisibilityOff />
                                )}
                              </IconButton>
                            </InputAdornment>
                          }
                        />
                        {errors.oldPassword && (
                          <Typography color="error">
                            *{errors.oldPassword}
                          </Typography>
                        )}
                        <TextField
                          variant="outlined"
                          margin="normal"
                          required
                          fullWidth
                          name="newPassword"
                          label="New Password"
                          type={this.state.showPassword ? "text" : "password"}
                          id="newPassword"
                          error={this.state.errors.pwd}
                          helperText={this.state.errors.pwd}
                          value={this.state.newPassword}
                          onChange={this.onChange}
                          endAdornment={
                            <InputAdornment position="end">
                              <IconButton
                                aria-label="toggle password visibility"
                                onClick={handleClickShowPassword}
                                onMouseDown={handleMouseDownPassword}
                                edge="end"
                              >
                                {this.state.showPassword ? (
                                  <Visibility />
                                ) : (
                                  <VisibilityOff />
                                )}
                              </IconButton>
                            </InputAdornment>
                          }
                        />

                        <TextField
                          variant="outlined"
                          margin="normal"
                          required
                          fullWidth
                          error={
                            this.state.newPassword ===
                            this.state.confirmPassword
                              ? false
                              : true && "Password do not match"
                          }
                          helperText={this.state.errors.confirmPassword}
                          name="confirmPassword"
                          label="Confirm Password"
                          type={this.state.showPassword ? "text" : "password"}
                          id="confirmpassword"
                          value={this.state.confirmPassword}
                          onChange={this.onChange}
                          endAdornment={
                            <InputAdornment position="end">
                              <IconButton
                                aria-label="toggle password visibility"
                                onClick={handleClickShowPassword}
                                onMouseDown={handleMouseDownPassword}
                                edge="end"
                              >
                                {this.state.showPassword ? (
                                  <Visibility />
                                ) : (
                                  <VisibilityOff />
                                )}
                              </IconButton>
                            </InputAdornment>
                          }
                        />
                      </Box>
                    ) : (
                      ""
                    )
                  ) : (
                    ""
                  )}

                  {editPage || changePassword ? (
                    <Button
                      type="submit"
                      fullWidth
                      variant="contained"
                      color="primary"
                      style={{ marginTop: "4%" }}
                    >
                      Update
                    </Button>
                  ) : (
                    ""
                  )}
                </form>
              </Card>
            </Box>
          )}
        </div>
      </div>
    );
  }
}
Profile.propTypes = {
  updateUser: PropTypes.func.isRequired,
  getUser: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
  users: PropTypes.object.isRequired,
  getSession: PropTypes.func.isRequired,
  userSession: PropTypes.object.isRequired,
  user: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  errors: state.errors,
  users: state.users,
  user: state.users.user,
  userSession: state.userSession,
});
export default connect(mapStateToProps, { updateUser, getUser, getSession })(
  Profile
);
