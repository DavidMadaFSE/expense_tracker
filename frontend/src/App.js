import './App.css';
import { useEffect, useState } from 'react';
import Login from "./components/Login";

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    setIsAuthenticated(!!token);
  }, []);

  return (
    <div className="App">
      <div>
        <h2>Expense Tracker Application</h2>
        {isAuthenticated ? <h2>Expense Page</h2> : <Login onLoginSuccess={() => setIsAuthenticated(true)} />}
      </div>
    </div>
  );
}

export default App;
