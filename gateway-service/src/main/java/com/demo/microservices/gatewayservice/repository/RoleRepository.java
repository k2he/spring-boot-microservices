package com.demo.microservices.gatewayservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.microservices.gatewayservice.model.security.Role;
import com.demo.microservices.gatewayservice.model.security.RoleName;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(RoleName name);
}
