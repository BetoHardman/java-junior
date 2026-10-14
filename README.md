# Java Júnior pelo terminal — 14/10 a 30/11/2026

Substitui `roteiro-java-backend.md`. A avaliação do curso antigo está em
`AVALIACAO-DO-CURSO-ANTIGO.md`.

---

## As três regras

**1. 40 minutos por dia. Seis dias por semana. Domingo livre.**
Quarenta minutos você faz num dia ruim, depois de atender cliente e postar. Se der vontade de
continuar depois dos 40, continua. Mas aos 40 o dia já está ganho. O curso antigo pedia 2h e você
fez 2 dias — esse é o motivo de 40.

**2. Sem IDE até a semana 4.**
`javac`, `java`, `jshell` e um editor de texto no terminal. Quando o IntelliJ entrar na semana 4,
você vai saber o que ele está fazendo por baixo em vez de depender dele.

**3. Commit todo dia.**
`./jt ok` no fim de cada sessão. Não tem checkbox pra marcar — o GitHub mostra se você fez ou não.
Dia sem commit é dia que não aconteceu.

---

## Seu ambiente (já está instalado, conferi)

| | |
|---|---|
| JDK | Temurin 21.0.12 LTS ✅ |
| `javac` `java` `jshell` | ✅ em `C:\Program Files\Eclipse Adoptium\jdk-21...\bin` |
| Git | ✅ |
| Terminal | Git Bash |

Nada pra instalar. Começa hoje.

---

## O painel: `jt`

```bash
./jt novo       # cria a pasta do dia
./jt run        # compila e roda
./jt sh         # abre o jshell
./jt test       # confere a saída contra o esperado
./jt ok         # commita e manda pro GitHub
./jt hoje       # em que dia do plano você está
./jt streak     # seus commits dos últimos 14 dias
```

Coloque o atalho no `~/.bashrc` pra chamar de qualquer lugar:

```bash
echo "alias jt='/d/DELL\ TI\ CL/Claude/java-junior/jt'" >> ~/.bashrc && source ~/.bashrc
```

---

## Semana 1 (14–19/10) — O terminal e o Java cru

| Dia | Assunto | Entrega |
|---|---|---|
| 1 | Terminal: navegação, `Tab`, `Ctrl+R`, `!!`, `cd -`, pipes, `>`, `grep` | 20 comandos sem olhar |
| 2 | `javac` e `java` na mão. O que é bytecode, classpath, por que `Main.class` | compilar e rodar sem IDE |
| 3 | `jshell`: tipos primitivos, `var`, casting, `String` | testar 15 expressões |
| 4 | `if`, `switch` moderno, ternário, `for`, `while`, `for-each` | FizzBuzz no terminal |
| 5 | Métodos: assinatura, retorno, overloading. Arrays | calculadora de orçamento |
| 6 | Git: `init`, `add`, `commit`, `log`, `diff`, `push`. Repositório no GitHub | repo no ar |

**Marco da semana:** `github.com/seu-usuario/java-junior` existe e tem 6 commits.

---

## Semana 2 (20–26/10) — Orientação a objetos

| Dia | Assunto |
|---|---|
| 7 | Classe, objeto, atributo, construtor, `this` |
| 8 | `private`/`public`, getters e setters, `static`, `final` |
| 9 | Herança, `super`, `@Override` |
| 10 | `interface`, classe abstrata, polimorfismo |
| 11 | `equals()`, `hashCode()`, `toString()` — **cai em entrevista** |
| 12 | `enum`, `record`. Refazer a calculadora com classes |

**Marco:** a calculadora da semana 1 vira `Cliente`, `Projeto`, `Orcamento` + interface `Precificavel`.

---

## Semana 3 (27/10–02/11) — Coleções, erros e Java moderno

| Dia | Assunto |
|---|---|
| 13 | `List`, `ArrayList` vs `LinkedList` |
| 14 | `Map`, `HashMap` por dentro (bucket, colisão) — **cai muito** |
| 15 | `Set`, `Comparable` vs `Comparator`, ordenação |
| 16 | `try/catch/finally`, checked vs unchecked, exceção própria |
| 17 | `Optional` e o fim do `NullPointerException` |
| 18 | Lambdas e Streams: `filter`, `map`, `collect`. Noção de Big-O em 20 min |

**Marco:** lista de leads em memória com busca, filtro e ordenação.

---

## Semana 4 (03–09/11) — Maven, Spring Boot e a nuvem entra

| Dia | Assunto |
|---|---|
| 19 | Maven pelo terminal: `mvn archetype`, `pom.xml`, `mvn clean package` |
| 20 | Spring Initializr via `curl`. Projeto rodando com `mvn spring-boot:run` |
| 21 | `@RestController`, `@GetMapping`, `@PostMapping`. Testar com `curl`, não Postman |
| 22 | `@Service`, `@Repository`, injeção de dependência |
| 23 | DTO, `@RequestBody`, `@PathVariable`, status HTTP |
| 24 | IntelliJ entra — agora que você já sabe o que ele faz por baixo |

**Marco:** `curl -X POST localhost:8080/leads -d '{...}'` responde 201.

---

## Semana 5 (10–16/11) — Banco de dados

| Dia | Assunto |
|---|---|
| 25 | SQL no terminal: `SELECT`, `WHERE`, `JOIN`, `GROUP BY` |
| 26 | PostgreSQL local (ou H2 pra não instalar nada) |
| 27 | JPA: `@Entity`, `@Id`, `@GeneratedValue` |
| 28 | `JpaRepository` e os métodos de graça |
| 29 | `@OneToMany` / `@ManyToOne` |
| 30 | `application.properties`, variáveis de ambiente, segredo fora do código |

**Marco:** o lead sobrevive a reiniciar a aplicação.

---

## Semana 6 (17–23/11) — Teste e automação

| Dia | Assunto |
|---|---|
| 31 | JUnit 5: `@Test`, `assertEquals`, `assertThrows` |
| 32 | Mockito: mock de `@Service` |
| 33 | `@ControllerAdvice`, `@Valid`, `@NotBlank` |
| 34 | **GitHub Actions**: build e teste automáticos a cada push |
| 35 | Git de verdade: branch, pull request, revisão |
| 36 | README do projeto com rotas e exemplos de `curl` |

**Marco:** você dá `git push` e o GitHub roda os testes sozinho. Isso é a automação que você pediu.

---

## Semana 7 (24–30/11) — Nuvem e fechamento

| Dia | Assunto |
|---|---|
| 37 | Deploy no Railway ou Render pelo terminal |
| 38 | Variáveis de ambiente em produção, logs |
| 39 | Ligar as landing pages na API: o `QUERO MEU SITE` grava lead de verdade |
| 40 | Revisar e limpar o projeto inteiro |
| 41 | Treinar as 10 perguntas de entrevista em voz alta |
| 42 | LinkedIn e GitHub atualizados ✅ |

---

## O projeto: API de leads do Alon Warm

```
POST /leads          grava (nome, whatsapp, negócio, origem)
GET  /leads          lista
GET  /leads/{id}     busca um
PUT  /leads/{id}     muda status (novo / conversando / fechado / perdido)
GET  /leads/resumo   quantos por status e por origem
```

Hoje quem clica em "QUERO MEU SITE" vai pro WhatsApp e some. No fim de novembro, cada lead fica
registrado. Você estuda e ganha uma ferramenta no mesmo movimento — e na entrevista fala
"está rodando no meu negócio", não "fiz num curso".

---

## As 10 perguntas

Saber responder em voz alta, sem gaguejar:

1. `==` vs `.equals()`
2. Como o `HashMap` funciona por dentro
3. `ArrayList` vs `LinkedList` — quando usar cada um
4. O que é injeção de dependência e por que o Spring faz isso
5. Checked vs unchecked exception
6. Por que `String` é imutável
7. Classe abstrata vs interface
8. O que é uma API REST; o que significam 200, 201, 400, 404, 500
9. O que o `@Transactional` faz
10. Como você testaria o método que acabou de escrever

---

## Se você falhar um dia

Não recupera. Não faz 80 minutos no dia seguinte. Faz os 40 do dia seguinte e segue.

O que mata não é perder um dia — é a culpa de dever dois. Foi assim que o curso antigo morreu no
dia 2. `./jt hoje` te diz onde você está sem drama.
