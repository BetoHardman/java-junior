# Plano — Java Júnior até 30/11/2026

Início: **14/10/2026** · Fim: **30/11/2026** · 7 semanas

---

## Antes de tudo: o recado honesto

Sete semanas dá pra chegar em júnior **porque você já programa**. Você já entende HTML, CSS,
JavaScript, deploy, domínio, HTTP. Isso é metade do caminho e a maioria de quem começa não tem.

O que você **não** tem ainda é o que separa quem faz site de quem é contratado como dev Java:
tipagem estática, orientação a objetos de verdade, Spring Boot, banco relacional e teste automatizado.

Sete semanas **não** dá pra: virar sênior, decorar algoritmo de entrevista de big tech, ou
aprender Java "inteiro". E tudo bem. Júnior não é saber tudo. Júnior é **conseguir entregar uma
tarefa pequena sem travar e sem quebrar o resto.**

O risco real não é o conteúdo ser difícil. É você ter duas contas de conteúdo rodando, clientes
ativos e sumir do plano na semana 3. Por isso o plano é curto por dia e tem um projeto só.

---

## Orçamento de tempo

**1h30 por dia, 6 dias por semana.** Domingo livre.

Sugestão de encaixe na sua rotina: **antes** da produção de conteúdo, não depois. Depois você
já gastou a cabeça. Bloco único, sem celular na mesa.

| | |
|---|---|
| Por dia | 1h30 |
| Por semana | 9h |
| Total até 30/11 | ~63h |

Regra: **45 min lendo/assistindo, 45 min escrevendo código.** Se um dia só der 30 min, faça os
30 min de código. Teoria sem teclado não fixa.

---

## Semana 1 (14 a 19/10) — Java em pé

**Meta:** escrever, compilar e rodar Java sem olhar tutorial.

- Instalar JDK 21 (LTS) + IntelliJ IDEA Community (grátis)
- Tipos primitivos, `String`, `var`, casting
- `if`, `switch`, `for`, `while`, arrays
- Métodos: parâmetros, retorno, sobrecarga
- `System.out.printf`, leitura com `Scanner`

**Exercício da semana:** calculadora de orçamento de site no terminal. Recebe tipo de página,
número de seções e prazo, devolve o valor. Sem interface, só console.

**Por que isso primeiro:** JavaScript te deixou mal acostumado com tipo. Java reclama na
compilação, e é justamente isso que você precisa sentir.

---

## Semana 2 (20 a 26/10) — Orientação a objetos

**Meta:** parar de pensar em funções soltas e começar a pensar em objetos.

- Classe, objeto, atributo, método, construtor
- `private` / `public`, getters e setters, encapsulamento
- Herança, `interface`, polimorfismo, classe abstrata
- `equals`, `hashCode`, `toString`
- `enum`

**Exercício:** refaz a calculadora da semana 1 com classes: `Cliente`, `Projeto`, `Orcamento`,
interface `Precificavel`. O mesmo programa, estruturado.

**Armadilha:** não decore a definição de polimorfismo. Escreva duas classes que implementam a
mesma interface e veja o código chamar as duas sem saber qual é qual.

---

## Semana 3 (27/10 a 02/11) — Coleções, exceções e Optional

**Meta:** manipular dados do jeito que todo código real faz.

- `List`, `ArrayList`, `Map`, `HashMap`, `Set`
- `for each`, iteração, ordenação com `Comparator`
- Stream API: `filter`, `map`, `collect` (o básico, sem exagero)
- `try / catch / finally`, exceções checadas vs não checadas, exceção própria
- `Optional` e por que `null` é problema

**Exercício:** lista de clientes em memória com busca, filtro por cidade e ordenação por valor.

---

## Semana 4 (03 a 09/11) — Spring Boot e a primeira API

**Meta:** subir uma API que responde de verdade.

- Maven: o que é `pom.xml` e por que existe
- Spring Initializr, estrutura de projeto
- `@RestController`, `@GetMapping`, `@PostMapping`
- `@Service`, `@Repository`, injeção de dependência
- DTO, `@RequestBody`, `@PathVariable`
- Testar com Postman ou Insomnia

**Marco:** no fim desta semana você tem um `GET /leads` e um `POST /leads` funcionando em
`localhost:8080`. Guarda esse dia.

---

## Semana 5 (10 a 16/11) — Banco de dados

**Meta:** os dados pararem de sumir quando você desliga o programa.

- SQL básico: `SELECT`, `INSERT`, `UPDATE`, `DELETE`, `WHERE`, `JOIN`
- PostgreSQL local (ou H2 para começar sem instalar nada)
- JPA / Hibernate: `@Entity`, `@Id`, `@GeneratedValue`
- `JpaRepository` e os métodos que ele te dá de graça
- Relacionamento `@OneToMany` / `@ManyToOne`
- `application.properties`: string de conexão

**Exercício:** a API da semana 4 passa a gravar em banco.

---

## Semana 6 (17 a 23/11) — Teste, Git e qualidade

**Meta:** escrever código que outra pessoa aceita receber.

- JUnit 5: `@Test`, `assertEquals`, `assertThrows`
- Mockito: o básico de mock em `@Service`
- Git de verdade: branch, commit com mensagem decente, pull request
- Tratamento global de erro com `@ControllerAdvice`
- Validação com `@Valid`, `@NotBlank`, `@Email`
- README do projeto

**Verdade do mercado:** muito júnior sabe fazer API. Pouco júnior entrega **com teste**. É aqui
que você passa na frente.

---

## Semana 7 (24 a 30/11) — Fechar e provar

**Meta:** ter o que mostrar.

- Deploy da API (Railway ou Render têm plano gratuito)
- README com print, instruções e descrição das rotas
- Revisar o projeto inteiro e limpar
- Preparar as respostas das perguntas de entrevista (lista abaixo)
- Atualizar LinkedIn e GitHub

---

## O projeto único: API de leads do Alon Warm

Um projeto real vale mais que cinco tutoriais no GitHub.

**O que é:** a API que recebe os leads das suas próprias landing pages. Hoje a pessoa clica em
"QUERO MEU SITE" e vai pro WhatsApp e some. Com a API, cada lead fica registrado.

```
POST /leads          grava um lead (nome, whatsapp, negócio, origem)
GET  /leads          lista todos
GET  /leads/{id}     busca um
PUT  /leads/{id}     muda o status (novo / conversando / fechado / perdido)
GET  /leads/resumo   quantos por status, quantos por origem
```

**Por que esse e não outro:**
1. É seu problema de verdade, então você não abandona na metade.
2. Na entrevista você diz "fiz pro meu negócio e está rodando", não "fiz num curso".
3. Conecta com o que você já sabe: as landing pages passam a mandar dados pra ele.
4. No fim você tem uma ferramenta que te dá dinheiro, não só uma nota.

---

## O que te perguntam numa entrevista de júnior

Saiba responder estas, em voz alta, sem gaguejar:

1. Diferença entre `==` e `.equals()`
2. O que é injeção de dependência e por que o Spring faz isso
3. `List` vs `Set` vs `Map` — quando usar cada um
4. O que acontece se você não tratar uma exceção
5. O que é uma API REST e o que significam 200, 201, 400, 404 e 500
6. Para que serve uma interface se ela não tem código
7. O que é `null pointer exception` e como evitar
8. Diferença entre classe abstrata e interface
9. O que o `@Transactional` faz
10. Como você testaria o método que você acabou de escrever

---

## Recursos gratuitos

**Em português**
- Maratona Java (DevDojo) — curso completo no YouTube, é a referência gratuita em PT-BR
- Documentação do Spring traduzida pela comunidade

**Em inglês**
- `dev.java` — tutoriais oficiais da Oracle
- `spring.io/guides` — guias curtos e oficiais, cada um resolve uma coisa
- Baeldung — quando travar em algo específico, a resposta boa costuma estar lá
- Exercism (trilha Java) — exercícios com correção de mentor, de graça

**Ferramentas (todas gratuitas)**
JDK 21 · IntelliJ IDEA Community · PostgreSQL · Insomnia · Git · GitHub · Railway

---

## Como saber se está funcionando

Checkpoint toda sexta. Pergunta única: **"eu consigo escrever isso do zero sem copiar?"**

Se a resposta for não, repete o assunto no sábado em vez de avançar. É melhor chegar em
30/11 com 5 semanas sólidas do que com 7 semanas rasas.
