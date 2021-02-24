package com.eterna.server.core.domain.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Programa", schema = "Core")
public class Programa {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "nome", unique = true )
  private String nome;
}
