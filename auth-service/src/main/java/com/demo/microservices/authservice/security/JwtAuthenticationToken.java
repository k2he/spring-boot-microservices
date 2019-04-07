package com.demo.microservices.authservice.security;

import com.demo.microservices.authservice.model.UserPrincipal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationToken {
	private String token;
	private UserPrincipal user;
}
