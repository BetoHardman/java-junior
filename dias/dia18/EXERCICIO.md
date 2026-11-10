# Dia 18 — a API aprende a dizer *por quê*

Ontem ela aprendeu a recusar. Mas olha o que ela responde quando recusa:

```json
{"detail":"Invalid request content.","status":400,"title":"Bad Request"}
```

"Conteúdo inválido." Qual campo? Por quê? Silêncio.

E as mensagens que você escreveu com cuidado — `"whatsapp precisa ter 11 digitos"` —
não apareceram em lugar nenhum. Ficaram guardadas.

**Hoje elas saem.** No fim do dia a resposta vai ser assim:

```json
{"whatsapp":"whatsapp precisa ter 11 digitos","nome":"nome e obrigatorio"}
```

Isso é o que o JavaScript da sua landing page precisa pra pintar o campo de
vermelho e escrever o aviso embaixo dele.

---

## 1. Exceção: o jeito do Java dizer "deu ruim aqui"

Quando o `@Valid` reprova, o Spring **lança uma exceção**: um objeto que
interrompe o fluxo normal e sobe procurando alguém que saiba lidar com ele.

Essa exceção tem nome e sobrenome:

```
MethodArgumentNotValidException
└──────┬──────┘ └───┬───┘
  o argumento    não era valido
  do metodo
```

Ela carrega dentro de si **a lista completa do que deu errado** — campo por
campo, com a sua mensagem junto. O trabalho de hoje é abrir esse pacote.

Até agora ninguém pegava essa exceção, então o Spring usava o plano B dele: uma
resposta genérica. Hoje você escreve o plano A.

---

## 2. `@RestControllerAdvice` — o plantão de erros

Você vai criar uma classe **nova**, que não é controlador e não atende endereço
nenhum. Ela fica de plantão:

```
POST /leads  ──▶  ControladorLeads.criar()
                         │
                  @Valid reprova
                         │
                  lança a excecao
                         │
                         ▼
                  TratadorDeErros   ← quem pega
                         │
                         ▼
                  400 + {"campo":"mensagem"}
```

`@RestControllerAdvice` quer dizer: *"Spring, quando qualquer controlador deixar
escapar uma exceção, olhe aqui primeiro."*

**Qualquer controlador.** Não só o `ControladorLeads` — os que você criar daqui
pra frente também. É uma classe, e o tratamento de erro da API inteira fica
resolvido. Isso tem nome: **tratamento centralizado de erros**, e é um dos
primeiros sinais de código profissional que um revisor procura.

---

## 3. As duas anotações do método

**`@ExceptionHandler(MethodArgumentNotValidException.class)`**

Diz *qual* exceção este método atende. Uma classe de plantão pode ter vários
métodos, cada um cuidando de um tipo de problema.

> Repare no `.class` no fim. Você não está passando *uma* exceção — está
> passando **o tipo** dela. É o mesmo `.class` do `SpringApplication.run(Main.class, args)`
> que você escreveu no dia 14.

**`@ResponseStatus(HttpStatus.BAD_REQUEST)`**

Diz qual código HTTP a resposta leva: 400.

Sem essa linha, acontece uma coisa que parece pequena e é grave: **o método
devolve o mapa de erros com código 200.** Testei. A API responde "deu tudo
certo" entregando uma lista de defeitos — e o JavaScript do outro lado, que
decide pelo código, acha que o lead foi gravado.

---

## 4. O laço — você já escreveu esse

A parte de dentro do método parece nova e não é. Compare com o seu
`contarPorStatus()`, do `RepositorioMemoria`, que você escreveu no dia 13:

```java
Map<String, Integer> contagem = new HashMap<>();   // 1. cria o mapa vazio

for (Cliente c : itens) {                          // 2. percorre a colecao
    contagem.put(chave, valor);                    // 3. poe no mapa
}

return contagem;                                   // 4. devolve
```

Hoje é a **mesma estrutura**, com outros nomes:

| | No dia 13 | Hoje |
|---|---|---|
| o mapa | `Map<String, Integer>` | `Map<String, String>` |
| a coleção | `itens` | `e.getBindingResult().getFieldErrors()` |
| o tipo do item | `Cliente c` | `FieldError erro` |
| a chave | o status | `erro.getField()` → `"whatsapp"` |
| o valor | a contagem | `erro.getDefaultMessage()` → `"whatsapp precisa ter 11 digitos"` |

`getDefaultMessage()` é onde foi parar aquele `message = "..."` que você digitou
ontem. Ele estava lá o tempo todo, esperando alguém buscar.

---

## 5. O que você escreve hoje

Tudo num arquivo só: **`web/TratadorDeErros.java`**. Os imports já estão
prontos — são muitos e nenhum é dedutível.

| TODO | O que é |
|---|---|
| 1 | a anotação da classe |
| 2 | a anotação do código HTTP |
| 3 | a anotação que diz qual exceção |
| 4 | criar o mapa vazio |
| 5 | o laço que preenche |
| 6 | devolver o mapa |

Os TODOs 4, 5 e 6 são o molde da seção 4, traduzido pela tabela.

> **Atenção na ordem do `put`:** a chave é o **campo**, o valor é a **mensagem**.
> Invertendo, o JSON sai com a mensagem como nome do campo — compila, roda, e
> fica errado em silêncio. Igual ao getter que faltava no dia 15.

---

## 6. Uma coisa que eu mudei no `application.properties`

Abra o arquivo e leia o comentário. Ontem a gente ligou o formato padrão de erro
(`spring.mvc.problemdetails.enabled`) porque não havia nada melhor.

Hoje ele está **desligado**, e por um motivo concreto que eu descobri testando:
ligado, o tratador padrão do Spring atende a exceção **antes** do seu e ganha. O
seu `TratadorDeErros` ficava lá, correto, sem nunca ser chamado.

É o tipo de detalhe que não dá erro, não dá aviso, e consome uma tarde. Ficou
anotado no arquivo pra quando acontecer de novo.

---

## 7. Fechar o dia

```bash
jt teste dia18
```

As mesmas cinco requisições de ontem. O que muda é o corpo das duas respostas
400:

```
POST {só nome}                   → 400 {"whatsapp":"whatsapp e obrigatorio"}
POST {nome vazio, whatsapp 619}  → 400 {"whatsapp":"...11 digitos","nome":"nome e obrigatorio"}
```

Repare na segunda: **dois erros de uma vez.** A validação não para no primeiro
problema — ela junta tudo e devolve de uma vez. Quem preenche o formulário
conserta os dois campos numa tentativa só, em vez de descobrir um por vez.

Passou? `jt ok "dia18 tratamento de erros com @RestControllerAdvice"`.

---

## 8. Experimento de 2 minutos

Com tudo passando, comente **só o `@ResponseStatus`**:

```java
// @ResponseStatus(HttpStatus.BAD_REQUEST)
@ExceptionHandler(MethodArgumentNotValidException.class)
```

Antes de rodar: o método continua montando o mapa certinho. **O que muda?**

Roda `jt teste dia18`. Os três POSTs voltam `-> 200`, inclusive os dois ruins.
O corpo da resposta continua listando os erros — mas o código diz que deu tudo
certo.

Essa é a pior categoria de defeito: **a resposta está certa por dentro e mentindo
por fora.** Um teste que só olhasse o corpo passaria. Descomente antes de
commitar.
