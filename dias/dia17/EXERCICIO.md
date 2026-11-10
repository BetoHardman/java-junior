# Dia 17 — a API aprende a dizer não

Ontem, no fim do dia 16, você mandou isto:

```json
{"nome":"Faltando Campo"}
```

E a sua API **gravou**. Respondeu `whatsapp: null`, sem reclamar de nada.

Um lead sem telefone é um lead que não serve pra nada — você não liga, não manda
mensagem, não vende. E ele está lá no meio dos bons, contando no total.

**Hoje a API passa a recusar.**

---

## 1. Quem recusa: o 400

Todo pedido HTTP volta com um número. Você já viu os dois que importam hoje no
próprio `jt teste`:

| Código | Família | Quer dizer |
|---|---|---|
| **200** | 2xx — deu certo | fiz o que você pediu |
| **400** | 4xx — **culpa de quem pediu** | o seu pedido está errado |
| 500 | 5xx — culpa do servidor | eu quebrei |

A diferença entre 4xx e 5xx é de quem é a culpa, e isso importa muito na vida
real: 4xx é o formulário mal preenchido, 5xx é o seu código quebrando. Quem
monitora um sistema trata os dois de forma completamente diferente.

Hoje você faz o lead ruim virar **400**.

---

## 2. As anotações que descrevem a regra

O `spring-boot-starter-validation`, novo no `pom.xml`, traz um conjunto de
etiquetas que descrevem **o que um campo precisa ser**:

| Anotação | Exige |
|---|---|
| `@NotNull` | que não seja nulo. `""` passa |
| `@NotBlank` | que tenha algum caractere de verdade. `""` e `"   "` **não passam** |
| `@Pattern(regexp = "...")` | que case com um padrão |
| `@Size(min =, max =)` | tamanho dentro da faixa |
| `@Email` | que pareça um e-mail |

Hoje você usa `@NotBlank` e `@Pattern`.

### Lendo o `regexp`

```
[0-9]{11}
└───┘ └──┘
  │     └── exatamente 11 vezes
  └──────── um dígito de 0 a 9
```

Onze dígitos, nada mais: `61999990000` passa, `619` não, `(61)99999-0000`
também não, porque tem parênteses e traço.

### A mensagem

Toda anotação aceita um `message`:

```java
@NotBlank(message = "nome e obrigatorio")
```

Escreva as mensagens com cuidado. Elas ainda não vão aparecer na resposta — isso
é problema do dia 18 — mas é nelas que a regra fica documentada.

---

## 3. `@Valid` — a etiqueta não vale nada sozinha

Esta é a parte que todo mundo esquece na primeira vez.

Você pode encher o `NovoLead` de `@NotBlank` que **nada acontece**. Anotação é
etiqueta, e etiqueta precisa de alguém que leia — foi a lição do `@Repository`,
no dia 14.

Quem manda ler é o **`@Valid`**, lá no parâmetro do controlador:

```
POST chega
   │
   ▼
@RequestBody   → Jackson monta o NovoLead
   │
   ▼
@Valid         → confere as anotacoes do NovoLead
   │
   ├── tudo certo ──▶ o seu metodo criar() roda
   │
   └── algo errado ─▶ 400, e o seu metodo NEM RODA
```

Repare no último ramo: quando a validação falha, **o corpo do seu método não é
executado**. Nenhum `new Cliente`, nenhum `repo.salvar`. O lead ruim não chega
nem perto do repositório.

---

## 4. Por que no `NovoLead` e não no `Cliente`

Lembra do dia 16: o `NovoLead` é a **fronteira**, a lista do que o mundo de fora
pode mandar. Validação é exatamente uma regra de fronteira — ela descreve o que é
um pedido aceitável.

O `Cliente` é o que o seu sistema é por dentro. Ele não deveria ter que se
defender de formulário mal preenchido, porque, quando o dado chega nele, já
passou pela portaria.

É por isso que o `record` de ontem valeu a pena. Hoje ele ganha a função dele.

---

## 5. O que você escreve hoje

Quatro anotações e três imports, em dois arquivos.

### `web/NovoLead.java` — TODO-1, 2 e 3

Os imports vêm do pacote `jakarta.validation.constraints`:

```java
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
```

> **Cuidado:** é `jakarta`, não `javax`. Um monte de exemplo antigo na internet
> ainda mostra `javax` — não funciona mais desde o Spring Boot 3.

As anotações vão **coladas no campo**, dentro dos parênteses do record:

- `nome` → `@NotBlank`, mensagem `"nome e obrigatorio"`
- `whatsapp` → `@NotBlank`, mensagem `"whatsapp e obrigatorio"`
- `whatsapp` → **também** `@Pattern`, com `regexp = "[0-9]{11}"` e mensagem
  `"whatsapp precisa ter 11 digitos"`

Sim, duas anotações no mesmo campo. Pode empilhar quantas quiser, uma por linha.

### `web/ControladorLeads.java` — TODO-4

Um import — e repare que este **não** é de `constraints`, é da raiz:

```java
import jakarta.validation.Valid;
```

E a anotação `@Valid` dentro dos parênteses do `criar`, **antes** do
`@RequestBody`.

---

## 6. Fechar o dia

```bash
jt teste dia17
```

O `requisicoes.txt` de hoje tem cinco linhas e conta uma história:

```
GET  /leads                            → 200, os 5 de sempre
POST /leads {nome, whatsapp}           → 200, entra
POST /leads {só nome}                  → 400, recusado
POST /leads {nome vazio, whatsapp 619} → 400, recusado
GET  /leads                            → 200, SEIS. so o bom entrou
```

O último GET é a prova. Dois pedidos ruins bateram na porta e **nenhum dos dois
virou lead**.

> O `jt` mudou hoje: ele passou a mostrar o código HTTP no cabeçalho de cada
> requisição (`-> 200`, `-> 400`). Sem isso não dava pra testar recusa.

Passou? `jt ok "dia17 validacao: @NotBlank, @Pattern e @Valid"`.

---

## 7. O que ainda está ruim

Olha o que a sua API responde quando recusa:

```json
{"detail":"Invalid request content.","status":400,"title":"Bad Request"}
```

Ela diz **que** recusou, mas não diz **o quê**. Quem está do outro lado — o
JavaScript da sua landing page — não tem como mostrar "preencha o telefone"
embaixo do campo certo.

E aquelas mensagens bonitas que você escreveu nos `message`? Não apareceram em
lugar nenhum.

Isso é o dia 18: pegar o erro de validação e devolver uma resposta útil, campo
por campo. As mensagens de hoje vão ser usadas lá.

---

## 8. Experimento de 2 minutos

Com tudo passando, **comente só o `@Valid`** do controlador:

```java
public Cliente criar(/* @Valid */ @RequestBody NovoLead novo) {
```

Antes de rodar, responda: as anotações do `NovoLead` continuam lá. **Elas
sozinhas seguram o lead ruim?**

Agora `jt teste dia17`.

Volta tudo pra 200. O `NovoLead` continua cheio de `@NotBlank`, e não adianta
nada — ninguém está lendo.

É a mesma régua do dia 14, e vale pros dois lados: **anotação é etiqueta;
etiqueta sem leitor é decoração.** Descomente antes de commitar.
