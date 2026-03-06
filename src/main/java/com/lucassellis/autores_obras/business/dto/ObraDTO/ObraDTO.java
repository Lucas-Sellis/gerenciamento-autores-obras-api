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

    private LocalDate dataPublicacao;

    private LocalDate dataExposicao;

    // Aqui passamos apenas os IDs dos autores para vincular à obra
    private List<Long> autoresIds;
}