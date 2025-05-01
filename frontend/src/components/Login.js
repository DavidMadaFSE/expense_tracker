import "../App.css";
import { useState } from "react";
import axios from "axios";

function Login({ onLoginSuccess }) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [errorMessage, setErrorMessage] = useState(false);

    // Trys to login the user with the given credentials
    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post("http://localhost:8080/api/auth/login", {
                email,
                password
            });

            localStorage.setItem('token', response.data.token);
            setErrorMessage(false);
            onLoginSuccess();
        } catch (error) {
            console.log("Error: " + error);
            setErrorMessage(true);
        }
    }

    return (
        <div>
            <h2>Login Screen</h2>
            <form onSubmit={handleLogin}>
                <input 
                    type="email"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                    placeholder="Email"
                    required
                />
                <br/>
                <input 
                    type="password"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                    placeholder="Password"
                    required
                />
                <br/>
                <button type="submit">Submit</button>
            </form>
            {errorMessage && <h2 className="Error">Invalid credentials</h2>}
        </div>
    );
}

export default Login;