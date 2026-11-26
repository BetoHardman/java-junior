# Dia 25 — teste de unidade, e o dia 21 finalmente paga

Ontem voce testou a aplicacao inteira: Spring de pe, porta aberta, banco ligado,
pedido HTTP de verdade. Funciona, e e o teste mais parecido com a vida real.

Custa **6,4 segundos**.

Hoje voce testa a mesma regra sem subir nada. Os quatro testes de hoje rodam em
**0,079 segundo** — medido, nao estimado. **Oitenta vezes mais rapido.**

---

## 1. Por que a velocidade importa

Teste lento voce roda no fim do dia. Teste rapido voce roda a cada mudanca.

E a diferenca nao e de conforto — e de **quando voce descobre o erro**. Rodando a
cada mudanca, o erro aparece enquanto o codigo que o causou ainda esta na sua
cabeca. Rodando no fim do dia, voce volta pra um arquivo que ja esqueceu.

| | Dia 24 — integracao | Dia 25 — unidade |
|---|---|---|
| Sobe Spring | sim | **nao** |
| Abre porta | sim | **nao** |
| Toca banco | sim | **nao** |
| Tempo | 6,4 s | **0,079 s** |
| Prova | que as pecas se encaixam | **que a regra esta certa** |

Os dois existem no mesmo projeto, e e assim em empresa: **muitos testes rapidos,
poucos lentos.** Hoje o `dia25` roda os oito juntos.

---

## 2. O duble — e a resposta pra pergunta do dia 21

No dia 21 voce transformou o repositorio numa **interface** e eu te disse que
nada mudava. O `jt teste` provou: a saida ficou identica, byte por byte.

Voce deve ter pensado: *entao pra que serve?*

No dia 22 veio a primeira resposta — deu pra trocar o ArrayList por banco H2 sem
o servico perceber.

**Hoje vem a segunda, e e a mais importante.** Abra
`src/test/java/.../servico/RepositorioFalso.java`. Ele vem pronto, voce nao mexe.

```java
public class RepositorioFalso implements Repositorio {
    private final Map<Long, Cliente> dados = new LinkedHashMap<>();
```

Aos olhos do `ServicoDeLeads` ele e um repositorio igual a qualquer outro —
implementa a mesma interface. Por dentro e um `HashMap`: sem banco, sem Spring,
sem rede.

```java
servico = new ServicoDeLeads(new RepositorioFalso());
```

**Isso so e possivel porque o servico depende da interface, nao da classe.** Se
no dia 21 ele tivesse ficado amarrado ao `RepositorioBanco`, hoje seria
impossivel testar a regra sem um banco de verdade.

Chama-se **duble** (test double). E a mesma ideia do duble de cinema: parece o
ator, faz a cena perigosa, e ninguem na plateia percebe a troca.

---

## 3. `@BeforeEach` — cada teste comeca limpo

```java
@BeforeEach
void antes_de_cada_teste() {
    servico = new ServicoDeLeads(new RepositorioFalso());
}
```

Roda **antes de cada teste**, nao uma vez so. Cada teste ganha um servico zerado,
com um repositorio vazio.

E a mesma lei do dia 24, agora sem banco nenhum: **teste parte sempre do mesmo
estado.** Sem isso, o lead que o teste A criou apareceria no teste B, e a ordem
em que o JUnit resolvesse rodar mudaria o resultado. Teste que passa ou falha
dependendo da ordem e pior que teste nenhum — ele mente.

---

## 4. `assertThrows` — testar o que NAO devolve

Ate agora todo assert olhava um valor devolvido. Mas o `buscar` com id errado
nao devolve nada: ele **estoura**.

```java
assertThrows(LeadNaoEncontrado.class, () -> servico.buscar(999L));
```

Le-se: *"exijo que isto aqui exploda, e exploda com esta excecao"*.

O `() ->` e novo. Ele **nao roda** o `servico.buscar(999L)` ali na hora — ele
embrulha o codigo e entrega fechado. Quem abre e roda e o `assertThrows`, por
dentro, com o cinto de seguranca afivelado. Se rodasse na hora, a excecao subiria
antes de alguem estar vigiando, e o teste quebraria em vez de passar.

> **Cuidado com o inverso:** se o `buscar` **nao** estourar, o teste fica
> vermelho. E isso esta certo — ele promete que a explosao acontece.

---

## 5. O que voce escreve hoje

Quatro TODOs, todos em
`src/test/java/br/com/betohardman/leads/servico/ServicoDeLeadsTest.java`.

| TODO | O que e |
|---|---|
| 1 | a anotacao do `antes_de_cada_teste` |
| 2 | teste: buscar id inexistente **lanca** `LeadNaoEncontrado` |
| 3 | teste: criar dois leads, `listar(null)` devolve dois |
| 4 | teste: status invalido **nao entra** |

O primeiro teste vem pronto, como molde. Os imports tambem.

### Antes de escrever, roda

```bash
jt teste dia25
```

```
Tests run: 5, Failures: 0, Errors: 1, Skipped: 0
```

Cinco: os quatro do dia 24, que continuam valendo, mais o unico de hoje — que
estoura. Sem o `@BeforeEach` do TODO-1, o `servico` nunca e criado e fica `null`.

---

## 6. Fechar o dia

```bash
jt teste dia25
```

Alvo:

```
Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
```

Passou? `jt ok "dia25 testes de unidade com duble de repositorio"`.

---

## 7. O experimento: o teste que acha um defeito de verdade

O TODO-4 nao e enfeite. Abra o `Cliente.java` e leia o `mudarStatus`:

```java
} else {
    System.out.println("status invalido: " + novo);
}
```

Status invalido **nao estoura e nao avisa ninguem**. Ele imprime uma linha no
terminal do servidor — que em producao ninguem le — e segue como se nada
tivesse acontecido. Quem chamou pela API recebe 200 e acha que deu certo.

E o **erro mudo numero 7** da sua lista, e o mais caro deles: o sistema mente
dizendo que funcionou.

O teste do TODO-4 congela o comportamento de hoje: *status invalido nao entra*.
Com ele no lugar, voce pode um dia trocar aquele `println` por uma excecao de
verdade — e o teste te diz na hora se a regra continuou de pe.

**E isso que teste compra.** Nao e so pegar erro: e poder consertar um defeito
velho sem medo de quebrar outra coisa junto.
