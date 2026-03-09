package com.lucassellis.autores_obras.infrastructure.entities;

import com.lucassellis.autores_obras.infrastructure.entities.ObraEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity // Diz ao Spring: "Crie uma tabela no banco baseada nesta classe"
@Getter // Cria os métodos de 'pegar' dados (getName, etc) automaticamente
@Setter // Cria os métodos de 'gravar' dados (setName, etc) automaticamente
@NoArgsConstructor // Cria um construtor vazio (exigido pelo Hibernate)
@AllArgsConstructor // Cria um construtor com todos os campos (útil para testes)
@Builder// Permite criar o objeto de um jeito elegante: AutorEntity.builder().nome("X").build()
@Table(name = "autor") // Define que o nome da tabela no banco será "autor"
public class AutorEntity {


    @Id // Define que este campo é a "Chave Primária" (o RG da linha no banco)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // O banco gera o número (1, 2, 3...) sozinho
    private Long id;

    @NotBlank // Regra: Não aceita nulo, nem texto vazio "" ou só espaços "  "
    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "sexo", length = 1) // Guarda apenas 'M' ou 'F'
    private String sexo;

    @Email // Valida se o texto tem formato de e-mail (ex: usuario@email.com)
    @Column(name = "email", length = 100, unique = true) // 'unique' impede e-mails repetidos no banco
    private String email;

    @Past // Validação: A data tem que ser obrigatoriamente no passado (já aconteceu)
    @Column(name = "data_nascimento")
    private LocalDate dataDeNascimento; // LocalDate é o padrão moderno para datas no Java

    @NotBlank
    @Column(name = "pais_origem", length = 100)
    private String paisOrigem;

    @Column(name = "cpf", length = 14, unique = true) // 14 caracteres cabem "123.456.789-01"
    private String cpf;

    // Relacionamento: Muitos autores podem ter muitas obras (e vice-versa)
    // mappedBy = "autores" diz que a regra de quem manda na relação está lá na classe ObraEntity
    @ManyToMany(mappedBy = "autores")
    private Set<ObraEntity> obras; // 'Set' é melhor que 'List' porque não deixa ter obras repetidas

}