package com.lucassellis.autores_obras.business.mapper;

import com.lucassellis.autores_obras.business.dto.AutorDTO.AutorDTO;
import com.lucassellis.autores_obras.infrastructure.entities.AutorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // Diz ao Spring: "Crie este tradutor e deixe-o pronto para eu usar (@Autowired)"
public interface AutorMapper {

    // 'source' é o que vem do DTO (Web) | 'target' é o nome que está na Entity (Banco)
    @Mapping(source = "pais", target = "paisOrigem")
    @Mapping(source = "dataNascimento", target = "dataDeNascimento")

    // SUA DÚVIDA: Por que 'ignore = true'? 
    // Como a lista de obras é complexa (ManyToMany), dizemos ao Mapper para ignorar ela agora.
    // Nós cuidaremos de ligar autores e obras manualmente na Service para evitar erros.
    @Mapping(target = "obras", ignore = true)
    AutorEntity toEntity(AutorDTO dto); // Traduz o DTO (que o usuário enviou) para Entity (que o banco entende)


    @Mapping(source = "paisOrigem", target = "pais") // Caminho de volta: O Banco nos dá uma Entity...
    @Mapping(source = "dataDeNascimento", target = "dataNascimento")
    AutorDTO toDto(AutorEntity entity); // ...e o Mapper traduz para DTO (que o usuário recebe na tela)
}