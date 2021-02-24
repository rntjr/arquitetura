package com.eterna.server.core.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.eterna.server.core.constants.SecurityConstants;
import com.eterna.server.core.domain.entities.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authManager;

  public JWTAuthenticationFilter(AuthenticationManager authManager) {
    this.authManager = authManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      Usuario usuario = new ObjectMapper()
        .readValue(request.getInputStream(), Usuario.class);

      return authManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          usuario.getUsuario(),
          usuario.getSenha(),
          new ArrayList<>())
      );
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication auth) throws IOException, ServletException {
    String token = JWT.create()
      .withSubject(((Usuario) auth.getPrincipal()).getUsuario())
      .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
      .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
    response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
  }
}
