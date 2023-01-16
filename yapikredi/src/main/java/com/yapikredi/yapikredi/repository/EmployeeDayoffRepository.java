package com.yapikredi.yapikredi.repository;

import com.yapikredi.yapikredi.entity.EmployeeDayoff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDayoffRepository extends JpaRepository<EmployeeDayoff, Long> {

        EmployeeDayoff findEmployeeDayoffsByStatus(String status);
}

