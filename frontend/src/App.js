import React from "react";
import "./App.css";
import RouteLinks from "./RouteLinks";
import store from "./store";
import { Provider } from "react-redux";
import history from "./history";

function App() {
  return (
    <div className="App">
      <Provider store={store}>
        <div className="App">
          <RouteLinks history={history}/>
        </div>
      </Provider>
    </div>
  );
}
export default App;
