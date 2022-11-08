package com.employeemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employeemanagement.model.AuthDetails;
@Repository
public interface TokenRepository extends JpaRepository<AuthDetails, Long>{

}
