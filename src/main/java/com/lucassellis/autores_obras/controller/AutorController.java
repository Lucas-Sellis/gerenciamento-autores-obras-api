package com.lucassellis.autores_obras.controller;

import com.lucassellis.autores_obras.business.dto.AutorDTO.AutorDTO;
import com.lucassellis.autores_obras.business.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController {

    private final AutorService service;

    @PostMapping
    public AutorDTO criar(@Valid @RequestBody AutorDTO dto) {

        return service.criar(dto);
    }

    @GetMapping("/{id}")
    public AutorDTO buscarPorId(@PathVariable Long id) {

        return service.buscarPorId(id);
    }

}