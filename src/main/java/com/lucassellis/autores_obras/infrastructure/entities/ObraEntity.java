package com.lucassellis.autores_obras.infrastructure.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity // Avisa ao banco: "Crie uma tabela para as Obras"
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "obra")
public class ObraEntity {

    @Id // Identificador único da obra
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank // Regra: A obra precisa ter um nome (não aceita vazio)
    @Column(name = "nome", length = 100)
    private String nome;

    @Size(max = 240) // Segurança: Se o texto passar de 240 letras, o Java barra antes de dar erro no banco
    @Column(name = "descricao")
    private String descricao;

    // Como uma das duas datas é obrigatória, o banco aceita nulo (@Column),
    // mas a Service terá um "if" para garantir que o usuário preencheu pelo menos uma.
    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Column(name = "data_exposicao")
    private LocalDate dataExposicao;

    @ManyToMany // Muitos autores podem ter feito muitas obras
    @JoinTable( // Como é "Muitos para Muitos", o banco cria uma 3ª tabela mágica para ligar os dois
            name = "obra_autor", // Nome dessa tabela de ligação no banco
            joinColumns = @JoinColumn(name = "obra_id"), // Coluna que guarda o ID da Obra
            inverseJoinColumns = @JoinColumn(name = "autor_id") // Coluna que guarda o ID do Autor
    )
    private Set<AutorEntity> autores; // 'Set' garante que você não adicione o mesmo autor duas vezes na mesma obra
}