package com.employeemanagement.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.model.ERole;
import com.employeemanagement.model.Role;
import com.employeemanagement.model.User;
import com.employeemanagement.repository.RoleRepository;
import com.employeemanagement.repository.UserRepository;
import com.employeemanagement.request.LoginRequest;
import com.employeemanagement.request.SignupRequest;
import com.employeemanagement.response.EmployeeResponseMessage;
import com.employeemanagement.security.JwtResponse;
import com.employeemanagement.security.JwtUtils;
import com.employeemanagement.security.UserDetailsImpl;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/v1")
@Log4j2
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	/**
	 * This is Signin Method. Input as Username and Password
	 * 
	 * @param loginRequest
	 * @return
	 */

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);


		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());
		log.info("Token Generate Succuessfully!");
		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	/**
	 * This is Signup Mathod to register user details and Role(Authority)
	 * 
	 * @param signUpRequest
	 * @return
	 */

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
			return ResponseEntity.badRequest().body(new EmployeeResponseMessage("Error: Username is already taken!"));
		}

		if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
			return ResponseEntity.badRequest().body(new EmployeeResponseMessage("Error: Email is already in use!"));
		}

		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "HR":
				Role adminRole = roleRepository.findByName(ERole.ROLE_HR)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(adminRole);
				break;
				
			case "EMPLOYEE":
				Role userRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
				break;
				
			default:
				Role defaultUser = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(defaultUser);
			}
		});

		user.setRoles(roles);
		userRepository.save(user);
		log.info("User Register Succuessfully!");
		return ResponseEntity.ok(new EmployeeResponseMessage("User registered successfully!"));
	}
}
