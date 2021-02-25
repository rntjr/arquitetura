package com.eterna.server.core.api.controllers;

import com.eterna.server.core.domain.entities.Usuario;
import com.eterna.server.core.domain.repositories.IUsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

  private IUsuarioRepository userRepo;
  private BCryptPasswordEncoder bcrypt;

  public AuthenticationController(IUsuarioRepository userRepo, BCryptPasswordEncoder bcrypt) {
    this.userRepo = userRepo;
    this.bcrypt = bcrypt;
  }

  @PostMapping ("/hello")
  public String hello(){
    return "Hello World!";
  }

  @PostMapping("/cadastrar")
  public void cadastrar(@RequestBody Usuario usuario){
    usuario.setSenha(bcrypt.encode(usuario.getSenha()));
    userRepo.save(usuario);
  }
}
