# 📚 API Biblioteca - Documentação Completa

## Visão Geral

A **API Biblioteca** é uma aplicação REST desenvolvida em Java com Spring Boot que permite gerenciar uma biblioteca, incluindo livros, autores, categorias, usuários e empréstimos com devolução.

## 📋 Requisitos Atendidos

✅ **API integrada com frontend** - RESTful com respostas em JSON
✅ **Endpoints para todas operações CRUD:**
- ✅ Criação de novo registro (POST)
- ✅ Atualização de registro (PUT)
- ✅ Exclusão de registro (DELETE)
- ✅ Consulta por chave primária (GET /{id})
- ✅ Consulta de todos registros (GET)
- ✅ Consulta por campos específicos (GET /buscar)

✅ **Métodos HTTP adequados** - GET, POST, PUT, DELETE com parâmetros corretos
✅ **DTOs para cada recurso** - Separação entre entidade e apresentação
✅ **Organização em camadas:**
- Domain (Entidades, DTOs, Repositórios)
- Application (Serviços)
- Presentation (Controladores)
- Infrastructure (Configurações, Segurança, Exceções)

✅ **Entidades com mapeamentos, validações e relacionamentos:**
- Um-para-Muitos (Autor → Livros, Categoria → Livros)
- Muitos-para-Um (Livros → Autor, Livros → Categoria)
- Um-para-Muitos (Usuário → Empréstimos, Livro → Empréstimos)

✅ **Segurança implementada** - Autenticação JWT
✅ **Documentação** - Swagger/OpenAPI integrado
✅ **Migrations** - Flyway para controle de schema

## 🏗️ Arquitetura

```
api/
├── src/main/java/com/biblioteca/api/
│   ├── domain/
│   │   ├── entity/          # Entidades JPA
│   │   ├── dto/             # DTOs para transferência de dados
│   │   └── repository/      # Interfaces de repositório
│   ├── application/
│   │   └── service/         # Lógica de negócio
│   ├── presentation/
│   │   └── controller/      # Endpoints REST
│   └── infrastructure/
│       ├── config/          # Configurações (Security, OpenAPI)
│       ├── security/        # JWT, UserDetails
│       └── exception/       # Tratamento de exceções
└── resources/
    ├── application.properties
    └── db/migration/        # Scripts Flyway
```

## 📊 Modelo de Dados

### Usuários
- **id**: Long (PK)
- **nome**: String
- **email**: String (Unique)
- **senha**: String (Criptografada)
- **funcao**: String (ADMIN, USER, etc)
- **dataCriacao**: LocalDateTime
- **dataAtualizacao**: LocalDateTime

### Autores
- **id**: Long (PK)
- **nome**: String
- **biografia**: Text
- **dataCriacao**: LocalDateTime
- **dataAtualizacao**: LocalDateTime
- **livros**: List<Livro> (One-to-Many)

### Categorias
- **id**: Long (PK)
- **nome**: String (Unique)
- **descricao**: Text
- **dataCriacao**: LocalDateTime
- **dataAtualizacao**: LocalDateTime
- **livros**: List<Livro> (One-to-Many)

### Livros
- **id**: Long (PK)
- **titulo**: String
- **isbn**: String (Unique)
- **descricao**: Text
- **anoPublicacao**: Integer
- **quantidadeTotal**: Integer
- **quantidadeDisponivel**: Integer
- **autor**: Autor (Many-to-One)
- **categoria**: Categoria (Many-to-One)
- **dataCriacao**: LocalDateTime
- **dataAtualizacao**: LocalDateTime

### Empréstimos
- **id**: Long (PK)
- **dataEmprestimo**: LocalDate
- **dataDevolucaoPrevista**: LocalDate
- **dataDevolucaoReal**: LocalDate (Nullable)
- **status**: String (ATIVO, DEVOLVIDO, ATRASADO)
- **usuario**: Usuario (Many-to-One)
- **livro**: Livro (Many-to-One)
- **dataCriacao**: LocalDateTime
- **dataAtualizacao**: LocalDateTime

## 🔐 Autenticação e Segurança

### JWT Token
- Implementação: JWT com JJWT
- Algoritmo: HS256
- Expiração: 24 horas (configurável)
- Header: `Authorization: Bearer {token}`

### Endpoints Públicos
- `POST /api/auth/register` - Registrar novo usuário
- `POST /api/auth/login` - Fazer login
- `GET /api/livros` - Listar livros
- `GET /api/autores` - Listar autores
- `GET /api/categorias` - Listar categorias
- Swagger UI

### Endpoints Protegidos
- Todas as operações de escrita (POST, PUT, DELETE)
- Consultas de usuários
- Consultas de empréstimos

## 📡 Endpoints da API

### Autenticação
```
POST   /api/auth/register           # Registrar usuário
POST   /api/auth/login              # Login (retorna JWT)
```

### Usuários
```
GET    /api/usuarios                # Listar todos
GET    /api/usuarios/{id}           # Obter por ID
GET    /api/usuarios/buscar/email   # Buscar por email (param: email)
PUT    /api/usuarios/{id}           # Atualizar
DELETE /api/usuarios/{id}           # Excluir
```

### Autores
```
POST   /api/autores                 # Criar novo
GET    /api/autores                 # Listar todos
GET    /api/autores/{id}            # Obter por ID
GET    /api/autores/buscar/nome     # Buscar por nome (param: nome)
PUT    /api/autores/{id}            # Atualizar
DELETE /api/autores/{id}            # Excluir
```

### Categorias
```
POST   /api/categorias              # Criar novo
GET    /api/categorias              # Listar todas
GET    /api/categorias/{id}         # Obter por ID
GET    /api/categorias/buscar/nome  # Buscar por nome (param: nome)
PUT    /api/categorias/{id}         # Atualizar
DELETE /api/categorias/{id}         # Excluir
```

### Livros
```
POST   /api/livros                  # Criar novo
GET    /api/livros                  # Listar todos
GET    /api/livros/{id}             # Obter por ID
GET    /api/livros/buscar/titulo    # Buscar por título (param: titulo)
GET    /api/livros/buscar/autor/{autorId}      # Buscar por autor
GET    /api/livros/buscar/categoria/{categoriaId} # Buscar por categoria
PUT    /api/livros/{id}             # Atualizar
DELETE /api/livros/{id}             # Excluir
```

### Empréstimos
```
POST   /api/emprestimos             # Criar novo empréstimo
GET    /api/emprestimos             # Listar todos
GET    /api/emprestimos/{id}        # Obter por ID
GET    /api/emprestimos/buscar/usuario/{usuarioId}  # Por usuário
GET    /api/emprestimos/buscar/livro/{livroId}      # Por livro
GET    /api/emprestimos/buscar/status/{status}      # Por status
PUT    /api/emprestimos/{id}/devolver               # Devolver livro
PUT    /api/emprestimos/{id}        # Atualizar
DELETE /api/emprestimos/{id}        # Excluir
```

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- PostgreSQL 12+
- Maven 3.8+

### Configuração do Banco de Dados

1. Criar banco de dados:
```sql
CREATE DATABASE biblioteca_db;
```

2. Verificar credenciais em `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/biblioteca_db
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### Executar a Aplicação

#### Usando Maven:
```bash
mvn spring-boot:run
```

#### Ou buildando e executando JAR:
```bash
mvn clean package
java -jar api/target/api-0.0.1-SNAPSHOT.jar
```

A aplicação iniciará em: `http://localhost:8080`

## 📖 Documentação Swagger

Acesse a documentação interativa em:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 📝 Exemplos de Requisições

### 1. Registrar Usuário
```bash
POST /api/auth/register
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@example.com",
  "senha": "senha123",
  "funcao": "USER"
}
```

### 2. Login
```bash
POST /api/auth/login
Content-Type: application/json

{
  "email": "joao@example.com",
  "senha": "senha123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer"
}
```

### 3. Criar Autor
```bash
POST /api/autores
Content-Type: application/json
Authorization: Bearer {token}

{
  "nome": "Machado de Assis",
  "biografia": "Escritor brasileiro do século XIX"
}
```

### 4. Criar Categoria
```bash
POST /api/categorias
Content-Type: application/json
Authorization: Bearer {token}

{
  "nome": "Romance",
  "descricao": "Livros de narrativa ficcional"
}
```

### 5. Criar Livro
```bash
POST /api/livros
Content-Type: application/json
Authorization: Bearer {token}

{
  "titulo": "Dom Casmurro",
  "isbn": "978-8535914443",
  "descricao": "Um dos maiores clássicos da literatura brasileira",
  "anoPublicacao": 1899,
  "quantidadeTotal": 5,
  "quantidadeDisponivel": 5,
  "autorId": 1,
  "categoriaId": 1
}
```

### 6. Criar Empréstimo
```bash
POST /api/emprestimos
Content-Type: application/json
Authorization: Bearer {token}

{
  "usuarioId": 1,
  "livroId": 1
}
```

### 7. Devolver Livro
```bash
PUT /api/emprestimos/1/devolver
Authorization: Bearer {token}
```

## 🗄️ Migrations Flyway

As migrations são executadas automaticamente ao iniciar a aplicação:

- **V1__Initial_schema.sql** - Cria as tabelas, índices e constraints
- **V2__Insert_sample_data.sql** - Insere dados de exemplo

Localização: `src/main/resources/db/migration/`

## ✅ Testes

Para executar os testes:
```bash
mvn test
```

## 🐛 Tratamento de Erros

A API retorna respostas de erro padronizadas:

```json
{
  "status": 404,
  "message": "Livro não encontrado",
  "timestamp": "2024-01-15T10:30:00"
}
```

Códigos HTTP usados:
- **200** - OK
- **201** - Created
- **204** - No Content
- **400** - Bad Request
- **404** - Not Found
- **500** - Internal Server Error

## 📦 Dependências Principais

- Spring Boot 3.5.8
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL Driver
- JWT (JJWT)
- Lombok
- Flyway
- Springdoc OpenAPI (Swagger)
- Validation

## 📄 Estrutura de Camadas

### Domain Layer
Contém as entidades, DTOs e repositórios. É o núcleo da aplicação.

### Application Layer
Contém os serviços com a lógica de negócio. Faz a orquestração entre entidades e repositórios.

### Presentation Layer
Contém os controladores REST que expõem os endpoints da API.

### Infrastructure Layer
Contém configurações de segurança, OpenAPI, tratamento de exceções.

## 🔍 Validações

Todas as entidades e DTOs possuem validações:
- `@NotBlank` - Campo não pode ser vazio
- `@Email` - Validação de email
- `@Positive` - Valores positivos
- `@Unique` (implícito) - Campos únicos no banco

