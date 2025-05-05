import axios from "axios";

function DeleteExpenseModal({ expenses, onDelete }) {

    const handleDeleteExpense = async (id) => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.delete(`http://localhost:8080/api/expense/delete/${id}`,
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                })
            onDelete();
            console.log("Response ", response);
        } catch (error) {
            console.log("Error: ", error);
        }
        console.log("Clicked delete expense: ", id);
    }

    return (
        <div>
            <h2>Delete Expense</h2>
            <form onSubmit={handleDeleteExpense}>
                {expenses.map(expense => (
                    <div key={expense.id}>
                        <p className="Clickable" onClick={() => handleDeleteExpense(expense.id)}>Name: {expense.name}</p>
                    </div>
                ))}

            </form>
        </div>
    );
}

export default DeleteExpenseModal;