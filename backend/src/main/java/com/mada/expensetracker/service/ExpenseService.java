package com.mada.expensetracker.service;

import java.time.LocalDate;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.mada.expensetracker.dto.ExpenseRequest;
import com.mada.expensetracker.dto.ExpenseResponse;
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
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

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

    // Gets all expenses from a user
    public List<ExpenseResponse> getAllExpenses(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        List<Expense> expenses = expenseRepository.findByUser(user);

        List<ExpenseResponse> response = expenses.stream()
                .map(expense -> new ExpenseResponse(
                        expense.getId(),
                        expense.getName(),
                        expense.getAmount(),
                        expense.getDescription(),
                        expense.getCategory(),
                        expense.getDate()))
                .toList();

        System.out.println("All expenses retrieved");

        return response;
    }

    public List<ExpenseResponse> getExpensesByDate(String email, int month, int year) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        LocalDate startDate = LocalDate.of(year, month, 1);
        int nextMonth = month == 12 ? 1 : month + 1;
        LocalDate endDate = LocalDate.of(year, nextMonth, 1);
        List<Expense> expenses = expenseRepository.findByUserIdAndDateBetween(user.getId(), startDate, endDate); 
        List<ExpenseResponse> response = new ArrayList();

        if (expenses.isEmpty()) {
            System.out.println("No expenses retrieved for the given month");
            return response;
        }

        response = expenses.stream()
            .map(expense -> new ExpenseResponse(
                expense.getId(),
                expense.getName(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getCategory(),
                expense.getDate()
            )).toList();

        System.out.println("All expenses retrived for the given month");

        return response;
    }

    // Get a specific expense from a user
    public ExpenseResponse getExpense(String email, Long expenseId) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expense not found"));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to view this expense");
        }
        
        return new ExpenseResponse(
            expense.getId(),
            expense.getName(),
            expense.getAmount(),
            expense.getDescription(),
            expense.getCategory(),
            expense.getDate());
    }

    // Deletes a specific expense from a user7
    public void deleteExpense(String email, Long expenseId) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expense not found"));
        
        if (!expense.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this expense");
        }

        expenseRepository.delete(expense);

        System.out.println("Expense deleted successfully");
    }

    // Updates an existing expense
    public void updateExpense(String email, ExpenseRequest request, Long expenseId) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Expense expense = expenseRepository.findById(expenseId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expense not found"));
        
        if (!expense.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to edit this expense");
        }

        expense.setName(request.name());
        expense.setAmount(request.amount());
        expense.setDescription(request.description());
        expense.setCategory(request.category());
        expense.setDate(request.date());

        expenseRepository.save(expense);

        System.out.println("Expense updated successfully");
    }
}
