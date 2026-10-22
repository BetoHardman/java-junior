# Dia 7 — herança e polimorfismo

Ontem você fechou o `Cliente`. Hoje ele vira **base** de outro: um cliente VIP, que é tudo
que o cliente comum é, **mais** um desconto.

**Três arquivos hoje.** `Cliente.java` e `Main.java` já estão prontos — **não mexa neles**.
Você escreve o `ClienteVip.java`.

```bash
nano dias/dia07/ClienteVip.java
```

---

## O que mudou no `Cliente` (repare antes de começar)

Os campos saíram de `private` para **`protected`**:

```java
    protected String nome;
```

| | Quem enxerga |
|---|---|
| `private` | só a própria classe |
| `protected` | a própria classe **e quem herda dela** |
| `public` | todo mundo |

Sem isso o `ClienteVip` não conseguiria usar os campos do pai.

E apareceu um método novo no `Cliente`:

```java
    public double valorHora() {
        return 120.0;
    }
```

## 1. O campo que só o VIP tem

```java
    private int desconto;
```

O VIP **já herda** `nome`, `whatsapp` e `status`. Não repete nenhum. Só acrescenta o que é dele.

## 2. O construtor e o `super(...)`

```java
    public ClienteVip(String nome, String whatsapp, int desconto) {
        super(nome, whatsapp);
        this.desconto = desconto;
    }
```

**`super(nome, whatsapp)` chama o construtor do pai.** Ele monta a parte "cliente comum" do
objeto, e só depois você preenche a parte VIP.

Regra do Java: **`super(...)` tem que ser a primeira linha do construtor.** Não é estilo, é
obrigatório — o pai precisa existir antes do filho.

## 3. Sobrescrever: `@Override`

```java
    @Override
    public double valorHora() {
        return super.valorHora() * (100 - desconto) / 100;
    }
```

O VIP **tem** um `valorHora()` herdado, mas quer um diferente. Escrever o método de novo com
a mesma assinatura **substitui** o do pai. Isso é sobrescrita.

Duas coisas:

- **`@Override` é opcional, mas sempre use.** Ele manda o compilador conferir se você está
  mesmo sobrescrevendo algo. Se errar o nome (`valorHoras`), ele acusa na hora. Sem a anotação,
  o Java criaria um método novo em silêncio e o do pai continuaria rodando — bug difícil de achar.
- **`super.valorHora()`** chama a versão do pai. Você não copia o 120.0: pede pro pai calcular
  e aplica o desconto em cima. No dia em que o valor base mudar, muda num lugar só.

## 4. Sobrescrever o resumo

```java
    @Override
    public String resumo() {
        return super.resumo() + " [VIP -" + desconto + "%]";
    }
```

Mesma ideia: aproveita o resumo do pai e **acrescenta** em vez de reescrever.

---

## Saída esperada

```
Luna Toys (61993687124) - novo
Bruna Severo (61999990000) - novo [VIP -20%]
Hora A: 120.0
Hora B: 96.0
--- polimorfismo ---
Luna Toys (61993687124) - novo -> 120.0
Bruna Severo (61999990000) - novo [VIP -20%] -> 96.0
```

## A parte que importa de verdade: o laço do fim

Olhe o `Main`:

```java
Cliente[] carteira = {a, b};
for (Cliente c : carteira) {
    System.out.println(c.resumo() + " -> " + c.valorHora());
}
```

O array é de **`Cliente`**. O laço trata os dois como cliente comum. E mesmo assim, o VIP
devolve **96.0** e o `[VIP -20%]`.

**Isso é polimorfismo.** O Java decide qual versão do método chamar olhando o objeto de
verdade, não o tipo da variável. Você escreve um código só e ele se comporta diferente
conforme a peça que chega.

É por isso que o Spring consegue trabalhar com objetos que ele nunca viu: ele programa contra
o tipo geral e cada peça responde do seu jeito.

## Fechar

```bash
jt teste
jt ok "dia07 heranca e polimorfismo"
```

## Prova do dia

1. Por que `super(...)` tem que ser a primeira linha do construtor?
2. O que o `@Override` faz de útil, se é opcional?
3. Por que o laço, que trata tudo como `Cliente`, imprime 96.0 para o VIP?
4. Diferença entre `private` e `protected`?
