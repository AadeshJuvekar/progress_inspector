import React, { Component } from "react";
import "./signup.css";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { addUser } from "../../actions/userAction";
import {
  Avatar,
  Button,
  Card,
  Container,
  CssBaseline,
  Grid,
  TextField,
  Typography,
  Box,
} from "@mui/material";
import { NavLink } from "react-router-dom";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import { useNavigate } from "react-router-dom";

class Signup extends Component {
  constructor(props) {
    super(props);
    this.state = {
      name: "",
      userName: "",
      password: "",
      confirmPassword: "",
      userType: "Teamleader",
      errors: {},
    };
  }
  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }
  onChange = (event) => {
    this.setState({ errors: {} });
    this.setState({ [event.target.name]: event.target.value });
  };

  onSubmit = (event) => {
    //1. Create JSON for project object
    event.preventDefault();
    this.setState({ errors: {} });
    if (this.state.password === this.state.confirmPassword) {
      const newUser = {
        name: this.state.name,
        loginName: this.state.userName,
        userType: "TeamLeader",
        pwd: this.state.password,
      };
      console.log(newUser);
      console.log("User Registered");
      //2. Send call to backend application InputEvent. Springboot app for saving project
      this.props.addUser(newUser);
    }
    // else{
    //   this.setState({errors:{confirmPassword : "Passwords does not match"}})
    // }
  };
  render() {
    const { errors } = this.state;
    return (
      <div className="signup">
        <div className="signupContainer">
          <Card style={{ width: "90%", marginLeft: "5%", padding: "20px 0" }}>
            <Container component="main" maxWidth="xs">
              <CssBaseline />
              <Box
                sx={{
                  marginTop: 2,
                  display: "flex",
                  flexDirection: "column",
                  alignItems: "center",
                }}
              >
                <Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
                  <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                  Sign up
                </Typography>
                <Box
                  component="form"
                  noValidate
                  onSubmit={this.onSubmit}
                  sx={{ mt: 1 }}
                >
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    // error = {this.state.errors.name}
                    // helperText = {this.state.errors.name}
                    id="name"
                    label="Name"
                    name="name"
                    value={this.state.name}
                    onChange={this.onChange}
                  />
                  {errors.name && (
                    <Typography color="error">*{errors.name}</Typography>
                  )}
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="loginName"
                    label="Login Name"
                    name="userName"
                    value={this.state.userName}
                    onChange={this.onChange}
                  />
                  {errors.loginName && (
                    <Typography color="error">*{errors.loginName}</Typography>
                  )}
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    name="password"
                    label="Password"
                    type="password"
                    id="password"
                    value={this.state.password}
                    onChange={this.onChange}
                  />
                  {errors.pwd && (
                    <Typography color="error">*{errors.pwd}</Typography>
                  )}
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    error={
                      this.state.password === this.state.confirmPassword
                        ? false
                        : true
                    }
                    name="confirmPassword"
                    label="Confirm Password"
                    type="password"
                    id="confirmpassword"
                    helperText={this.state.errors.confirmPassword}
                    value={this.state.confirmPassword}
                    onChange={this.onChange}
                  />
                  {errors.pwd && (
                    <Typography color="error">*{errors.pwd}</Typography>
                  )}
                  <Button
                    data-testid="Register"
                    type="submit"
                    fullWidth
                    variant="contained"
                    color="primary"
                    style={{ marginTop: "4%" }}
                  >
                    Register
                  </Button>
                  <Grid
                    container
                    justify="flex-end"
                    style={{ marginTop: "4%" }}
                  >
                    <Grid item>
                      <NavLink to="/login">
                        Already have an account? Sign In
                      </NavLink>
                    </Grid>
                  </Grid>
                </Box>
              </Box>
            </Container>
          </Card>
        </div>
      </div>
    );
  }
}
Signup.propTypes = {
  addUser: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  errors: state.errors,
});
export default connect(mapStateToProps, { addUser })(Signup);
