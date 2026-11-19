# Pet Bem-Estar

Aplicação web para gerenciamento de tutores e animais de estimação, desenvolvida com Java, Spring Boot, Spring Data JPA, Thymeleaf e MySQL.

**Repositório:** https://github.com/alexsandrokobelinski/pet-bem-estar-spring

## Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Web MVC
- Spring Data JPA / Hibernate
- Thymeleaf
- MySQL
- Maven
- BCrypt para hash de senhas

## Funcionalidades implementadas nesta etapa da N2

### 1. Camada de serviço

Foi criada uma camada de serviços entre os controllers e os DAOs para os cadastros de tutores e animais.

- `TutorService`
- `AnimalService`

Os controllers delegam as operações aos serviços, que aplicam as regras de negócio e acessam os DAOs.

### 2. Regra para exclusão de tutor

O sistema impede a exclusão de um tutor que possua animais vinculados.

A tentativa de exclusão gera uma `RegraDeNegocioException` com mensagem explicativa. O tutor permanece cadastrado.

### 3. Telefone único por tutor

O sistema verifica se o telefone já está cadastrado para outro tutor.

- No cadastro, não permite telefone duplicado.
- Na edição, permite manter o telefone do próprio tutor.
- Se o telefone pertencer a outro tutor, apresenta uma mensagem de validação.

### 4. Detalhes do tutor e seus animais

A lista de tutores permite abrir a página de detalhes de cada tutor.

A página apresenta nome, telefone e os animais vinculados. A consulta dos animais é realizada no DAO por JPQL, filtrando pelo identificador do tutor.

### 5. Cadastro de usuário e autenticação

Foi implementado o cadastro de usuários e o login.

- As senhas são armazenadas com hash BCrypt.
- O sistema cria uma sessão após a autenticação.
- Um interceptor protege as páginas que exigem autenticação.
- Há opção para encerrar a sessão (sair).
- As rotas JSON protegidas retornam resposta HTTP 401 quando não há autenticação.

O acesso ao sistema é realizado com e-mail e senha.

### 6. Identidade visual

Foi criada a identidade visual do Pet Bem-Estar, com logo, folha de estilos e cabeçalho compartilhado nas páginas, inclusive na tela de login.

Paleta utilizada:

- Verde: `#2E8B7A`
- Laranja: `#F28C45`
- Fundo claro: `#FFF8EE`
- Texto: `#3B3A36`

## Estrutura principal do projeto

```text
src/
├── main/
│   ├── java/br/edu/ifrs/petbemestar/
│   │   ├── dao/
│   │   ├── dominio/
│   │   ├── servico/
│   │   └── web/
│   └── resources/
│       ├── static/
│       │   ├── css/
│       │   └── img/
│       └── templates/
└── test/
```

## Banco de dados

Nome do banco de dados:

```text
pet_bem_estar_spring
```

A configuração padrão está em `src/main/resources/application.properties`:

```properties
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/pet_bem_estar_spring}
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:teste}
```

É possível substituir os valores padrão pelas variáveis de ambiente `DB_URL`, `DB_USERNAME` e `DB_PASSWORD`.

O banco MySQL deve estar instalado e em execução. Crie o banco `pet_bem_estar_spring` antes de iniciar a aplicação. O Hibernate está configurado com `spring.jpa.hibernate.ddl-auto=update`.

## Como executar

Pré-requisitos:

- JDK 17 ou superior compatível com o projeto.
- MySQL em execução.
- Banco `pet_bem_estar_spring` criado.

No Windows, abra o PowerShell na pasta raiz do projeto e execute:

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw clean test
./mvnw spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8080
```

A tela de login pode ser acessada em:

```text
http://localhost:8080/login
```

Para criar um usuário, acesse:

```text
http://localhost:8080/cadastro
```

## Rotas principais

### Páginas

- `/login` — login.
- `/cadastro` — cadastro de usuário.
- `/tutores` — listagem de tutores.
- `/animais` — listagem de animais.
- `/sair` — encerramento da sessão.

### API REST

As rotas JSON de tutores e animais são disponibilizadas pelos respectivos controllers. As rotas protegidas exigem autenticação.

## Observação

As funcionalidades de atendimento e agendamento, incluindo regras de horários, não fazem parte dos itens implementados nesta etapa da N2.
