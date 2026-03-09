package com.lucassellis.autores_obras.infrastructure.repository;

import com.lucassellis.autores_obras.infrastructure.entities.AutorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// O Repository é uma INTERFACE: você diz O QUE quer, e o Spring faz o COMO (o SQL) sozinho.
// Ele estende JpaRepository para ganhar poderes de: Salvar, Deletar, Buscar e Contar.
public interface AutorRepository extends JpaRepository<AutorEntity, Long> {

    // O Spring lê o nome do método: "find" (buscar) "By" (por) "Email" (coluna email).
    // Ele cria o comando "SELECT * FROM autor WHERE email = ?" na hora!
    Optional<AutorEntity> findByEmail(String email);

    // 'existsBy' retorna apenas true ou false. 
    // É muito mais rápido que buscar o objeto inteiro só para saber se ele existe.
    boolean existsByEmail(String email);

    // Sua dúvida: "Está faltando algo?" -> Não! Para o que a API pede, está PERFEITO.
    // Usamos isso na Service para impedir que dois autores tenham o mesmo CPF.
    boolean existsByCpf(String cpf);
}