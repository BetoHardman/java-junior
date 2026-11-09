# Dia 15 — a carteira vira endereço na internet

Nos dias 11 a 14 o programa começava, imprimia e acabava. **Hoje ele não acaba
mais.** Sobe, fica no ar e espera alguém pedir alguma coisa.

É a diferença entre um programa e um **serviço**. E é aqui que o que você está
construindo começa a parecer com o que a sua landing page vai chamar.

---

## 1. Uma linha no `pom.xml`, um servidor inteiro

Olhe a dependência nova. Ontem era `spring-boot-starter`, hoje é
`spring-boot-starter-web`. Mudou uma palavra e vieram duas coisas pesadas:

| O que veio | Pra que serve |
|---|---|
| **Tomcat** | o servidor. Abre a porta 8080 e atende o navegador |
| **Jackson** | o tradutor. Pega um objeto Java e devolve JSON |

Você não baixou, não instalou, não configurou porta. Declarou a dependência e
o Spring montou o resto. Esse é o argumento inteiro do Spring Boot.

> O `starter-web` traz o `starter` de ontem **por dentro**. Por isso ele sumiu
> do `pom.xml` — não foi removido, foi absorvido.

---

## 2. A ideia do endereço

Até agora, quem chamava seu código era a `Carteira`, de dentro. Hoje entra um
segundo caminho: **alguém de fora digita um endereço** e o Spring decide qual
método executar.

```
navegador                 Spring                   seu codigo
   |                        |                          |
   |  GET /leads            |                          |
   |----------------------->|                          |
   |                        |  quem respondeu por      |
   |                        |  "/leads"? o listar()    |
   |                        |------------------------->|
   |                        |                          | repo.listar()
   |                        |<-------------------------|
   |                        |  Jackson vira JSON       |
   |<-----------------------|                          |
   |  [{"nome":"Luna...     |                          |
```

Duas anotações fazem isso:

- **`@RestController`** na classe — "Spring, esta classe atende HTTP, e o que
  ela devolver vira JSON direto na resposta".
- **`@GetMapping("/leads")`** no método — "quando chegar um GET em `/leads`,
  chame este método aqui".

`@RestController` é prima de `@Component` e `@Repository`, as de ontem. Todas
dizem a mesma coisa — "Spring, essa classe é sua" — mudando só o papel.

---

## 3. O Jackson é míope: ele só enxerga getter

Aqui está a parte que pega todo mundo na primeira vez.

Seu `Cliente` tem três campos:

```java
protected String nome;
protected String whatsapp;
protected String status;
```

Eles são `protected`. De fora da classe, **ninguém vê**. E o Jackson está de
fora. A única porta que ele conhece é o **getter**: um método público que
começa com `get` e devolve o campo.

Hoje o `Cliente` tem só o `getStatus()`. Ou seja: se você não fizer nada, o
JSON sai assim —

```json
[{"status":"fechado"}, {"status":"contato"}]
```

Nome e whatsapp somem. Não porque deram erro: porque o Jackson **não tem como
chegar neles**.

> **A régra:** o Jackson não lê campo, lê getter. Campo sem getter não aparece
> no JSON. `resumo()` e `valorHora()` também não aparecem — não começam com
> `get`.

Por isso três dos seus seis TODOs de hoje são getters.

---

## 4. O que você escreve hoje

Seis linhas-ideia, em três arquivos. Procure por `TODO`.

### `web/ControladorLeads.java` — TODO-1, 2 e 3

O arquivo é novo e está quase vazio. Repare que ele ficou numa pasta nova,
`web/`, **abaixo** de `leads` — por isso o Spring acha ele sozinho.

**TODO-1 — a anotação da classe**, mais o import. Os dois:

```java
import org.springframework.web.bind.annotation.RestController;
```
```java
@RestController
```

**TODO-2 — a anotação do método**, mais o import:

```java
import org.springframework.web.bind.annotation.GetMapping;
```
```java
@GetMapping("/leads")
```

**TODO-3 — o construtor.** Esse você já sabe: é o mesmo da `Carteira`, trocando
o nome da classe. Pede um `Repositorio`, guarda no campo. Sem `new` nenhum.

> Se você rodar antes de escrever o construtor, o compilador reclama:
> `variable repo not initialized in the default constructor`.
> Tradução: o campo é `final`, e `final` significa **"tem que ser preenchido no
> construtor"**. Não tendo construtor, ele fica vazio pra sempre — e o Java não
> deixa isso passar.

### `modelo/Cliente.java` — TODO-4 e 5

Dois getters, no mesmo formato do `getStatus()` que já está ali três linhas
abaixo. Um devolve `nome`, o outro devolve `whatsapp`.

### `modelo/ClienteVip.java` — TODO-6

Um getter pro `desconto`. Cuidado com um detalhe: `desconto` é **`int`**, não
`String`.

---

## 5. Fechar o dia

```bash
jt teste dia15
```

O `jt` mudou hoje. Como o programa não termina mais sozinho, ele agora:

1. sobe o servidor em segundo plano
2. espera a porta 8080 atender
3. chama os endereços listados no `requisicoes.txt` com `curl`
4. encerra o processo e compara tudo com o `esperado.txt`

Por isso o `esperado.txt` tem duas partes: a carteira impressa no terminal,
como sempre, **e** o JSON que voltou do `GET /leads`.

### Depois, veja no navegador

```bash
jt rodar dia15
```

Esse **não termina** — é o servidor ficando no ar, e está certo. Abra:

```
http://localhost:8080/leads
```

É o seu código respondendo um endereço de verdade. Pra parar, `Ctrl+C` no
terminal.

Passou? `jt ok "dia15 API REST: @RestController e GET /leads"`.

---

## 6. Experimento de 2 minutos

Com tudo passando, comente **um** getter — por exemplo o `getWhatsapp()`:

```java
// public String getWhatsapp() {
//     return whatsapp;
// }
```

Antes de rodar, responda: **o programa vai quebrar, ou vai responder errado?**

Agora `jt teste dia15`.

Compila, sobe, responde — e o `whatsapp` simplesmente **não está** no JSON. É o
tipo de defeito que não dá erro em lugar nenhum e só aparece quando alguém do
outro lado reclama que falta um campo. Grave a sensação: a partir de hoje, boa
parte dos seus bugs vai ser desse tipo.

Descomente e rode de novo antes de commitar.
