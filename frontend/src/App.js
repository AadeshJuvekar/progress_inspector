import logo from "./logo.svg";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  solid,
  regular,
  brands,
} from "@fortawesome/fontawesome-svg-core/import.macro"; // <-- import styles to be used
import "./App.css";

function App() {
  return (
    <div className="App">
      <header>
        <i class="fa-brands fa-github"></i>
        <FontAwesomeIcon icon={solid("user-secret")} />
        <p>Progress Inspector</p>
      </header>
    </div>
  );
}

export default App;
