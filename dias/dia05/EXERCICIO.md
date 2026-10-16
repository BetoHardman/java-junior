# Dia 5 — classes e objetos

Hoje muda o jeito de pensar. Até agora seu programa era um monte de métodos soltos dentro de
`Main`. Agora você cria um **molde** e fabrica coisas a partir dele.

**Hoje são dois arquivos.** O `Main.java` já está pronto — **não mexa nele**.
Você escreve o `Cliente.java`.

```bash
nano dias/dia05/Cliente.java
```

---

## A ideia: molde e peça

`Cliente` é o **molde**: diz que todo cliente tem nome, whatsapp e status.
`new Cliente("Luna Toys", "61993687124")` fabrica **uma peça** a partir do molde.

Do mesmo molde saem quantas peças você quiser, cada uma com seus próprios dados.
A forma de bolo é a classe; cada bolo é um objeto.

---

## 1. Os campos

```java
    String nome;
    String whatsapp;
    String status;
```

São variáveis que vivem **dentro de cada objeto**. Cada cliente tem o seu nome, o seu status.
Repare: não tem `static`. `static` seria da classe inteira; sem `static` é de cada peça.

## 2. O construtor

```java
    Cliente(String nome, String whatsapp) {
        this.nome = nome;
        this.whatsapp = whatsapp;
        this.status = "novo";
    }
```

Três coisas pra reparar:

- **O nome é igual ao da classe** (`Cliente`) e **não tem tipo de retorno**. Nem `void`.
  É assim que o Java sabe que é construtor e não método.
- **`this`** quer dizer "este objeto aqui". Como o parâmetro se chama `nome` e o campo
  também, `this.nome = nome` lê-se: *o nome DESTE objeto recebe o nome que chegou*.
  Sem o `this`, você estaria mandando a variável para ela mesma — e o campo ficaria vazio.
- **`status` não vem de fora.** Todo cliente nasce `"novo"`. O construtor é onde você
  decide o estado inicial.

## 3. `mudarStatus`

```java
    void mudarStatus(String novo) {
        this.status = novo;
    }
```

Um método `void` que altera o objeto por dentro. Depois de chamar, aquele cliente mudou.

## 4. `resumo`

```java
    String resumo() {
        return nome + " (" + whatsapp + ") - " + status;
    }
```

Repare: **sem parâmetro nenhum**. Ele já tem tudo que precisa, porque está dentro do objeto
e enxerga os próprios campos.

---

## Saída esperada

```
Luna Toys (61993687124) - novo
Bruna Severo (61999990000) - novo
--- depois ---
Luna Toys (61993687124) - novo
Bruna Severo (61999990000) - fechado
Fechados: 1
```

**O ponto principal está no "depois":** você chamou `b.mudarStatus("fechado")` e **só o b mudou**.
O `a` continua `novo`. São dois objetos independentes, saídos do mesmo molde.

---

## Uma coisa no Main que vale olhar

```java
if (c.status.equals("fechado"))
```

Texto se compara com **`.equals()`**, nunca com `==`. O `==` pergunta "é o mesmo objeto na
memória?" e o `.equals()` pergunta "tem o mesmo conteúdo?". Em 95% dos casos você quer o segundo.

**Essa é a pergunta número 1 de entrevista de Java.** Anota.

## Fechar

```bash
jt teste
jt ok "dia05 classes e objetos"
```

## Prova do dia

1. Qual a diferença entre a classe `Cliente` e o objeto `a`?
2. Por que o construtor não tem tipo de retorno?
3. Por que `b.mudarStatus()` não mexeu no `a`?
4. Por que `.equals()` e não `==` pra comparar texto?
