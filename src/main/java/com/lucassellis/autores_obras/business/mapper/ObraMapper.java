package com.lucassellis.autores_obras.business.mapper;

import com.lucassellis.autores_obras.business.dto.ObraDTO.ObraDTO;
import com.lucassellis.autores_obras.infrastructure.entities.ObraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") // Avisa ao Spring: "Este é o tradutor oficial das Obras"
public interface ObraMapper {

    // 'ignore = true' aqui porque o DTO traz uma lista de IDs (números),
    // mas a Entity espera uma lista de Objetos AutorEntity.
    // Faremos essa "mágica" de buscar os autores no banco lá na Service.
    @Mapping(target = "autores", ignore = true)
    ObraEntity toEntity(ObraDTO dto); // Transforma o que veio da Web (DTO) para o Banco (Entity)

    // SUA DÚVIDA: Por que 'autoresIds' se você não criou?
    // Dê uma olhada no seu 'ObraDTO'. Provavelmente lá existe um campo chamado 'autoresIds'.
    // Como a Entity não tem esse campo (ela tem a lista de objetos), o Mapper se perde.
    // O 'ignore' serve para ele não tentar traduzir o que não existe nos dois lados.
    @Mapping(target = "autoresIds", ignore = true)
    ObraDTO toDto(ObraEntity entity); // Transforma o que veio do Banco (Entity) para a Web (DTO)
}