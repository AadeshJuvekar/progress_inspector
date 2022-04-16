import React, { Component } from "react";
import {
  Button,
  Card,
  CardContent,
  Chip,
  Container,
  Grid,
  Typography,
} from "@mui/material";
import { withStyles } from "@mui/styles";
import { connect } from "react-redux";
import PropTypes from "prop-types";
import { NavLink } from "react-router-dom";
import { getTask, deleteTask } from "./../../actions/userAction";
import { getSession } from "./../../actions/userSession";

const useStyles = () => ({
  root: {
    "& > *": {
      margin: "1%",
    },
  },
  textboxstyle: {
    width: "50%",
    marginLeft: "35%",
    marginBottom: "2px",
  },
  typographystyle: {
    marginTop: "5px",
    color: "black",
  },
  addremark: {
    width: "20%",
    textAlign: "center",
    paddinBottom: "2%",
    fontSize: "120%",
    fontWeight: "120%",
    marginRight: "2%",
  },
  container: {
    maxHeight: 440,
  },
  cardroot: {
    width: "98%",
    margin: "12px",
  },
  textarea: {
    width: "100%",
    border: "1px solid white",
    backgroundColor: "#ddd",
    borderRadius: "10px",
    borderBottom: "none",
    outline: "none",
  },
  remark: {
    textAlign: "left",
    marginLeft: "2%",
    marginTop: "1%",
  },
  addBtn: {
    float: "right",
  },
  givenby: {
    marginTop: "1%",
    marginBottom: "2%",
    // fontSize: "150%",
    marginLeft: "2%",
    width: "100%",
    textAlign: "left",
  },
});

class Task extends Component {
  onClick = (event) => {
    const taskIdentifier = this.props.taskIdentifier.taskIdentifier;
    this.props.deleteTask(taskIdentifier);
  };

  componentDidMount() {
    const taskIdentifier = this.props.taskIdentifier.taskIdentifier;
    console.log("did :", taskIdentifier);
    this.props.getSession();
    this.props.getTask(taskIdentifier);
  }

  render() {
    const taskIdentifier = this.props.taskIdentifier.taskIdentifier;
    const { classes } = this.props;
    const { task } = this.props;
    const { remark } = this.props.tasks.task;
    console.log("DATA :", taskIdentifier, this.props.userSession.authType);
    const page = this.setState;
    console.log(remark);
    return (
      <div
        style={{
          height: "85vh",
          width: "100%",
          position: "relative",
          top: "3em",
        }}
      >
        <div className="card d-block">
          <Card
            variant="outlined"
            style={{ margin: "10px", borderRadius: "10px" }}
          >
            <Container maxWidth="lg" style={{ minHeight: "65vh" }}>
              <Grid container>
                <Grid item md={6}>
                  {/* <!-- project title--> */}
                  <h1 className="mt-0">{task.title}</h1>
                  <h4 className="mt-0">Task Id : {task.taskIdentifier}</h4>
                  <div className="badge bg-secondary text-light mb-3">
                    <Chip label={task.progress} color="primary" />
                  </div>

                  <h4>Task Description:</h4>

                  <p className="text-muted mb-2">{task.description}</p>

                  <Grid container spacing={1}>
                    <Grid item md={4}>
                      <div className="mb-4">
                        <h4>Created At :</h4>
                        <p>{task.createdAt}</p>
                      </div>
                    </Grid>
                    <Grid item sm={4}>
                      <div className="mb-1">
                        <h4>Updated At :</h4>
                        <p>{task.updatedAt}</p>
                      </div>
                    </Grid>
                  </Grid>

                  {/* <div id="tooltip-container">
                    <h5>Team Members:</h5>                                            
                </div> */}

                  <Grid container spacing={1}>
                    <Grid item md={3}>
                      <div className="mb-4">
                        <NavLink
                          to={`/task/update/${taskIdentifier}`}
                          style={{ textDecoration: "none" }}
                        >
                          <Button variant="outlined">Update Task</Button>
                        </NavLink>
                      </div>
                    </Grid>
                    <Grid item md={3}>
                      <div className="mb-4">
                        <NavLink
                          to={`/task/assignDeveloper/${task.taskIdentifier}`}
                          style={{ textDecoration: "none" }}
                        >
                          <Button variant="outlined">Assign Developer</Button>
                        </NavLink>
                      </div>
                    </Grid>
                    <Grid item md={3}>
                      <div className="mb-4">
                        <Button
                          variant="outlined"
                          onClick={this.onClick.bind(this, task.taskIdentifier)}
                        >
                          Delete
                        </Button>
                      </div>
                    </Grid>
                  </Grid>
                  <br />
                  <br />
                </Grid>

                {/* Remark Card */}

                <Grid item md={6}>
                  <Card className={classes.cardroot}>
                    {remark !== undefined ? (
                      <Typography
                        gutterBottom
                        variant="h5"
                        component="h2"
                        className={classes.remark}
                      >
                        {`Remarks (` + remark.length + `)`}
                      </Typography>
                    ) : (
                      ""
                    )}
                    <CardContent></CardContent>
                    {remark !== undefined
                      ? remark
                          .reverse()
                          .slice(page)
                          .map((taskremark) => {
                            return (
                              <Typography
                                className={classes.givenby}
                                key={taskremark.id}
                              >
                                <Typography style={{ fontSize: "150%" }}>
                                  {taskremark.givenBy}
                                </Typography>
                                <Typography>{taskremark.createdAt}</Typography>
                                <Typography>
                                  {taskremark.description}
                                </Typography>
                                <hr />
                              </Typography>
                            );
                          })
                      : ""}
                  </Card>
                </Grid>
              </Grid>
            </Container>
          </Card>

          {/* <!-- end card-body--> */}
        </div>
      </div>
    );
  }
}

Task.propTypes = {
  getTask: PropTypes.func.isRequired,
  getSession: PropTypes.func.isRequired,
  userSession: PropTypes.object.isRequired,
  deleteTask: PropTypes.func.isRequired,
  tasks: PropTypes.object.isRequired,
  task: PropTypes.object.isRequired,
};

const mapStateToProps = (state) => ({
  tasks: state.tasks,
  task: state.tasks.task,
  remarks: state.tasks.task.remark,
  userSession: state.userSession,
});

export default connect(mapStateToProps, {
  getTask,
  getSession,
  deleteTask,
})(withStyles(useStyles)(Task));
