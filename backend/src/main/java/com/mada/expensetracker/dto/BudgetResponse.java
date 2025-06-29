package com.mada.expensetracker.dto;

import java.time.LocalDate;

public record BudgetResponse(Long id, Double amount, LocalDate date) { }
