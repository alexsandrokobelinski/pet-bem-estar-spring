Pet Bem-Estar
Aplicação Spring Boot do projeto de Programação para Web I.

Em application.properties, configure as variáveis DB_URL, DB_USERNAME e DB_PASSWORD conforme o MySQL local. O padrão da URL é jdbc:mysql://localhost:3306/pet_bem_estar_spring.

Páginas:
1) Tutores: http://localhost:8080/paginas/tutores
2) Animais: http://localhost:8080/paginas/animais

Modelo de domínio:
Tutor possui nome, telefone e e-mail opcional, além da coleção de animais.
Animal pertence a um Tutor e possui nome, espécie e porte.
Atendimento pertence a um Animal e utiliza TipoServico e SituacaoAgendamento.

Os enums são persistidos como texto (EnumType.STRING). Nesta etapa não há páginas nem regras de agendamento para Atendimento.
Remover um cliente com bichos
A relação entre Tutor e Animal não utiliza remoção em cascata. O banco mantém uma chave estrangeira de Animal para Tutor.

Teste solicitado pela atividade: cadastre um tutor, cadastre um animal associado a ele e tente excluir o tutor pela página de tutores.

Comportamento esperado: a exclusão deve ser recusada pela integridade referencial, pois existe um animal que referencia esse tutor. O animal não deve ser apagado automaticamente. A página apresenta uma mensagem informando que não foi possível remover o tutor.

Resultado observado no ambiente local: o teste foi executado pela página de tutores. Ao tentar excluir a tutora Rosa Silva, que possui animais vinculados, a aplicação recusou a operação e exibiu a mensagem:
Não foi possível remover o tutor. Verifique se existem animais vinculados.

O registro do tutor e os animais associados permaneceram na listagem, confirmando que a exclusão não ocorreu e que os animais não foram apagados automaticamente.