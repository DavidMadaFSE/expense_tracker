import { useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

function Register({ onRegisterSuccess }) {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [passwordSame, setPasswordSame] = useState("");
    const [fullName, setFullName] = useState("");

    const handleRegister = async (e) => {
        e.preventDefault();

        if (password !== passwordSame) {
            console.log("Different passwords");
            return;
        }

        try {
            const response = await axios.post("http://localhost:8080/api/auth/register", {
                email,
                password,
                fullName
            });
            console.log("Created Account");
            console.log("Response: " + response);
            onRegisterSuccess();
        } catch (error) {
            console.log("Error: " + error);
        }
    }

    return (
        <div>
            <h2>Register Screen</h2>
            <form onSubmit={handleRegister}>
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
                <input
                    type="password"
                    value={passwordSame}
                    onChange={e => setPasswordSame(e.target.value)}
                    placeholder="Re-type Password"
                    required
                />
                <br/>
                <input
                    type="text"
                    value={fullName}
                    onChange={e => setFullName(e.target.value)}
                    placeholder="Full Name"
                    required
                />
                <br/>
                <button type="submit">Submit</button>
            </form>
            <p>Already have an account? <Link to='/'>Sign in</Link></p>
        </div>
    );
}

export default Register;