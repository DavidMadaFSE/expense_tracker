import {
    useEffect,
    useState
} from "react";
import axios from "axios";
import CreateExpenseModal from "./CreateExpenseModal";
import DeleteExpenseModal from "./DeleteExpenseModal";
import UpdateExpenseModal from "./UpdateExpenseModal";
import { useNavigate } from "react-router-dom";

function Expenses() {
    const [expenses, setExpenses] = useState([]);
    const [updateExpense, setUpdateExpense] = useState();
    const [isCreateExpenseShowing, setIsCreatingExpenseShowing] = useState(false);
    const [isDeleteExpenseShowing, setIsDeleteExpenseShowing] = useState(false);
    const [isUpdatingExpenseShowing, setIsUpdatingExpenseShowing] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchExpenses = async () => {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get("http://localhost:8080/api/expense", {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

                setExpenses(response.data);
                console.log("response: ", JSON.stringify(response.data));
            } catch (error) {
                console.log("Error: ", error);
            }
        };

        fetchExpenses();
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate("/");
    }

    const handleUpdateExpense = (expense) => {
        setIsUpdatingExpenseShowing(true);
        setUpdateExpense(expense);
    }

    return (
        <div>
            <h2>My Expense Page</h2>
            {expenses.map(expense => (
                <div key={expense.id}>
                    <p>Name: {expense.name}</p>
                    <p>Amount: ${expense.amount}</p>
                    <p>Description: {expense.description}</p>
                    <p>Category: {expense.category}</p>
                    <p>Date: {expense.date}</p>
                    <button onClick={() => handleUpdateExpense(expense)}>Edit expense</button>
                    <br/>
                </div>
            ))}
            <br/>
            {isUpdatingExpenseShowing && (
                <div className="Modal-Backdrop">
                    <div className="Modal">
                        <UpdateExpenseModal expense={updateExpense} />
                        <button onClick={() => setIsUpdatingExpenseShowing(false)}>Back</button>
                    </div>
                </div>
            )}
            <button onClick={() => setIsCreatingExpenseShowing(true)}>Create expense</button>
            {isCreateExpenseShowing && (
                <div className="Modal-Backdrop">
                    <div className="Modal">
                        <CreateExpenseModal />
                        <button onClick={() => setIsCreatingExpenseShowing(false)}>Back</button>
                    </div>
                </div>
            )}
            <button onClick={() => setIsDeleteExpenseShowing(true)}>Delete expense</button>
            {isDeleteExpenseShowing && (
                <div className="Modal-Backdrop">
                    <div className="Modal">
                        <DeleteExpenseModal expenses={expenses} onDelete={() => setIsDeleteExpenseShowing(false)} />
                        <button onClick={() => setIsDeleteExpenseShowing(false)}>Back</button>
                    </div>
                </div>
            )}
            <button onClick={() => handleLogout()}>Logout</button>
        </div>
    );
}

export default Expenses;