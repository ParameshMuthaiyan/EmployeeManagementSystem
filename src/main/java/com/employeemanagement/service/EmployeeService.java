package com.employeemanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.employeemanagement.dao.EmployeeDao;
import com.employeemanagement.exception.BusinessException;
import com.employeemanagement.model.Employee;
import com.employeemanagement.request.UpdateEmployeeRequest;
import com.employeemanagement.response.EmployeeResponseMessage;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private EmployeeValidation employeeValidation;

	public Employee postEmployee(Employee employee) throws BusinessException {
		Employee emp = null;
		try {
			employeeValidation.checkValidName(employee.getEmployeeName());
			emp = employeeDao.postEmployee(employee);
			log.info("Data Passed POST method in Service Layer");
		} catch (BusinessException e) {
			log.error("Error Occur on POST method in Service Layer");
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
		return emp;
	}

	
	public Employee getEmployee(Authentication authentication) throws BusinessException {
		Employee emp = null;
		try {
			emp = employeeDao.getEmployee(authentication);
			log.info("Data Passed GET method in Service Layer");

		} catch (BusinessException e) {
			log.error("Error Occur on GET method in Service Layer");
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
		return emp;
	}

	
	public List<Employee> showEmployeeMySQL() throws BusinessException {
		List<Employee> empList=null;
		try {
			empList=employeeDao.showEmployeeMySQL();
			log.info("Data Passed GET method in Service Layer");

		} catch (BusinessException e) {
			log.error("Error Occur on GET method in Service Layer");
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
		return empList;
	}

	public List<Employee> showEmployeeMongoDB() throws BusinessException {
		List<Employee> empList=null;
		try {
			empList=employeeDao.showEmployeeMongoDB();
			log.info("Data Passed showEmployeeMongoDB() method in Service Layer");

		} catch (BusinessException e) {
			log.error("Error Occur on showEmployeeMongoDB() method in Service Layer");
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
		return empList;	}
	
	public EmployeeResponseMessage updateEmployee(String jobTitle, String jobType, String email)
			throws BusinessException {
		EmployeeResponseMessage message;
		try {
			message = employeeDao.updateEmployee(jobTitle, jobType, email);
			log.info("Data Passed UPDATE method in Service Layer");

		} catch (BusinessException e) {
			log.error("Error Occur on UPDATE method in Service Layer");
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
		return message;
	}

	
	public EmployeeResponseMessage deleteEmployee(String id) throws BusinessException {
		EmployeeResponseMessage message;
		try {
			message = employeeDao.deleteEmployee(id);
			log.info("Data Passed DELETE method in Service Layer");

		} catch (BusinessException e) {
			log.error("Error Occur on DELETE method in Service Layer");
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
		return message;
	}


	public Employee updateDetails(UpdateEmployeeRequest employee,Authentication authentication) throws BusinessException{
		Employee emp=null;
		try {
			emp=employeeDao.updateDetails(employee,authentication);
			log.info("Data Successfully passed in PUT method in Service Layer");		
		} catch (BusinessException e) {
			log.error("Error was occur in PUT() method in Service Layer");
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
		return emp;
	}


	public List<Employee> pageList(Integer pageNo,Integer pageSize) throws BusinessException{
		List<Employee> pageList = null;
		try {
			pageList = employeeDao.pageList(pageNo,pageSize);
			log.info("Successfly Passed To GET PageList() Method in Service Layer.");
		} catch (BusinessException e) {
			log.error("Error was occur in GET PageList() method in Service Layer");
			throw new BusinessException(e.getErrorCode(), e.getMessage());
		}
		return pageList;
	}


	
}
