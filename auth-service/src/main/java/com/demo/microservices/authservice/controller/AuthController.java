package com.demo.microservices.authservice.controller;

import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.microservices.authservice.security.RSAKeys;
import com.demo.microservices.servicelibs.security.JwtPublicKey;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  @NonNull
  private RSAKeys jwtKeys;

  @GetMapping("/public-key")
  public JwtPublicKey getPublicKey() {
    String publicKeyStr = Base64.getEncoder().encodeToString(jwtKeys.getPublicKey().getEncoded());
    return new JwtPublicKey(publicKeyStr);
  }
}
