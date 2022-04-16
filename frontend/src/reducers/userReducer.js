import {
  GET_USER,
  GET_USERS,
  GET_USER_TASK,
  GET_USER_TASKS,
} from "../actions/types";

const initialState = {
  user: {},
  users: {},
  userTasks: [],
  userTask: {},
};

export default function(state = initialState, action) {
  switch (action.type) {
    case GET_USERS:
      return {
        ...state,
        users: action.payload,
      };
    case GET_USER:
      return {
        ...state,
        user: action.payload,
      };
    case GET_USER_TASKS:
      return {
        ...state,
        userTasks: action.payload,
      };
    case GET_USER_TASK:
      return {
        ...state,
        userTask: action.payload,
      };
    default:
      return state;
  }
}
