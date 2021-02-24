package com.eterna.server.core.services.implementarions;

import com.eterna.server.core.domain.entities.Usuario;
import com.eterna.server.core.domain.repositories.IUsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsuarioDetailsServiceImpl implements UserDetailsService {

  private IUsuarioRepository usuarioRepo;

  public void UserDetailsServiceImpl(IUsuarioRepository usuarioRepo) {
    this.usuarioRepo = usuarioRepo;
  }


  @Override
  public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
    Usuario dataUsuario = usuarioRepo.findByUsuario(usuario);
    if(dataUsuario == null){
      throw new UsernameNotFoundException(usuario);
    }
    return dataUsuario;
  }
}
