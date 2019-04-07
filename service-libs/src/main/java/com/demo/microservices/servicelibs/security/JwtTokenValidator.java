package com.demo.microservices.servicelibs.security;

import java.security.PublicKey;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.client.RestTemplate;
import com.demo.microservices.servicelibs.security.user.AppUserPrincipal;
import com.demo.microservices.servicelibs.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtTokenValidator {
  private static final Logger logger = LoggerFactory.getLogger(JwtTokenValidator.class);

  private PublicKey publicKey;

  @NonNull
  private RestTemplate restTemplate;

  public void getJwtPublicKey() {
    // Get JWT public signing key and store locally.
    JwtPublicKey key =
        restTemplate.getForObject("http://auth-service/public-key/", JwtPublicKey.class);
    publicKey = JwtUtil.convertFrom(key);
  }

  public AppUserPrincipal getUserDetailFromJWT(String token) {
    // Get UserId
    Claims claimsBody = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
    Long userId = Long.parseLong(claimsBody.getSubject());

    // Get user details
    Jws<Claims> claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);

    String email = String.valueOf(claims.getBody().get(JwtConstants.CLAIM_KEY_EMAIL));
    String name = String.valueOf(claims.getBody().get(JwtConstants.CLAIM_KEY_FULLNAME));
    String username = (String) claims.getBody().get(JwtConstants.CLAIM_KEY_USERNAME);
    List<String> roles = (List<String>) claims.getBody().get(JwtConstants.CLAIM_KEY_AUTHORITIES);
    List<GrantedAuthority> authorities =
        roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

    return new AppUserPrincipal(userId, username, name, email, authorities);
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
