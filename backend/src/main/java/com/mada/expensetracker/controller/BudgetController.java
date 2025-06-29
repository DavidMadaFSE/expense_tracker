package com.mada.expensetracker.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mada.expensetracker.service.BudgetService;
import com.mada.expensetracker.repository.BudgetRepository;
import com.mada.expensetracker.dto.BudgetRequest;
import com.mada.expensetracker.dto.BudgetResponse;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {
    private BudgetService budgetService;
    private BudgetRepository budgetRepository;

    public BudgetController(BudgetService budgetService, BudgetRepository budgetRepository) {
        this.budgetService = budgetService;
        this.budgetRepository = budgetRepository;
    }

    @PostMapping("/create")
    public void createBudget(@RequestBody BudgetRequest request) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Creating a budget");
        budgetService.createBudget(request, userEmail);
    }

    @GetMapping("/get/{budgetId}")
    public BudgetResponse getBudget(@PathVariable Long budgetId) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("Fetching budget");
        return budgetService.getBudget(budgetId, userEmail);
    }

}
