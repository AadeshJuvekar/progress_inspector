import { combineReducers } from "redux";
import errorReducer from "./errorReducer";
import userSessionReducer from "./userSessionReducer";
import userReducer from "./userReducer";
import taskReducer from "./taskReducer";
import ownerReducer from "./ownerReducer";
import developerReducer from "./developerReducer";

export default combineReducers({
  errors: errorReducer,
  userSession: userSessionReducer,
  users: userReducer,
  tasks: taskReducer,
  owners: ownerReducer,
  developers: developerReducer,
});
