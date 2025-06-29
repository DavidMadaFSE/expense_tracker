import './App.css';
import { useEffect, useState } from 'react';
import Login from "./components/Login";
import Register from "./components/Register";
import Expenses from "./components/Expenses";
import {
  BrowserRouter as Router,
  Routes,
  Route,
} from 'react-router-dom';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem('token');
    setIsAuthenticated(!!token);
  }, []);

  return (
    <Router>
      <div className="App">
        <h2>Expense Tracker Application</h2>
        <Routes>
          <Route path='/' element={<Login />} />
          <Route path='/register' element={<Register />} />
          <Route path='/expenses' element={<Expenses />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
