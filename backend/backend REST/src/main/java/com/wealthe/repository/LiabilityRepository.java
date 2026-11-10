package com.wealthe.repository;
import com.wealthe.entity.Liability; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface LiabilityRepository extends JpaRepository<Liability,Long>{ List<Liability> findByUserIdOrderByIdDesc(Long userId); }
