package com.eterna.server.domain.entities.core;

import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "PerfilPermissaoAcesso", schema = "Core")
public class PerfilPermissaoAcesso {
  @Id
  @GeneratedValue
  @Column(name = "id")
  private BigInteger id;

  @ManyToOne()
  @JoinColumn(name = "perfilAcessoId")
  private PerfilAcesso perfilAcessoId;

  @ManyToOne()
  @JoinColumn(name = "telaId")
  private Tela telaId;

  @ManyToOne()
  @JoinColumn(name = "usuarioId")
  private Usuario usuarioId;

  @Column( insertable = false, name = "isMenu" )
  private Boolean isMenu;

  @Column( insertable = false, name = "isAcesso" )
  private Boolean isAcesso;

  @Column( insertable = false, name = "isSalvar" )
  private Boolean isSalvar;

  @Column( insertable = false, name = "isEditar" )
  private Boolean isEditar;

  @Column( insertable = false, name = "isDeletar" )
  private Boolean isDeletar;
}
