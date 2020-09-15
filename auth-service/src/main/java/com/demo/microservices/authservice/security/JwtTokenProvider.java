package com.demo.microservices.authservice.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.demo.microservices.authservice.config.AppProperties;
import com.demo.microservices.authservice.model.UserPrincipal;
import com.demo.microservices.servicelibs.security.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

  @NonNull
  private AppProperties appProperties;

  @NonNull
  private RSAKeys jwtKeys;

  public String generateToken(Authentication authentication) {
    UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

    Date now = new Date();
    Date expiration = new Date(now.getTime() + appProperties.getJwt().getTokenExpirationMsec());

    Claims claims = Jwts.claims().setSubject(String.valueOf(userPrincipal.getId()));
    claims.put(JwtConstants.CLAIM_KEY_USERNAME, userPrincipal.getUsername());
    claims.put(JwtConstants.CLAIM_KEY_EMAIL, userPrincipal.getEmail());
    claims.put(JwtConstants.CLAIM_KEY_FULLNAME, userPrincipal.getName());
    List<String> authorities = userPrincipal.getAuthorities().stream()
        .map(authoritity -> authoritity.getAuthority()).collect(Collectors.toList());
    claims.put(JwtConstants.CLAIM_KEY_AUTHORITIES, authorities);

    return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(expiration)
        .signWith(SignatureAlgorithm.RS256, jwtKeys.getPrivateKey()).compact();
  }
}
