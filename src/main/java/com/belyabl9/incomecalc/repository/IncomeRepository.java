package com.belyabl9.incomecalc.repository;

import com.belyabl9.incomecalc.domain.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findAllByOrderByDateAsc();

    @Query("select inc from Income inc where inc.date between (:dateFrom) and (:dateTo) order by inc.date")
    List<Income> forPeriod(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
    
}
