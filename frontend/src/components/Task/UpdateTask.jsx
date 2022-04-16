import React, { Component } from "react";
import {
  Typography,
  Button,
  TextField,
  CssBaseline,
  Select,
  MenuItem,
  Card,
} from "@mui/material";
import { connect } from "react-redux";
import { withStyles } from "@mui/styles";
import { getTask, getOwners, updateTask } from "./../../actions/userAction";
import PropTypes from "prop-types";
import { getSession } from "../../actions/userSession";
import { NavLink } from "react-router-dom";

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
  updatebtn: {
    textAlign: "center",
    marginLeft: "45%",
    marginBottom: "2%",
  },
});

class UpdateTask extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: "",
      title: "",
      description: "",
      taskIdentifier: "",
      progress: "",
      errors: {},
    };
  }
  onChange = (event) => {
    // console.log(event.target.value);
    //study syntheticbaseevent its an object
    // this.setState({errors : {}})
    this.setState({ [event.target.name]: event.target.value });
  };

  onSubmit = (event) => {
    event.preventDefault();
    const updateTask = {
      id: this.state.id,
      title: this.state.title,
      taskIdentifier: this.state.taskIdentifier,
      description: this.state.description,
      progress: this.state.progress,
    };
    const taskIdentifier = this.props.taskIdentifier.taskIdentifier;
    this.props.updateTask(taskIdentifier, updateTask);
  };
  //componentWillRecieveProps
  componentWillReceiveProps(nextProps) {
    const { id, title, taskIdentifier, description, progress } = nextProps.task;
    this.setState({
      id,
      title,
      taskIdentifier,
      description,
      progress,
    });
    if (nextProps.errors) {
      this.setState({ errors: nextProps.errors });
    }
  }
  //componentDidMount - getProject(id,history)
  componentDidMount() {
    const taskIdentifier = this.props.taskIdentifier.taskIdentifier;
    this.props.getSession();
    this.props.getTask(taskIdentifier);
  }
  render() {
    const { classes } = this.props;
    const taskIdentfier = this.props.taskIdentifier.taskIdentifier;
    return (
      <>
        <CssBaseline />
        <Card style={{ marginTop: "2%" }}>
          <Typography className={classes.heading} variant="h4" align="center">
            Update Task
          </Typography>

          <form noValidate onSubmit={this.onSubmit}>
            <TextField
              className={classes.field}
              name="title"
              label="Task Title"
              variant="outlined"
              color="secondary"
              fullWidth
              required
              onChange={this.onChange}
              value={this.state.title}
            />
            <TextField
              className={classes.field}
              label="Task Identifier"
              variant="outlined"
              color="secondary"
              fullWidth
              required
              name="taskIdentifier"
              value={this.state.taskIdentifier}
              disabled
            />
            <TextField
              className={classes.field}
              label="Description"
              variant="outlined"
              color="secondary"
              multiline
              rows={2}
              fullWidth
              required
              name="description"
              onChange={this.onChange}
              value={this.state.description}
            />
            <Select
              className={classes.dropdown}
              name="progress"
              onChange={this.onChange}
              value={this.state.progress}
            >
              {" "}
              <MenuItem value={"Pending"}>Pending</MenuItem>
              <MenuItem value={"In Progress"}>In Progress</MenuItem>
              <MenuItem value={"Testing"}>Testing</MenuItem>
              <MenuItem value={"Done"}>Done</MenuItem>
            </Select>
            <Button
              align="center"
              type="submit"
              color="primary"
              variant="contained"
              // className = {classes.updatebtn}
              style={{ marginLeft: "40%", marginBottom: "2%" }}
            >
              Update
            </Button>{" "}
            &nbsp;
            <NavLink
              to={`/task/${taskIdentfier}`}
              style={{ textDecoration: "none" }}
            >
              <Button
                align="center"
                color="primary"
                variant="outlined"
                style={{ marginBottom: "2%" }}
              >
                Cancel
              </Button>
            </NavLink>
          </form>
        </Card>
      </>
    );
  }
}

UpdateTask.propTypes = {
  updateTask: PropTypes.func.isRequired,
  getTask: PropTypes.func.isRequired,
  task: PropTypes.object.isRequired,
  userSession: PropTypes.object.isRequired,
  getSession: PropTypes.func.isRequired,
  getOwners: PropTypes.func.isRequired,
  owner: PropTypes.object.isRequired,
  errors: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  task: state.tasks.task,
  errors: state.errors,
  userSession: state.userSession,
  owners: state.owners,
  owner: state.owners.owner,
});

export default connect(mapStateToProps, {
  getTask,
  updateTask,
  getSession,
  getOwners,
})(withStyles(useStyles)(UpdateTask));
