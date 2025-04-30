package com.mada.expensetracker.dto;

import java.time.LocalDate;

public record ExpenseResponse(Long id, String name, Double amount, String description, String category, LocalDate date) { }
