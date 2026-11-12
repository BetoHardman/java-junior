# Dia 19 — mexer num lead só

A sua API hoje sabe listar **todos** e gravar **mais um**. Não sabe tocar em
**aquele ali**.

E é isso que o negócio precisa: o lead entrou como `novo`, você ligou, virou
`contato`; fechou, virou `fechado`. Sem isso o `GET /leads` é uma lista morta.

---

## 1. Primeiro: todo lead ganhou um id

Pra apontar pra um lead específico, ele precisa de um nome único. Isso é o id.

Esse pedaço **já está escrito** — é encanamento, não conceito novo. Mas leia,
porque vai aparecer de novo quando entrar banco de dados:

```java
// no RepositorioMemoria
private long proximoId = 1;

public void salvar(Cliente c) {
    c.setId(proximoId);
    proximoId = proximoId + 1;
    itens.add(c);
}
```

**Quem decide o id é o repositório, nunca quem manda o POST.** Se o cliente
pudesse escolher, dois mandariam o mesmo número. Repare que o `NovoLead` não tem
campo `id` — de propósito.

E agora o `GET /leads` mostra:

```json
{"nome":"Luna Toys","whatsapp":"61993687124","id":1,"status":"fechado"}
```

---

## 2. O endereço que carrega informação

Até hoje os seus endereços eram fixos: `/leads` e pronto. Hoje entra um que
muda:

```
PUT /leads/1     ← mexe no lead 1
PUT /leads/42    ← mexe no lead 42
```

Na anotação isso se escreve com chaves:

```java
@PutMapping("/leads/{id}")
```

`{id}` é um **buraco**. Ele casa com qualquer coisa naquela posição. E pra pegar
o que caiu no buraco, o parâmetro leva `@PathVariable`:

```java
public Cliente mudarStatus(@PathVariable Long id, ...)
                           └──────┬──────┘
              "pega o {id} do endereço e põe aqui"
```

> O nome do parâmetro tem que ser **igual** ao nome entre chaves. `{id}` casa
> com `Long id`. Se você escrever `Long codigo`, o Spring não acha e dá erro ao
> subir.

### Duas formas de mandar informação, e elas convivem

```
PUT /leads/1        ←  no ENDEREÇO: quem é o alvo       (@PathVariable)
{"status":"contato"} ←  no CORPO: o que fazer com ele   (@RequestBody)
```

A regra que o mercado segue: **o endereço identifica, o corpo descreve.**

---

## 3. PUT, e não POST

| | POST | PUT |
|---|---|---|
| Serve pra | criar uma coisa nova | alterar uma que existe |
| Endereço | `/leads` (a coleção) | `/leads/1` (um item) |
| Repetir duas vezes | cria **dois** leads | deixa no mesmo estado |

Essa última linha tem nome: **idempotente**. Mandar `PUT /leads/1` com
`status: contato` dez vezes dá no mesmo que mandar uma. Mandar `POST /leads` dez
vezes cria dez leads.

Importa na prática: se a internet do cliente cai e o app tenta de novo, um PUT
repetido é inofensivo e um POST repetido vira lead duplicado.

---

## 4. E se o id não existir?

`PUT /leads/999` — não existe lead 999. O que responder?

Não é 400: o pedido está bem formado. Não é 500: o seu código não quebrou. É
**404 Not Found** — "esse endereço não aponta pra nada".

O caminho que você vai usar tem duas peças.

**Primeira: uma exceção com o nome do seu negócio.** Já está escrita:

```java
public class LeadNaoEncontrado extends RuntimeException {
}
```

Uma classe vazia. O valor dela é o **nome** — quando ela aparecer num log às
duas da manhã, `LeadNaoEncontrado` diz o que houve na hora.

**Segunda: um segundo método de plantão** no `TratadorDeErros` que você escreveu
ontem. Aquela classe foi feita justamente pra isso: cada tipo de problema ganha
um método, e a API inteira responde de forma consistente.

```
repo.buscarPorId(999) devolve null
   │
   ▼
throw new LeadNaoEncontrado()
   │
   ▼
TratadorDeErros  →  404  {"erro":"lead nao encontrado"}
```

---

## 5. O que você escreve hoje

Cinco TODOs, em três arquivos.

| TODO | Arquivo | O que é |
|---|---|---|
| 1 | `web/MudarStatus.java` | o record, com um campo só |
| 2 | `web/ControladorLeads.java` | a anotação do PUT |
| 3 | `web/ControladorLeads.java` | o parâmetro do `{id}` |
| 4 | `web/ControladorLeads.java` | o corpo do método |
| 5 | `web/TratadorDeErros.java` | o segundo método de plantão |

### Os imports do controlador já estão lá

```java
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
```

### O corpo (TODO-4), em três movimentos

1. pedir o lead ao repositório pelo id
2. **se veio `null`**, lançar a exceção
3. achando, mudar o status e devolver

O `if` você escreve desde o dia 4. O método do repositório é `buscarPorId`, e
ele devolve `null` quando não acha. O método que muda o status no `Cliente` é o
`mudarStatus`, do dia 11. E o novo status vem do record: lembra que record se lê
com parênteses e sem `get`.

> **Apague o `return null;`** que está no fim. Ele é só o andaime pro arquivo
> compilar enquanto o método está vazio.

---

## 6. Fechar o dia

```bash
jt teste dia19
```

As requisições de hoje contam uma história de três respostas diferentes:

```
GET  /leads                      → 200, os 5, agora com id
PUT  /leads/1   {"status":"contato"}  → 200, Luna Toys muda de fechado pra contato
PUT  /leads/999 {"status":"contato"}  → 404, nao existe
PUT  /leads/2   {"status":""}         → 400, status vazio
GET  /leads                      → 200, so o lead 1 mudou
```

Três códigos num teste só: **200, 404, 400**. Cada um dizendo uma coisa
diferente pra quem chamou.

Repare na quarta linha: a validação do dia 17 funciona no PUT também, sem você
fazer nada. O `@Valid` vale pra qualquer método que o tenha no parâmetro.

Passou? `jt ok "dia19 PUT /leads/{id} com @PathVariable e 404"`.

---

## 7. Experimento de 2 minutos

Com tudo passando, troque o nome do parâmetro — só o nome:

```java
public Cliente mudarStatus(@PathVariable Long codigo, ...)
```

E deixe a anotação como está, com `{id}`.

Antes de rodar: **compila?** E se compilar, **o aplicativo sobe?**

Roda `jt teste dia19`. Eu testei isto aqui antes de escrever, e o resultado foi
diferente do que eu esperava:

```
--- GET /leads     -> 200 ---
--- PUT /leads/1   -> 500 ---
--- PUT /leads/999 -> 500 ---
--- PUT /leads/2   -> 500 ---
```

Compila. **Sobe.** O `GET` continua funcionando. E todo `PUT` morre com **500**.

Vale entender cada parte disso:

- **compila**, porque pro Java `codigo` e um nome de variavel tao valido quanto
  `id`. Ele nao sabe nada de `@PathVariable`.
- **sobe**, porque o Spring so vai casar `{id}` com o parametro na hora em que
  um pedido chegar.
- **500**, e nao 400 nem 404, porque a culpa nao e de quem pediu: e do seu
  codigo. Lembra da regra do dia 17 — **4xx e culpa de quem pede, 5xx e culpa
  de quem responde.**

E repare no mais perigoso: **metade da API continua funcionando.** Se voce so
tivesse aberto o `/leads` no navegador pra conferir, teria achado que estava
tudo certo.

E um erro de categoria nova pra voce: nao e de compilacao, nao e de resposta
errada, e nao aparece ao ligar. So aparece quando alguem usa aquele endereco
especifico. Por isso teste automatico existe.

Volte pra `id` antes de commitar.
