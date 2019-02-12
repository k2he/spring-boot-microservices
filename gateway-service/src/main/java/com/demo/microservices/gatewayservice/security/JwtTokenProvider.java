package com.demo.microservices.gatewayservice.security;

import java.security.PublicKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.demo.microservices.gatewayservice.config.AppProperties;
import com.demo.microservices.servicelibs.security.JwtPublicKey;
import com.demo.microservices.servicelibs.util.JwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

	private PublicKey publicKey;
	
	@NonNull
	private RestTemplate restTemplate;
	
	@NonNull
	private AppProperties appProperties;

    
    private static final String CLAIM_KEY_AUTHORITIES = "authorities";
    private static final String CLAIM_KEY_USERNAME = "username";
    
    public void getJwtPublicKey() {
    	// Get JWT public signing key and store locally. 
    	JwtPublicKey key = restTemplate.getForObject("http://auth-service/public-key/", JwtPublicKey.class);
    	publicKey = JwtUtil.convertFrom(key);
    }
    
    public Long getUserIdFromJWT(String token) {
    	Claims claims = Jwts.parser().setSigningKey(publicKey)
    								.parseClaimsJws(token).getBody();
    	return Long.parseLong(claims.getSubject());
    }
    
    
    public boolean validateToken(String token) {
    	try {
    		getJwtPublicKey();
            Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
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
