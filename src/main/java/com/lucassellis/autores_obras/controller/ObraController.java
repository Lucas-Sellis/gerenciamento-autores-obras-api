package com.lucassellis.autores_obras.controller;

import com.lucassellis.autores_obras.business.dto.ObraDTO.ObraDTO;
import com.lucassellis.autores_obras.business.service.ObraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/obras") // o endereço dela é obras (ex: localhost:8080/obras)
@RequiredArgsConstructor
public class ObraController {

    private final ObraService service;

    // Criar uma nova obra
    @PostMapping
    public ResponseEntity<ObraDTO> criar(@Valid @RequestBody ObraDTO dto) {
        // método de criar obra, ele vai receber os dados da obra (DTO) dentro do corpo da requisição
        // e o @Valid vai validar nossas regras que colocamos lá no DTO
        ObraDTO novaObra = service.criar(dto);

        // o retorno vai ser um status 201 (Created) e o corpo da nova obra que acabou de ser criada
        return ResponseEntity.status(201).body(novaObra);

    }

    // Buscar obra por ID
    @GetMapping("/{id}")
    public ResponseEntity<ObraDTO> buscarPorId(@PathVariable Long id) {
        // ResponseEntity.ok já manda o status 200 automaticamente
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping // Isso permite o GET em /obras para listar tudo
    public ResponseEntity<List<ObraDTO>> listar() {
        // Aqui sim, no <List<ObraDTO>>, estamos avisando que o corpo da resposta é uma lista!
        return ResponseEntity.ok(service.listarTodos());
    }
}