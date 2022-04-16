import React, { Component } from "react";
import {
  Button,
  Container,
  CssBaseline,
  Grid,
  Typography,
  Box,
  Select,
  MenuItem,
  Card,
} from "@mui/material";
import PropTypes from "prop-types";
import { connect } from "react-redux";
import {
  assignDeveloper,
  getTask,
  getDevelopers,
} from "./../../actions/userAction";
import { withStyles } from "@mui/styles";
import { NavLink } from "react-router-dom";

const useStyles = () => ({
  title: {
    fontSize: 55,
    padding: "55px 0px",
    fontWeight: 300,
  },
  card: {
    width: "80%",
    margin: "5%",
    marginLeft: "12%",
  },
});

class AssignDeveloper extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loginName: "",
      errors: {},
    };
  }

  onChange = (event) => {
    console.log(event);
    //study syntheticbaseevent its an object
    this.setState({ loginName: event.target.value });
  };

  onSubmit = (event) => {
    event.preventDefault();
    if (this.state.loginName === "") {
      this.setState({ errors: { loginName: "Cannot be blank" } });
    } else {
      //   const assignDeveloper = {
      //     loginName: this.state.loginName,
      //   };
      this.props.assignDeveloper(
        this.props.taskIdentifier.taskIdentifier,
        this.state.loginName
      );
    }
  };

  componentDidMount() {
    this.props.getDevelopers();
    this.props.getTask();
  }

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  render() {
    const { classes } = this.props;
    const { developers } = this.props.developers;
    console.log(this.props);

    return (
      <>
        <CssBaseline />
        <Card className={classes.card}>
          <Container maxWidth="sm">
            <div>
              <Typography variant="h1" align="center" className={classes.title}>
                Assign Developer
              </Typography>
              <form noValidate autoComplete="off" onSubmit={this.onSubmit}>
                <Grid container alignItems="flex-start" spacing={1}>
                  {developers !== undefined ? (
                    <Select
                      style={{ width: "100%", minHeight: "10%" }}
                      name="loginName"
                      onChange={this.onChange}
                      value={this.state.loginName}
                    >
                      {developers.map((developer) => (
                        <MenuItem
                          key={developer.id}
                          value={developer.loginName}
                        >
                          {" "}
                          {developer.name}{" "}
                        </MenuItem>
                      ))}
                    </Select>
                  ) : (
                    ""
                  )}

                  <Container maxWidth="sm">
                    <Box m="30px auto">
                      <Button
                        variant="contained"
                        type="submit"
                        color="primary"
                        style={{ marginLeft: "30%" }}
                      >
                        Assign Task
                      </Button>{" "}
                      &nbsp;
                      <NavLink
                        to={`/task/${this.props.taskIdentifier.taskIdentifier}`}
                        style={{ textDecoration: "none" }}
                      >
                        <Button variant="outlined" color="primary">
                          Cancel
                        </Button>
                      </NavLink>
                    </Box>
                  </Container>
                </Grid>
              </form>
            </div>
          </Container>
        </Card>
      </>
    );
  }
}

AssignDeveloper.propTypes = {
  classes: PropTypes.object.isRequired,
  assignDeveloper: PropTypes.func.isRequired,
  getTask: PropTypes.func.isRequired,
  getDevelopers: PropTypes.func.isRequired,
  developer: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  errors: state.errors,
  task: state.tasks.task,
  developers: state.developers,
  developer: state.developers.developer,
});

export default connect(mapStateToProps, {
  assignDeveloper,
  getTask,
  getDevelopers,
})(withStyles(useStyles)(AssignDeveloper));
