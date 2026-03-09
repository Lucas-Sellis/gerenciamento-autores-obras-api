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
    private final AutorRepository autorRepository; // Porque para criar uma Obra, você precisa buscar os Autores que já existem.
                                                    // Sem o repositório do autor, você não consegue "achar" o dono da obra no banco.
    private final ObraMapper mapper;

    @Transactional
    // Isso garante que, se der erro no meio do salvamento, o banco "volta atrás" em tudo.
    // Ou salva tudo (Obra + Autores), ou não salva nada. Evita deixar lixo no banco.

    public ObraDTO criar(ObraDTO dto) {

        // 1. Regra das datas: se o usuário deixou as duas nulas, a gente barra!
        if (dto.getDataPublicacao() == null && dto.getDataExposicao() == null) {
            throw new RuntimeException("A obra deve ter pelo menos uma data (publicação ou exposição).");
        }

        // 2. Transforma o DTO (Web) em Entity (Banco)
        ObraEntity obraEntity = mapper.toEntity(dto);

        // 3. Busca os autores no banco pelos IDs enviados no DTO
        // DÚVIDA: "Se não for nula ou vazia" -> Estamos checando se o usuário mandou algum ID de autor. Se ele mandou...
        if (dto.getAutoresIds() != null && !dto.getAutoresIds().isEmpty()) {

            // Vai no banco de autores e traz todo mundo que tem o ID da lista
            List<AutorEntity> autoresEncontrados = autorRepository.findAllById(dto.getAutoresIds());

            // DÚVIDA: "Se o tamanho for diferente" -> Se eu pedi 3 autores e o banco só achou 2, significa que tem ID que não existe.
            // Aí a gente joga a mensagem de erro!
            if (autoresEncontrados.size() != dto.getAutoresIds().size()) { // se o tamanho de autores encontrados nao for do tamanho que ele achou
                throw new RuntimeException("Um ou mais autores informados não foram encontrados.");
            }

            // DÚVIDA: "HashSet" -> O HashSet é só pra garantir que não tenha autor repetido na lista. A gente "seta" esses autores dentro da obra.
            obraEntity.setAutores(new HashSet<>(autoresEncontrados));
        }

        // 4. Salva a obra de fato no banco (e o Spring já cria a relação na tabela 'obra_autor' sozinho!)
        ObraEntity obraSalva = repository.save(obraEntity);

        // 5. Devolve a resposta pro usuário
        ObraDTO resposta = mapper.toDto(obraSalva);
        resposta.setAutoresIds(dto.getAutoresIds()); // Devolvemos os IDs para o cliente ver no Postman

        return resposta;
    }

    public List<ObraDTO> listarTodos() { // para listar todos
        return repository.findAll() // buscar todos
                .stream() //  vai passando item por item.
                .map(mapper::toDto) // Pega cada obra que passou na esteira e "traduz" para DTO.
                .toList(); // No final da esteira, coloca tudo dentro de uma lista de novo.
    }

    public ObraDTO buscarPorId(Long id) {
        ObraEntity obra = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Obra não encontrada com o ID: " + id));

        ObraDTO dto = mapper.toDto(obra);

        // DÚVIDA: "Toda essa parte do stream" -> O banco nos deu a Obra com os objetos Autores dentro.

        // Esse código pega os autores, extrai só o ID de cada um e coloca na listinha do DTO.
        dto.setAutoresIds(obra.getAutores() // O usuário quer ver apenas os NÚMEROS (IDs) dos autores.
                .stream()//  vai passando item por item.
                .map(AutorEntity::getId)// Pega cada obra que passou na esteira
                .toList());// No final da esteira, coloca tudo dentro de uma lista de novo.

        return dto;
    }
}