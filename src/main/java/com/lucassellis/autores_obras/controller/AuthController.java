package com.lucassellis.autores_obras.controller;

import com.lucassellis.autores_obras.business.dto.LoginDTO;
import com.lucassellis.autores_obras.infrastructure.secutiry.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth") // aqui e para fazer o login (ex: localhost:8080/auth/login)
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login) { // vamos fazer o login

        // Usuário fixo para o desafio ser simples (sem precisar de banco para o login)
        // se o username de login for igual a "admin" e a senha for igual a "123456"
        if ("admin".equals(login.getUsername()) && "123456".equals(login.getPassword())) {

            // pega o username do login, gera o token chamando o jwtservice e guarda na variável 'token'
            String token = jwtService.gerarToken(login.getUsername());

            // DÚVIDA: "map.of nao sei pq ta ai"
            // RESPOSTA: O Map.of serve para criar um JSON rapidinho na resposta sem precisar de um DTO.
            // Ele vai mostrar no Postman assim: { "token": "seu-token-aqui" }
            return ResponseEntity.ok(Map.of("token", token));
        }

        // se nao for igual ao que pedimos acima vai vir essa resposta status 401 (Não Autorizado)
        // e no body vai vir essa informacao de texto
        return ResponseEntity.status(401).body("Usuário ou senha inválidos");
    }
}