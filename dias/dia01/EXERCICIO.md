# Dia 1 — O terminal e o primeiro compilado

## Parte 1 — Drill de terminal (15 min)

Faça cada um e confira a saída. Sem copiar e colar: **digite**.

1. `pwd` — onde você está
2. `ls -la` — tudo, inclusive oculto
3. `mkdir -p teste/a/b` e depois `cd teste/a/b`
4. `cd -` — volta pra pasta anterior. Agora `cd -` de novo.
5. `cd ~` e depois `cd !$`  (o `!$` repete o último argumento)
6. `history | grep mkdir`
7. **`Ctrl+R`** e digite `mkdir` — ache o comando sem digitar de novo. Enter.
8. `ls -la > lista.txt` e depois `cat lista.txt`
9. `grep -rn "class" .` dentro da pasta java-junior
10. `rm -rf teste lista.txt`

Ao terminar, responda em voz alta: **o que o `|` faz?**

## Parte 2 — Java sem IDE (25 min)

Edite `Main.java` nesta pasta para imprimir exatamente:

```
Roberto
java junior
40 minutos por dia
```

Depois, **na mão**, sem o `jt`:

```bash
javac -d ../../.build Main.java
java -cp ../../.build Main
```

Entenda o que cada parte faz:
- `javac` gera `Main.class` — **bytecode**, não é código de máquina
- `-d` diz onde colocar o `.class`
- `java` chama a JVM, que lê o bytecode
- `-cp` (classpath) diz à JVM onde procurar as classes

Agora confira com o painel:

```bash
./jt test
./jt ok "dia01 terminal e primeiro compilado"
```

## Prova do dia

Sem olhar: qual a diferença entre `javac` e `java`? E o que é bytecode?
