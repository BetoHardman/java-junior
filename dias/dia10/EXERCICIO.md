# Dia 10 — Buscar na lista: `for`, `if` e `.equals()`

Ontem a lista aprendeu a **guardar**. Hoje ela aprende a **responder pergunta**:
*"quais clientes estão em contato?"*, *"quantos já fecharam?"*.

É a primeira metade do `GET /leads/resumo` da API de leads.

**Três arquivos hoje, você edita os três.** `ClienteVip.java` e `Main.java` estão prontos. **Não mexa.**
Os outros já vêm com o seu código do dia 9 — você só acrescenta.

> A pasta `dia10` já existe. **Não rode `jt novo`** — ele criaria um `dia11` vazio.

---

## 1. O cliente muda de etapa

```bash
nano dias/dia10/Cliente.java
```

Até hoje todo cliente nascia `"novo"` e morria `"novo"`. Acrescente **dois métodos**
dentro da classe, depois do `resumo()`:

**`getStatus()`** — devolve o `status`. Uma linha. Você já fez getter no dia 6.

**`mudarStatus(String novo)`** — troca o status, mas **só aceita três valores**:
`"novo"`, `"contato"` ou `"fechado"`. Qualquer outra coisa:

```
status invalido: perdido
```

e o status antigo fica como estava.

Pistas:
- é um `if` com `||` (ou) juntando as três comparações
- **compare texto com `.equals()`**, nunca com `==` — o porquê está na seção 4
- é o mesmo padrão do setter com validação do dia 6

## 2. O contrato ganha uma pergunta

```bash
nano dias/dia10/Repositorio.java
```

Uma linha nova no contrato: um método `porStatus` que recebe um `String status`
e devolve uma **lista de Cliente**. O tipo de volta é o mesmo do `listar()`.

Assim que salvar, **compile antes do passo 3**:

```bash
jt rodar
```

Vai dar erro no `RepositorioMemoria`: *"is not abstract and does not override abstract
method porStatus"*. **Esse erro é bom.** É o contrato cobrando: você prometeu um método
na interface e a implementação ainda não cumpriu. Foi pra isso que serviu o dia 8.

## 3. A busca

```bash
nano dias/dia10/RepositorioMemoria.java
```

Implemente o `porStatus` (com `@Override`). A receita, em português:

1. crie uma lista **nova e vazia** — chame de `achados`
2. passe por cada cliente de `itens` com o `for (Cliente c : itens)` que você já usa no `Main`
3. **se** o status dele for igual ao que pediram, `add` em `achados`
4. depois do `for`, devolva `achados`

Atenção ao passo 1: **não** devolva a própria `itens` nem apague nada dela.
A lista original continua intacta; a busca monta uma lista à parte só com o que achou.

## 4. A armadilha — `==` com texto

Rode e olhe as duas últimas linhas da saída:

```
== : false
equals: true
```

O mesmo texto, `"fechado"`, e o `==` diz que **não é igual**.

`==` pergunta *"é o mesmo objeto na memória?"*. `.equals()` pergunta
*"tem o mesmo conteúdo?"*. O texto que chega de um formulário (na API, de uma
landing page) é **outro objeto** com as mesmas letras. Com `==` a sua busca devolveria
zero clientes e nenhum erro apareceria — o pior tipo de bug.

**É uma das perguntas que mais caem em entrevista de Java júnior.**

---

## Saída esperada

```
status invalido: perdido
novo: 1
  Creator Pro (61988887777) - novo
contato: 1
  Bruna Severo (61999990000) - contato [VIP -20%]
fechado: 1
  Luna Toys (61993687124) - fechado
--- armadilha ---
== : false
equals: true
```

Repare: o `Creator Pro` continua `novo`, porque `"perdido"` foi recusado.

## Fechar

```bash
jt teste
jt ok "dia10 busca por status e equals"
```

## Prova do dia

1. Qual a diferença entre `==` e `.equals()` com `String`?
2. Por que o compilador reclamou do `RepositorioMemoria` no passo 2?
3. Por que o `porStatus` cria uma lista nova em vez de mexer em `itens`?
4. O que acontece com o status se alguém chamar `mudarStatus("perdido")`?
5. Na API de leads, qual endpoint vai usar exatamente esse `porStatus`?
