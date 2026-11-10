package com.wealthe.repository;
import com.wealthe.entity.Investment; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface InvestmentRepository extends JpaRepository<Investment,Long>{ List<Investment> findByUserIdOrderByIdDesc(Long userId); }
