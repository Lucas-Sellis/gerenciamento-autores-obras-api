package com.lucassellis.autores_obras.business.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {

    // Por que não tem na Entity?
    // Porque o Login é apenas uma "chave de acesso".
    // Nós não salvamos uma "tabela de login" toda vez que o usuário tenta entrar.
    // Usamos esse DTO apenas para receber os dados do Postman e comparar com o banco.

    private String username;
    private String password;
}