# Teste para desenvolvedores mobile - Banco Neon
-------------------

![alt text](https://github.com/gorio/neon/tree/master/Doc/screenflow.png "Screen Flow")

A primeira tela exibirá  seu  nome, e-mail e dois botões, esses botões levarão para tela de enviar dinheiro e ver histórico de envios. O nome e e-mail é para ser inserido via código e serão campos não editáveis.

![alt text](https://github.com/gorio/neon/tree/master/Doc/tela1.png "Tela 1")

A segunda tela exibirá uma lista com os contatos, você pode criar os contatos de forma estática, o mínimo deverá ser 15. Estes contatos devem conter: Nome, Telefone, Foto, e um ID. Esta lista servirá para você selecionar qual o contato que deseja enviar dinheiro.

![alt text](https://github.com/gorio/neon/tree/master/Doc/tela2.png "Tela 2")

![alt text](https://github.com/gorio/neon/tree/master/Doc/tela2_detalhes.png "Tela 2 - Detalhes")

A terceira tela exibirá uma lista com os contatos que você enviou dinheiro anteriormente, junto ao gráfico do total de transferência feita para cada contato, você terá a informação do dinheiro transferido, mas o total por usuário será um cálculo feito no app.

![alt text](https://github.com/gorio/neon/tree/master/Doc/tela3.png "Tela 3")

O fluxo desejado é:

● exibir tela com o seu nome e e-mail;

● De forma transparente ao usuário, realizar a chamada "GenerateToken" e armazenar esse token para usar nas demais chamadas.

● Ir para tela de contatos;

● Selecionar o contato desejado.

● Exibir detalhes do contato (telefone) e um campo para inserir o valor a ser enviado, isso sem sair da tela, ver imagem em anexo como exemplo.

● Enviar o dinheiro realizando a chamada "SendMoney". A ação de enviar pode se dar através de um botão, ou outra forma criativa.

● Exibir o loading e dar feedback ao usuário, sucesso ou falha.

● Após concluir o envio, voltar a tela inicial.

● Entrar na tela de histórico de envio.

● Realizar a chamada "GetTransfers".

● Exibir a lista do histórico de envio junto ao gráfico.

A seguir, os detalhes das chamadas necessárias para o teste:

# API

> **URL: http://processoseletivoneon.azurewebsites.net/**

# Método:  GenerateToken

Método HTTP:  GET

Envio:  nome - string; email - string.

Exemplo de retorno:  "1d40d305-c836-43a2-b4db-acc56bcc1393"

# Método:  SendMoney

Método HTTP:  POST

Envio:  string - ClienteId; string - token; double - valor. 

Exemplo de retorno:  true

# Método:  GetTransfers 

Método HTTP:  GET 

Envio:  string - token. 

Exemplo de retorno: 

```json
[

{

"Id": 0,

"ClienteId": 10,

"Valor": 24,

"Token": "1d40d305-c836-43a2-b4db-acc56bcc1393",

"Data": "2016-08-02T14:25:37.55"

} ]
```
 
# Pontos adicionais:

● pode usar qualquer framework, mas use apenas se necessário.

● o app tem que estar em linguagem nativa, testes multiplataformas serão desconsiderados.
