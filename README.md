📚 API de Gestão de Autores e Obras
Este projeto é uma API REST desenvolvida em Spring Boot para a gestão de autores e suas respectivas obras, com suporte a relacionamentos Many-to-Many e segurança robusta via JWT.

🚀 Tecnologias Utilizadas
Java 17

Spring Boot 3.2.3

Spring Security & JWT (Autenticação Bearer)

Spring Data JPA

H2 Database (Banco em memória para testes rápidos)

MapStruct (Mapeamento de DTOs)

Lombok (Produtividade)

🛠️ Como Executar o Projeto
Clone o repositório: git clone <url-do-seu-repo>

Importe como um projeto Gradle.

Execute o comando: ./gradlew bootRun.

A API estará disponível em http://localhost:8080.

🗄️ Banco de Dados (H2)
O console do banco de dados está liberado na segurança para facilitar o acompanhamento dos dados em tempo real:

Console H2: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb

User: sa | Password: (vazio)

🔐 Autenticação e Fluxo de Testes
Para acessar as rotas protegidas (como Cadastro de Obras e Autores), siga este fluxo:

Obter Token: Faça um POST em /auth/login com as credenciais cadastradas.

Configurar Header: Use o token recebido como Bearer Token nas requisições.

Regra de Negócio: Autores com país de origem "Brasil" exigem validação de CPF.

Relacionamentos: Uma obra só pode ser criada se pelo menos um ID de autor válido for enviado.