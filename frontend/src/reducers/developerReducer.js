import { GET_DEVELOPERS } from "../actions/types";

const initialState = {
  developers: [],
  developer: {},
};
export default function(state = initialState, action) {
  switch (action.type) {
    case GET_DEVELOPERS:
      return {
        ...state,
        developers: action.payload,
      };
    default:
      return state;
  }
}
