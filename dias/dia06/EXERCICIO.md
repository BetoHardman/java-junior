# Dia 6 — encapsulamento: private, getters e setters

Ontem qualquer um podia escrever `a.status = "banana"` e o objeto aceitava calado.
Hoje o objeto passa a **se defender**.

```bash
nano dias/dia06/Cliente.java
```

O `Main.java` já está pronto. **Não mexa nele.**

---

## O problema que isso resolve

No dia 5 os campos eram abertos. Qualquer código em qualquer lugar podia escrever
qualquer coisa dentro do seu objeto:

```java
a.status = "banana";      // aceito, sem reclamar
a.status = null;          // aceito tambem
```

Um cliente com status `"banana"` quebra qualquer relatório depois. E quando quebrar,
você vai procurar o erro **no relatório**, não aqui — porque o estrago foi feito longe
de onde aparece.

## `private` fecha a porta

```java
    private String nome;
    private String whatsapp;
    private String status;
```

`private` quer dizer: **só o código dentro desta classe toca nisso.** De fora, `a.status`
nem compila mais. A porta fechou.

## Getters abrem uma janela de leitura

```java
    public String getNome() {
        return nome;
    }
```

Ler é inofensivo, então liberamos. O nome `getAlgo()` é **convenção**, não regra do Java —
mas o mundo inteiro usa, e o Spring depende dela pra funcionar. Siga.

## O setter é o segurança da porta

Aqui está o dia inteiro:

```java
    public boolean setStatus(String novo) {
        if (novo.equals("novo") || novo.equals("conversando")
                || novo.equals("fechado") || novo.equals("perdido")) {
            this.status = novo;
            return true;
        }
        return false;
    }
```

Três coisas pra reparar:

**1. Ele devolve `boolean`, não `void`.** Quem chamou fica sabendo se deu certo.
Setter que engole erro em silêncio é pior que não ter setter.

**2. Ele valida antes de aceitar.** Só quatro status existem. `"banana"` é recusado
e o objeto continua íntegro.

**3. `||` é "ou".** E ele tem curto-circuito: se o primeiro já for verdadeiro,
o Java nem testa os outros.

E repare que é `.equals()`, nunca `==`. Texto se compara por conteúdo.

---

## Saída esperada

```
Luna Toys (61993687124) - novo
--- tentando mudar ---
para fechado: true
Luna Toys (61993687124) - fechado
para banana: false
Luna Toys (61993687124) - fechado
--- lendo pelos getters ---
Nome: Luna Toys
Whats: 61993687124
Status: fechado
```

**Olhe as duas linhas do meio.** `para banana: false` e, logo abaixo, o status continua
`fechado`. O objeto recusou a besteira e **não se corrompeu**. Isso é encapsulamento
fazendo o trabalho dele.

## Fechar

```bash
jt teste
jt ok "dia06 encapsulamento"
```

## Prova do dia

1. O que acontece se alguém escrever `a.status = "banana"` agora?
2. Por que o `setStatus` devolve `boolean` em vez de `void`?
3. Por que `.equals()` e não `==`?
4. Em uma frase: pra que serve `private`?
