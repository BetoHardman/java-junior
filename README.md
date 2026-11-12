# Java do zero, pelo terminal

Aprendendo Java backend sem IDE: `javac`, `java`, `jshell` e um editor de texto no terminal.
Um exercício por dia, um commit por dia.

**Início:** 14/10/2026 · **Meta:** Spring Boot e uma API em produção até 30/11/2026

---

## Por que sem IDE

Porque o IDE esconde o que importa no começo. Compilando na mão você descobre o que é
bytecode, o que a JVM faz, por que existe classpath e como ler um erro de compilador de
verdade — incluindo que **um erro gera três**, e que consertar o primeiro mata os outros.

O IntelliJ entra depois, quando digitar deixa de ensinar e passa a atrasar.

---

## O que já está aqui

| Dia | Assunto | Arquivo |
|-----|---------|---------|
| 01 | Terminal, `javac`, `java`, bytecode e JVM | [dias/dia01](dias/dia01) |
| 02 | `jshell`, tipos primitivos, casting | [dias/dia02](dias/dia02) |
| 03 | `if`, `switch` moderno, ternário, laços | [dias/dia03](dias/dia03) |
| 04 | Métodos, assinatura, overloading, arrays | [dias/dia04](dias/dia04) |
| 05 | Classes, objetos, construtor, `this` | [dias/dia05](dias/dia05) |
| 06 | Encapsulamento: `private`, getters, setter com validação | [dias/dia06](dias/dia06) |
| 07 | Herança, `super`, `@Override`, polimorfismo | [dias/dia07](dias/dia07) |
| 08 | Interface: contrato, `implements`, programar contra o tipo | [dias/dia08](dias/dia08) |
| 09 | `ArrayList`, generics `List<Cliente>`, `import` | [dias/dia09](dias/dia09) |
| 10 | Buscar na lista: `for` + `if`, `.equals()` vs `==` | [dias/dia10](dias/dia10) |
| 11 | `Map`, `HashMap`, `getOrDefault` · primeiro dia no IntelliJ | [dias/dia11](dias/dia11) |
| 12 | Maven: `pom.xml`, `src/main/java`, dependências | [dias/dia12](dias/dia12) |
| 13 | Pacotes: `package`, importar o próprio código, fronteira | [dias/dia13](dias/dia13) |
| 14 | Spring Boot: `@SpringBootApplication`, `@Component`, injeção de dependência | [dias/dia14](dias/dia14) |
| 15 | API REST: `@RestController`, `@GetMapping`, JSON e getters | [dias/dia15](dias/dia15) |
| 16 | `POST /leads`: `@RequestBody`, `record` como DTO | [dias/dia16](dias/dia16) |
| 17 | Validação: `@NotBlank`, `@Pattern`, `@Valid` e o 400 | [dias/dia17](dias/dia17) |
| 18 | Erros com mensagem: `@RestControllerAdvice` e `@ExceptionHandler` | [dias/dia18](dias/dia18) |
| 19 | `PUT /leads/{id}`: `@PathVariable`, exceção própria e 404 | [dias/dia19](dias/dia19) |
| 20 | Resumo, filtro com `@RequestParam` e busca por id | [dias/dia20](dias/dia20) |
| 21 | Camada de serviço: `@Service` e separação de responsabilidades | [dias/dia21](dias/dia21) |

Cada pasta tem o enunciado (`EXERCICIO.md`), o código e a saída esperada
(`esperado.txt`) usada como teste.

---

## O painel `jt`

Script em bash que automatiza o ciclo do dia:

```bash
./jt novo        # cria a pasta do dia
./jt rodar       # compila e roda
./jt lab         # abre o jshell
./jt teste       # compara a saída com a esperada
./jt ok "msg"    # commita e envia pro GitHub
./jt hoje        # em que dia do plano estou
./jt streak      # commits por dia
```

O `jt teste` normaliza fim de linha antes de comparar — no Windows o Java gera `\r\n`
e o arquivo esperado tem `\n`, o que fazia o teste reprovar código correto.

Tudo que o painel executa fica gravado em `.jt-log/sessao.log`.

---

## O destino

O projeto final é uma **API de leads em Spring Boot**: recebe os contatos que chegam pelas
landing pages que eu construo, grava em banco e acompanha o status de cada um.

Problema real, de um negócio que existe — não exercício de curso.

```
POST /leads          grava um lead
GET  /leads          lista
PUT  /leads/{id}     muda o status
GET  /leads/resumo   quantos por status e por origem
```

---

## Rodando

```bash
git clone https://github.com/BetoHardman/java-junior.git
cd java-junior
./jt teste dia06
```

Requer JDK 21.

---

## O resto

Anotações de terminal, atalhos e armadilhas do Java em [CHEATSHEET.md](CHEATSHEET.md).
