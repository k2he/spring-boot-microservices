package com.demo.microservices.gatewayservice.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.demo.microservices.gatewayservice.config.AppProperties;
import com.demo.microservices.gatewayservice.model.security.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	@Autowired
	private AppProperties appProperties;

    
    private static final String CLAIM_KEY_AUTHORITIES = "authorities";
    private static final String CLAIM_KEY_USERNAME = "username";
    
    public String generateToken(Authentication authentication) {
    	UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
    	
    	Date now = new Date();
    	Date expiration = new Date(now.getTime() + appProperties.getJwt().getTokenExpirationMsec());
    	
    	Claims claims = Jwts.claims().setSubject(String.valueOf(userPrincipal.getId()));
    	claims.put(CLAIM_KEY_USERNAME, userPrincipal.getUsername());
    	claims.put(CLAIM_KEY_AUTHORITIES, userPrincipal.getAuthorities());
	    
    	return Jwts.builder()
    			.setClaims(claims)
    			.setIssuedAt(now)
    			.setExpiration(expiration)
    			.signWith(SignatureAlgorithm.HS512, appProperties.getJwt().getTokenSecret())
    			.compact();	
    }
    
    public Long getUserIdFromJWT(String token) {
    	Claims claims = Jwts.parser().setSigningKey(appProperties.getJwt().getTokenSecret())
    								.parseClaimsJws(token).getBody();
    	return Long.parseLong(claims.getSubject());
    }
    
    
    public boolean validateToken(String token) {
    	try {
            Jwts.parser().setSigningKey(appProperties.getJwt().getTokenSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
