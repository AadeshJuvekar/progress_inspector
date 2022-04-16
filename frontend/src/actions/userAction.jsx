import axios from "axios";
import React from "react";
import {
  GET_ERRORS,
  GET_USER,
  GET_USERS,
  DELETE_TASK,
  GET_DEVELOPERS,
  GET_TASK,
  GET_TASKS,
  DELETE_USER,
  GET_OWNERS,
  GET_USER_TASKS,
  GET_USER_TASK,
} from "./types";
import history from "../history";
//To register user
export const addUser = (user, history) => async (dispatch) => {
  try {
    console.log(user);
    await axios.post("/api/register", user);
    // console.log(history);
    // history.push("/login");
    window.location.href = "/login";
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

export const getUser = (loginName) => async (dispatch) => {
  const res = await axios.get(`/api/${loginName}`);
  dispatch({
    type: GET_USER,
    payload: res.data,
  });
};

export const updateUser = (user) => async (dispatch) => {
  try {
    const res = await axios.patch("/api/update", user);
    window.location.replace("/dashboard");
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};
export const deleteUser = (loginName) => async (dispatch) => {
  const res = await axios.delete(`/api/${loginName}`);
  dispatch({
    type: DELETE_USER,
    payload: res.data,
  });
};
/**
 * This function provides the authentication of client to the task
 * @param {*} taskIdentifier of the task
 * @param {*} clientLoginName of the client to be authorized
 * @param {*} history
 * @returns errors if any
 */
export const authorizeClient = (
  taskIdentifier,
  clientLoginName,
  history
) => async (dispatch) => {
  try {
    const res = await axios.get(
      `/api/authorizeClient/${clientLoginName}/${taskIdentifier}`
    );
    alert(`Client with Login Name ${clientLoginName} authorized successfully.`);
    history.push(`/productowner/viewTask/${taskIdentifier}`);
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

//To assign task to developer
export const assignDeveloper = (taskIdentifier, loginName) => async (
  dispatch
) => {
  try {
    const res = await axios.patch(
      `/api/task/assignDeveloper/${taskIdentifier}/${loginName}`
    );
    window.location.replace(`/task/${taskIdentifier}`);
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};
//To create task
export const createTask = (
  task,
  productOwnerLoginName,
  teamleaderLoginName
) => async (dispatch) => {
  try {
    const res = await axios.post(
      `/api/task/create/${productOwnerLoginName}/${teamleaderLoginName}`,
      task
    );
    window.location.replace("/dashboard");
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

//To update task
export const updateTask = (taskIdentifier, task) => async (dispatch) => {
  try {
    const res = await axios.patch(`/api/task/update/${taskIdentifier}`, task);
    window.location.replace(`/task/${taskIdentifier}`);
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};
export const deleteTask = (taskIdenitifer) => async (dispatch) => {
  if (
    window.confirm("Are you sure! This will delete task and data related to it")
  ) {
    const res = await axios.delete(`/api/task/${taskIdenitifer}`);
    window.location.replace("/dashboard");
    dispatch({
      type: DELETE_TASK,
      payload: taskIdenitifer,
    });
  }
};
//To update progress in task
export const updateTaskStatus = (
  taskIdentifier,
  developerLoginName,
  task,
  history
) => async (dispatch) => {
  try {
    const res = await axios.post(
      `/api/task/updatestatus/${taskIdentifier}/${developerLoginName}`,
      task
    );
    window.location.href(`/developer/viewTask/${taskIdentifier}`);
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};

/**
 * This function provides a single task based on the task identifier
 * @param {*} taskIdentifier of the task
 * @param {*} history
 * @returns task with given identifier
 */
export const getTask = (taskIdentifier) => async (dispatch) => {
  try {
    const res = await axios.get(`/api/task/${taskIdentifier}`);
    dispatch({
      type: GET_TASK,
      payload: res.data,
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
    //  window.location.href = "/dashboard";
  }
};

/**
 * This function provides all available tasks
 * @returns list of tasks
 */
export const getTasks = () => async (dispatch) => {
  const res = await axios.get("/api/tasks");
  console.log(res.data);
  dispatch({
    type: GET_TASKS,
    payload: res.data,
  });
};
export const getUserTask = (taskIdentifier, loginName) => async (dispatch) => {
  try {
    const res = await axios.get(`/api/task/${taskIdentifier}/${loginName}`);
    dispatch({
      type: GET_USER_TASK,
      payload: res.data,
    });
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
    //  window.location.href = "/dashboard";
  }
};

/**
 * This function provides all available tasks
 * @returns list of tasks
 */
export const getUserTasks = (loginName) => async (dispatch) => {
  const res = await axios.get(`/api/tasks/${loginName}`);
  console.log(res.data);
  dispatch({
    type: GET_USER_TASKS,
    payload: res.data,
  });
};

/**
 * This function provides all availale clients to the product owner
 * @returns list of clients
 */
export const getUsers = () => async (dispatch) => {
  const res = await axios.get(`/api/all`);
  console.log(res.data);
  dispatch({
    type: GET_USERS,
    payload: res.data,
  });
};
export const getOwners = () => async (dispatch) => {
  const res = await axios.get("/api/all/ProductOwner");
  dispatch({
    type: GET_OWNERS,
    payload: res.data,
  });
};
export const getDevelopers = () => async (dispatch) => {
  let userType = "Developer";
  const res = await axios.get(`/api/all/${userType}`);
  console.log(res.data);
  dispatch({
    type: GET_DEVELOPERS,
    payload: res.data,
  });
};

export const addRemark = (remark, id, history) => async (dispatch) => {
  try {
    const res = await axios.post(`/api/client/addremark/${id}`, remark);
    window.location.reload();
  } catch (error) {
    dispatch({
      type: GET_ERRORS,
      payload: error.response.data,
    });
  }
};
