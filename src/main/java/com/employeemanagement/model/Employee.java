package com.employeemanagement.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "employee")
public class Employee {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private String id;
	@Column(unique = true)
	private String employeeId;
	@JsonIgnore
	private String userName;
	private String employeeName;
	private String jobTitle;
	private String jobType;
	@Column(unique = true)
	private String emailId;
	private String mobilePhone;	
	private LocalDate dateOfJoining;
		
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<Address> addressList;

	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Person person;

}
