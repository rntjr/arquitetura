package com.eterna.server.core.security.implementations;

import com.eterna.server.core.domain.entities.Usuario;
import com.eterna.server.core.domain.repositories.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private IUsuarioRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = userRepository.findByUsuario(username);
    if(usuario == null){
      throw new UsernameNotFoundException("Usuario não encontrado!");
    }
    return usuario;
  }
}
