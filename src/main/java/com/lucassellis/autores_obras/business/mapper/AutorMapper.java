package com.lucassellis.autores_obras.business.mapper;

import com.lucassellis.autores_obras.business.dto.AutorDTO.AutorDTO;
import com.lucassellis.autores_obras.infrastructure.entities.AutorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(source = "pais", target = "paisOrigem")
    @Mapping(source = "dataNascimento", target = "dataDeNascimento")
    @Mapping(target = "obras", ignore = true)
    AutorEntity toEntity(AutorDTO dto);

    @Mapping(source = "paisOrigem", target = "pais")
    @Mapping(source = "dataDeNascimento", target = "dataNascimento")
    AutorDTO toDto(AutorEntity entity);

}