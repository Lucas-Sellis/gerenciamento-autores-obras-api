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

        // 1. Regra do CPF para Brasil (Validação manual)
        if ("Brasil".equalsIgnoreCase(dto.getPais()) &&
                (dto.getCpf() == null || dto.getCpf().isBlank())) {
            throw new RuntimeException("CPF é obrigatório para autores do Brasil");
        }

        // 2. Validação de Email único no banco
        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Este e-mail já está em uso!");
        }

        // 3. Validação de CPF único (só se o CPF não estiver vazio)
        if (dto.getCpf() != null && !dto.getCpf().isBlank()) {
            if (repository.existsByCpf(dto.getCpf())) {
                throw new RuntimeException("Este CPF já está cadastrado!");
            }
        }

        // 4. Usando o Mapper para transformar DTO em Entity
        AutorEntity autorEntity = mapper.toEntity(dto);

        // 5. Salvando no banco de dados através do Repository
        AutorEntity autorSalvo = repository.save(autorEntity);

        // 6. Transformando a Entity salva de volta em DTO para retornar
        return mapper.toDto(autorSalvo);
    }

    // Regra mental: Busca por ID com orElseThrow
    public AutorDTO buscarPorId(Long id) {
        AutorEntity autor = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor com ID " + id + " não encontrado"));

        return mapper.toDto(autor);
    }
}