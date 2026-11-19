README · MD
Pet Bem-Estar

Uma aplicação web feita com Spring Boot para cuidar de tutores e dos seus animais de estimação. O projeto nasceu como atividade da disciplina de Programação para Web I, no IFRS.

 Repositório: https://github.com/alexsandrokobelinski/pet-bem-estar-spring

 O que foi usado
Java
Spring Boot e Spring MVC
Spring Data JPA / Hibernate
Thymeleaf
MySQL
Maven
BCrypt (via spring-security-crypto), para guardar as senhas com segurança
HTML e CSS


O que o sistema já faz
Login e controle de acesso
Cadastro de usuários com nome, e-mail e senha, avisando quando o e-mail já existe.
A senha é guardada com hash BCrypt, nunca em texto puro.
Login por e-mail e senha, com a sessão controlada por HttpSession.
As rotas são protegidas por um LoginInterceptor: quem tenta abrir uma página protegida sem estar logado é levado para a tela de login, e as rotas REST respondem com HTTP 401.
Dá para encerrar a sessão pela opção de sair.

Tutores
Listar, cadastrar, editar e excluir tutores.
Não deixa cadastrar dois tutores com o mesmo telefone, mas, ao editar, o tutor pode continuar com o telefone que já é dele.
Não deixa excluir um tutor que ainda tenha animais cadastrados. Quando uma regra impede a operação, a tela mostra uma mensagem explicando o motivo.
Página de detalhes com nome, telefone e os animais vinculados ao tutor.

Animais
Listar, cadastrar, editar e excluir animais.
Todo animal precisa estar ligado a um tutor.
Os animais de um tutor são buscados por uma consulta JPQL no DAO.
Espécie e porte são validados.

Como o código está organizado

As operações de tutores e animais seguem uma divisão em três camadas:

Controller: recebe as requisições, vindas das páginas ou da API REST.
Service: aplica as regras de negócio.
DAO: cuida da persistência por meio do JPA.

Quando alguma regra de negócio é violada, quem avisa é a exceção RegraDeNegocioException.

Estrutura de pastas
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main>dir /d/b/s
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\resources
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dao
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dominio
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\PetbemestarApplication.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\servico
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\web
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dao\AnimalDAO.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dao\AnimalDAOJPA.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dao\AtendimentoDAO.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dao\AtendimentoDAOJPA.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dao\TutorDAO.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dao\TutorDAOJPA.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dao\UsuarioDAO.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dao\UsuarioDAOJPA.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dominio\Animal.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dominio\Atendimento.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dominio\Especie.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dominio\Porte.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dominio\SituacaoAgendamento.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dominio\TipoServico.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dominio\Tutor.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\dominio\Usuario.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\servico\AnimalService.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\servico\RegraDeNegocioException.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\servico\TutorService.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\servico\UsuarioService.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\web\AnimalController.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\web\AnimalPaginaController.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\web\LoginController.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\web\LoginInterceptor.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\web\TutorController.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\web\TutorPaginaController.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\java\br\edu\ifrs\petbemestar\web\WebConfig.java
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\resources\application.properties
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\resources\static
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\resources\templates
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\resources\static\css
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\resources\static\css\estilo.css
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\resources\templates\animais
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\resources\templates\cadastro.html
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\resources\templates\login.html
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\resources\templates\tutores
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\resources\templates\animais\formulario.html
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\resources\templates\animais\lista.html
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\resources\templates\tutores\detalhes.html
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\resources\templates\tutores\formulario.html
C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main\resources\templates\tutores\lista.html

C:\Dev\eclipse-workspace\IFRS\2026-2-PW1\petbemestar\src\main>

 Modelo de domínio
Tutor: tem nome, telefone e e-mail (opcional). Pode ter vários animais.
Animal: tem nome, espécie e porte, e pertence a um único tutor.
Atendimento: pertence a um animal e usa os enums TipoServico e SituacaoAgendamento.
Usuario: guarda nome, e-mail e a senha protegida por hash BCrypt.

Os enums são salvos no banco como texto (EnumType.STRING).

A relação entre Tutor e Animal não usa remoção em cascata. Se o tutor ainda tem animais, a exclusão é bloqueada pela regra de negócio, e assim nenhum registro fica perdido pelo caminho.

Nesta etapa, o Atendimento existe só no modelo: ainda não há páginas nem regras de agendamento para ele.

Configurando o ambiente
O que você precisa ter
JDK compatível com a versão de Java definida no pom.xml
MySQL
Maven (o Maven Wrapper já vem no projeto, então não precisa instalar)
Banco de dados

Primeiro, crie o banco no MySQL:

sql
CREATE DATABASE pet_bem_estar_spring;

Depois, configure as credenciais por variáveis de ambiente:

Variável	Para que serve	Valor padrão
DB_URL	URL JDBC do banco	jdbc:mysql://localhost:3306/pet_bem_estar_spring
DB_USERNAME	Usuário do MySQL	root
DB_PASSWORD	Senha do MySQL	teste

Esses valores padrão ficam em src/main/resources/application.properties. Vale configurar as variáveis no seu ambiente local, principalmente a senha do banco.

O Hibernate está com spring.jpa.hibernate.ddl-auto=update, ou seja, o esquema do banco é atualizado conforme as entidades mapeadas.

Rodando o projeto

Na pasta raiz do projeto, use o PowerShell:

Executar os testes

powershell
.\mvnw.cmd clean test

Iniciar a aplicação

powershell
.\mvnw.cmd spring-boot:run

Por padrão, a aplicação fica disponível em http://localhost:8080.

Páginas

O cadastro de usuário e o login são públicos. Todo o resto exige autenticação, então cadastre um usuário e faça login antes de abrir as páginas de tutores e animais.

Página	Endereço
Cadastro de usuário	http://localhost:8080/cadastro
Login	http://localhost:8080/login
Lista de tutores	http://localhost:8080/paginas/tutores
Novo tutor	http://localhost:8080/paginas/tutores/novo
Lista de animais	http://localhost:8080/paginas/animais
Novo animal	http://localhost:8080/paginas/animais/novo
Sair	http://localhost:8080/sair 

API REST
As rotas REST também só funcionam com uma sessão autenticada.

Tutores
Método	Rota	O que faz
GET	/tutores	Lista todos os tutores
GET	/tutores/{id}	Consulta um tutor
POST	/tutores	Cadastra um tutor
PUT	/tutores/{id}	Atualiza um tutor
DELETE	/tutores/{id}	Exclui um tutor, se ele não tiver animais
Animais
Método	Rota	O que faz
GET	/animais	Lista todos os animais
GET	/animais/{id}	Consulta um animal
POST	/animais	Cadastra um animal
PUT	/animais/{id}	Atualiza um animal
DELETE	/animais/{id}	Exclui um animal, respeitando as regras de negócio

No cadastro e na atualização, os dados vão no corpo da requisição em JSON. As respostas usam os códigos HTTP adequados para cada situação: sucesso, registro não encontrado, conflito com regra de negócio ou falta de autenticação.

Regras de negócio
Todo animal precisa estar ligado a um tutor que exista.
Dois tutores não podem ter o mesmo telefone.
Ao editar um tutor, ele pode manter o telefone que já é dele.
Um tutor com animais vinculados não pode ser excluído.
Excluir um tutor nunca apaga animais automaticamente.
As senhas são sempre guardadas com hash BCrypt.
Páginas e rotas REST de tutores e animais exigem autenticação.

Observações
As configurações de acesso ao banco devem ser ajustadas conforme o seu ambiente.
O cadastro de usuário não tem níveis de permissão: todo mundo que se cadastra tem o mesmo acesso.
Atendimento e agendamento ainda não fazem parte das funcionalidades desta etapa.