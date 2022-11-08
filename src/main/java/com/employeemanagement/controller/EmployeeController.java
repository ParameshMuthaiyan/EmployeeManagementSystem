package com.employeemanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.exception.BusinessException;
import com.employeemanagement.model.Employee;
import com.employeemanagement.request.UpdateEmployeeRequest;
import com.employeemanagement.service.EmployeeService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/v1")
@Log4j2
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/***
	 * This is GET API for User side are Login that particular user details only
	 * showing
	 * 
	 * @param authentication
	 * @return Showing Login User Details
	 * @throws BusinessException
	 */
	@GetMapping("/getEmployee")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public Employee getEmployee(Authentication authentication) throws BusinessException {
		Employee emp = null;
		try {
			emp = employeeService.getEmployee(authentication);
			log.info("Data Successfully passed in GET method in EmployeeController Layer");
		} catch (BusinessException e) {
			log.error("Error was occur in GET() method in EmployeeController Layer");
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
		return emp;
	}

	
	/**
	 * This is PUT API for User side that particular user has Update the Personal/
	 * Address details.
	 * 
	 * @param employee
	 * @param authentication
	 * @return Showing Login User Details
	 * @throws BusinessException
	 */
	@PutMapping("/updateDetails")
	@PreAuthorize("hasRole('EMPLOYEE')")
	public Employee updateDetails(@RequestBody UpdateEmployeeRequest employee, Authentication authentication)
			throws BusinessException {
		Employee emp = null;
		try {
			emp = employeeService.updateDetails(employee, authentication);
			log.info("Data Successfully passed in PUT method in EmployeeController Layer");
		} catch (BusinessException e) {
			log.error("Error was occur in PUT() method in EmployeeController Layer");
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
		return emp;
	}

}
