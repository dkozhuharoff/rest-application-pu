package com.pu.elektronendomoupravitel.repositories;

import com.pu.elektronendomoupravitel.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
}
