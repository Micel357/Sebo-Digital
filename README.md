# Sebo Digital

Estou resgatando alguns projetos antigos da faculdade para testar minhas capacidades e colocar em prática o que fui aprendendo durante o curso de Análise e Desenvolvimento de Sistemas. O Sebo Digital está sendo modernizado aos poucos, mantendo o projeto acadêmico como referência.

## Estado atual

O projeto tem modelos e serviços Java para clientes, funcionários, livros e compras. O serviço de clientes agora pode usar persistência JDBC no SQL Server; os demais serviços continuam guardando dados em memória. Os testes existentes seguem usando o adaptador em memória.

Ainda não há API REST Spring Boot, frontend nem integração com a Open Library. Essas etapas ficam para Issues futuras.

## Tecnologias

- Java 11
- Maven
- Microsoft JDBC Driver for SQL Server
- JUnit 5, Mockito e Cucumber
- GitHub Actions para validar pull requests

## Executar a suíte

Requisitos: JDK 11 ou superior e Maven.

```bash
mvn clean verify
```

A suíte padrão não precisa de um SQL Server ativo. O driver JDBC está no escopo `runtime`, e o smoke test do banco fica desativado a menos que seja solicitado explicitamente.

## SQL Server

A integração usa JDBC e as variáveis de ambiente `SEBO_DB_URL`, `SEBO_DB_USER` e `SEBO_DB_PASSWORD`. O arquivo `.env.example` mostra valores locais de exemplo; Java e Maven não carregam arquivos `.env` automaticamente. Configure as variáveis no terminal ou na configuração de execução do IntelliJ.

1. Conecte ao SQL Server com uma conta que possa criar bancos e execute `database/sqlserver/000_create_database.sql`.
2. Abra uma conexão com o banco `SeboDigital` e execute `database/sqlserver/001_create_clientes.sql`.
3. Configure as variáveis de ambiente. Exemplo para um servidor local:

```text
SEBO_DB_URL=jdbc:sqlserver://localhost:1433;databaseName=SeboDigital;encrypt=true;trustServerCertificate=true;loginTimeout=30
SEBO_DB_USER=sa
SEBO_DB_PASSWORD=<sua senha local>
```

`trustServerCertificate=true` serve apenas para desenvolvimento local com certificado autoassinado. Para outros ambientes, configure um certificado confiável e use `trustServerCertificate=false`.

O serviço pode ser composto com o repositório SQL Server assim:

```java
ClienteService clientes = SqlServerConfiguration.clienteServiceFromEnvironment();
```

No IntelliJ, use **Run > Edit Configurations** e preencha as três variáveis em **Environment variables**. Para configurar a conexão visual do banco, use a janela **Database**; essa conexão é uma ferramenta de administração e não substitui as variáveis JDBC usadas pelo código.

Para executar o smoke test contra o SQL Server configurado, use uma configuração JUnit para `SqlServerConnectionIntegrationTest` com `-Dsebo.sqlserver.integration=true` em **VM options**. Pelo terminal, depois de definir as mesmas variáveis no ambiente:

```bash
mvn -Dsebo.sqlserver.integration=true -Dtest=SqlServerConnectionIntegrationTest test
```

Sem a propriedade `sebo.sqlserver.integration=true`, o teste de conexão é ignorado. O smoke test exige o banco e a tabela já criados pelos scripts acima.

## Estrutura

```text
.
├── database/sqlserver/   # scripts iniciais do banco e da tabela de clientes
├── config/
├── data/
├── docs/
├── src/
│   ├── main/java/com/sebo/
│   │   ├── models/
│   │   ├── persistence/  # adaptadores em memória e JDBC
│   │   └── service/
│   └── test/
│       ├── java/com/sebo/
│       └── resources/features/
├── tests/
├── pom.xml
└── CHANGELOG.md
```

A versão publicada é `1.1.0`; o histórico de mudanças está no [CHANGELOG](CHANGELOG.md). A tag `v1.1.0` e a GitHub Release apontam para o commit validado desta versão.
