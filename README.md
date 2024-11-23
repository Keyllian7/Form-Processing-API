# Form-Processing-API

## Descrição
Esta API processa formulários enviados por usuários de forma assíncrona, validando e armazenando os dados sem impactar o sistema principal. A aplicação permite que usuários e administradores façam login e acessem suas respectivas áreas.

- Usuário: Cria e envia solicitações relatando problemas e acompanha o status.
- Admin: Recebe, resolve os problemas, atualiza o status e envia soluções aos usuários.

Construída com Java no backend para robustez e escalabilidade, a API utiliza PostgreSQL para armazenamento seguro. Bibliotecas como (add aqui) complementam suas funcionalidades.
Este projeto foi desenvolvido como parte do trabalho da A3, com o objetivo de melhorar a comunicação e gestão de solicitações, otimizando a experiência do usuário e o fluxo de trabalho dos administradores, enquanto mantém o sistema principal escalável e ágil.

## Funcionalidades
- Processamento assíncrono de formulários.
- Validação de dados.
- Armazenamento seguro dos dados.

## Instalação
Para clonar o repositório e instalar as dependências:

### Pré-requisitos
- Java 11+ instalado
- Maven
- PostgreSQL
- Visual Studio Code (VSCode)
- DBeaver
- Git bash (Opcional)

### Passo a passo
1. Clonar o repositório e :
```bash
git clone https://github.com/Keyllian7/Form-Processing-API.git
```
2. Acessar a pasta:
```bash
cd Form-Processing-API
```
3. Abra no Visual Studio Code
```bash
code .
```

### Configure as variáveis de ambiente
1. Pressione Win + R para abrir a caixa de diálogo "Executar".
2. Digite "sysdm.cpl" e pressione Enter para abrir as Propriedades do Sistema.
3. Clique na aba "Avançado" e depois em "Variáveis de Ambiente".
4. Na seção "Variáveis do Usuário", clique em "Novo" para adicionar uma nova 
variável de ambiente.
5. Adicione as seguintes variávei:
- #### Nome da aplicação
SPRING_APPLICATION_NAME=form.processing
- #### Configurações do PostgreSQL
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/FormProcessingDB
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=keyllian
SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver
#### (substitua keyllian por sua senha)

- #### Configurações do JPA
SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.PostgreSQLDialect
SPRING_JPA_HIBERNATE_DDL_AUTO=update
#### Caso não haja nenhuma tabela no banco de dados, substitua (none) por (update).

- #### Configurações do Flyway
FLYWAY_ENABLED=true
FLYWAY_LOCATIONS=classpath:db/migration
FLYWAY_BASELINE_ON_MIGRATE=true

- #### Nível de log do Spring Security
LOGGING_LEVEL_ORG_SPRINGFRAMEWORK_SECURITY=DEBUG

- #### Configuração do token JWT
JWT_SECRET=my-secret-key

## Instrução de uso

(Colocar em ordem ou o codigo, ver o que colocar)
