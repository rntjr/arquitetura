package com.eterna.server.core.domain.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Tela", schema = "Core")
public class Tela {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @ManyToOne()
  @JoinColumn(name = "programaId")
  private Programa programa;

  @Column( name = "codigo", unique = true )
  private String codigo;

  @Column( name = "nome" )
  private String nome;

  @Column( name = "tipo")
  private Integer tipo;

  @Column(name = "rota", unique = true )
  private String rota;

}
