package com.demo.microservices.servicelibs.util;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.microservices.servicelibs.security.JwtPublicKey;

public class JwtUtil {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	public static PublicKey convertFrom(JwtPublicKey key) {
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			String publicKeyStr = key.getBase64Key();
			byte[] decodedKey = Base64.getDecoder().decode(publicKeyStr);

			X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
			PublicKey pubKey = kf.generatePublic(spec);
			return pubKey;
		} catch (Exception e) {
			logger.error("Error on converting public key:" + e);
			return null;
		}
	}
}
