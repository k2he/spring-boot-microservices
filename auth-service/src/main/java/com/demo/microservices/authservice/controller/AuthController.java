package com.demo.microservices.authservice.controller;

import java.util.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.microservices.authservice.security.RSAKeys;
import com.demo.microservices.servicelibs.security.JwtPublicKey;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author kaihe
 *
 */

@RestController
@RequiredArgsConstructor
public class AuthController {

  @NonNull
  private RSAKeys jwtKeys;

  @GetMapping("/public-key")
  public JwtPublicKey getPublicKey() {
    String publicKeyStr = Base64.getEncoder().encodeToString(jwtKeys.getPublicKey().getEncoded());
    return JwtPublicKey.builder().base64Key(publicKeyStr).build();
  }
}
