# Dia 11 — `Map` e o primeiro dia no IntelliJ

Hoje mudam **duas coisas**: a ferramenta e o assunto. É de propósito que o assunto
seja pequeno — você vai gastar metade do tempo aprendendo o editor.

> **Antes de abrir o enunciado, faz o setup:** [COMECAR-NO-INTELLIJ.md](../../COMECAR-NO-INTELLIJ.md)
> São uns 10 minutos, uma vez só.

**Dois arquivos hoje, os mesmos de sempre.** `Cliente.java`, `ClienteVip.java` e
`Main.java` estão prontos. **Não mexa.**
O `porStatus` que você escreveu ontem já veio junto — hoje você só acrescenta.

---

## 1. O problema

Ontem o `porStatus("contato")` te deu **a lista** de quem está em contato.
Mas a tela de resumo da API não quer a lista. Ela quer o **número**:

```
novo: 1     contato: 2     fechado: 2
```

Dava pra resolver chamando `porStatus` três vezes e pegando o `.size()` de cada uma.
Funciona — e percorre a carteira inteira **três vezes**. Com 5 clientes ninguém nota.
Com 5.000, e com sete etapas de funil em vez de três, você nota.

Hoje você resolve **numa passada só**.

## 2. `Map` — a estrutura de hoje

`List` guarda uma coisa atrás da outra, e você acha pela posição.
**`Map` guarda pares, e você acha pela chave.**

```
List<Cliente>              Map<String, Integer>
 [0] Luna                   "novo"    -> 1
 [1] Bruna                  "contato" -> 2
 [2] Creator                "fechado" -> 2
```

`Map<String, Integer>` se lê **"mapa de String para Integer"**: a chave é texto,
o valor é número. Dois generics em vez de um, separados por vírgula.

E é a mesma dupla interface/implementação do dia 9:

| Interface | Implementação |
|---|---|
| `List` | `ArrayList` |
| `Map` | `HashMap` |

Dois imports novos no `RepositorioMemoria`: `java.util.Map` e `java.util.HashMap`.
No `Repositorio`, só o `Map`.

### Os dois métodos que você vai usar

| Método | O que faz |
|---|---|
| `mapa.put(chave, valor)` | guarda. Se a chave já existir, **substitui** o valor |
| `mapa.getOrDefault(chave, padrao)` | devolve o valor da chave, ou `padrao` se ela não existir |

## 3. O contrato

Uma linha nova no `Repositorio.java`: um método `contarPorStatus`, **sem parâmetro
nenhum**, que devolve um `Map<String, Integer>`.

Compila (`jt rodar`) antes de seguir. O erro `is not abstract and does not override`
tem que aparecer — você já conhece ele.

## 4. A contagem

No `RepositorioMemoria.java`, implementa o `contarPorStatus` (com `@Override`):

1. cria um mapa novo e vazio — `new HashMap<>()`
2. passa por cada cliente de `itens` com o `for` de sempre
3. pega o status dele e guarda numa variável `String`
4. **`put`** desse status com o valor: *o que já tinha nele, mais 1*
5. depois do `for`, devolve o mapa

O passo 4 é o dia inteiro. Pensa assim, em português:

> *"guarda no status dele o número que ele já tinha, mais um"*

E **"o número que ele já tinha"** é onde entra o `getOrDefault`: no primeiro cliente
de cada status a chave ainda não existe, e você precisa que isso valha **zero**,
não que quebre.

## 5. A armadilha — `get` numa chave que não existe

Nenhum cliente está `"perdido"`. Olha as duas linhas da saída:

```
perdido com get: null
perdido com getOrDefault: 0
```

`get` numa chave que não existe devolve **`null`** — não dá erro, não devolve zero.
E `null` é a causa do erro mais comum do Java inteiro:

```java
int x = resumo.get("perdido");   // NullPointerException em tempo de execucao
```

Compila liso. Quebra na cara do usuário. **Por isso `getOrDefault` existe** — e por
isso ele é o certo dentro da sua contagem.

---

## Saída esperada

```
--- resumo da carteira ---
novo: 1
contato: 2
fechado: 2
--- armadilha ---
perdido com get: null
perdido com getOrDefault: 0
soma do resumo: 5
total do repositorio: 5
```

As duas últimas linhas são a sua conferência: a soma do mapa tem que bater com o
total do repositório. Se não bater, você perdeu ou contou alguém duas vezes.

## Fechar

Edita no IntelliJ, testa no Git Bash — **o `jt` continua igual**:

```bash
jt teste
jt ok "dia11 Map, HashMap e getOrDefault"
```

## Prova do dia

1. Qual a diferença entre achar por **posição** e achar por **chave**?
2. `Map` e `HashMap` — qual é a interface e qual é a implementação?
3. O que `get` devolve numa chave que não existe, e por que isso é perigoso?
4. Por que a contagem usa `getOrDefault` e não `get`?
5. Por que contar numa passada só é melhor que chamar `porStatus` três vezes?
