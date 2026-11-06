# Dia 14 — Spring Boot: o dia em que você para de dar `new`

Quarto dia seguido com **a mesma saída na tela**. Dia 12 mudou o build, dia 13
mudou o endereço, hoje muda **quem cria os objetos**.

Até ontem, quem criava era você:

```java
Repositorio repo = new RepositorioMemoria();
```

Hoje essa linha **não existe mais em lugar nenhum** — e o programa continua
funcionando. Quem passa a criar é o Spring. Esse é o dia inteiro.

---

## 1. O que é o Spring Boot, sem enrolação

O Spring é um **gerente de objetos**. Você diz quais classes são dele, e ele:

1. cria uma instância de cada uma, sozinho, quando o programa sobe
2. olha o que cada uma precisa e **entrega pronto**
3. guarda tudo num lugar chamado **contexto**

Isso tem nome: **injeção de dependência**. E é o motivo de existir Spring.

Parece pouco. Mas é o que permite, daqui a dois dias, trocar o
`RepositorioMemoria` por um `RepositorioPostgres` **sem mexer em nenhuma outra
linha do projeto**. Quem dá `new` fica preso na classe que escolheu. Quem pede
não fica.

---

## 2. O `pom.xml` ganhou um pai

Abre o `pom.xml`. Tem um bloco novo no topo:

```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>4.1.1</version>
</parent>
```

**Herança de `pom`.** Esse pai é um `pom.xml` que mora no servidor do Spring e
que já sabe a versão certa de cada uma das centenas de bibliotecas que o Spring
usa — todas testadas juntas.

Prova disso, logo abaixo:

```xml
<dependency>
  <groupId>org.apache.commons</groupId>
  <artifactId>commons-lang3</artifactId>
</dependency>
```

**Sem `<version>`.** No dia 12 você teve que escrever `3.17.0` na mão. Hoje não
precisa: o pai decide. É menos liberdade e muito menos dor de cabeça — escolher
versão de biblioteca errada é um dos jeitos mais chatos de perder um dia.

E apareceu uma pasta nova:

```
src/main/resources/application.properties
```

`resources` é onde mora o que **não é código**: configuração, texto, imagem.
O Maven leva tudo que está lá junto com as classes compiladas.

---

## 3. Leia primeiro: `Carteira.java`

**Esse arquivo já vem pronto. É o exemplo resolvido do dia — leia antes de
escrever qualquer coisa.** É todo o código que ontem estava dentro do `main`,
mudado de casa.

Três coisas pra reparar nele.

**A primeira — a classe é do Spring:**

```java
@Component
public class Carteira implements CommandLineRunner {
```

`@Component` quer dizer: *"Spring, essa classe é sua. Cria ela."*

**A segunda, e é o coração do dia — o construtor:**

```java
private final Repositorio repo;

public Carteira(Repositorio repo) {
    this.repo = repo;
}
```

Esse construtor **pede** um `Repositorio`. Ele não dá `new` em nada. Quando o
programa sobe, o Spring vê que a `Carteira` precisa de um `Repositorio`,
procura no contexto quem é capaz de ser um, e entrega.

Repara no tipo: `Repositorio`, a **interface**. Não `RepositorioMemoria`. A
`Carteira` não faz ideia de qual implementação ela recebeu — e não precisa
saber. É exatamente a separação que você construiu no dia 9 começando a pagar.

**A terceira — `CommandLineRunner`:**

```java
@Override
public void run(String... args) {
```

É uma interface do Spring com um método só. Quem implementa ela está dizendo:
*"quando você terminar de subir, me chama"*. Você não chama o `run()` em lugar
nenhum. O Spring chama.

---

## 4. O que você escreve hoje

Seis linhas, em dois arquivos. Procure por `TODO` neles.

### `Main.java` — TODO-1 e TODO-2

O arquivo hoje está assim:

```java
public class Main {
    public static void main(String[] args) {
    }
}
```

Compila. Roda. **E não faz absolutamente nada**, porque ninguém mandou o Spring
subir. Duas coisas entram:

**TODO-1 — a anotação, na linha de cima da classe:**

```java
@SpringBootApplication
```

Ela marca esta classe como a porta de entrada e, principalmente, manda o Spring
**varrer este pacote e todos os de baixo** procurando classes anotadas. É assim
que ele acha a `Carteira` e o `RepositorioMemoria` sem você listar nada.

> Por isso o `Main` fica em `br.com.betohardman.leads` e todo o resto em pastas
> **abaixo** dele. A estrutura do dia 13 não era organização por gosto.

**TODO-2 — a linha que liga, dentro do `main`:**

```java
SpringApplication.run(Main.class, args);
```

**E os dois imports**, no topo. Esses não dá pra deduzir, então estão aqui:

```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
```

### `RepositorioMemoria.java` — TODO-3

Uma anotação na linha de cima da classe:

```java
@Repository
```

e o import dela:

```java
import org.springframework.stereotype.Repository;
```

`@Repository` é irmã de `@Component` — faz a mesma coisa, com um nome que diz
*"esta é a classe que guarda dado"*. Spring tem uma para cada papel:
`@Component` (genérica), `@Repository` (dados), `@Service` (regra de negócio),
`@RestController` (fala HTTP — essa é a de amanhã).

---

## 5. Fechar o dia

```bash
jt teste dia14
```

A saída esperada é **idêntica** à do dia 13. Quarta vez. A tela não mudou; o
que mudou foi quem montou o que está nela.

> A primeira build vai demorar — o Maven está baixando o Spring inteiro pro
> `~/.m2`. Da segunda vez em diante é rápido.

Passou? `jt ok "dia14 Spring Boot e injecao de dependencia"`.

---

## 6. Antes de fechar, faz essa experiência

Vale mais que o exercício. Leva um minuto.

**Comenta o `@Repository`** do `RepositorioMemoria` (põe `//` na frente) e roda
`jt teste dia14` de novo.

Antes de rodar, **tenta prever**: vai dar erro de compilação, ou vai compilar e
quebrar na hora de rodar?

Pensa, depois roda, depois volta a descomentar.

O que você vai ver é a mensagem que mais aparece na vida de quem usa Spring:

```
***************************
APPLICATION FAILED TO START
***************************

Description:

Parameter 0 of constructor in br.com.betohardman.leads.Carteira required
a bean of type 'br.com.betohardman.leads.repositorio.Repositorio' that
could not be found.

Action:

Consider defining a bean of type
'br.com.betohardman.leads.repositorio.Repositorio' in your configuration.
```

Repara que o Spring separa **Description** (o que houve) de **Action** (o que
fazer). Quase todo erro de arranque dele vem nesse formato — vale ler as duas.

Traduzindo: *"a `Carteira` pediu um `Repositorio`, e eu não achei ninguém no
contexto capaz de ser um"*. Sem a anotação, o Spring nem sabe que a classe
existe.

Guarda essa mensagem. Você vai reencontrar ela muitas vezes, e a causa é quase
sempre a mesma: **faltou anotação, ou a classe está fora do pacote varrido.**

---

## 7. Amanhã

Dia 15: entra o `spring-boot-starter-web`, a `Carteira` vira `@RestController`,
e essa mesma saída para de aparecer no terminal e passa a aparecer **no
navegador**, em `GET /leads`.

Aí já é a API de leads. De verdade.
