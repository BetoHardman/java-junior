# Dia 21 — arrumar a casa

Hoje é diferente de todos os outros. **A sua API não vai ganhar nada novo.**
Nenhum endereço, nenhuma regra, nenhum campo.

No fim do dia ela responde exatamente igual. Eu conferi byte a byte: as
primeiras respostas do `esperado.txt` de hoje são **idênticas** às de ontem.

O que muda é **onde cada coisa mora**. E esse é um dos dias mais importantes do
curso, porque é a diferença entre código que cresce e código que empaca.

---

## 1. O problema: o controlador virou um balcão que faz tudo

Abra o `ControladorLeads.java` do **dia 20** e olhe o `mudarStatus`:

```java
@PutMapping("/leads/{id}")
public Cliente mudarStatus(@PathVariable Long id, @Valid @RequestBody MudarStatus mudanca) {
    Cliente c = repo.buscarPorId(id);          // ← busca dado
    if (c == null) {                            // ← decide regra
        throw new LeadNaoEncontrado();
    }
    c.mudarStatus(mudanca.status());            // ← aplica regra
    return c;
}
```

Três trabalhos diferentes num método só:

1. **falar HTTP** — ler o `{id}` do endereço, ler o corpo, devolver JSON
2. **decidir a regra** — "lead que não existe é erro"
3. **mexer no dado** — procurar no repositório

Funciona. O problema aparece depois:

- amanhã você quer a mesma regra numa **tarefa agendada**, que não tem HTTP
  nenhum. Como reaproveita? Copiando e colando.
- depois você troca memória por **banco de dados**. Quantos arquivos mexem?
  Todos os que tocam no `repo` — inclusive o controlador, que não tem nada a ver
  com isso.
- e pra testar a regra "lead que não existe é erro", você precisa subir um
  servidor web inteiro. Pra testar um `if`.

---

## 2. A arrumação: três camadas

```
            ┌──────────────────────┐
  HTTP  ──▶ │  ControladorLeads    │  so fala HTTP
            │  (@RestController)   │  le endereco, le corpo, devolve JSON
            └──────────┬───────────┘
                       │
            ┌──────────▼───────────┐
            │  ServicoDeLeads      │  a REGRA
            │  (@Service)          │  "lead que nao existe e erro"
            └──────────┬───────────┘
                       │
            ┌──────────▼───────────┐
            │  RepositorioMemoria  │  so guarda e devolve DADO
            │  (@Repository)       │
            └──────────────────────┘
```

Cada uma só conhece a de baixo. **A de baixo não sabe que a de cima existe** —
o repositório não faz ideia do que é HTTP, e o serviço também não.

É por isso que as três anotações têm nomes diferentes desde o dia 14. Elas fazem
a mesma coisa pro Spring; o nome é pra **gente**, pra dizer qual é o papel:

| Anotação | Camada | Responsabilidade |
|---|---|---|
| `@RestController` | borda | traduzir HTTP |
| **`@Service`** | **meio** | **a regra do negócio** |
| `@Repository` | fundo | guardar e achar dado |

---

## 3. O resultado: olhe o controlador de hoje

Ele já vem pronto — **você não escreve nada nele hoje**, só lê. Cada método
virou uma linha:

```java
@PutMapping("/leads/{id}")
public Cliente mudarStatus(@PathVariable Long id, @Valid @RequestBody MudarStatus mudanca) {
    return servico.mudarStatus(id, mudanca.status());
}
```

E repare no que sumiu do topo do arquivo: **o import do `Repositorio`**. O
controlador não conhece mais o repositório. Ele pede um `ServicoDeLeads` no
construtor, e pronto.

Quando a gente trocar memória por banco de dados — e vai ser em breve — **este
arquivo não muda uma vírgula.**

---

## 4. Uma mudança pequena que diz muito

O `LeadNaoEncontrado.java` saiu da pasta `web/` e foi pra `servico/`.

Pergunte-se por quê. "Esse lead não existe" é uma afirmação sobre **o seu
negócio**, não sobre HTTP. Quem decide isso é a regra. O fato de virar um 404 lá
na frente é detalhe da borda — e continua resolvido no `TratadorDeErros`, que
nem precisou mudar.

> A régua: **o nome da pasta é uma promessa.** Se um arquivo não cumpre a
> promessa da pasta onde está, ele está no lugar errado.

---

## 5. O que você escreve hoje

Seis TODOs, **tudo em `servico/ServicoDeLeads.java`**. É o único arquivo seu
hoje.

| TODO | O que é |
|---|---|
| 1 | a anotação da classe |
| 2 | o construtor |
| 3 | `listar` — o `if` do filtro |
| 4 | `buscar` — procurar ou lançar |
| 5 | `criar` — montar, salvar, devolver |
| 6 | `mudarStatus` — pegar, mudar, devolver |

**O `resumo()` vem pronto**, como exemplo do formato dos outros.

### Nada disso é novo

Todos os quatro corpos **já existem**, escritos por você, no `ControladorLeads`
do dia 20. Abra os dois projetos lado a lado e mova o miolo. O que fica pra trás
é só a parte de HTTP: as anotações, o `@PathVariable`, o `@RequestBody`.

### Uma dica que vale pelo dia (TODO-6)

O `mudarStatus` precisa achar o lead e dar 404 se não existir. Isso é
**exatamente** o que o `buscar()` logo acima já faz.

Chame ele. Um método do serviço pode chamar outro do mesmo serviço — é só
escrever `buscar(id)`, sem `this.`, sem nada.

Assim a regra "lead que não existe é erro" fica escrita **em um lugar só**. No
dia em que ela mudar, muda num lugar só. Esse é o ponto inteiro de hoje.

> **Apague os `return null;`** que estão nos quatro métodos. Eles são andaime
> pro arquivo compilar enquanto está vazio.

---

## 6. Fechar o dia

```bash
jt teste dia21
```

Sete requisições, cobrindo os cinco endereços:

```
GET  /leads/resumo           → 200
GET  /leads?status=contato   → 200
GET  /leads/3                → 200
GET  /leads/999              → 404
POST /leads                  → 200   entra o lead 6
PUT  /leads/6                → 200   o lead que acabou de entrar muda
GET  /leads                  → 200   os 6
```

Repare no POST seguido do PUT: o lead nasce e é alterado na mesma corrida. Os
cinco endereços da sua API de leads funcionando juntos.

Passou? `jt ok "dia21 camada de servico com @Service"`.

---

## 7. O exercício de 2 minutos é só olhar

Hoje não tem experimento de quebrar. Tem um de **comparar**.

Abra os dois `ControladorLeads.java` lado a lado — o do dia 20 e o de hoje — e
conte as linhas dos métodos.

| | dia 20 | dia 21 |
|---|---|---|
| `mudarStatus` | 8 linhas | 1 |
| `buscar` | 6 linhas | 1 |
| `criar` | 4 linhas | 1 |
| importa o `Repositorio`? | sim | **não** |

Mesma API. Mesmas respostas, byte a byte. Metade do código no lugar certo.

Guarde esse dia: em entrevista de Java júnior, *"como você separa as camadas de
uma API?"* é uma das perguntas mais comuns que existem. A partir de hoje você
responde mostrando o seu repositório.
