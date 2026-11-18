# Sebo Digital

Estou resgatando alguns projetos antigos da faculdade para testar minhas capacidades e colocar em prática o que fui aprendendo durante o curso de Análise e Desenvolvimento de Sistemas. Este é um sistema acadêmico de sebo, em modernização gradual.

## Estado atual

O código contém modelos e serviços Java para clientes, funcionários, livros e compras. Os serviços guardam os dados em memória. Ainda não há API REST, frontend, banco de dados ou integração com a Open Library.

A suíte cobre regras existentes dos serviços com JUnit 5 e Mockito. Há também um cenário Cucumber de cadastro de cliente. Cenários de segurança, pagamentos externos, backup, carga e persistência só serão adicionados quando houver implementação real para exercitar.

## Tecnologias

- Java 11
- Maven
- JUnit 5
- Mockito
- Cucumber

## Como executar

Requisitos: JDK 11 ou superior e Maven.

```bash
mvn clean test
```

## Estrutura

```text
.
├── config/
├── data/
├── docs/
├── src/
│   ├── main/java/com/sebo/
│   │   ├── models/
│   │   └── service/
│   └── test/
│       ├── java/com/sebo/
│       └── resources/features/
├── tests/
├── pom.xml
└── README.md
```

Esta é a linha de base da modernização v1.1.0. A evolução para Spring Boot, SQL Server, Open Library, Testcontainers, WireMock, CI e cobertura será feita em etapas posteriores e vinculada a Issues e PRs.
