package com.lucassellis.autores_obras.infrastructure.secutiry;

import com.lucassellis.autores_obras.infrastructure.secutiry.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    // Essa classe intercepta cada requisição e verifica se tem token e se o mesmo é valido
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Pega o cabeçalho Authorization
        String authHeader = request.getHeader("Authorization");

        // 2. Verifica se é um Bearer Token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String username = jwtService.extrairUsername(token);

            // 3. Se extraiu o nome e o usuário não está autenticado ainda
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Criamos um objeto de autenticação simples
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username, null, Collections.emptyList()
                );

                // Salva a autenticação no contexto do Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 4. Segue o fluxo da requisição
        filterChain.doFilter(request, response);
    }
}