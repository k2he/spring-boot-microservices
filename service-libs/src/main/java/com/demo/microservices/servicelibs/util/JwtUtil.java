package com.demo.microservices.servicelibs.util;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import com.demo.microservices.servicelibs.security.JwtPublicKey;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {

  public static PublicKey convertFrom(JwtPublicKey key) {
    try {
      KeyFactory kf = KeyFactory.getInstance("RSA");
      String publicKeyStr = key.getBase64Key();
      byte[] decodedKey = Base64.getDecoder().decode(publicKeyStr);

      X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
      PublicKey pubKey = kf.generatePublic(spec);
      return pubKey;
    } catch (Exception e) {
      log.error("Error on converting public key:" + e);
      return null;
    }
  }
}
