package com.eterna.server.core.services.implementarions;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;

@Service
public class TokenAuthenticationServiceImpl {
  // EXPIRATION_TIME = 10 dias
  static final long EXPIRATION_TIME = 860_000_000;
  static final String SECRET = "MySecret";
  static final String TOKEN_PREFIX = "Bearer";
  static final String HEADER_STRING = "Authorization";

  public static void addAuthentication(HttpServletResponse response, String usuario) {
    Algorithm hash = Algorithm.HMAC512(SECRET);
    String token = JWT.create()
      .withClaim("usuario",usuario).withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
      .sign(hash);
    response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token);
  }

  public static Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING).replace("Bearer ","");
    try{
      Algorithm hash = Algorithm.HMAC512(SECRET);
      JWTVerifier verifyToken = JWT.require(hash).build();
      DecodedJWT jwt = verifyToken.verify(token);

      if (jwt.getPayload()!= null) {
        return new UsernamePasswordAuthenticationToken(jwt.getPayload(), null, Collections.emptyList());
      }
      return null;
    }catch(JWTVerificationException exception){
      throw new JWTVerificationException("Falha na autenticação!");
    }
  }
}
