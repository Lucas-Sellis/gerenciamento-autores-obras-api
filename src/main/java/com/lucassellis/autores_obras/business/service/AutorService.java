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

    private final AutorRepository repository; // injeçao de dependencia: precisamos do repository aqui
    private final AutorMapper mapper; // mapper tambem para fazer a traducao

   // criar um autor recebendo ele como dto
    public AutorDTO criar(AutorDTO dto) {

        // 1. Regra do CPF para Brasil (Validação manual)
        // se a palavra for igual a Brasil (ignorando se é maiúsculo) e o cpf for nulo ou em branco
        // ou seja: se o autor for brasileiro, é necessário colocar o cpf dele!
        if ("Brasil".equalsIgnoreCase(dto.getPais()) &&
                (dto.getCpf() == null || dto.getCpf().isBlank())) {
            throw new RuntimeException("CPF é obrigatório para autores do Brasil");
        }

        // 2. Validação de Email único no banco
        // se o email recebido no dto ja existe no banco lá do repositorio
        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Este e-mail já está em uso!"); // devolve essa mensagem (podia ser outra exceção, mas assim é mais simples)
        }

        // 3. Validação de CPF único (só se o CPF não estiver vazio)
        // se o cpf nao for nulo e nao estiver em branco...
        if (dto.getCpf() != null && !dto.getCpf().isBlank()) {
            if (repository.existsByCpf(dto.getCpf())) { // se o cpf recebido existir no repositorio
                throw new RuntimeException("Este CPF já está cadastrado!"); // jogue a mensagem
            }
        }

        // passou das validações.... agora vamos trabalhar:

        // 4. Usando o Mapper para transformar DTO em Entity
        // recebemos o dto, vamos passar para entity usando o mapper e transformar em autorEntity (o banco só entende entity)
        AutorEntity autorEntity = mapper.toEntity(dto);

        // 5. Salvando no banco de dados através do Repository
        // vamos receber o autorEntity, salvar no repository e dar o nome de autorSalvo
        // (precisa ser Entity aqui porque o banco devolve o objeto com o ID que ele criou)
        AutorEntity autorSalvo = repository.save(autorEntity);

        // 6. Transformando a Entity salva de volta em DTO para retornar
        // vamos pegar o autor salvo, passar para dto usando o mapper e retornar ao cliente
        return mapper.toDto(autorSalvo);
    }

    // Regra mental: Busca por ID com orElseThrow
    public AutorDTO buscarPorId(Long id) { // buscar autor por id recebendo o número (long id)
        AutorEntity autorId = repository.findById(id) // pegue o id, procure por ele no repositorio e dê o nome de autor
                .orElseThrow(() -> new RuntimeException("Autor com ID " + id + " não encontrado")); // se não achar nada, lance essa exceção

        return mapper.toDto(autorId); // depois pegue o autor, passe para dto usando o mapper e retorne pro cliente
    }
}