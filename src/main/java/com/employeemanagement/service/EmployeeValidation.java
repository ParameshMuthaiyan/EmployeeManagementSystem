package com.employeemanagement.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.employeemanagement.exception.BusinessException;

@Component
public class EmployeeValidation {
	private static final String MOBILE_NO_PATTERN = "[6-9][0-9]{9}";
	private static final String NAME_PATTERN = "^[A-Z][a-zA-Z ]+";

	public void checkValidMobileNumber(String mobileNo) throws BusinessException {
		Pattern ptrn = Pattern.compile(MOBILE_NO_PATTERN);
		Matcher match = ptrn.matcher(mobileNo);
		if (!(match.find() && match.group().equals(mobileNo))) {
			throw new BusinessException("Invalid_Mobile_Number", null, new String[] { mobileNo });
		}
	}

	public void checkValidName(String passangerName) throws BusinessException {
		Pattern ptrn = Pattern.compile(NAME_PATTERN);
		Matcher match = ptrn.matcher(passangerName);
		if (!(match.find() && match.group().equals(passangerName))) {
			throw new BusinessException("Invalid_Name");
		}
	}

}
