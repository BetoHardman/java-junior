# Dia 13 — Pacotes: o seu código ganha endereço

Terceiro dia seguido com **o mesmo código**. Dia 12 ele mudou de pasta; hoje ele
ganha **endereço**.

`Main.java` já está pronto e é o seu exemplo resolvido — **leia ele primeiro.**
Nos outros quatro você escreve 4 linhas de `package` e 2 de `import`. Nada mais.

---

## 1. O que mudou na pasta

```
src/main/java/
└── br/com/betohardman/leads/
    ├── Main.java
    ├── modelo/
    │   ├── Cliente.java          <- o que E dado
    │   └── ClienteVip.java
    └── repositorio/
        ├── Repositorio.java      <- o que GUARDA dado
        └── RepositorioMemoria.java
```

Os cinco arquivos de sempre, separados por responsabilidade. Isso não é organização
por gosto: **é o desenho da API de leads começando a aparecer.** Daqui a duas semanas
entra uma pasta `controller` ao lado, e é ela que vai falar HTTP.

## 2. `package` — a linha que diz onde o arquivo mora

Abre o `Main.java`. A primeira linha é:

```java
package br.com.betohardman.leads;
```

**Lê ela ao lado do caminho da pasta:**

```
pasta:    src/main/java/ br/com/betohardman/leads /Main.java
package:                 br.com.betohardman.leads
```

É a mesma coisa, com ponto no lugar da barra. **A linha `package` tem que bater
exatamente com a pasta.** Não é escolha: é uma declaração de endereço, e se ela
mentir o compilador recusa.

E `package` vem **antes de qualquer import**, sempre na primeira linha útil do arquivo.

## 3. Rode antes de escrever

```bash
jt teste
```

Vai dar um erro comprido. Procura esta parte:

```
bad source file: ...\modelo\Cliente.java
  file does not contain class br.com.betohardman.leads.modelo.Cliente
  Please remove or make sure it appears in the correct subdirectory
```

Em português: *"o arquivo está na pasta `modelo`, mas lá dentro ele não diz que
pertence ao pacote `modelo`."* A pasta diz uma coisa, o arquivo diz outra.

Tem um `duplicate class: Cliente` junto. Esse é consequência: sem a linha `package`,
o `Cliente` cai no **pacote padrão** — e aí o compilador enxerga dois `Cliente`
diferentes, um sem endereço e um que o `Main` pediu com endereço.

É isso que você vai consertar.

## 4. As quatro linhas de `package`

Primeira linha de cada arquivo, batendo com a pasta onde ele está:

| Arquivo | Pasta | O que escrever |
|---|---|---|
| `Cliente.java` | `modelo` | `package br.com.betohardman.leads.modelo;` |
| `ClienteVip.java` | `modelo` | a mesma |
| `Repositorio.java` | `repositorio` | `package br.com.betohardman.leads.repositorio;` |
| `RepositorioMemoria.java` | `repositorio` | a mesma |

Repara: **dois arquivos na mesma pasta têm o mesmo `package`.** Pacote é a pasta,
não o arquivo.

## 5. As duas linhas de `import` — e aqui está o dia

`Repositorio.java` e `RepositorioMemoria.java` falam de `Cliente` o tempo todo. Mas
agora o `Cliente` mora em **outro pacote**. Então os dois precisam de:

```java
import br.com.betohardman.leads.modelo.Cliente;
```

Logo depois da linha `package`, junto dos outros imports.

**Essa é a virada do dia.** Até ontem `import` era pra coisa dos outros:

```java
import java.util.List;                           // do Java
import org.apache.commons.lang3.StringUtils;     // do commons-lang3
import br.com.betohardman.leads.modelo.Cliente;  // SEU
```

As três linhas são a mesma coisa. **`import` é endereço, e agora o seu código tem
endereço** — exatamente igual ao de qualquer biblioteca do mundo. Olha os imports do
`Main.java`: tem Java, tem biblioteca de terceiro e tem código seu, lado a lado.

### E o `ClienteVip`, por que não precisa de import?

Ele usa `Cliente` (faz `extends Cliente`) e **não** importa nada. Porque os dois estão
na **mesma pasta**, logo no mesmo pacote — e dentro de um pacote todo mundo se enxerga
sem apresentação. Import só é preciso pra atravessar a fronteira.

## 6. Por que isso existe

Dois motivos práticos:

**Nome repetido.** `Cliente` é um nome que meio mundo usa. Com pacote, o seu é
`br.com.betohardman.leads.modelo.Cliente` e o de outra biblioteca é outro. Não colidem.

**Fronteira de verdade.** Dentro de um pacote todo mundo se enxerga. Entre pacotes,
só o que é `public` atravessa. A partir de hoje `public` e `private` deixam de ser
enfeite e viram parede — e é isso que faz código grande não virar macarrão.

> Repara que `Cliente` guarda `protected String nome`. O `ClienteVip` continua
> alcançando esses campos porque está no mesmo pacote. Se você mudasse ele de pasta,
> pararia de compilar. Essa é a parede funcionando.

## 7. Por que o Spring exige isso

O Spring Boot acha os seus controllers **varrendo o pacote da classe principal pra
baixo**. O `Main` em `br.com.betohardman.leads`, tudo que importa embaixo dele.

Se um controller ficar fora dessa árvore, o Spring não acha, e você ganha um `404`
sem nenhum erro explicando. É o tropeço número 1 de quem começa em Spring, e em duas
semanas você vai estar lá.

---

## Saída esperada

A mesma do dia 12, sem tirar nem pôr:

```
========== CARTEIRA ==========
novo........1
contato.....2
fechado.....2
...
```

**O programa não mudou.** Mudou o endereço de cada peça.

## Fechar

```bash
jt teste
jt ok "dia13 pacotes e organizacao"
```

## Prova do dia

1. Qual a relação entre a linha `package` e a pasta onde o arquivo está?
2. Por que `ClienteVip` não precisa importar `Cliente`, mas `Repositorio` precisa?
3. O que `import java.util.List` e `import ...leads.modelo.Cliente` têm em comum?
4. O que o `duplicate class: Cliente` queria dizer?
5. Por que o Spring Boot se importa com onde a sua classe principal mora?
