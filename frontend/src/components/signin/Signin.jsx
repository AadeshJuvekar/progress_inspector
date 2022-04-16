import React, { Component } from "react";
import {
  Avatar,
  Box,
  Button,
  Card,
  Checkbox,
  Container,
  CssBaseline,
  FormControlLabel,
  Grid,
  TextField,
  Typography,
} from "@mui/material";

import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import "./signin.css";
import { NavLink } from "react-router-dom";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import { userLogin, getSession } from "./../../actions/userSession";
class SignIn extends Component {
  constructor() {
    super();

    this.state = {
      loginName: "",
      pwd: "",
      userType: "",
      authType: "",
      errors: {},
    };
  }

  componentDidMount() {
    this.props.getSession();
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
    event.preventDefault();

    if (this.state.pwd.length > 0) {
      const userDetail = {
        loginName: this.state.loginName,
        pwd: this.state.pwd,
      };

      this.props.userLogin(userDetail, this.props.history);
    } else {
      this.setState({
        errors: {
          loginName: "Login Name is Required",
          pwd: "Password is Required",
        },
      });
    }
  };
  render() {
    const { errors } = this.state;
    console.log(this.state);
    const { userSession } = this.props;
    console.log(userSession);
    return userSession.authType !== undefined &&
      userSession.authType !== "notLoggedIn" ? (
      (window.location.href = "/dashboard")
    ) : (
      <div className="login">
        <div className="loginContainer">
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
                  Sign in
                </Typography>
                <Box
                  component="form"
                  onSubmit={this.onSubmit}
                  noValidate
                  sx={{ mt: 1 }}
                >
                  <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="loginName"
                    label="User Name"
                    name="loginName"
                    autoComplete="email"
                    autoFocus
                    value={this.state.loginName}
                    onChange={this.onChange}
                  />
                  {this.state.errors.loginName && (
                    <Typography color="error">
                      *{this.state.errors.loginName}
                    </Typography>
                  )}
                  <TextField
                    margin="normal"
                    required
                    fullWidth
                    name="pwd"
                    label="Password"
                    type="password"
                    id="password"
                    autoComplete="current-password"
                    value={this.state.pwd}
                    onChange={this.onChange}
                  />
                  {this.state.errors.pwd && (
                    <Typography color="error">
                      *{this.state.errors.pwd}
                    </Typography>
                  )}
                  <FormControlLabel
                    control={<Checkbox value="remember" color="primary" />}
                    label="Remember me"
                  />
                  <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    sx={{ mt: 3, mb: 2 }}
                  >
                    Sign In
                  </Button>
                  <Grid container sx={{ mt: 4, mb: 4 }}>
                    <Grid item xs>
                      <NavLink to="/register">Forgot password?</NavLink>
                    </Grid>
                    <Grid item>
                      <NavLink to="/register">
                        {"Don't have an account? Sign Up"}
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

SignIn.propTypes = {
  userLogin: PropTypes.func.isRequired,
  errors: PropTypes.object.isRequired,
  userSession: PropTypes.object.isRequired,
  getSession: PropTypes.func.isRequired,
};
const mapStateToProps = (state) => ({
  userSession: state.userSession,
  errors: state.errors,
});
export default connect(mapStateToProps, { userLogin, getSession })(SignIn);
