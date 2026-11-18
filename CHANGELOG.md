# Changelog

Mudanças notáveis do Sebo Digital são registradas aqui seguindo versionamento semântico. O histórico acadêmico da v1.0.0 permanece preservado.

## [1.1.0] - 2026-11-18

### Added
- Cenário Cucumber em português para o cadastro de clientes existente.
- Suíte focada no fluxo de compras.
- Workflow mínimo do GitHub Actions para validar pull requests com Maven.
- Driver JDBC oficial da Microsoft para SQL Server, compatível com Java 11.
- Adaptador JDBC para operações CRUD de clientes e scripts para criar o banco e a tabela.
- Smoke test de conexão com SQL Server, executado somente quando habilitado explicitamente.

### Changed
- Migração dos testes de JUnit 4 para JUnit 5.
- Uso de Mockito para isolar a dependência de funcionários nos testes de livros.
- Serviço de clientes depende de um contrato de persistência e mantém o adaptador em memória como padrão para os testes existentes.
- Configuração SQL Server lida de variáveis de ambiente, sem credenciais versionadas.
- README atualizado para refletir o código e os passos de conexão.

### Removed
- Cenários de placeholder para persistência, segurança, backup, carga, pagamentos externos e notificações sem implementação correspondente.

## [1.0.0] - baseline acadêmica

- Snapshot acadêmico preservado no commit `4373a4cf45b355706a6090a8abf877e299d39771`.
- Inclui serviços em memória e a suíte original em JUnit 4. O relatório Surefire arquivado registra 1 falha e 2 erros.
