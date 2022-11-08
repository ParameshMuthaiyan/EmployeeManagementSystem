package com.employeemanagement;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.employeemanagement.model.AuthenticationToken;
import com.employeemanagement.model.Employee;
import com.employeemanagement.request.LoginRequest;
import com.employeemanagement.response.Response;

@SpringBootTest
public class CheckInvalidAPIJUnitTest {

	String authenicationToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBbXV5YW4iLCJpYXQiOjE2NjA2NTQ2MjAsImV4cCI6MTY2MTUxODYyMH0.Nz78_3B9zALou0jiGuJ2ciclKEp64CMDizXfH1n0nKjQU9JCOzG1Kxuk7mkincKvabxNPx2Ebr0cGyraQFa-sQ";
	String employeeToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJQYXJhbWVzaF9EVCIsImlhdCI6MTY2MDY1NTAxMiwiZXhwIjoxNjYxNTE5MDEyfQ.Q3gDs29lmkBIzhJPZwhb65waxWwP8nn_HVzPhGk2a8ZpKX-EzlAce2R5la4TzpaCht3eg0f579xAwLK0_BUfJw";

	@Test
	void inValidSignupEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:8080/v1/sign";
		Employee employee = new Employee();
		try {
			restTemplate.postForEntity(baseUrl, employee, Response.class);
		} catch (HttpStatusCodeException ex) {
			Assert.assertEquals("404 NOT_FOUND", ex.getStatusCode().toString());
		}
	}

	@Test
	void InvalidSigninEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		LoginRequest login = new LoginRequest();
		login.setUsername("Amuyan");
		login.setPassword("12");
		final String baseUrl = "http://localhost:8080/v1/signin";
		try {
			ResponseEntity<Response> result = restTemplate.postForEntity(baseUrl, login, Response.class);
		} catch (HttpStatusCodeException ex) {
			Assert.assertEquals("401 UNAUTHORIZED", ex.getStatusCode().toString());
		}
	}

	@Test
	void InvalidGetMapping() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(employeeToken);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:8080/v1/mySql/showEmployee";
		try {
			ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.GET, request, Response.class);
		} catch (HttpStatusCodeException ex) {
			Assert.assertEquals("403 FORBIDDEN", ex.getStatusCode().toString());
		}
	}

	@Test
	void InvalidGetMappingEmployee() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(employeeToken);
		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:8080/v1/getEmployee1";
		try {
			ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.GET, request, Response.class);
		} catch (HttpStatusCodeException ex) {
			Assert.assertEquals("404 NOT_FOUND", ex.getStatusCode().toString());
		}
	}

	@Test
	void InvalidPutMapping() {
		RestTemplate restTemplate = new RestTemplate();
		Employee employee = new Employee();
		employee.setJobTitle("JavaDeveloper");
		employee.setJobTitle("Temperory");
		employee.setEmailId("emp33@gmail.com");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(authenicationToken);
		HttpEntity<Employee> request = new HttpEntity<Employee>(employee, headers);
		final String baseUrl = "http://localhost:8080/v1/updateEmployee?jobTitle=JavaDeveloper&jobType=Temperory&emil=emp33@gmail.com";
		try {
			ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.PUT, request, Response.class);
		} catch (HttpStatusCodeException ex) {
			Assert.assertEquals("400 BAD_REQUEST", ex.getStatusCode().toString());
		}
	}

	@Test
	void InvalidDeleteMapping() {
		RestTemplate restTemplate = new RestTemplate();
		Employee employee = new Employee();
		employee.setId("66d9c12c-a9b9-4709-bed0-8284cdf25c11");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(authenicationToken);
		HttpEntity<Employee> request = new HttpEntity<>(employee, headers);
		final String baseUrl = "http://localhost:8080/v1/deleteEmployee?id=66d9c12c-a9b9-4709-bed0-8284cdf25c16";
		try {
			ResponseEntity<Response> result = restTemplate.exchange(baseUrl, HttpMethod.DELETE, request,
					Response.class);
		} catch (HttpStatusCodeException ex) {
			Assert.assertEquals("500 INTERNAL_SERVER_ERROR", ex.getStatusCode().toString());
		}
	}

	@Test
	void InvalidPostMapping() {
		RestTemplate restTemplate = new RestTemplate();
		Employee employee = new Employee();
		employee.setEmployeeId("102");
		employee.setUserName("Ramesh_DT");
		employee.setEmployeeName("RameshKumar");
		employee.setJobTitle("JavaDeveloper");
		employee.setJobType("Temperory");
		employee.setEmailId("emp33333@gmail.com");
		employee.setMobilePhone("9090909090");
		employee.setDateOfJoining(LocalDate.now());

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setBearerAuth(authenicationToken);
		HttpEntity request = new HttpEntity(employee, headers);
		final String baseUrl = "http://localhost:8080/v1/postEmployee";
		try {
			ResponseEntity<AuthenticationToken> result = restTemplate.exchange(baseUrl, HttpMethod.PUT, request,
					AuthenticationToken.class);
		} catch (HttpStatusCodeException ex) {
			Assert.assertEquals("405 METHOD_NOT_ALLOWED", ex.getStatusCode().toString());
		}
	}

	@Test
	void InvalidGetPagination() {
		RestTemplate restTemplte = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(employeeToken);
		HttpEntity<?> request = new HttpEntity<Object>(headers);
		final String baseUrl = "http://localhost:8080/v1/pagination?pageNo=0&pageSize=5";
		try {
			ResponseEntity<AuthenticationToken> result = restTemplte.exchange(baseUrl, HttpMethod.GET, request,
					AuthenticationToken.class);
		} catch (HttpStatusCodeException ex) {
			Assert.assertEquals("403 FORBIDDEN", ex.getStatusCode().toString());
		}
	}
}
