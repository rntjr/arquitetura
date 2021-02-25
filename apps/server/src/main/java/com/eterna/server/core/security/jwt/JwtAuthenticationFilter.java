package com.eterna.server.core.security.jwt;

import com.eterna.server.core.constants.SecurityConstants;
import com.eterna.server.core.domain.entities.Usuario;
import com.eterna.server.core.security.configuration.ServletUtil;
import com.eterna.server.core.security.dto.AcessarDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;

    // api/authenticate
    setFilterProcessesUrl(SecurityConstants.AUTH_URL);
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
    try {
      AcessarDTO login = new ObjectMapper().readValue(request.getInputStream(), AcessarDTO.class);
      String usuario = login.getUsername();
      String senha = login.getPassword();

      if(usuario == null || senha == null) {
        throw new BadCredentialsException("Usuario/Senha inválidos.");
      }

      Authentication auth = new UsernamePasswordAuthenticationToken(usuario, senha);
      System.out.println(auth);

      return authenticationManager.authenticate(auth);
    } catch (IOException e) {
      throw new BadCredentialsException(e.getMessage());
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws IOException {
    try{
      Usuario user = (Usuario) authentication.getPrincipal();
      String jwtToken = JwtUtils.criarToken(user);
      // String json = ServletUtil.getJson("token", jwtToken);
      // String json = UserDTO.create(user, jwtToken).toJson();
      ObjectMapper m = new ObjectMapper();
      m.writeValueAsString(jwtToken);
      ServletUtil.write(response, HttpStatus.OK, jwtToken);
    }catch(IOException e){
      throw new IOException(e);
    }
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException error) throws IOException, ServletException {
    String json = ServletUtil.getJson("error", "Login incorreto");
    ServletUtil.write(response, HttpStatus.UNAUTHORIZED, json);
  }
}
