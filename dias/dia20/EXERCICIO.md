# Dia 20 — a API aprende a responder perguntas

Hoje a sua API tem um jeito só de mostrar lead: **todos, de uma vez**. Isso
funciona com 5 leads. Com 500, não.

As perguntas que o negócio faz de verdade são outras:

- *quantos estão em cada etapa?*
- *me mostra só os que estão em contato*
- *abre o lead 3 pra mim*

As três entram hoje. E tem um detalhe bonito: **duas delas você já programou no
dia 13.**

---

## 1. Dois métodos seus estavam dormindo

Abra o `RepositorioMemoria.java` e olhe estes dois, escritos por você há uma
semana:

```java
public List<Cliente> porStatus(String status) { ... }
public Map<String, Integer> contarPorStatus() { ... }
```

Desde então ninguém chamou nenhum dos dois. Eles ficaram ali, prontos.

Hoje cada um vira um endereço da API. **Você não vai escrever lógica nova** —
vai ligar um fio entre o mundo de fora e o que já existe. É assim que software
cresce: a maior parte do trabalho de amanhã já está escrita hoje.

---

## 2. A informação depois do `?`

Ontem você aprendeu a pôr informação **no caminho**:

```
PUT /leads/1
```

Hoje entra o outro lugar: **depois da interrogação**.

```
GET /leads?status=contato
          └──────┬──────┘
          um parametro de consulta
```

Em Java isso é `@RequestParam`:

```java
public List<Cliente> listar(@RequestParam(required = false) String status)
```

O `required = false` é o que faz dele **opcional**. Sem isso, o Spring exigiria
o `?status=` em toda chamada, e o `GET /leads` puro quebraria. Com ele, quando
o parâmetro não vem, o Java entrega `null` — e aí o seu `if` decide.

### Quando usar cada um

| | `@PathVariable` | `@RequestParam` |
|---|---|---|
| Fica | `/leads/1` | `/leads?status=contato` |
| Serve pra | **identificar** uma coisa | **filtrar, ordenar, paginar** |
| Obrigatório? | sim, faz parte do endereço | geralmente opcional |

A régua: **se a informação some e o endereço deixa de fazer sentido, é caminho.
Se o endereço continua valendo sem ela, é parâmetro.**

`/leads/` sozinho não aponta pra lead nenhum → o id é caminho.
`/leads` sozinho é uma pergunta válida ("todos") → o status é parâmetro.

---

## 3. Um conflito que não acontece

Olha esses dois endereços que vão existir juntos no mesmo arquivo:

```java
@GetMapping("/leads/resumo")
@GetMapping("/leads/{id}")
```

Quando chegar `GET /leads/resumo`, **os dois casam**: `{id}` é um buraco, e
"resumo" cabe nele.

O Spring resolve sozinho, e a regra é: **o mais específico ganha**. `/leads/resumo`
é literal, escrito por extenso; `/leads/{id}` tem um buraco. Literal vence.

Não precisa fazer nada — mas precisa saber, porque no dia em que alguém criar um
lead e tentar `GET /leads/resumo` esperando o lead chamado "resumo", você vai
saber por que não funciona.

---

## 4. O que você escreve hoje

Cinco TODOs, tudo no `web/ControladorLeads.java`.

| TODO | O que é |
|---|---|
| 1 | o parâmetro `?status=` no `listar` |
| 2 | o `if` que escolhe: lista inteira ou filtrada |
| 3 | a anotação do `/leads/resumo` |
| 4 | o corpo do resumo — uma linha |
| 5 | o método inteiro do `GET /leads/{id}` |

O import já está lá:

```java
import org.springframework.web.bind.annotation.RequestParam;
```

### Sobre o TODO-2

Duas saídas, e o `if` escolhe. A pergunta certa é **"o status veio?"** — e, em
Java, "não veio" é `null`.

> Cuidado com um detalhe que já te pegou: pra comparar com `null` usa-se `==`,
> não `.equals()`. Chamar `.equals()` em algo que é `null` estoura na hora.

### Sobre o TODO-5

Esse é o método inteiro, do zero — anotação, parâmetro e corpo. Mas você **já
escreveu ele ontem**: é o mesmo desenho do `PUT`, sem a parte de mudar o status.

Olhe o `mudarStatus` logo abaixo no arquivo e troque o que precisa trocar:

| | No PUT de ontem | Hoje |
|---|---|---|
| anotação | `@PutMapping` | ? |
| parâmetros | id **e** corpo | só o id |
| o que faz | procura, muda, devolve | procura, devolve |
| se não achar | `throw new LeadNaoEncontrado()` | igual |

---

## 5. Fechar o dia

```bash
jt teste dia20
```

```
GET /leads/resumo            → 200  {"fechado":2,"novo":1,"contato":2}
GET /leads?status=contato    → 200  só os 2 em contato
GET /leads/3                 → 200  o Creator Pro, sozinho
GET /leads/999               → 404  lead nao encontrado
GET /leads                   → 200  os 5
```

Cinco perguntas diferentes, no mesmo recurso. Isso é uma API de leads de
verdade — o painel que você construir em cima dela pede exatamente esses cinco.

Passou? `jt ok "dia20 resumo, filtro por status e busca por id"`.

---

## 6. Um código novo que você vai ver antes de terminar

Rode `jt teste dia20` **agora**, antes de escrever qualquer coisa. Três
requisições voltam assim:

```
GET /leads/resumo -> 405
GET /leads/3      -> 405
GET /leads/999    -> 405
```

**405 Method Not Allowed.** Família 4xx, culpa de quem pediu — mas diferente do
400 e do 404:

| Código | Quer dizer |
|---|---|
| 404 | esse endereço não existe |
| **405** | **esse endereço existe, mas não com esse verbo** |

E faz sentido: `/leads/3` **existe** no seu código — é o `@PutMapping("/leads/{id}")`
de ontem. Só que ele atende **PUT**, e você mandou **GET**. O Spring achou o
endereço, não achou o verbo, e respondeu 405.

Quando os TODOs 3 e 5 estiverem escritos, o GET passa a existir e o 405 some.

> Guarde esse: na vida real, 405 quase sempre quer dizer *"você escreveu
> `@PostMapping` onde queria `@GetMapping`"*. É um dos erros mais rápidos de
> achar — desde que você saiba o que o número significa.
