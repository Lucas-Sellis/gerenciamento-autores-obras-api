package com.lucassellis.autores_obras.infrastructure.repository;

import com.lucassellis.autores_obras.infrastructure.entities.AutorEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<AutorEntity, Long> {

    Optional<AutorEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

}



