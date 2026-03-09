package com.lucassellis.autores_obras.controller;

import com.lucassellis.autores_obras.business.dto.ObraDTO.ObraDTO;
import com.lucassellis.autores_obras.business.service.ObraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/obras")
@RequiredArgsConstructor
public class ObraController {

    private final ObraService service;

    @PostMapping
    public ResponseEntity<ObraDTO> criar(@Valid @RequestBody ObraDTO dto) {

        ObraDTO novaObra = service.criar(dto);
        return ResponseEntity.status(201).body(novaObra);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObraDTO> buscarPorId(@PathVariable Long id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ObraDTO>> listar() {

        return ResponseEntity.ok(service.listarTodos());
    }

}