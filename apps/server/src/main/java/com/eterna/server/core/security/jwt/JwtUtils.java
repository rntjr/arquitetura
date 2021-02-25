package com.eterna.server.core.security.jwt;

import org.springframework.security.core.userdetails.UserDetails;

public class JwtUtils {
  public static String criarToken(UserDetails usuario){
    return "Token";
  }

  public static boolean isTokenValido(String token){
    return false;
  }

  public static String getUsuario(String token){
    return null;
  }
}
