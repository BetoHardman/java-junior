# Dia 16 — agora o mundo de fora pode gravar

Ontem sua API **respondeu**. Hoje ela **recebe**.

É a virada que importa pro seu negócio: o formulário da landing page manda os
dados, a API grava. A partir de hoje o `/leads` deixa de ser uma vitrine e
começa a ser uma caixa de entrada.

---

## 1. GET e POST não são a mesma coisa

| | GET | POST |
|---|---|---|
| Pra que | pedir informação | **mandar** informação |
| Tem corpo? | não | **sim**, um JSON junto |
| Dá pra digitar no navegador? | sim | não direto |
| Repetir é seguro? | sim | não — grava de novo |

O navegador só sabe fazer GET quando você digita um endereço. Por isso o POST
de hoje você vai testar com o `jt` e com o `curl`, não pela barra de endereço.

---

## 2. `@RequestBody` — pegando o pacote que chegou

No GET, o método não recebia nada. No POST chega um JSON junto do pedido:

```json
{"nome":"Alon Warm","whatsapp":"61900001111"}
```

`@RequestBody` na frente do parâmetro diz ao Spring: *"pega o JSON que veio no
corpo, manda o Jackson transformar em objeto e me entrega pronto"*.

É o caminho inverso de ontem. Ontem o Jackson virou objeto em JSON pra sair.
Hoje ele vira JSON em objeto pra entrar. **Mesmo tradutor, mão contrária.**

---

## 3. `record` — a classe que só guarda dado

Novidade de Java hoje, e das boas.

Quando uma classe serve só pra **carregar dados** — sem regra, sem
comportamento — escrever construtor, getter e `equals` é puro desperdício. O
Java tem um atalho:

```java
public record Ponto(int x, int y) {
}
```

Essa linha gera sozinha: os dois campos, o construtor que recebe os dois, os
acessos `x()` e `y()`, mais `equals`, `hashCode` e `toString`.

Repare no formato do acesso: **`ponto.x()`, não `ponto.getX()`**. Record não
usa o prefixo `get`.

> O Jackson entende record nativamente. Não precisa de anotação nenhuma.

---

## 4. Por que um `NovoLead`, e não o `Cliente` direto?

Pergunta justa — e eu fui testar antes de te responder.

**Receber `Cliente` direto funciona.** Testei aqui, grava igual. Então o motivo
do `record` não é "não funciona". É outro, e é o que separa código que dura de
código que apenas roda:

**O que entra não é o que sai.**

Olhe os dois lados:

| O formulário manda | O `Cliente` tem |
|---|---|
| nome | nome |
| whatsapp | whatsapp |
| | status (quem decide é você, não quem preenche) |
| | desconto (só VIP) |
| | valorHora (regra sua) |

Se o endereço receber `Cliente`, o contrato da sua API passa a ser *o formato
interno do seu modelo*. No dia em que você mexer no `Cliente` — e você vai, a
API de leads ainda tem muito chão — você quebra quem já está mandando dado pra
você.

O `NovoLead` é a **fronteira**: ele diz, por escrito, quais são os únicos
campos que o mundo de fora pode mandar. Uma linha de código que vale um
problema a menos. Esse padrão tem nome de mercado: **DTO**.

E ele prepara o dia 17: validação (`nome` não pode vir vazio, `whatsapp`
precisa ter 11 dígitos) se pendura **no DTO**, não no seu modelo.

---

## 5. O que você escreve hoje

Seis linhas-ideia, em dois arquivos. Procure por `TODO`.

### `web/NovoLead.java` — TODO-1

O arquivo tem só o `package`. A classe inteira é **uma linha sua** — pelo
molde da seção 3, com `nome` e `whatsapp`, os dois `String`.

### `web/ControladorLeads.java` — TODO-2 a 6

**TODO-2 e TODO-3 — os dois imports.** Esses não dá pra deduzir:

```java
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
```

**TODO-4 — a anotação do método.** Mesmo formato do `@GetMapping("/leads")`
que está dez linhas acima, trocando a palavra. O endereço é **o mesmo**:
`/leads`. Não dá conflito — o que diferencia um do outro é o verbo.

**TODO-5 — o parâmetro.** Dentro dos parênteses do `criar`, três pedaços nesta
ordem: a anotação, o tipo e o nome da variável.

**TODO-6 — o corpo do método.** Três linhas:

1. criar um `Cliente` novo com os dados que vieram no `NovoLead`
   (lembre: `novo.nome()`, com parênteses, sem `get`)
2. salvar no `repo`
3. devolver o cliente criado — e apagar o `return null;` que está lá

---

## 6. Fechar o dia

```bash
jt teste dia16
```

O `requisicoes.txt` de hoje tem três linhas, e é isso que o teste faz:

```
GET  /leads     ← os 5 de sempre
POST /leads     ← grava o "Alon Warm"
GET  /leads     ← agora tem 6
```

O segundo `GET` é a prova real. Não basta o POST responder bonito — o lead tem
que **estar lá** depois.

### Testar com a mão

```bash
jt rodar dia16
```

Com o servidor no ar, **em outro terminal**:

```bash
curl -X POST http://localhost:8080/leads -H "Content-Type: application/json" -d "{\"nome\":\"Cliente Teste\",\"whatsapp\":\"61911112222\"}"
```

Depois abra `http://localhost:8080/leads` no navegador e veja ele lá.

Passou? `jt ok "dia16 POST /leads com record e @RequestBody"`.

---

## 7. Experimento de 2 minutos

Com tudo passando, mande um POST **sem o whatsapp**:

```bash
curl -X POST http://localhost:8080/leads -H "Content-Type: application/json" -d "{\"nome\":\"Faltando Campo\"}"
```

Antes de rodar: **vai dar erro, ou vai gravar?**

Vai gravar. Com `"whatsapp":null`. Ninguém reclamou, nada quebrou, e agora tem
lixo no seu repositório — um lead sem telefone, que é um lead que não serve pra
nada.

Essa é exatamente a dor que o **dia 17** resolve: validação. O `NovoLead` que
você escreveu hoje é onde ela vai morar.
