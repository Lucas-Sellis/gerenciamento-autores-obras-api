package com.lucassellis.autores_obras.business.service;

import com.lucassellis.autores_obras.business.dto.ObraDTO.ObraDTO;
import com.lucassellis.autores_obras.business.mapper.ObraMapper;
import com.lucassellis.autores_obras.infrastructure.entities.AutorEntity;
import com.lucassellis.autores_obras.infrastructure.entities.ObraEntity;
import com.lucassellis.autores_obras.infrastructure.repository.AutorRepository;
import com.lucassellis.autores_obras.infrastructure.repository.ObraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        // 1. Validação da regra: Pelo menos uma data deve ser obrigatória
        if (dto.getDataPublicacao() == null && dto.getDataExposicao() == null) {
            throw new RuntimeException("A obra deve ter pelo menos uma data (publicação ou exposição).");
        }

        // 2. Transforma o DTO em Entity (campos básicos)
        ObraEntity obraEntity = mapper.toEntity(dto);

        // 3. Busca os autores no banco pelos IDs enviados no DTO
        if (dto.getAutoresIds() != null && !dto.getAutoresIds().isEmpty()) {
            List<AutorEntity> autoresEncontrados = autorRepository.findAllById(dto.getAutoresIds());

            // Validação extra: se algum ID não existir, podemos avisar
            if (autoresEncontrados.size() != dto.getAutoresIds().size()) {
                throw new RuntimeException("Um ou mais autores informados não foram encontrados.");
            }

            obraEntity.setAutores(new HashSet<>(autoresEncontrados));
        }

        // 4. Salva a obra e a relação na tabela intermediária 'obra_autor'
        ObraEntity obraSalva = repository.save(obraEntity);

        // 5. Retorna o DTO (Note: o seu toDto pode precisar de ajuste manual para os IDs se desejar retornar eles)
        ObraDTO resposta = mapper.toDto(obraSalva);
        resposta.setAutoresIds(dto.getAutoresIds()); // Devolvemos os IDs para o cliente ver

        return resposta;
    }


    public List<ObraDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public ObraDTO buscarPorId(Long id) {
        ObraEntity obra = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada com o ID: " + id));

        ObraDTO dto = mapper.toDto(obra);
        // Mapeia os IDs dos autores de volta para o DTO
        dto.setAutoresIds(obra.getAutores().stream().map(AutorEntity::getId).toList());

        return dto;
    }
}