package com.wealthe.repository;
import com.wealthe.entity.Budget; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface BudgetRepository extends JpaRepository<Budget,Long>{ List<Budget> findByUserIdOrderByEndDateDesc(Long userId); }
