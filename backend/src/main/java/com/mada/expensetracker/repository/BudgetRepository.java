package com.mada.expensetracker.repository;

import com.mada.expensetracker.entity.Budget;
import com.mada.expensetracker.entity.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    @Query("SELECT b FROM Budget b WHERE b.user.id = :userId AND FUNCTION('MONTH', b.date) = :month AND FUNCTION('YEAR', b.date) = :year")
    List<Budget> findByUserIdAndMonthAndYear(@Param("userId") Long userId, @Param("month") int month,  @Param("year") int year);

    List<Budget> findByUserId(Long userId);
}
