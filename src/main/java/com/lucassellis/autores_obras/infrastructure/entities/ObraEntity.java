package com.lucassellis.autores_obras.infrastructure.entities;

import com.lucassellis.autores_obras.business.dto.ObraDTO.ObraDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Table(name = "obra")

public class ObraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "nome", length = 100)
    private String nome;

    @Size(max = 240)
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;


    @Column(name = "data_exposicao")
    private LocalDate dataExposicao;



    @ManyToMany
    @JoinTable(
            name = "obra_autor",
            joinColumns = @JoinColumn(name = "obra_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private Set<AutorEntity> autores;


    public ObraEntity(ObraDTO dto) {
    }
}


//Obra:
//        ○ Nome (obrigatório)
//○ Descrição (máximo 240 caracteres)
//○ Data de publicação ou data de exposição (uma é obrigatória).
