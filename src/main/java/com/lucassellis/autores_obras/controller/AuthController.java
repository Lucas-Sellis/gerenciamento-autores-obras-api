package com.lucassellis.autores_obras.controller;

import com.lucassellis.autores_obras.business.dto.LoginDTO;
import com.lucassellis.autores_obras.infrastructure.secutiry.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) {
        // Usuário fixo para o desafio ser simples
        if ("admin".equals(login.getUsername()) && "123456".equals(login.getPassword())) {
            String token = jwtService.gerarToken(login.getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        }

        return ResponseEntity.status(401).body("Usuário ou senha inválidos");
    }
}