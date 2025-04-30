package com.mada.expensetracker.dto;

import java.time.LocalDate;


public record ExpenseRequest(String name, Double amount, String description, String category, LocalDate date) { }
