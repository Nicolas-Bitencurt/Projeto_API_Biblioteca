# 📚 API Biblioteca - Sistema de Gerenciamento de Biblioteca

Uma aplicação RESTful completa para gerenciamento de biblioteca desenvolvida em **Java com Spring Boot 3.5.8**, incluindo autenticação JWT, Swagger, migrations com Flyway e segurança implementada.

## ✨ Características Principais

### ✅ Completo CRUD
- **Usuários** - Registro e gerenciamento
- **Autores** - Cadastro de autores
- **Categorias** - Classificação de livros
- **Livros** - Gerenciamento do acervo
- **Empréstimos** - Controle de empréstimos e devoluções

### 🔐 Segurança
- Autenticação JWT (Bearer Token)
- Criptografia de senhas com BCrypt
- Controle de acesso baseado em tokens
- Endpoints públicos e protegidos

### 📖 Documentação
- **Swagger UI** - Documentação interativa
- **OpenAPI 3.0** - Especificação completa
- Exemplos de requisições e respostas
- Markdown detalhado

### 🗄️ Banco de Dados
- PostgreSQL 12+
- JPA/Hibernate
- Flyway para migrations
- Índices otimizados

### 🏗️ Arquitetura em Camadas
- **Domain** - Entidades, DTOs, Repositórios
- **Application** - Serviços e lógica de negócio
- **Presentation** - Controladores REST
- **Infrastructure** - Configurações e segurança

### 📊 Relacionamentos
- Um-para-Muitos: Autor → Livros
- Um-para-Muitos: Categoria → Livros
- Um-para-Muitos: Usuário → Empréstimos
- Muitos-para-Um: Livros → Autor/Categoria

## 🚀 Quick Start

### Pré-requisitos
- Java 17+
- Maven 3.8+
- PostgreSQL 12+

### Instalação Rápida

1. **Criar banco de dados:**
```sql
CREATE DATABASE biblioteca_db;
```

2. **Navegar para o projeto:**
```bash
cd api
```

3. **Executar a aplicação:**
```bash
mvn spring-boot:run
```

4. **Acessar Swagger:**
```
http://localhost:8080/swagger-ui.html
```

## 📡 Endpoints Principais

### Autenticação
```
POST   /api/auth/register    # Registrar usuário
POST   /api/auth/login       # Fazer login (retorna JWT)
```

### Recursos (CRUD Completo)
```
GET    /api/{recurso}              # Listar todos
GET    /api/{recurso}/{id}         # Obter por ID
GET    /api/{recurso}/buscar/**    # Buscar por campo
POST   /api/{recurso}              # Criar
PUT    /api/{recurso}/{id}         # Atualizar
DELETE /api/{recurso}/{id}         # Excluir
```

**Recursos disponíveis:**
- `/usuarios`
- `/autores`
- `/categorias`
- `/livros`
- `/emprestimos`

## 🔑 Exemplo de Uso

### 1. Registrar
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@example.com",
    "senha": "senha123",
    "funcao": "USER"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@example.com",
    "senha": "senha123"
  }'
```

Retorna: `{ "token": "eyJhbGc...", "tipo": "Bearer" }`

### 3. Usar Token
```bash
curl -X GET http://localhost:8080/api/livros \
  -H "Authorization: Bearer {seu_token}"
```

## 📁 Estrutura do Projeto

```
api/
├── src/main/java/com/biblioteca/api/
│   ├── domain/
│   │   ├── entity/       # Entidades JPA
│   │   │   ├── Usuario.java
│   │   │   ├── Autor.java
│   │   │   ├── Categoria.java
│   │   │   ├── Livro.java
│   │   │   └── Emprestimo.java
│   │   ├── dto/          # Data Transfer Objects
│   │   │   ├── UsuarioDTO.java
│   │   │   ├── AutorDTO.java
│   │   │   ├── CategoriaDTO.java
│   │   │   ├── LivroDTO.java
│   │   │   ├── EmprestimoDTO.java
│   │   │   ├── LoginDTO.java
│   │   │   └── TokenDTO.java
│   │   └── repository/   # Interfaces JPA
│   │       ├── UsuarioRepository.java
│   │       ├── AutorRepository.java
│   │       ├── CategoriaRepository.java
│   │       ├── LivroRepository.java
│   │       └── EmprestimoRepository.java
│   ├── application/
│   │   └── service/      # Lógica de negócio
│   │       ├── UsuarioService.java
│   │       ├── AutorService.java
│   │       ├── CategoriaService.java
│   │       ├── LivroService.java
│   │       ├── EmprestimoService.java
│   │       └── AuthenticationService.java
│   ├── presentation/
│   │   └── controller/   # Endpoints REST
│   │       ├── AuthenticationController.java
│   │       ├── UsuarioController.java
│   │       ├── AutorController.java
│   │       ├── CategoriaController.java
│   │       ├── LivroController.java
│   │       └── EmprestimoController.java
│   └── infrastructure/
│       ├── config/
│       │   ├── SecurityConfig.java
│       │   └── OpenApiConfig.java
│       ├── security/
│       │   ├── JwtService.java
│       │   ├── JwtAuthenticationFilter.java
│       │   └── CustomUserDetailsService.java
│       └── exception/
│           ├── GlobalExceptionHandler.java
│           ├── ResourceNotFoundException.java
│           └── ErrorResponse.java
├── src/main/resources/
│   ├── application.properties
│   └── db/migration/
│       ├── V1__Initial_schema.sql
│       └── V2__Insert_sample_data.sql
├── pom.xml
└── mvnw / mvnw.cmd
```

## 🔧 Configurações

### application.properties

```properties
# Banco de Dados
spring.datasource.url=jdbc:postgresql://localhost:5432/biblioteca_db
spring.datasource.username=postgres
spring.datasource.password=postgres

# JWT
jwt.secret=mySecretKeyThatIsLongEnoughForHS256AlgorithmRequirement
jwt.expiration=86400000  # 24 horas

# Flyway
spring.flyway.locations=classpath:db/migration
```

## 📊 Modelo ER (Entidade-Relacionamento)

```
┌─────────────┐
│   USUARIOS  │
├─────────────┤
│ id (PK)     │────┐
│ nome        │    │
│ email       │    │
│ senha       │    │ 1:N
│ funcao      │    │
└─────────────┘    │
                   │
                   │
                   └─────────┐
                             │
                    ┌────────────────┐
                    │  EMPRESTIMOS   │
                    ├────────────────┤
                    │ id (PK)        │
                    │ dataEmprestimo │
                    │ dataDevolucao  │
                    │ status         │
                    │ usuario_id (FK)│
                    │ livro_id (FK)  │
                    └────────────────┘
                             │
                             │ N:1
                             │
        ┌────────────────────┴────────────────────┐
        │                                         │
   ┌────────────┐                          ┌──────────┐
   │   LIVROS   │                          │  AUTORES │
   ├────────────┤                          ├──────────┤
   │ id (PK)    │                          │ id (PK)  │
   │ titulo     │─────────────┬────────────│ nome     │
   │ isbn       │             │ N:1        │ biografia│
   │ descricao  │             │            └──────────┘
   │ quantidade │             │
   │ autor_id───┼─────────────┘
   │ categoria  │
   │ _id────────┼───────┐
   └────────────┘       │
                        │ N:1
                        │
                   ┌─────────────┐
                   │ CATEGORIAS  │
                   ├─────────────┤
                   │ id (PK)     │
                   │ nome        │
                   │ descricao   │
                   └─────────────┘
```

## 📋 Requisitos Atendidos

✅ API facilmente integrada com frontend  
✅ Endpoints para CRUD completo (POST, GET, PUT, DELETE)  
✅ Consulta por chave primária e campos específicos  
✅ Métodos HTTP adequados com parâmetros corretos  
✅ DTOs para cada recurso  
✅ Organização em camadas (Domain, Application, Presentation, Infrastructure)  
✅ Entidades com mapeamentos, validações e relacionamentos  
✅ Mecanismo de segurança (JWT + BCrypt)  
✅ Documentação (Swagger + Markdown)  
✅ Migrations (Flyway)  

## 🧪 Testando os Endpoints

### Via Swagger UI
```
http://localhost:8080/swagger-ui.html
```
- Interface interativa
- Teste direto dos endpoints
- Ver estrutura de requisição/resposta

### Via cURL
Veja o arquivo `GUIA_INSTALACAO.md` para exemplos completos

### Via Postman/Insomnia
Importe as requisições do Swagger JSON:
```
http://localhost:8080/v3/api-docs
```

## 🐛 Tratamento de Erros

Respostas padronizadas:

```json
{
  "status": 404,
  "message": "Livro não encontrado",
  "timestamp": "2024-01-15T10:30:00"
}
```

**Códigos HTTP:**
- 200 - OK
- 201 - Created
- 204 - No Content
- 400 - Bad Request
- 404 - Not Found
- 500 - Internal Server Error

## 📖 Documentação Completa

- **DOCUMENTACAO.md** - Documentação técnica completa
- **GUIA_INSTALACAO.md** - Passo a passo de instalação

## 🛠️ Stack Tecnológico

- **Java 17**
- **Spring Boot 3.5.8**
- **Spring Security**
- **Spring Data JPA**
- **Hibernate**
- **PostgreSQL**
- **JWT (JJWT)**
- **Flyway**
- **Swagger/OpenAPI 3.0**
- **Lombok**
- **Maven**

## 📝 Validações Implementadas

- `@NotBlank` - Campos obrigatórios
- `@Email` - Validação de email
- `@Positive` - Valores positivos
- Unique constraints - Campos únicos
- Relacionamentos cascata

## 🔐 Segurança

- ✅ Autenticação JWT
- ✅ Criptografia BCrypt
- ✅ CSRF desabilitado (API REST)
- ✅ Session Stateless
- ✅ Endpoints protegidos
- ✅ Controle de acesso por função

## 💡 Diferenciais

1. **Arquitetura limpa** - Separação clara de responsabilidades
2. **Validações robustas** - Dados sempre consistentes
3. **Tratamento de exceções** - Erros padronizados
4. **Documentação completa** - Swagger interativo + Markdown
5. **Migrations versionadas** - Controle de schema com Flyway
6. **JWT seguro** - HS256 com expiração
7. **Índices otimizados** - Performance em queries
8. **DTOs segregados** - Criação vs Atualização

