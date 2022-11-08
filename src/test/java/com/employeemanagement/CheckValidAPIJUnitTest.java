package com.employeemanagement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.employeemanagement.model.Address;
import com.employeemanagement.model.AuthenticationToken;
import com.employeemanagement.model.Employee;
import com.employeemanagement.model.Person;
import com.employeemanagement.request.LoginRequest;
import com.employeemanagement.request.SignupRequest;
import com.employeemanagement.response.Response;

import net.bytebuddy.utility.RandomString;

@SpringBootTest
public class CheckValidAPIJUnitTest {

	String authenicationToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBbXV5YW4iLCJpYXQiOjE2NjA2NTQ2MjAsImV4cCI6MTY2MTUxODYyMH0.Nz78_3B9zALou0jiGuJ2ciclKEp64CMDizXfH1n0nKjQU9JCOzG1Kxuk7mkincKvabxNPx2Ebr0cGyraQFa-sQ";
	String employeeToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJQYXJhbWVzaF9EVCIsImlhdCI6MTY2MDY1NTAxMiwiZXhwIjoxNjYxNTE5MDEyfQ.Q3gDs29lmkBIzhJPZwhb65waxWwP8nn_HVzPhGk2a8ZpKX-EzlAce2R5la4TzpaCht3eg0f579xAwLK0_BUfJw";

	@Test
	void validSignupEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		SignupRequest signup = new SignupRequest();
		signup.setUsername(RandomString.make(7) + "_DT");
		signup.setPassword("123456");
		signup.setEmail(RandomString.make(7) + "@gmail.com");
		final String baseUrl = "http://localhost:8080/v1/signup";
		ResponseEntity<Response> result = restTemplate.postForEntity(baseUrl, signup, Response.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	void validSigninEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		LoginRequest login = new LoginRequest();
		login.setUsername("Amuyan");
		login.setPassword("123456");
		final String baseUrl = "http://localhost:8080/v1/signin";
		ResponseEntity<Response> result = restTemplate.postForEntity(baseUrl, login, Response.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	void validGetMapping() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(authenicationToken);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:8080/v1/mySql/showEmployee";
		ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.GET, request, Response.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	void validGetMappingEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(employeeToken);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:8080/v1/getEmployee";
		ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.GET, request, Response.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	void validPostMapping() {
		RestTemplate restTemplate = new RestTemplate();
		Employee employee = new Employee();
		employee.setEmployeeId("102");
		employee.setEmployeeName("HarishKumar");
		employee.setUserName(employee.getEmployeeName() + "_DT");
		employee.setJobTitle("JavaDeveloper");
		employee.setJobType("Temperory");
		employee.setEmailId(RandomString.make(5) + "@gmail.com");
		employee.setMobilePhone("9090909090");
		employee.setDateOfJoining(LocalDate.now());

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(authenicationToken);
		HttpEntity request = new HttpEntity(employee, headers);
		final String baseUrl = "http://localhost:8080/v1/postEmployee";
		ResponseEntity<AuthenticationToken> result = restTemplate.exchange(baseUrl, HttpMethod.POST, request,
				AuthenticationToken.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	void validPutMapping() {
		RestTemplate restTemplate = new RestTemplate();
		Employee employee = new Employee();
		employee.setJobTitle("RDeveloper");
		employee.setJobType("Permenent");
		employee.setEmailId("emp121212@gmail.com");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(authenicationToken);
		HttpEntity request = new HttpEntity(employee, headers);
		final String baseUrl = "http://localhost:8080/v1/updateEmployee?jobTitle=RDeveloper&jobType=Permenent&email=emp121212@gmail.com";
		ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Response.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	void validDeleteMapping() {
		RestTemplate restTemplate=new RestTemplate();
		Employee employee = new Employee();
		employee.setId("54b319d6-3240-43a2-ad14-3249573c287a");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(authenicationToken);
		HttpEntity<Employee> request = new HttpEntity<>(employee, headers);
		final String baseUrl = "http://localhost:8080/v1/deleteEmployee?id=54b319d6-3240-43a2-ad14-3249573c287a";
		ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.DELETE, request, Response.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

	@Test
	void validGetPagination() {
		RestTemplate restTemplte = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(authenicationToken);
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:8080/v1/pagination?pageNo=0&pageSize=5";
		ResponseEntity<AuthenticationToken> result = restTemplte.exchange(baseUrl, HttpMethod.GET, request,
				AuthenticationToken.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
	}

}
