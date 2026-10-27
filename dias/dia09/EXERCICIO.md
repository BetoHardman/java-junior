# Dia 9 — `ArrayList` e a biblioteca do Java

Ontem você construiu o repositório à mão: um array de 10 posições e um contador `qtd`
pra saber onde terminava o que era real.

Hoje você **joga os dois fora.** O Java já tem isso pronto, e melhor.

**Dois arquivos hoje** — um você edita, outro você reescreve.
`Cliente.java`, `ClienteVip.java` e `Main.java` estão prontos. **Não mexa.**

---

## 1. O problema do dia 8

Olhe o `Main` de hoje. Depois dos três clientes nomeados, tem isto:

```java
for (int i = 1; i <= 9; i++) {
    repo.salvar(new Cliente("Cliente " + i, "6190000000" + i));
}
```

São **12 clientes no total.** O seu repositório de ontem tinha `new Cliente[10]`.

Na 11ª volta ele estouraria com `ArrayIndexOutOfBoundsException`. E a saída não
seria "erro de programador iniciante" — seria o seu sistema caindo no 11º cliente
que o negócio conquistou.

Array tem tamanho fixo **decidido na hora que você escreve o código.** Você nunca
sabe quantos clientes vão chegar.

## 2. O `import` — a primeira vez no curso

```bash
nano dias/dia09/Repositorio.java
```

Acrescente a **primeira linha** do arquivo, antes do `public interface`:

```java
import java.util.List;
```

`import` é você dizendo ao compilador **onde achar** uma classe que não é sua.
`java.util` é o pacote da biblioteca padrão onde moram as coleções.
Sem o import, o compilador responde `cannot find symbol: class List` — o mesmo erro
do `CLiente` do dia 8, por motivo diferente: o nome existe, mas ele não sabe onde.

E troque a linha do `listar`:

```java
    List<Cliente> listar();     // era: Cliente[] listar();
```

Só isso neste arquivo. Duas linhas.

### O que `<Cliente>` significa

`List<Cliente>` se lê **"lista DE Cliente"**. Os sinais `< >` são *generics*: você
está dizendo ao compilador qual tipo mora dentro da lista.

Por que isso importa: com `List<Cliente>`, se alguém tentar guardar um `String` ali
dentro, **o erro acontece na compilação**, não com o cliente usando o sistema.

## 3. Reescrever a implementação

```bash
nano dias/dia09/RepositorioMemoria.java
```

Apague tudo e refaça. Começa com **dois imports**:

```java
import java.util.ArrayList;
import java.util.List;
```

E agora olhe o campo com atenção:

```java
    private List<Cliente> itens = new ArrayList<>();
```

**Essa linha é exatamente o que você aprendeu ontem.**

```
List<Cliente> itens = new ArrayList<>();
     ↑                      ↑
   interface           implementação

Repositorio repo = new RepositorioMemoria();
     ↑                      ↑
   interface           implementação
```

`List` é uma **interface** da biblioteca do Java. `ArrayList` é uma classe que a
implementa. **A biblioteca do Java é construída do mesmo jeito que você construiu
o seu repositório ontem** — contrato de um lado, peça do outro.

O `<>` vazio no fim se chama *diamante*: o Java olha para a esquerda, vê
`List<Cliente>` e deduz sozinho. Escrever `new ArrayList<Cliente>()` também funciona,
só é redundante.

### Os três métodos

Agora compare o que você escreveu ontem com o que você escreve hoje:

| | Dia 8 (na mão) | Dia 9 (biblioteca) |
|---|---|---|
| guardar | `itens[qtd] = c; qtd++;` | `itens.add(c);` |
| contar | `return qtd;` | `return itens.size();` |
| devolver | criar array, copiar num `for`, devolver | `return itens;` |

**O `listar()` some quase inteiro.** Aquele `for` de cópia existia só pra contornar
as posições vazias do array. Com `ArrayList` não existe posição vazia: a lista tem
exatamente o que você colocou. Nada pra contornar.

E o tamanho? **Não existe.** A lista cresce sozinha. Os 12 clientes entram, e o
décimo-terceiro também entraria.

---

## Saída esperada

```
total: 12
--- carteira ---
Luna Toys (61993687124) - novo -> 120.0
Bruna Severo (61999990000) - novo [VIP -20%] -> 96.0
Creator Pro (61988887777) - novo -> 120.0
Cliente 1 (61900000001) - novo -> 120.0
... (até Cliente 9)
```

## O que você levou de hoje

Escreveu **menos código** e ganhou mais: tamanho ilimitado, segurança de tipo e
três métodos que cabem numa linha cada.

Essa é a diferença entre programador que resolve e programador que **sabe o que já
está resolvido.** Na API de leads você vai escrever `List<Lead>` sem pensar duas
vezes — e vai saber exatamente o que tem dentro, porque construiu a versão crua.

## Fechar

```bash
jt teste
jt ok "dia09 ArrayList e generics"
```

## Prova do dia

1. O que o `import` faz, e qual erro aparece sem ele?
2. O que `<Cliente>` em `List<Cliente>` garante?
3. `List` e `ArrayList` — qual é a interface e qual é a implementação?
4. Por que o `listar()` de hoje não precisa copiar nada?
5. Quantos clientes cabem no repositório de hoje?
