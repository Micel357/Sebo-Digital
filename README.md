# Sebo Digital — Testes Automatizados

Estou resgatando alguns projetos antigos da faculdade para testar minhas capacidades e colocar em prática o que fui aprendendo durante o curso de Análise e Desenvolvimento de Sistemas. Este é um deles: um projeto de testes automatizados para um sistema de sebo digital.

## Sobre o projeto

Os testes exercitam as regras de negócio de clientes, funcionários, livros e compras, incluindo validações e cenários de integração entre serviços.

## Tecnologias

- Java 11
- Maven
- JUnit 4
- Mockito
- Hamcrest

## Como executar

É necessário ter Java 11 ou superior e Maven instalado.

```bash
mvn clean compile
mvn test
```

Para executar somente uma classe de testes:

```bash
mvn test -Dtest=ClienteServiceTest
```

## Estrutura

```text
.
├── config/                 # Configurações e exemplos do projeto
├── data/                   # Dados locais usados durante o desenvolvimento
├── docs/                   # Documentação complementar
├── src/
│   ├── main/java/com/sebo/
│   │   ├── models/         # Cliente, funcionário, livro e compra
│   │   └── service/        # Regras de negócio e validações
│   └── test/java/com/sebo/
│       └── service/        # Testes unitários e de integração
├── tests/                  # Orientações sobre a suíte de testes
├── pom.xml
└── README.md
```

Os testes ficam em src/test/java, no padrão esperado pelo Maven. Arquivos gerados durante a compilação ficam fora do controle de versão.
