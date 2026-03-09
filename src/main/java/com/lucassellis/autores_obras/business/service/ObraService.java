package com.lucassellis.autores_obras.business.service;

import com.lucassellis.autores_obras.business.dto.ObraDTO.ObraDTO;
import com.lucassellis.autores_obras.business.mapper.ObraMapper;
import com.lucassellis.autores_obras.infrastructure.entities.AutorEntity;
import com.lucassellis.autores_obras.infrastructure.entities.ObraEntity;
import com.lucassellis.autores_obras.infrastructure.repository.AutorRepository;
import com.lucassellis.autores_obras.infrastructure.repository.ObraRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ObraService {

    private final ObraRepository repository;

    private final AutorRepository autorRepository;

    private final ObraMapper mapper;

    @Transactional
    public ObraDTO criar(ObraDTO dto) {

        if (dto.getDataPublicacao() == null && dto.getDataExposicao() == null) {
            throw new RuntimeException("A obra deve ter pelo menos uma data (publicação ou exposição).");
        }
        ObraEntity obraEntity = mapper.toEntity(dto);
        if (dto.getAutoresIds() != null && !dto.getAutoresIds().isEmpty()) {
            List<AutorEntity> autoresEncontrados = autorRepository.findAllById(dto.getAutoresIds());
            if (autoresEncontrados.size() != dto.getAutoresIds().size()) {
                throw new RuntimeException("Um ou mais autores informados não foram encontrados.");
            }
            obraEntity.setAutores(new HashSet<>(autoresEncontrados));
        }
        ObraEntity obraSalva = repository.save(obraEntity);
        ObraDTO resposta = mapper.toDto(obraSalva);
        resposta.setAutoresIds(dto.getAutoresIds());
        return resposta;
    }

    public List<ObraDTO> listarTodos() {

        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public ObraDTO buscarPorId(Long id) {

        ObraEntity obra = repository.findById(id).orElseThrow(() -> new RuntimeException("Obra não encontrada com o ID: " + id));
        ObraDTO dto = mapper.toDto(obra);
        dto.setAutoresIds(obra.getAutores().stream().map(AutorEntity::getId).toList());
        return dto;
    }

}