package com.ruokatracker.ruokatracker_api.util;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class JwtTokenUtil {

    private static RSAPublicKey rsaPublicKey;
    private static RSAPrivateKey rsaPrivateKey;

    static {

        try {
            // Load RSA public key from file
            try (InputStream publicKeyStream = JwtTokenUtil.class.getClassLoader()
                    .getResourceAsStream("keys/public_key.pem")) {
                if (publicKeyStream == null) {
                    Logger.printError("Public key file not found.");
                } else {
                    byte[] publicKeyBytes = publicKeyStream.readAllBytes();

                    // Remove PEM headers and footers
                    String keyContent = new String(publicKeyBytes);
                    keyContent = keyContent.replace("-----BEGIN PUBLIC KEY-----", "")
                            .replace("-----END PUBLIC KEY-----", "").replaceAll("\\s", "");
                    publicKeyBytes = Base64.getDecoder().decode(keyContent);

                    // Load the public key
                    X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    rsaPublicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
                }
            }

            // Load RSA private key from file
            try (InputStream privateKeyStream = JwtTokenUtil.class.getClassLoader()
                    .getResourceAsStream("keys/private_key.pem")) {
                if (privateKeyStream == null) {
                    Logger.printError("Private key file not found.");
                } else {
                    byte[] privateKeyBytes = privateKeyStream.readAllBytes();

                    // Remove PEM headers and footers
                    String keyContent = new String(privateKeyBytes);
                    keyContent = keyContent.replace("-----BEGIN PRIVATE KEY-----", "")
                            .replace("-----END PRIVATE KEY-----", "").replaceAll("\\s", "");
                    privateKeyBytes = Base64.getDecoder().decode(keyContent);
                    // Load the private key
                    PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    rsaPrivateKey = (RSAPrivateKey) keyFactory.generatePrivate(privateKeySpec);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Generate JWT token
    public static String generateToken(String email) {
        try {
            Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
            String token = JWT.create()
                    .withIssuer("ruokatracker-api") // Issuer
                    .withClaim("email", email) // Custom claim
                    .withIssuedAt(new Date()) // Issued at time
                    .withExpiresAt(new Date(System.currentTimeMillis() + 60L * 60L * 24L * 30L * 1000L)) // (30 days)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            Logger.printError("Error creating token");
            exception.printStackTrace();
            return null;
        }

    }

    // Parse the JWT token
    public static String getEmailFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.RSA256(rsaPublicKey, rsaPrivateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer("ruokatracker-api")
                    // reusable verifier instance
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);
            return decodedJWT.getClaim("email").asString();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }
}
