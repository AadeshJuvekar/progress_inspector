import "./App.css";
import Topbar from "./components/topbar/Topbar";
import Footer from "./components/footer/Footer";
import Login from "./components/pages/login/Login";
import Landing from "./components/pages/home/Landing";

function App() {
  return (
    <div className="App">
      <Topbar />
      {/* <Landing/> */}
      <Login />
      <Footer />
    </div>
  );
}
export default App;
