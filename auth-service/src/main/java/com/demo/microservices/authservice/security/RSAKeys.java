package com.demo.microservices.authservice.security;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import javax.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
/**
 * @author kaihe
 *
 */

@Slf4j
@Component
@Getter
@Setter
public class RSAKeys {

  private PrivateKey privateKey;
  private PublicKey publicKey;

  @PostConstruct
  public void init() {
    getKeysFromStore();
  }

  public void getKeysFromStore() {
    try {
      ClassPathResource resource = new ClassPathResource("keystore.jks");
      KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
      keystore.load(resource.getInputStream(), "storePassword".toCharArray());
      privateKey = (PrivateKey) keystore.getKey("jwtkey", "keyPassword".toCharArray());
      Certificate cert = keystore.getCertificate("jwtkey");
      publicKey = cert.getPublicKey();

    } catch (Exception e) {
      log.error("Error getting private and public keys: " + e);
    }
  }
}
