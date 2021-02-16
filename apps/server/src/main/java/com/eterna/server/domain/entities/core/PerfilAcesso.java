package com.eterna.server.domain.entities.core;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "PerfilAcesso", schema = "Core")
public class PerfilAcesso implements Serializable {
  @Id @GeneratedValue
  @Column(name = "id")
  private BigInteger id;

  @Column(name = "nome")
  private String nome;
}
