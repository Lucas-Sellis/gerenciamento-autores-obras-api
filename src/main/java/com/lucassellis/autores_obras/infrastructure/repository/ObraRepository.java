package com.lucassellis.autores_obras.infrastructure.repository;

import com.lucassellis.autores_obras.infrastructure.entities.ObraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ObraRepository  extends JpaRepository<ObraEntity, Long>  {

}




