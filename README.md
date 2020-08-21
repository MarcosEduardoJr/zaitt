![picture](https://shippmedia.s3.sa-east-1.amazonaws.com/popups/desafioshipp.png)

# Desafio Shipp Mobile

### Fechamento de Pedidos


O teste consiste em se desenvolver um app nativo iOS ou Android, dessa forma, deverá ser implementado em Swift, Kotlin, Objective-C ou Java. É uma simulação de fechamento de pedido em estabelecimento utilizando a Api do Google.

O usuário deverá escolher o estabelecimento disponibilzados pela api autocomplete do Google, informar a descrição e o valor e prosseguir para tela de fechamento de pedido com cartão de credito. Se não houver cartão de credito cadastrado, deverá informá-lo também (número do cartão, data de validade e o CVV) antes de finalizar o pagamento.

Os cartões devem ser persistidos no aplicativo para serem usados em pagamentos futuros.

A possição do usuário deve ser extraida da localização atual do dispositivo.

Devem ser usadas boas práticas de programação, assim como padrões de projeto e Arquitetura.

Documentação Google Places: Android - https://developers.google.com/places/android-sdk/intro iOs - https://developers.google.com/places/ios-sdk/intro

O layout está disponível em: 
Android - https://xd.adobe.com/view/ecd81eb3-b1fa-4279-9e5d-60d7aa396660-b98b
iOS - https://xd.adobe.com/view/24abc259-37e4-490b-8fd5-f902edc01df2-71f8

-----
###### Resumo do checkout

Para informar os valores do resumo do checkout:  (POST) https://k12xubei40.execute-api.sa-east-1.amazonaws.com/challenge/v1/order/resume

+ latitude atual do usuario obtida pelo dispositivo (double) 
+ longitude atual do usuario obtida pelo dispositivo (double) 
+ latitude atual do estabelecimento obtida pelo google (double) 
+ longitude atual do estabelecimento obtida pelo google (double) 
+ Valor declarado (double)


REQUEST

``` json
{  
   "store_latitude": 23.42,
   "store_longitude": 23.12,
   "user_latitude":23.42,
   "user_longitude": 23.12,
   "value": 79.00
}
```

RESPONSE

``` json
{
    "product_value": 79.00,
    "distance": 5.6566,
    "total_value": 90.31,
    "fee_value": 11.31
}
```

-----
###### Fechamento de pedido

Para finalizar o pedido: (POST) https://k12xubei40.execute-api.sa-east-1.amazonaws.com/challenge/v1/order

+ latitude atual do usuario obtida pelo dispositivo (double) 
+ longitude atual do usuario obtida pelo dispositivo (double) 
+ latitude atual do estabelecimento obtida pelo google (double) 
+ longitude atual do estabelecimento obtida pelo google (double) 
+ Número do cartão (string)
+ Vencimento do cartão MM/YY (string)
+ CVV (string)
+ Valor Declarado (double)


REQUEST

``` json
{  
   "store_latitude": 23.42,
   "store_longitude": 23.12,
   "user_latitude":23.42,
   "user_longitude": 23.12,
   "card_number":"1111111111111111",
   "cvv":"789",
   "expiry_date":"01/18",
   "value":79.90
}
```

RESPONSE

``` json
{  
   "message": "Obrigado por comprar na shipp :D",
   "value":90.31
}
```