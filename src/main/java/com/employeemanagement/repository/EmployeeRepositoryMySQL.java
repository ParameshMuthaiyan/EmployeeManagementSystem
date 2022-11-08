package com.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemanagement.model.Employee;

public interface EmployeeRepositoryMySQL extends JpaRepository<Employee, String>{

	public Employee findByUserName(String username);

	public Employee findByEmailId(String email);

}
