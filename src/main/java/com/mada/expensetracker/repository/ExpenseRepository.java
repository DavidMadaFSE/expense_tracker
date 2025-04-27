package com.mada.expensetracker.repository;

import com.mada.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long id);
    List<Expense> findByUserIdAndDateBetween(Long id, LocalDate startDate, LocalDate endDate);
}
