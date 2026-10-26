# Dia 8 — interface

Ontem o `ClienteVip` **herdou** do `Cliente`: pegou tudo que o pai tinha e mudou um pedaço.

Hoje é outra coisa. Interface **não dá nada pronto.** Ela só diz *o que* tem que existir,
e deixa *como* por conta de quem assina.

**Dois arquivos hoje.** `Cliente.java`, `ClienteVip.java` e `Main.java` já estão prontos —
**não mexa neles.**

```bash
nano dias/dia08/Repositorio.java
```

---

## 1. A interface — o contrato

```java
public interface Repositorio {
    void salvar(Cliente c);
    Cliente[] listar();
    int total();
}
```

Repare no que **não** tem aí:

- **Nenhum método tem corpo.** Acaba no `;`, não em `{ }`. Interface não executa nada.
- **Nenhum campo.** Ela não guarda estado.
- Não escreve `public` nos métodos. Numa interface **já é público** — o compilador assume.

Isso é um contrato: *"quem assinar isso promete saber salvar, listar e contar."*

É a diferença que importa:

| | O que é | Palavra |
|---|---|---|
| `extends Cliente` | **sou um** Cliente, com extras | herança |
| `implements Repositorio` | **sei fazer** o que o Repositorio pede | implementação |

Java deixa herdar de **uma** classe só, mas implementar **quantas interfaces quiser.**
Por isso o mundo real é construído em cima de interface, não de herança.

## 2. A implementação

```bash
nano dias/dia08/RepositorioMemoria.java
```

```java
public class RepositorioMemoria implements Repositorio {
    private Cliente[] itens = new Cliente[10];
    private int qtd = 0;
```

Dois campos privados: a caixa com espaço pra 10, e **quantos já entraram de verdade.**
O array nasce com 10 posições vazias — sem o contador você não sabe onde termina o que é real.

### salvar

```java
    @Override
    public void salvar(Cliente c) {
        itens[qtd] = c;
        qtd++;
    }
```

Guarda na primeira posição livre e anda com o contador. `qtd` aponta sempre pro próximo
buraco vazio: com 0 salvos ele vale 0, que é exatamente onde o primeiro tem que cair.

### listar

```java
    @Override
    public Cliente[] listar() {
        Cliente[] copia = new Cliente[qtd];
        for (int i = 0; i < qtd; i++) {
            copia[i] = itens[i];
        }
        return copia;
    }
```

**Por que não devolver `itens` direto?** Porque ele tem 10 posições e só 3 têm cliente.
As outras 7 são `null`, e o `for` do `Main` ia estourar `NullPointerException` na quarta volta.
Devolve um array **do tamanho certo.**

### total

```java
    @Override
    public int total() {
        return qtd;
    }
```

O `@Override` aqui também vale: se você escrever `totais()` por engano, o compilador acusa
na hora *"não sobrescreve nada"*. Sem ele, o Java criaria um método novo em silêncio e
reclamaria que a classe não cumpriu o contrato — erro bem mais confuso de ler.

---

## Saída esperada

```
total: 3
--- carteira ---
Luna Toys (61993687124) - novo -> 120.0
Bruna Severo (61999990000) - novo [VIP -20%] -> 96.0
Creator Pro (61988887777) - novo -> 120.0
```

## A linha que é a aula inteira

Olhe o começo do `Main`:

```java
Repositorio repo = new RepositorioMemoria();
```

A **variável** é do tipo da interface. O **objeto** é da classe. Daí pra baixo, o `Main`
só sabe que `repo` salva, lista e conta. **Ele não faz ideia de que é array.**

Troque por um `RepositorioMySQL`, um `RepositorioArquivo`, um `RepositorioNuvem` — o `Main`
não muda **uma letra.** Isso se chama programar contra o contrato, não contra a peça.

É literalmente o que o Spring Boot faz na sua API de leads: você declara
`interface LeadRepository`, e o Spring entrega a implementação que fala com o banco.
Você nunca escreve o código do banco. Hoje você construiu a peça à mão pra entender
o que ele vai te dar de graça.

## Fechar

```bash
jt teste
jt ok "dia08 interface"
```

## Prova do dia

1. Por que os métodos da interface terminam em `;` e não em `{ }`?
2. Diferença entre `extends` e `implements`, em uma frase cada?
3. Por que o `listar()` copia em vez de devolver o `itens` direto?
4. Se eu trocar `new RepositorioMemoria()` por outra classe que implementa `Repositorio`,
   quantas linhas do `Main` mudam?
