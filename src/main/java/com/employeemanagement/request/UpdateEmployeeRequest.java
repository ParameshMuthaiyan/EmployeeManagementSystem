package com.employeemanagement.request;

import java.time.LocalDate;

import javax.persistence.Column;

import lombok.Data;

@Data
public class UpdateEmployeeRequest {
	
	private String personName;
	private LocalDate dateOfBirth;
	private String gender;
	private Integer age;
	private String qualification;
	private String place;
	@Column(unique = true)
	private String emailId;
	private String mobileNo;	
	
	private String addressType;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private Integer zipcode;
	private String state;
	private String country;	
	private String contactNo;
}
