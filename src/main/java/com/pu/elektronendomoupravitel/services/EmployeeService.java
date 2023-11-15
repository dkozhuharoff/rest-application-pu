package com.pu.elektronendomoupravitel.services;

import com.pu.elektronendomoupravitel.model.Employee;
import com.pu.elektronendomoupravitel.repositories.EmployeeRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public List<Employee> fetchEmployees() {
        return employeeRepo.findAll();
    }

    public Optional<Employee> getEmployeeById(long id) {
        return employeeRepo.findById(id);
    }

    public Employee newEmployee(@RequestBody Employee employee) {
        return employeeRepo.save(employee);
    }

    public Optional<Employee> updateEmployee(long id, @RequestBody Employee employee) {
        return employeeRepo.findById(id).map(updateEmployee->{return employeeRepo.save(employee);});
    }

    public void deleteEmployeeById(long id) {
        employeeRepo.deleteById(id);
    }

    public void deleteEmployees(long id) {
        employeeRepo.deleteAll();
    }

}
