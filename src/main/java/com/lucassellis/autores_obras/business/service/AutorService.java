package com.lucassellis.autores_obras.business.service;

import com.lucassellis.autores_obras.business.dto.AutorDTO.AutorDTO;
import com.lucassellis.autores_obras.business.mapper.AutorMapper;
import com.lucassellis.autores_obras.infrastructure.entities.AutorEntity;
import com.lucassellis.autores_obras.infrastructure.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AutorService {

    private final AutorRepository repository;

    private final AutorMapper mapper;

    public AutorDTO criar(AutorDTO dto) {

        if ("Brasil".equalsIgnoreCase(dto.getPais()) && (dto.getCpf() == null || dto.getCpf().isBlank())) {
            throw new RuntimeException("CPF é obrigatório para autores do Brasil");
        }
        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Este e-mail já está em uso!");
        }
        if (dto.getCpf() != null && !dto.getCpf().isBlank()) {
            if (repository.existsByCpf(dto.getCpf())) {
                throw new RuntimeException("Este CPF já está cadastrado!");
            }
        }
        AutorEntity autorEntity = mapper.toEntity(dto);
        AutorEntity autorSalvo = repository.save(autorEntity);
        return mapper.toDto(autorSalvo);
    }

    public AutorDTO buscarPorId(Long id) {

        AutorEntity autorId = repository.findById(id).orElseThrow(() -> new RuntimeException("Autor com ID " + id + " não encontrado"));
        return mapper.toDto(autorId);
    }

}