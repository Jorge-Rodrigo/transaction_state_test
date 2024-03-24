
<h1 align="center">Transaction State - Java</h1>

<p align="center">Este é o backend da aplicação Transaction State - Um sistema de Cadstro de Empresa e Cliente com a possibilidade de fazer transações e controle de saldos</p>


<p align="center">
  <a href="#endpoints">Endpoints</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</p>

## **Endpoints**

A API tem um total de 4 endpoints

O url base da API é http://127.0.0.1:8080/api

<h2 align ='center'> Cadastrando Clietes </h2>

É necessario enviar todos as chaves para completar o cadastro

`POST /clientes -  FORMATO DA REQUISIÇÃO`
```json
{
  "nome": "Jorge",
  "cpf": "999-999-999-99",
  "email": "jorgerodrigo.monteiro19@gmail.com",
  "profissao": "Desenvolvedor"
}
```

Para mostrar todos os clientes: 

`GET /clientes -  FORMATO DA REQUISIÇÃO`

`FORMATO DA RESPOSTA - STATUS 200`

```json
[
  {
    "id": 1,
    "nome": "Jorge",
    "cpf": "999-999-999-99",
    "email": "jorgerodrigo.monteiro19@gmail.com",
    "profissao": "Desenvolvedor",
    "saldo": 1000.0
  }
]
```

Para mostrar um cliente em especifico: 

`GET /clientes/{id do cliente} -  FORMATO DA REQUISIÇÃO`

`FORMATO DA RESPOSTA - STATUS 200`

```json
{
  "id": 1,
  "nome": "Jorge",
  "cpf": "999-999-999-99",
  "email": "jorgerodrigo.monteiro19@gmail.com",
  "profissao": "Desenvolvedor",
  "saldo": 1000.0
}
```

<b>Para Editar um Cliente:</b>

Para atualizar os dados dos clientes e necessario colocar todos de da igual a criação

`PUT /clientes/{id do cliente} - FORMATO DA REQUISIÇÃO`
```json
{
  "nome": "Jorge",
  "cpf": "999-999-999-99",
  "email": "jorgerodrigo.monteiro19@gmail.com",
  "profissao": "Desenvolvedor"
}
```

<b>Para Editar o saldo de um Cliente:</b>

Para atualizar os dados dos clientes e necessario apenas colocar o saldo e nada mais assim atualizando o saldo desse cliente

`PATCH /clientes/{id do cliente} - FORMATO DA REQUISIÇÃO`
```json
{
 "saldo": 1000.0
}
```


<b>Para Deletar o Usuário:</b>


`DELETE /clientes -  FORMATO DA REQUISIÇÃO`

```json
  no body
```



<h2 align ='center'> Adicionar Empresa </h2>

Para cadastrar uma empresa: 

`POST /empresas -  FORMATO DA REQUISIÇÃO`
```json
{
  "nome": "nome da empresa",
  "cnpj": "99.999.999/9999-99",
  "areaAtuacao": "A area da empresa"
}
```

Para mostrar todos as empresas: 

`GET /empresas -  FORMATO DA REQUISIÇÃO`

`FORMATO DA RESPOSTA - STATUS 200`

```json
[
 {
    "id": 1,
    "nome": "nome da empresa",
    "cnpj": "99.999.999/9999-99",
    "areaAtuacao": "A area da empresa",
    "saldo": 150000.0
  }
]
```

Para mostrar uma empresa em especifico: 

`GET /empresas/{id do empresa} -  FORMATO DA REQUISIÇÃO`

`FORMATO DA RESPOSTA - STATUS 200`

```json
{
  "id": 1,
  "nome": "nome da empresa",
  "cnpj": "99.999.999/9999-99",
  "areaAtuacao": "A area da empresa",
  "saldo": 150000.0
}
```

<b>Para Editar uma Empresa:</b>

Para atualizar os dados da empresa e necessario colocar todos de da igual a criação

`PUT /empresas/{id da empresa} - FORMATO DA REQUISIÇÃO`
```json
{
  "nome": "nome da empresa",
  "cnpj": "99.999.999/9999-99",
  "areaAtuacao": "A area da empresa"
}
```


<b>Para Deletar a Empresas:</b>


`DELETE /empresas/{id da empresa} -  FORMATO DA REQUISIÇÃO`

```json
  no body
```
<h2 align ='center'> Operações </h2>

Para executar o saque e o deposito são necessarioe 2 endpoints:

<b>Para realizar o saque:</b>

É necessario colocar o id do cliente na url e no corpo coloque o id da empresa e o valor do saque

`POST operacoes/saque/{id do cliente} - FORMATO DA REQUISIÇÃO`
```json
{
  "empresaId": 1,
  "valor": 850.00
}
```

<b>Para realizar o Deposito:</b>

É necessario colocar o id do cliente na url e no corpo coloque o id da empresa e o valor do saque

`POST operacoes/deposito/{id do cliente} - FORMATO DA REQUISIÇÃO`
```json
{
  "empresaId": 1,
  "valor": 850.00
}
```
