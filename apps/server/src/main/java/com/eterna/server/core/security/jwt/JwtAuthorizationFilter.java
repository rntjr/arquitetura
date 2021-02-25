package com.eterna.server.core.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

  private static Logger logger = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

  private UserDetailsService userDetailsService;

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
    super(authenticationManager);
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

    String token = request.getHeader("Authorization");

    if (token == null || !token.startsWith("Bearer ")) {
      // Não informou o authorization
      filterChain.doFilter(request, response);
      return;
    }

    try {

      if(! JwtUtils.isTokenValido(token)) {
        throw new AccessDeniedException("Acesso negado.");
      }

      String login = JwtUtils.getUsuario(token);

      UserDetails userDetails = userDetailsService.loadUserByUsername(login);

      Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, null);

      // Salva o Authentication no contexto do Spring
      SecurityContextHolder.getContext().setAuthentication(auth);
      filterChain.doFilter(request, response);

    } catch (RuntimeException ex) {
      logger.error("Authentication error: " + ex.getMessage(),ex);
      throw ex;
    }
  }

}
