import { GET_OWNERS } from "../actions/types";

const initialState = {
  owners: [],
  owner: {},
};
export default function(state = initialState, action) {
  switch (action.type) {
    case GET_OWNERS:
      return {
        ...state,
        owners: action.payload,
      };
    default:
      return state;
  }
}
