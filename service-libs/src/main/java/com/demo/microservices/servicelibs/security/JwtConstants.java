package com.demo.microservices.servicelibs.security;

public class JwtConstants {
	public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
    
	public static final String CLAIM_KEY_AUTHORITIES = "authorities";
	public static final String CLAIM_KEY_USERNAME = "username";
	public static final String CLAIM_KEY_EMAIL = "email";
	public static final String CLAIM_KEY_FULLNAME = "fullname";
}
