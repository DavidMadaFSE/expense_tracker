import { useState } from "react";
import axios from "axios";

function CreateExpenseModal() {
    const [name, setName] = useState("");
    const [amount, setAmount] = useState(0.0);
    const [description, setDescription] = useState("");
    const [category, setCategory] = useState("");
    const [date, setDate] = useState("");

    const handleCreateExpense = async () => {
        try {
            const token = localStorage.getItem('token');
            const expenseData = {
                name: name,
                amount: amount,
                description: description,
                category: category,
                date: date,
            }
            const response = await axios.post("http://localhost:8080/api/expense/create",
                expenseData,
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });

            console.log("Response: ", response.data);

        } catch (error) {
            console.log("Error: ", error);
        }
    }

    return (
        <div>
            <h2>Create Expense</h2>
            <form onSubmit={handleCreateExpense}>
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

export default CreateExpenseModal;