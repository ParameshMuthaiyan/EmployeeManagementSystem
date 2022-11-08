package com.employeemanagement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.employeemanagement.dao.EmployeeDao;
import com.employeemanagement.exception.BusinessException;
import com.employeemanagement.model.Employee;
import com.employeemanagement.repository.EmployeeRepositoryMySQL;
import com.employeemanagement.service.EmployeeService;

@SpringBootTest
class EmployeeMockitoTest {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeDao employeeDao;

	@MockBean
	private EmployeeRepositoryMySQL employeeRepository;

	@Test
	void testPostEmployee() throws BusinessException {
		Employee employee = new Employee();
		employee.setEmployeeId("100");
		employee.setEmployeeName("Dummy");
		employee.setUserName("Dummy_DT");

		Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
		Employee result = employeeDao.postEmployee(employee);
		assertThat(result.getEmployeeId()).isEqualTo(employee.getEmployeeId());

	}

	@Test
	void testGetAllEmployee() throws BusinessException {
		Employee employee1 = new Employee();
		employee1.setEmployeeId("100");
		employee1.setEmployeeName("Dummy");
		employee1.setUserName("Dummy_DT");
		
		Employee employee2 = new Employee();
		employee2.setEmployeeId("100");
		employee2.setEmployeeName("Dummy");
		employee2.setUserName("Dummy_DT");

		List<Employee> employeeList = new ArrayList<>();
		employeeList.add(employee1);
		employeeList.add(employee2);
		
		Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
		assertThat(employeeDao.showEmployeeMySQL()).isEqualTo(employeeList);
	}

	@Test
	void testDeleteEmployee() throws BusinessException {
	
		Employee employee=new Employee();
		employee.setId("10101010");
		
	    when(employeeRepository.findById(employee.getId())).thenReturn(Optional.of(employee));
	    employeeService.deleteEmployee(employee.getId());
	    verify(employeeRepository).deleteById(employee.getId());
	    
	}

	

}
