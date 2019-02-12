package com.demo.microservices.authservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.demo.microservices.servicelibs.audit.UserDateAudit;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="app_role")
@Getter
@Setter
public class Role extends UserDateAudit implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	 
	@Column(name="role_name")
	@Enumerated(EnumType.STRING)
	private RoleName name;

	@Column(name="description")
	private String description;

	private Boolean active;

	@Override
	public String getAuthority() {
		return name.name();
	}
}
