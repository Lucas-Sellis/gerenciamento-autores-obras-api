package com.lucassellis.autores_obras.infrastructure.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "autor")

public class Autores {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "sexo", length = 1)
    private String sexo;

    @Email
    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Past
    @Column(name = "data_nascimento")
    private LocalDate dataDeNascimento;

    @NotBlank
    @Column(name = "pais_origem", length = 100)
    private String paisOrigem;

    @Column(name = "cpf", length = 100)
    private String cpf;

    @ManyToMany(mappedBy = "autores")
    private Set<Obra> obras;
}


//1. Autor:
//        ○ Nome (obrigatório)
//○ Sexo
//○ E-mail (validado, único)
//○ Data de nascimento (validada)
//○ País de origem (obrigatório)
//○ CPF (obrigatório para autores do Brasil, único)
