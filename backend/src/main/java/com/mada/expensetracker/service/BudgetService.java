package com.mada.expensetracker.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mada.expensetracker.repository.UserRepository;
import com.mada.expensetracker.repository.ExpenseRepository;
import com.mada.expensetracker.repository.BudgetRepository;
import com.mada.expensetracker.entity.User;
import com.mada.expensetracker.entity.Budget;
import com.mada.expensetracker.dto.BudgetRequest;
import com.mada.expensetracker.dto.BudgetResponse;

@Service
public class BudgetService {
    UserRepository userRepository;
    ExpenseRepository expenseRepository;
    BudgetRepository budgetRepository;

    public BudgetService(UserRepository userRepository,
            ExpenseRepository expenseRepository,
            BudgetRepository budgetRepository) {
                this.userRepository = userRepository;
                this.expenseRepository = expenseRepository;
                this.budgetRepository = budgetRepository;
    }

    // Creates a budget for the given month
    public void createBudget(BudgetRequest request, String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found."));
        
        LocalDate date = request.date();
        int month = date.getMonthValue();
        int year = date.getYear();
        
        List<Budget> existingBudgets = budgetRepository.findByUserIdAndMonthAndYear(user.getId(), month, year);

        if (!existingBudgets.isEmpty()) {
            System.out.println("Budget already exists for this month");
            return;
        }

        Budget budget = new Budget();

        budget.setAmount(request.amount());
        budget.setDate(request.date());
        budget.setUser(user);

        budgetRepository.save(budget);

        System.out.println("Budget Created!");
    }

    // Gets a budget for a month
    public BudgetResponse getBudget(Long budgetId, String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Budget budget = budgetRepository.findById(budgetId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expense not found"));
        
        if (!budget.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are not allowed to view this budget");
        }

        return new BudgetResponse(
            budget.getId(),
            budget.getAmount(),
            budget.getDate());
    }

}
