# Dia 3 — controle de fluxo

Hoje é o dia em que o programa passa a **decidir** e a **repetir**. São 5 blocos num arquivo só.

```bash
nano dias/dia03/Main.java
```

---

## 1. `for` com `if / else`

Conte de 1 a 5 e diga se cada número é par ou ímpar.

```java
for (int i = 1; i <= 5; i++) {
    if (i % 2 == 0) {
        System.out.println(i + " par");
    } else {
        System.out.println(i + " impar");
    }
}
```

O `for` tem três partes separadas por `;` — **onde começa**, **até quando continua**, **o que muda a cada volta**.
O `%` é o resto da divisão: `4 % 2` dá 0, então 4 é par.

## 2. `switch` moderno

```java
String status = "fechado";
switch (status) {
    case "novo" -> System.out.println("Responder hoje");
    case "conversando" -> System.out.println("Fazer followup");
    case "fechado" -> System.out.println("Comecar o site");
    default -> System.out.println("Status desconhecido");
}
```

A seta `->` é a forma nova (Java 14+). A antiga usava `case "novo":` e exigia `break;` em cada caso —
esquecer o `break` era um dos bugs mais comuns do Java. A seta acabou com isso.

## 3. Operador ternário

```java
int leads = 7;
String semana = leads >= 5 ? "boa" : "fraca";
System.out.println("Semana " + semana);
```

Leia assim: **se** `leads >= 5`, **então** `"boa"`, **senão** `"fraca"`.
É um `if/else` que cabe numa linha e **devolve um valor**.

## 4. `for-each`

```java
String[] sites = {"lunatoys", "creator-pro", "metodo-produza"};
for (String s : sites) {
    System.out.println("no ar: " + s);
}
```

Leia o `:` como "**em**": para cada `String s` **em** `sites`.
Sem contador, sem índice, sem risco de passar do fim da lista.

## 5. `while`

```java
int contador = 3;
while (contador > 0) {
    System.out.println("faltam " + contador);
    contador--;
}
```

O `while` repete **enquanto** a condição for verdadeira.
`contador--` tira 1. **Se você esquecer essa linha, o programa nunca para** — é o laço infinito.
Se acontecer: `Ctrl+C`.

---

## Saída esperada

```
1 impar
2 par
3 impar
4 par
5 impar
Comecar o site
Semana boa
no ar: lunatoys
no ar: creator-pro
no ar: metodo-produza
faltam 3
faltam 2
faltam 1
```

## Fechar

```bash
jt teste
jt ok "dia03 controle de fluxo"
```

## Prova do dia

1. Qual a diferença entre `for` e `while`?
2. O que acontece se você esquecer o `contador--`?
3. Por que o `switch` com `->` é melhor que o antigo com `break`?
