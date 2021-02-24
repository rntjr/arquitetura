package com.eterna.server.core.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "Usuario", schema = "Core")
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements UserDetails {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private BigInteger id;

  @Column(name = "tipo")
  private Integer tipo;

  @Column(name = "nome")
  private String nome;

  @Column(name = "usuario", unique = true)
  private String usuario;

  @Column(name = "senha")
  private String senha;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "isAtivo")
  private Boolean isAtivo;

  @Column(name = "senhaVencimento")
  private LocalDate senhaVencimento;

  @Column(name = "criadoEm")
  private LocalDateTime criadoEm;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return this.senha;
  }

  @Override
  public String getUsername() {
    return this.usuario;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return this.isAtivo;
  }
}
