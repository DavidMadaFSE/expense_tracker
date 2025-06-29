package com.mada.expensetracker.dto;

import java.time.LocalDate;

public record BudgetRequest(Double amount, LocalDate date) { }
