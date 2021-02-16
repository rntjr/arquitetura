package com.eterna.server.domain.entities.core;

import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Usuario", schema = "Core")
public class Usuario implements Serializable {
  @Id @GeneratedValue
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
}
