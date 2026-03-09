package com.lucassellis.autores_obras.controller;

import com.lucassellis.autores_obras.business.dto.AutorDTO.AutorDTO;
import com.lucassellis.autores_obras.business.service.AutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autores") // o endereco dela é autores (ex: localhost:8080/autores)
@RequiredArgsConstructor
public class AutorController {

    private final AutorService service; // vamos trazer a service para a controller (injeção de dependência)

    // Método para SALVAR o autor
    @PostMapping // Quando o Postman mandar um POST para /autores
    public AutorDTO criar(@Valid @RequestBody AutorDTO dto) {
        // vamos criar um autor, para isso vamos receber o dto autor dentro do body da requisicao
        // @Valid: ele olha aquelas anotações que você fez no DTO (@NotBlank, @Email)
        // e se estiver errado, ele nem deixa o código entrar na Service!
        return service.criar(dto); // vamos criar chamando o metodo criar dentro da service e retornando
    }

    // Método para BUSCAR o autor por ID
    @GetMapping("/{id}") // metodo para buscar pelo id dentro de chaves sempre...
    public AutorDTO buscarPorId(@PathVariable Long id) {
        // @PathVariable: diz pro Spring que o número que vier na URL (ex: /autores/5)
        // deve ser colocado dentro dessa variável 'id'.
        return service.buscarPorId(id); // recebemos o id vamos chamar o metodo buscar por id na service
    }
}