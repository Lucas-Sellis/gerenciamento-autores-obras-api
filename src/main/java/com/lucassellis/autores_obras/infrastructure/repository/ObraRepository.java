package com.lucassellis.autores_obras.infrastructure.repository;

import com.lucassellis.autores_obras.infrastructure.entities.ObraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Avisa ao Spring: "Este é o componente que mexe com o banco de dados"
public interface ObraRepository extends JpaRepository<ObraEntity, Long> {

    // "Por que não tem nada aqui?" -> Porque o JpaRepository já te deu de presente:
    // 1. .save(obra) -> Para criar ou atualizar.
    // 2. .findById(id) -> Para buscar uma obra específica.
    // 3. .findAll() -> Para listar todas as obras.
    // 4. .deleteById(id) -> Para apagar.

    // Como o desafio não pede buscas malucas por Obra (ex: busca por preço ou data),
    // o padrão que o Spring te deu já é 100% suficiente!
}