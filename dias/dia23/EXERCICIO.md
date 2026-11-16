# Dia 23 — os leads param de sumir

Ontem entrou banco de dados. Mas com uma ressalva que eu fiz questão de repetir:

```properties
spring.datasource.url=jdbc:h2:mem:leads
```

**`mem` é memória.** Tinha tabela, tinha chave primária, tinha SQL — e tudo
evaporava ao desligar o programa.

Hoje isso acaba. E a correção é de uma palavra.

---

## 1. De `mem` para `file`

```properties
spring.datasource.url=jdbc:h2:file:./dados/leads
```

O H2 passa a gravar num arquivo de verdade: `dias/dia23/dados/leads.mv.db`.
Desligou, os dados ficam. Ligou de novo, estão lá.

### E o `ddl-auto` tem que mudar junto

```properties
spring.jpa.hibernate.ddl-auto=update
```

Essa configuração diz o que o Hibernate faz com a **estrutura** da tabela ao
subir:

| Valor | O que faz | Quando usar |
|---|---|---|
| `create-drop` | cria ao subir, **apaga tudo** ao desligar | teste, brincadeira |
| `update` | mantém os dados, só ajusta a tabela quando a classe muda | desenvolvimento |
| `validate` | não mexe em nada, só confere se bate | produção |
| `none` | ignora | produção com migração à parte |

Deixar `create-drop` com banco em arquivo seria o pior dos dois mundos: um
arquivo no disco que se apaga sozinho.

> **Em produção ninguém usa `update`.** Se você renomear um campo, ele cria a
> coluna nova e **deixa a velha lá**, com os dados presos dentro. Quem roda de
> verdade usa `validate` e muda o banco com ferramenta própria (Flyway,
> Liquibase). Pra hoje, `update` serve.

---

## 2. O problema que a permanência cria

Olha a `Carteira`. Toda vez que o programa sobe, ela insere os 5 leads.

Em memória isso era inofensivo — o banco nascia vazio a cada vez. **Com arquivo,
não:**

```
1a vez que liga:   5 leads
2a vez:           10 leads
3a vez:           15 leads
```

Os mesmos cinco nomes, repetidos, com ids diferentes. Em uma semana a sua base
está inutilizável.

A correção é uma guarda: **semear só se o banco estiver vazio.**

```
se o repositorio ja tem lead:
    so imprime o que tem e sai
senao:
    cria os 5, salva, imprime
```

Isso tem nome: **operação idempotente** — rodar dez vezes dá no mesmo que rodar
uma. Você já viu a ideia no dia 19, falando de PUT. Agora ela aparece em
carregamento de dado, que é onde mais importa.

> O `imprimir()` já foi separado pra você: é o bloco que desenha a carteira na
> tela. Nos dois caminhos ele é chamado.

---

## 3. `DELETE` — fechando o CRUD

Sua API sabe criar, ler e alterar. Falta apagar.

```
POST   /leads       criar
GET    /leads       ler
PUT    /leads/{id}  alterar
DELETE /leads/{id}  apagar     ← hoje
```

Essas quatro operações juntas têm nome: **CRUD** (Create, Read, Update, Delete).
É o vocabulário básico de qualquer API, e a partir de hoje a sua tem as quatro.

### Um código novo: 204

```java
@ResponseStatus(HttpStatus.NO_CONTENT)
```

**204 No Content** quer dizer: *"deu certo, e não tenho nada pra te devolver."*

Faz sentido num DELETE — o recurso acabou de deixar de existir, então não há o
que mandar no corpo. Repare que o método é `void`: não devolve nada mesmo.

| Código | Quando |
|---|---|
| 200 OK | deu certo, **e** aqui está a resposta |
| **204 No Content** | **deu certo, e não há o que responder** |

> E o 404 continua valendo: apagar um lead que não existe **não** é sucesso.
> Por isso o serviço confere antes.

---

## 4. Uma mudança no `jt`, e o motivo importa

A partir de hoje, o `jt teste` **apaga a pasta `dados/` antes de rodar**.

Parece contraditório — acabamos de fazer os dados sobreviverem. Mas pensa no que
aconteceria sem isso: a segunda corrida do teste encontraria os leads da
primeira, o `POST` criaria o id 7 em vez do 6, o `DELETE /leads/6` daria 404, e a
saída mudaria a cada vez.

**Teste tem que partir sempre do mesmo estado.** Essa é uma regra de verdade, não
um truque do curso — é o motivo de existirem bancos separados pra teste em
qualquer empresa.

O `jt rodar` **não apaga nada**. É com ele que você vai provar a permanência, na
mão, no fim do dia.

> A pasta `dados/` entrou no `.gitignore`. Banco não vai pro repositório: ele é
> dado, não código.

---

## 5. O que você escreve hoje

Seis TODOs, em cinco arquivos.

| TODO | Arquivo | O que é |
|---|---|---|
| 1 | `resources/application.properties` | `mem` vira `file` |
| 2 | `resources/application.properties` | `create-drop` vira `update` |
| 3 | `Carteira.java` | a guarda que impede semear duas vezes |
| 4 | `repositorio/RepositorioBanco.java` | o método que apaga |
| 5 | `servico/ServicoDeLeads.java` | conferir que existe, depois apagar |
| 6 | `web/ControladorLeads.java` | as duas anotações do DELETE |

> Os TODOs 1 e 2 são num arquivo `.properties`, não `.java`. Lá o comentário
> começa com `#`, não com `//`.

### Antes de escrever, roda

```bash
jt teste dia23
```

```
DELETE /leads/6 -> 405
GET    /leads/6 -> 200
```

**405** você já conhece do dia 20: o endereço `/leads/6` existe — tem GET e PUT —
mas não atende DELETE. E o `GET /leads/6` responde 200 porque o lead continua
vivo: ninguém apagou.

---

## 6. Fechar o dia

```bash
jt teste dia23
```

```
GET    /leads/resumo          → 200
GET    /leads?status=contato  → 200
POST   /leads                 → 200   entra o lead 6
PUT    /leads/6               → 200   muda o status
DELETE /leads/6               → 204   apaga, sem corpo
GET    /leads/6               → 404   não existe mais
GET    /leads                 → 200   voltou aos 5
```

O lead nasce, muda, morre e some — tudo numa corrida.

Passou? `jt ok "dia23 banco em arquivo, DELETE e carga idempotente"`.

---

## 7. O experimento de hoje é a prova do dia

O teste apaga o banco antes de rodar, então **ele não prova permanência**. Essa
parte é com você, na mão. São dois minutos.

**1.** Sobe o servidor:

```bash
jt rodar dia23
```

**2.** Em **outro terminal**, grava um lead com o seu nome:

```bash
curl -X POST http://localhost:8080/leads -H "Content-Type: application/json" -d "{\"nome\":\"Roberto Teste\",\"whatsapp\":\"61995572995\"}"
```

**3.** Volta no primeiro terminal e aperta **Ctrl+C**. O servidor morre.

**4.** Sobe de novo:

```bash
jt rodar dia23
```

**5.** Olha a carteira que ele imprime ao subir, e abre no navegador:

```
http://localhost:8080/leads
```

Duas coisas pra conferir, e as duas são o dia inteiro:

- **o "Roberto Teste" está lá**, mesmo o programa tendo sido desligado
- **continuam 6 leads, não 11** — a guarda do TODO-3 impediu a segunda semeadura

Se aparecerem 11, o TODO-3 não está funcionando. Se o "Roberto Teste" sumiu, o
TODO-1 ou o TODO-2 ficaram pra trás.

**Depois do experimento**, apaga a pasta pra não deixar lixo no commit:

```bash
rm -rf dias/dia23/dados
```

---

## 8. Agora sim você pode dizer que persiste

Até ontem, em entrevista, a frase honesta era *"usei JPA, Hibernate e H2"*.

A partir de hoje, depois de rodar o experimento acima com os próprios olhos, a
frase passa a ser: **"os leads sobrevivem ao reinício do servidor, e a carga
inicial é idempotente"**. As duas metades importam — a segunda mostra que você
pensou no que acontece na segunda vez que o programa sobe, que é onde a maioria
tropeça.
