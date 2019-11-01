package com.sstapels.jfall.ccdemo;

import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.LinkedHashMap;
import java.util.Map;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.RSAKeyProvider;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @GetMapping(value = "/balance")
    public ResponseEntity<ResponseBody> getBalance(@RequestHeader("Authorization") String authHeader) throws Exception {

        ResponseBody response = new ResponseBody();
        
        try {
            System.out.println("\n\n**************************************************************");
            System.out.println("**************************************************************");
            System.out.println("\n\n==================== Authorization Header ====================");
            System.out.print(authHeader);

            /* Trim authHeader */
            String token = StringUtils.substringAfterLast(authHeader, "Bearer").trim();

            System.out.println("\n\n==================== Encoded JWT =============================");
            System.out.print(token);

            /* Decode token */
            DecodedJWT jwt = JWT.decode(token);
            
            System.out.println("\n\n==================== Decoded JWT =============================");
            System.out.println(jwtToJson(jwt));
            
            /* Get JWK from JWKS */
            String kid = jwt.getKeyId();
            JwkProvider jwks = new UrlJwkProvider("https://sstapels.eu.auth0.com/");
            Jwk jwk = jwks.get(kid);

            System.out.println("\n\n==================== JWK =====================================");
            gson.toJson(jwk, System.out);

            /* Verify token */
            RSAPublicKey publicKey = (RSAPublicKey) jwk.getPublicKey();
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("scope", "read:users").build();
            verifier.verify(token);

            System.out.println("\n\n==================== IS VALID ================================");
            System.out.println(true);

            /* Get requested data from database */
            String currency = "EUR";
            Double balance = 1500.00;
            
            /* Respond */
            response = new ResponseBody(200, currency, balance);
   
        } catch (JWTDecodeException exception) {
            System.out.println("\n\nERROR: " + exception.getMessage());
            response = new ResponseBody(400, "Invalid Bearer token");

        } catch (TokenExpiredException exception) {
            System.out.println("\n\nERROR: " + exception.getMessage());
            response = new ResponseBody(401, exception.getMessage());
            
        } catch (JWTVerificationException exception) {
            System.out.println("\n\nERROR: " + exception.getMessage());
            response = new ResponseBody(401, "Invalid authorization token");

        } catch (Exception exception) {
            System.out.println("\n\nERROR: " + exception.getMessage());
            response = new ResponseBody(500, "Internal Server Error");
        }

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     *
     * @param DecodedJWT jwt
     * @return String json
     */
    private String jwtToJson(DecodedJWT jwt) {
        Map<String, Object> header = new LinkedHashMap<>();
        header.put("typ", jwt.getType());
        header.put("alg", jwt.getAlgorithm());
        header.put("kid", jwt.getKeyId());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("iss", jwt.getIssuer());
        body.put("iat", jwt.getIssuedAt().getTime()/1000);
        body.put("exp", jwt.getExpiresAt().getTime()/1000);
        body.put("scope", jwt.getClaim("scope").asString());

        Map<String, Object> decodedToken = new LinkedHashMap<>();
        decodedToken.put("header", header);
        decodedToken.put("body", body);
        decodedToken.put("signature", jwt.getSignature());

        return gson.toJson(decodedToken);
    }
}
