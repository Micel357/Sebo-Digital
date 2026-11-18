# language: pt
Funcionalidade: Cadastro de cliente
  Cenário: Cadastrar cliente sem informar telefone
    Dado que tenho um cliente válido sem telefone
    Quando realizo o cadastro
    Então o cliente deve ser cadastrado
    E o telefone padrão deve ser aplicado
