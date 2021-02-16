package com.eterna.server.domain.entities.core;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "UsuarioPerfisAcesso", schema = "Core")
public class UsuarioPerfisAcesso {
  @Id
  @GeneratedValue
  @Column(name = "id")
  private BigInteger id;

  @ManyToOne
  @JoinColumn(name = "usuarioId")
  private Usuario usuarioId;

  @ManyToOne
  @JoinColumn(name = "perfilAcessoId")
  private PerfilAcesso perfilAcessoId;
}
