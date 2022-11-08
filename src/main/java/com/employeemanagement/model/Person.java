package com.employeemanagement.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "person")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@JsonIgnore
	private String personId;
	private String personName;
	private LocalDate dateOfBirth;
	private String gender;
	private Integer age;
	private String qualification;
	private String place;
	@Column(unique = true)
	private String emailId;
	private String contactNo;
		
	public Person(String personName, LocalDate dateOfBirth, String gender, Integer age, String qualification,
			String place, String emailId, String contactNo) {
		super();
		this.personName = personName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.age = age;
		this.qualification = qualification;
		this.place = place;
		this.emailId = emailId;
		this.contactNo = contactNo;
	}
	
	
}
