package com.lucassellis.autores_obras.business.mapper;

import com.lucassellis.autores_obras.business.dto.ObraDTO.ObraDTO;
import com.lucassellis.autores_obras.infrastructure.entities.ObraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ObraMapper {

    @Mapping(target = "autores", ignore = true)
    ObraEntity toEntity(ObraDTO dto);

    @Mapping(target = "autoresIds", ignore = true)
    ObraDTO toDto(ObraEntity entity);
}