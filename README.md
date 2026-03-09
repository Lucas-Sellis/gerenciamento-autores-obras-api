# 📚 API de Gestão de Autores e Obras
Este projeto é uma API REST desenvolvida em **Spring Boot** para a gestão de autores e as suas respetivas obras, com suporte a relacionamentos muitos-para-muitos e segurança via JWT.
## 🚀 Tecnologias Utilizadas
- **Java 17**
- **Spring Boot 3.x**
- **Spring Security & JWT** (Autenticação Bearer)
- **Spring Data JPA**
- **H2 Database** (Banco em memória)
- **Lombok & MapStruct**
## 🛠️ Como Executar o Projeto
1. Clone o repositório.
2. Certifique-se de que tem o Maven ou Gradle instalado.
3. Execute o comando: `./mvnw spring-boot:run` ou `./gradlew bootRun`.
4. A API estará disponível em `http:
## 🗄️ Banco de Dados (H2)
O projeto utiliza o banco de dados H2 para facilitar a avaliação.
- **Console H2:** `http:
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **User:** `sa` | **Password:** (vazio)
## 🔐 Autenticação
Para aceder às rotas protegidas, é necessário obter um token:
1. Faça um **POST** em `/auth/login` com o corpo:
   ```json
   { "username": "admin", "password": "123456" }
