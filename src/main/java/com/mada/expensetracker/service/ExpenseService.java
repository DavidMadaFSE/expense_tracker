package com.mada.expensetracker.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mada.expensetracker.dto.ExpenseRequest;
import com.mada.expensetracker.entity.Expense;
import com.mada.expensetracker.entity.User;
import com.mada.expensetracker.repository.ExpenseRepository;
import com.mada.expensetracker.repository.UserRepository;

@Service
public class ExpenseService {
    UserRepository userRepository;
    ExpenseRepository expenseRepository;

    public ExpenseService(UserRepository userRepository, ExpenseRepository expenseRepository) {
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    // Creates an expense for the given user
    public void createExpense(ExpenseRequest request, String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User was not found."));

        Expense expense = new Expense();
        
        expense.setName(request.name());
        expense.setDescription(request.description());
        expense.setAmount(request.amount());
        expense.setDate(request.date());
        expense.setCategory(request.category());
        expense.setUser(user);

        expenseRepository.save(expense);

        System.out.println("Expense Created!");
    }
}
