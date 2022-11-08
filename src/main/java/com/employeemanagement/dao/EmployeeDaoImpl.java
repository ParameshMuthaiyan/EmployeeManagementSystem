package com.employeemanagement.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.employeemanagement.exception.BusinessException;
import com.employeemanagement.model.Address;
import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Person;
import com.employeemanagement.repository.EmployeeRepositoryMongoDB;
import com.employeemanagement.repository.EmployeeRepositoryMySQL;
import com.employeemanagement.request.UpdateEmployeeRequest;
import com.employeemanagement.response.EmployeeResponseMessage;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class EmployeeDaoImpl implements EmployeeDao {

	@Autowired
	private EmployeeRepositoryMySQL employeeRepositoryMySQL;

	@Autowired
	private EmployeeRepositoryMongoDB employeeRepositoryMongoDB;

	@Override
	public Employee postEmployee(Employee employee) throws BusinessException {
		Employee emp = new Employee();
		try {
			BeanUtils.copyProperties(employee, emp);
			emp.setUserName(employee.getEmployeeName() + "_DT");
			employeeRepositoryMySQL.save(emp);
			employeeRepositoryMongoDB.save(emp);
			log.info("Data Passed POST method in DAOImpl Layer");
		} catch (Exception e) {
			log.error("Error Occur on POST method in DAOImpl Layer");
			throw new BusinessException(e.getMessage());
		}
		return emp;
	}

	@Override
	public Employee getEmployee(Authentication authentication) throws BusinessException {
		Employee emp = new Employee();
		try {
			emp = employeeRepositoryMySQL.findByUserName(authentication.getName());
			log.info("Data Passed GET method in DAOImpl Layer");
		} catch (Exception e) {
			log.error("Error Occur on GET method in DAOImpl Layer");
			throw new BusinessException(e.getMessage());
		}
		return emp;
	}

	@Override
	public EmployeeResponseMessage updateEmployee(String jobTitle, String jobType, String email)
			throws BusinessException {
		try {
			Employee emp = employeeRepositoryMySQL.findByEmailId(email);
			if ((!ObjectUtils.isEmpty(emp))) {
				emp.setJobTitle(jobTitle);
				emp.setJobType(jobType);
			}
			employeeRepositoryMySQL.saveAndFlush(emp);
			log.info("Data Passed UPDATE method in DAOImpl Layer");
		} catch (Exception e) {
			log.error("Error Occur on UPDATE method in DAOImpl Layer");
			throw new BusinessException(e.getMessage());
		}
		return new EmployeeResponseMessage("Updated Successfully");
	}

	@Override
	public EmployeeResponseMessage deleteEmployee(String id) throws BusinessException {
		try {
			employeeRepositoryMySQL.deleteById(id);
			log.info("Data Passed DELETE method in DAOImpl Layer");

		} catch (Exception e) {
			log.error("Error Occur on DELETE method in DAOImpl Layer");
			throw new BusinessException(e.getMessage());
		}
		return new EmployeeResponseMessage("Successfully Deleted");
	}

	@Override
	public List<Employee> showEmployeeMySQL() throws BusinessException {
		List<Employee> empList = new ArrayList<>();
		try {
			employeeRepositoryMySQL.findAll().forEach(empList::add);
			log.info("Data Passed GET ALL method in DAOImpl Layer");
		} catch (Exception e) {
			log.error("Error Occur on GETALL method in DAOImpl Layer");
			throw new BusinessException(e.getMessage());
		}
		return empList;
	}

	@Override
	public List<Employee> showEmployeeMongoDB() throws BusinessException {
		List<Employee> empList = new ArrayList<>();
		try {
			employeeRepositoryMongoDB.findAll().forEach(empList::add);
			log.info("Data Passed GET ALL MongoDB method in DAOImpl Layer");
		} catch (Exception e) {
			log.error("Error Occur on GETALL MongoDB method in DAOImpl Layer");
			throw new BusinessException(e.getMessage());
		}
		return empList;
	}

	@Override
	public Employee updateDetails(UpdateEmployeeRequest employee, Authentication authentication)
			throws BusinessException {
		Employee emp = employeeRepositoryMySQL.findByUserName(authentication.getName());
		try {
			List<Address> ad = new ArrayList<>();
			ad.addAll(emp.getAddressList());
			for(Address addType: ad) {
				if(addType.getAddressType().equals(employee.getAddressType())) {
					
					if (employee.getAddressType() != null) {
						addType.setAddressType(employee.getAddressType());
					}
					if (employee.getAddressLine1() != null) {
						addType.setAddressLine1(employee.getAddressLine1());
					}
					if (employee.getAddressLine2() != null) {
						addType.setAddressLine2(employee.getAddressLine2());
					}
					if (employee.getCity() != null) {
						addType.setCity(employee.getCity());
					}
					if (employee.getZipcode() != null) {
						addType.setZipcode(employee.getZipcode());
					}
					if (employee.getState() != null) {
						addType.setState(employee.getState());
					}
					if (employee.getCountry() != null) {
						addType.setCountry(employee.getCountry());
					}
					if (employee.getContactNo() != null) {
						addType.setContactNo(employee.getContactNo());
					}
					emp.setAddressList(ad);
				}
			}
			
			Person person= emp.getPerson();
			if (employee.getPersonName() != null) {
                person.setPersonName(employee.getPersonName());
            }
            if (employee.getDateOfBirth() != null) {
            	person.setDateOfBirth(employee.getDateOfBirth());
            }
            if (employee.getGender() != null) {
            	person.setGender(employee.getGender());
            }
            if (employee.getAge() != null) {
            	person.setAge(employee.getAge());
            }
            if (employee.getQualification() != null) {
            	person.setQualification(employee.getQualification());
            }
            if (employee.getPlace() != null) {
            	person.setPlace(employee.getPlace());
            }
            if (employee.getEmailId() != null) {
            	person.setEmailId(employee.getEmailId());
            }
            if (employee.getMobileNo() != null) {
            	person.setContactNo(employee.getMobileNo());
            }
            
			emp.setPerson(person);
			
			employeeRepositoryMySQL.save(emp);
			log.info("Data Passed UPDATE method in DAOImpl Layer");
		} catch (Exception e) {
			log.error("Error Occur on UPDATE method in DAOImpl Layer");
			throw new BusinessException(e.getMessage());
		}
		return emp;
	}

	@Override
	public List<Employee> pageList(Integer pageNo, Integer pageSize) throws BusinessException {
		List<Employee> pageResult;
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize);
			pageResult = employeeRepositoryMySQL.findAll(paging).toList();
			log.info("Data Passed GET PageList method in DAOImpl Layer");
		} catch (Exception e) {
			log.error("Error Occur on PageList method in DAOImpl Layer");
			throw new BusinessException(e.getMessage());
		}
		return pageResult;

	}

}
