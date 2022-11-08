package com.employeemanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.exception.BusinessException;
import com.employeemanagement.model.Employee;
import com.employeemanagement.response.EmployeeResponseMessage;
import com.employeemanagement.service.EmployeeService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/v1")
@Log4j2
public class HRController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * This is Get API for show all Employee Details from the MySQL DataBase. Access
	 * for HR only
	 * 
	 * @return List<Employee>
	 * @throws BusinessException
	 */
	@GetMapping("/mySql/showEmployee")
	@PreAuthorize("hasRole('HR')")
	public List<Employee> showEmployeeMySql() throws BusinessException {
		List<Employee> empList = null;
		try {
			empList = employeeService.showEmployeeMySQL();
			log.info("Successfly Passed To GET ALL() Method in Controller Layer.");
		} catch (BusinessException e) {
			log.error("Error was occur in GET ALL() method in Controller Layer");
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
		return empList;
	}

	/***
	 * This is Get API for show all Employee Details from the MongoDB DataBase.
	 * Access for HR only
	 * 
	 * @return List<Employee>
	 * @throws BusinessException
	 */
	@GetMapping("/mongoDB/showEmployee")
	@PreAuthorize("hasRole('HR')")
	public List<Employee> showEmployeeMongoDB() throws BusinessException {
		List<Employee> empList = null;
		try {
			empList = employeeService.showEmployeeMongoDB();
			log.info("Successfly Passed To showEmployeeMongoDB() Method in Controller Layer.");
		} catch (BusinessException e) {
			log.error("Error was occur in showEmployeeMongoDB() method in Controller Layer");
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
		return empList;
	}

	/****
	 * This is POST API for Employee Details from the MySQL DataBase. HR has posted
	 * only Employee Details
	 * 
	 * @param employee
	 * @return Employee Object
	 * @throws BusinessException
	 */
	@PostMapping("/postEmployee")
	@PreAuthorize("hasRole('HR')")
	public Employee postEmployee(@RequestBody Employee employee) throws BusinessException {
		Employee emp = null;
		try {
			emp = employeeService.postEmployee(employee);
			log.info("Successfly Passed To Post Method in Controller Layer.");
		} catch (BusinessException e) {
			log.error("Error was occur in POST() method in Controller Layer");
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
		return emp;
	}

	/***
	 * This is PUT API for Update Employee Details from the MySQL DataBase. HR
	 * has Updated Employee Details only.
	 * @param jobTitle
	 * @param jobType
	 * @param email
	 * @return Valid message
	 * @throws BusinessException
	 */
	@PutMapping("/updateEmployee")
	@PreAuthorize("hasRole('HR')")
	public EmployeeResponseMessage updateEmployee(@RequestParam String jobTitle, @RequestParam String jobType,
			@RequestParam String email) throws BusinessException {
		EmployeeResponseMessage message;
		try {
			message = employeeService.updateEmployee(jobTitle, jobType, email);
			log.info("Successfly Passed To PUT Method in Controller Layer.");
		} catch (BusinessException e) {
			log.error("Error was occur in PUT() method in Controller Layer");
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
		return message;
	}

	/***
	 * This is Delete API for Delete Employee Details from the MySQL DataBase. HR
	 * will remove Employee All record from DataBase.
	 * 
	 * @param id
	 * @return valid message.
	 * @throws BusinessException
	 */
	@DeleteMapping("/deleteEmployee")
	@PreAuthorize("hasRole('HR')")
	public EmployeeResponseMessage deleteEmployee(@RequestParam String id) throws BusinessException {
		EmployeeResponseMessage message;
		try {
			message = employeeService.deleteEmployee(id);
			log.info("Successfly Passed To DELETE Method in Controller Layer.");
		} catch (BusinessException e) {
			log.error("Error was occur in DELETE() method in Controller Layer");
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
		return message;
	}

	/***
	 * This is Get API for Employee Details for showing Page No vice from the MySQL
	 * DataBase.
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return List<Employee>
	 * @throws BusinessException
	 */
	@GetMapping("/pagination")
	@PreAuthorize("hasRole('HR')")
	public List<Employee> pageList(@RequestParam Integer pageNo, @RequestParam Integer pageSize)
			throws BusinessException {
		List<Employee> pageList = null;
		try {
			pageList = employeeService.pageList(pageNo, pageSize);
			log.info("Successfly Passed To GET PageList() Method in Controller Layer.");
		} catch (BusinessException e) {
			log.error("Error was occur in GET PageList() method in Controller Layer");
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
		return pageList;
	}
}
