package com.mada.expensetracker.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mada.expensetracker.dto.ExpenseRequest;
import com.mada.expensetracker.dto.ExpenseResponse;
import com.mada.expensetracker.service.ExpenseService;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/create")
    public void createExpense(@RequestBody ExpenseRequest request) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Creating an expense");
        expenseService.createExpense(request, userEmail);
    }

    @GetMapping("")
    public List<ExpenseResponse> getAllExpenses() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Fetching all expenses");
        return expenseService.getAllExpenses(userEmail);
    }

    @GetMapping("/{expenseId}")
    public ExpenseResponse getExpense(@PathVariable Long expenseId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Fetching expense " + expenseId);
        return expenseService.getExpense(userEmail, expenseId);
    }
}