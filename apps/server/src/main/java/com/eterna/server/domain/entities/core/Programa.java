package com.eterna.server.domain.entities.core;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Programa", schema = "Core")
public class Programa {
  @Id
  @GeneratedValue
  @Column(name = "id")
  private Integer id;

  @Column(name = "nome", unique = true )
  private String nome;
}
