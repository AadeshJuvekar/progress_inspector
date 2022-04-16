import React, { Component } from "react";
import PropTypes from "prop-types";
import { connect } from "react-redux";
// import classnames from "classnames";
import {
  Typography,
  Button,
  TextField,
  CssBaseline,
  Select,
  MenuItem,
  Card,
  InputLabel,
} from "@mui/material";
import { withStyles } from "@mui/styles";
import { getSession } from "../../actions/userSession";
import { NavLink } from "react-router-dom";
import { getOwners, createTask } from "./../../actions/userAction";

const useStyles = () => ({
  field: {
    margin: 20,
    marginLeft: "6%",
    display: "block",
    width: "90%",
  },
  heading: {
    marginTop: 20,
  },
  form: {
    // width: "100%", // Fix IE 11 issue.
    marginTop: "12%",
  },
  dropdown: {
    width: "90%",
    margin: 20,
    marginLeft: "6%",
    fontWeight: "200%",
    textAlign: "left",
  },
  createbtn: {
    textAlign: "center",
    marginLeft: "40%",
    marginBottom: "2%",
  },
});

class CreateTask extends Component {
  constructor(props) {
    super(props);
    this.state = {
      title: "",
      taskIdentifier: "",
      description: "",
      loginName: "",
      errors: {},
    };
    this.onChange = this.onChange.bind(this);
  }

  onChange = (event) => {
    // console.log(event.target.value);
    //study syntheticbaseevent its an object
    this.setState({ errors: {} });
    this.setState({ [event.target.name]: event.target.value });
  };

  onSubmit = (event) => {
    event.preventDefault();
    const newTask = {
      title: this.state.title,
      taskIdentifier: this.state.taskIdentifier,
      description: this.state.description,
    };
    this.props.getSession();
    this.props.createTask(
      newTask,
      this.state.loginName,
      this.props.userSession.loginName
    );
  };

  componentWillReceiveProps(nextProps) {
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }

  componentDidMount() {
    this.props.getOwners();
  }

  //const classes = useStyles();

  render() {
    const { errors } = this.state;
    const { classes } = this.props;
    const { owners } = this.props.owners;
    console.log(this.props.owners[0]);

    return (
      <div
        style={{
          height: "85vh",
          width: "100%",
          position: "relative",
          top: "3em",
        }}
      >
        <Card style={{ width: "90%", margin: "4% auto" }}>
          <CssBaseline />
          <Typography className={classes.heading} variant="h4" align="center">
            Create Task
          </Typography>

          <form autoComplete="off" onSubmit={this.onSubmit}>
            <TextField
              className={classes.field}
              label="Task Title"
              variant="outlined"
              color="secondary"
              name="title"
              fullWidth
              error={this.state.errors.title}
              onChange={this.onChange}
              value={this.state.title}
              helperText={this.state.errors.title}
            />
            {/* {errors.title && (
            <Typography color="error">*{errors.title}</Typography>
          )} */}
            <TextField
              className={classes.field}
              label="Task Identifier"
              variant="outlined"
              color="secondary"
              name="taskIdentifier"
              fullWidth
              //  required
              onChange={this.onChange}
              value={this.state.taskIdentifier}
              error={this.state.errors.taskIdentifier}
              helperText={this.state.errors.taskIdentifier}
            />
            {/* {errors.taskIdentifier && (
            <Typography color="error">*{errors.taskIdentifier}</Typography>
          )} */}
            <TextField
              className={classes.field}
              label="Description"
              variant="outlined"
              color="secondary"
              name="description"
              multiline
              rows={2}
              fullWidth
              onChange={this.onChange}
              value={this.state.description}
              error={this.state.errors.description}
              helperText={this.state.errors.description}
            />
            {/* {errors.description && (
              <Typography color="error">*{errors.description}</Typography>
            )} */}
            {owners !== undefined ? (
              <>
                <TextField
                  fullWidth
                  select
                  style={{ color: "grey" }}
                  className={classes.field}
                  name="loginName"
                  label="Select Product Owner"
                  onChange={this.onChange}
                  value={this.state.loginName}
                  labelId="Product-Owner"
                  error={this.state.errors.loginName}
                  helperText={this.state.errors.loginName}
                >
                  {owners.map((owner) => (
                    <MenuItem key={owner.id} value={owner.loginName}>
                      {owner.name + "( " + owner.userType + " )"}
                    </MenuItem>
                  ))}
                </TextField>
              </>
            ) : (
              ""
            )}
            <Button
              align="center"
              type="submit"
              color="primary"
              variant="contained"
              className={classes.createbtn}
            >
              Create
            </Button>{" "}
            &nbsp;
            <NavLink to={`/dashboard`} style={{ textDecoration: "none" }}>
              <Button
                align="center"
                color="primary"
                variant="outlined"
                // className = {classes.createbtn}\
                style={{ marginBottom: "2%" }}
              >
                Cancel
              </Button>
            </NavLink>
          </form>
        </Card>
      </div>
    );
  }
}

CreateTask.propTypes = {
  classes: PropTypes.object.isRequired,
  createTask: PropTypes.func.isRequired,
  getSession: PropTypes.func.isRequired,
  userSession: PropTypes.object.isRequired,
  errors: PropTypes.object.isRequired,
  getOwners: PropTypes.func.isRequired,
  owner: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  errors: state.errors,
  userSession: state.userSession,
  owners: state.owners,
  owner: state.owners.owner,
});

export default connect(mapStateToProps, {
  createTask,
  getSession,
  getOwners,
})(withStyles(useStyles)(CreateTask));
