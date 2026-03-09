package com.lucassellis.autores_obras.business.dto.ObraDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ObraDTO {

    private Long id;

    @NotBlank(message = "O nome da obra é obrigatório")
    private String nome;

    @Size(max = 240, message = "A descrição deve ter no máximo 240 caracteres")
    private String descricao;

    // As datas estão aqui sem @NotBlank porque, na Service, você vai validar
    // se o usuário preencheu PELO MENOS uma delas.
    private LocalDate dataPublicacao;
    private LocalDate dataExposicao;

    // A GRANDE DÚVIDA: Por que uma lista de IDs (Long) e não de Autores?
    // Pense no Postman: O usuário não vai digitar o nome, e-mail e CPF do autor toda vez.
    // Ele apenas envia os "RGs" (IDs) dos autores que já existem no banco.
    // Exemplo no JSON: "autoresIds": [1, 5, 10]
    private List<Long> autoresIds;
}