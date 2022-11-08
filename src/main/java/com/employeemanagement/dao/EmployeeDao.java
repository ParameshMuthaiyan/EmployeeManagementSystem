package com.employeemanagement.dao;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.employeemanagement.exception.BusinessException;
import com.employeemanagement.model.Employee;
import com.employeemanagement.request.UpdateEmployeeRequest;
import com.employeemanagement.response.EmployeeResponseMessage;

public interface EmployeeDao {

	public Employee getEmployee(Authentication authentication) throws BusinessException;

	public Employee postEmployee(Employee employee) throws BusinessException;

	public List<Employee> showEmployeeMySQL() throws BusinessException;

	public List<Employee> showEmployeeMongoDB() throws BusinessException;

	public EmployeeResponseMessage updateEmployee(String jobTitle, String jobType, String email)
			throws BusinessException;

	public EmployeeResponseMessage deleteEmployee(String empId) throws BusinessException;

	public Employee updateDetails(UpdateEmployeeRequest employee, Authentication authentication)
			throws BusinessException;

	public List<Employee> pageList(Integer pageNo, Integer pageSize) throws BusinessException;

}
