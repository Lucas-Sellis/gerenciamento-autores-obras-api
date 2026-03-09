package com.lucassellis.autores_obras.business.dto.AutorDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutorDTO {

    private Long id; // O ID aqui serve para o usuário saber quem ele acabou de criar

    // No DTO, o @NotBlank com 'message' é essencial para o Front-end/Postman
    // saber exatamente o que corrigiu sem precisar adivinhar o erro.
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail é obrigatório")
    private String email;

    private String sexo;

    @Past(message = "A data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    @NotBlank(message = "País é obrigatório")
    private String pais;

    // SUA ESTRATÉGIA: Perfeita! Não colocou @NotBlank aqui porque, se o autor
    // for dos EUA, o CPF pode ser nulo. A inteligência de "Se for Brasil, exija CPF"
    // fica escondida na Service, protegendo a lógica.
    private String cpf;
}