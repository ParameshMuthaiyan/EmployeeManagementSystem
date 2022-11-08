package com.employeemanagement.model;

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
@Table(name = "address")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@JsonIgnore
	private String id;
	private String addressType;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private Integer zipcode;
	private String state;
	private String country;	
	private String contactNo;
	
	public Address(String addressType, String addressLine1, String addressLine2, String city, Integer zipcode,
			String state, String country, String contactNo) {
		super();
		this.addressType = addressType;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.zipcode = zipcode;
		this.state = state;
		this.country = country;
		this.contactNo = contactNo;
	}
	
	
}
