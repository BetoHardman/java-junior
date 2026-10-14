# Dia 2 — jshell e os tipos

## Bloco 1 — o laboratório (15 min)

```bash
jt lab
```

Abriu o `jshell`. Aqui você digita e ele responde **na hora**, sem arquivo e sem compilar.
É onde você testa antes de escrever de verdade.

Digite um por um e **leia a resposta**:

```java
2 + 2
int x = 10
x * 3
double preco = 240.50
preco * 5
String nome = "Luna Toys"
nome.length()
nome.toUpperCase()
nome.contains("Toys")
boolean urgente = true
!urgente
10 / 3
10 / 3.0
(int) 9.99
"5" + 5
5 + 5
```

**Pare nas quatro últimas.** Elas são pegadinha de entrevista:

- `10 / 3` dá **3**, não 3.33. Dois inteiros → divisão inteira, o resto é jogado fora.
- `10 / 3.0` dá **3.333...** Um dos lados é `double` → o Java promove tudo pra `double`.
- `(int) 9.99` dá **9**. Casting pra `int` **corta**, não arredonda.
- `"5" + 5` dá **"55"**. Com String, o `+` vira concatenação. `5 + 5` dá 10.

Comandos do jshell:

```
/vars     lista suas variáveis
/list     mostra tudo que você digitou
/exit     sai
```

## Bloco 2 — escrever (25 min)

```bash
nano dias/dia02/Main.java
```

Declare estas cinco variáveis, **cada uma com o tipo certo**:

| variável | valor |
|---|---|
| `cliente` | `"Luna Toys"` |
| `secoes` | `5` |
| `valorPorSecao` | `240.50` |
| `urgente` | `true` |
| `total` | `secoes * valorPorSecao` |

Depois crie `totalInteiro`, que é o `total` convertido para inteiro **por casting**.

Imprima exatamente:

```
Cliente: Luna Toys
Secoes: 5
Total: 1202.5
Inteiro: 1202
Urgente: true
```

Dica: `System.out.println("Cliente: " + cliente);`

## Fechar

```bash
jt teste
jt ok "dia02 jshell e tipos"
```

## Prova do dia

Sem olhar, responda:
1. Por que `10 / 3` não dá 3.33?
2. `(int) 9.99` arredonda ou corta?
3. Quanto dá `"5" + 5`?
