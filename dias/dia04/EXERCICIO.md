# Dia 4 — métodos e arrays

Hoje o código para de ser uma lista de comandos e vira **peças que você reaproveita**.

O `main` já está pronto. Você só preenche o **corpo dos 5 métodos** — os nomes e as
assinaturas já estão escritos, pra você não perder tempo com digitação.

```bash
nano dias/dia04/Main.java
```

---

## Anatomia de um método

```java
static double orcamento(int secoes, double valorPorSecao) {
   ↑       ↑         ↑          ↑
   |       |         |          parametros: o que ele recebe
   |       |         nome
   |       tipo do retorno: o que ele devolve
   pertence a classe, nao ao objeto
```

`void` no lugar do tipo quer dizer **não devolve nada** — só faz algo (imprime, por exemplo).
Quem devolve alguma coisa precisa de `return`.

---

## 1. `cabecalho` — void

```java
System.out.println("== " + titulo + " ==");
```

Não tem `return`, porque é `void`.

## 2. `orcamento(int, double)` — com retorno

```java
return secoes * valorPorSecao;
```

Apague o `return 0;` que está lá.

## 3. `orcamento(int)` — overloading

**Dois métodos com o mesmo nome**, diferentes nos parâmetros. O Java escolhe qual chamar
pelo que você passa. Este aqui é o atalho: quando não informam o valor, usa 240.50.

```java
return orcamento(secoes, 240.50);
```

Repare: um método chamando o outro. Isso evita repetir a conta em dois lugares.

## 4. `soma(double[])` — array como parâmetro

```java
double t = 0;
for (double v : valores) {
    t = t + v;
}
return t;
```

## 5. `media` — método usando método

```java
return soma(valores) / valores.length;
```

`valores.length` é o tamanho do array. **Sem parênteses** — é um campo, não um método.
(Em `String` é `.length()`, com parênteses. Essa inconsistência do Java confunde todo mundo.)

---

## Saída esperada

```
== Orcamentos ==
1202.5
1202.5
900.0
== Resumo ==
Quantos: 3
Soma: 3602.5
Media: 1200.8333333333333
```

Repare que as duas primeiras linhas dão igual: `orcamento(5, 240.50)` e `orcamento(5)`
chegam no mesmo lugar. É o overloading funcionando.

E a média sai com 13 casas decimais. Isso é o `double` mostrando que ele não é exato —
guarde essa observação, porque é o motivo de **nunca se usar `double` para dinheiro**
em sistema de verdade. A gente vai ver `BigDecimal` mais pra frente.

## Fechar

```bash
jt teste
jt ok "dia04 metodos e arrays"
```

## Prova do dia

1. O que `void` quer dizer?
2. Como o Java sabe qual dos dois `orcamento` chamar?
3. Por que `valores.length` não tem parênteses e `texto.length()` tem?
