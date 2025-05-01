import './App.css';
import { useEffect, useState } from 'react';
import Login from "./components/Login";
import Register from "./components/Register";

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isRegisterClicked, setIsRegisterClicked] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    setIsAuthenticated(!!token);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    setIsAuthenticated(false);
  }

  return (
    <div className="App">
      <div>
        <h2>Expense Tracker Application</h2>
        {isAuthenticated ?
          <div><h2>Expense Page</h2> <button onClick={() => handleLogout()}>Logout</button></div> :
          isRegisterClicked ?
          <div>
          <Register onRegisterSuccess={() => setIsRegisterClicked(false)} />
          <p>Already have an account? <button onClick={() => setIsRegisterClicked(false)} className="Link">Sign in</button></p>
          </div> :
          <div>
          <Login onLoginSuccess={() => setIsAuthenticated(true)} />
          <p>Don't have an account? <button onClick={() => setIsRegisterClicked(true)} className="Link">Sign up</button></p>
          </div>}
      </div>
    </div>
  );
}

export default App;
