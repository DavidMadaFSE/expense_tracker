import {
    useState,
    useEffect
} from "react";
import axios from "axios";

function UpdateExpenseModal({ expense }) {
    const [expenseId, setExpenseId] = useState();
    const [name, setName] = useState("");
    const [amount, setAmount] = useState(0.0);
    const [description, setDescription] = useState("");
    const [category, setCategory] = useState("");
    const [date, setDate] = useState("");

    useEffect(() => {
        setExpenseId(expense.id);
        setName(expense.name);
        setAmount(expense.amount);
        setDescription(expense.description);
        setCategory(expense.category);
        setDate(expense.date);
    }, [])

    const handleUpdateExpense = async () => {
        const token = localStorage.getItem('token');
        const expenseData = {
            name: name,
            amount: amount,
            description: description,
            category: category,
            date: date
        }

        try {
            const response = await axios.put(`http://localhost:8080/api/expense/update/${expenseId}`,
                expenseData,
                {
                    headers: {
                        Authorization: `Bearer ${token}`
                    }
                }
            );
            
            console.log("Response: ", response);
        } catch (error) {
            console.log("Error: ", error);
        }
    }

    return (
        <div>
            <h2>Update Expense</h2>
            <form onSubmit={handleUpdateExpense}>
                <input
                    type="text"
                    value={name}
                    onChange={e => setName(e.target.value)}
                    placeholder="Name"
                />
                <input
                    type="text"
                    value={amount}
                    onChange={e => setAmount(e.target.value)}
                    placeholder="Amount"
                />
                <input
                    type="text"
                    value={description}
                    onChange={e => setDescription(e.target.value)}
                    placeholder="Description"
                />
                <input
                    type="text"
                    value={category}
                    onChange={e => setCategory(e.target.value)}
                    placeholder="Category"
                />
                <input
                    type="text"
                    value={date}
                    onChange={e => setDate(e.target.value)}
                    placeholder="Date"
                />
                <br />
                <button type="submit">Submit</button>
            </form>
        </div>
    );
}

export default UpdateExpenseModal;