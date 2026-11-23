# Dia 24 — teste de verdade

Até hoje, "testar" no curso era o `jt teste`: rodar o programa, pegar o que ele
imprimiu e comparar com o `esperado.txt`.

Funciona. Mas é **ferramenta caseira** — eu escrevi, só existe aqui, e nenhuma
empresa do mundo usa. Num repositório que recrutador abre, a pergunta é outra:
*"cadê os testes?"*

Hoje entram os de verdade: JUnit, rodando com `mvn test`, do jeito que todo
projeto Java faz.

---

## 1. O que muda

| | `jt teste` (até ontem) | `mvn test` (hoje) |
|---|---|---|
| Quem escreveu | eu | **você** |
| O que confere | o texto que saiu na tela | **cada regra, uma por uma** |
| Quando falha, diz | "a saída está diferente" | **qual regra quebrou e o que veio no lugar** |
| Roda onde | só na sua máquina | em qualquer máquina, em qualquer servidor |

A última linha importa mais do que parece. Teste que roda sozinho é o que
permite mexer no código sem medo: você muda uma coisa, roda, e em 10 segundos
sabe se quebrou alguma das outras.

---

## 2. `@SpringBootTest` — sobe a aplicação inteira

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
```

Essa anotação faz, antes dos testes, **exatamente o que o `jt rodar` faz**: liga
o Spring, cria todos os objetos, conecta no banco, sobe o servidor. A diferença
é que no fim ela desliga tudo sozinha.

`RANDOM_PORT` manda subir numa porta **sorteada**, não na 8080. O motivo é
prático: se você estiver com um `jt rodar` aberto noutra janela, a 8080 está
ocupada e o teste quebraria sem culpa nenhuma do seu código.

> Isso me aconteceu enquanto eu montava este dia: rodei os testes e, logo
> depois, o teste do dia 22 falhou — a porta ainda não tinha sido liberada.
> Rodando de novo, passou. Por isso o `RANDOM_PORT` existe.

### E a porta sorteada, como você descobre?

```java
@LocalServerPort
int porta;
```

O Spring preenche esse campo com o número que ele sorteou. É o mesmo mecanismo
de injeção do dia 14 — você declara o que precisa, ele entrega.

---

## 3. O banco do teste é outro, e isso não é detalhe

Abra `src/test/resources/application.properties`. Ele existe só pro teste e
sobrescreve o de produção:

```properties
spring.datasource.url=jdbc:h2:mem:teste
spring.jpa.hibernate.ddl-auto=create-drop
```

**Em memória, criado e apagado a cada execução.**

Pensa no que aconteceria sem isso: o teste usaria o banco em arquivo de ontem,
encontraria os leads que você gravou brincando, e o `listar_devolve_os_cinco`
falharia porque tem seis. Pior: passaria hoje e falharia amanhã, sem você mudar
nada.

É a mesma regra do dia 23, agora do lado de dentro: **teste parte sempre do
mesmo estado.** Em empresa isso é lei, e é por isso que existe banco separado
pra teste em todo lugar que você vai trabalhar.

---

## 4. Anatomia de um teste

```java
@Test
void listar_devolve_os_cinco_leads_da_carteira() throws Exception {
    HttpResponse<String> r = get("/leads");                    // 1. faz

    assertEquals(200, r.statusCode());                          // 2. confere
    assertTrue(r.body().contains("Luna Toys"), "esperava...");  // 3. confere
}
```

Três partes: **faz**, e depois **confere** uma coisa por linha.

| Peça | Para que serve |
|---|---|
| `@Test` | marca o método como teste. Sem ela, o JUnit ignora |
| `void`, sem parâmetro | teste não devolve nem recebe nada |
| `assertEquals(esperado, recebido)` | falha se forem diferentes |
| `assertTrue(condicao, "mensagem")` | falha se for falso, e mostra a mensagem |

**O nome do método é documentação.** `listar_devolve_os_cinco_leads_da_carteira`
diz o que o sistema promete. Quando um teste assim fica vermelho, você lê o nome
e já sabe o que quebrou, sem abrir o código.

> As duas ajudantes `get()` e `post()` já estão prontas no arquivo. Elas usam o
> cliente HTTP **do próprio Java** — sem biblioteca nenhuma. O teste bate no seu
> servidor por HTTP de verdade, igual ao navegador.

---

## 5. O que você escreve hoje

Cinco TODOs, todos em
`src/test/java/br/com/betohardman/leads/web/ControladorLeadsTest.java`.

| TODO | O que é |
|---|---|
| 1 | a anotação que sobe a aplicação |
| 2 | a anotação da porta |
| 3 | teste: `GET /leads/999` devolve 404 |
| 4 | teste: `POST` válido devolve 200 |
| 5 | teste: `POST` sem whatsapp devolve 400 |

**O primeiro teste vem pronto**, como molde dos outros três. Os imports também —
todos já estão no topo.

### Antes de escrever, roda

```bash
jt teste dia24
```

```
Tests run: 1, Failures: 0, Errors: 1, Skipped: 0
```

Um teste, um erro. Sem o `@SpringBootTest` do TODO-1, a aplicação não sobe — e
sem aplicação no ar, não há o que chamar.

> **O `jt teste` mudou hoje.** Quando a pasta do dia tem `src/test/java`, ele
> não sobe servidor nem compara tela: roda `mvn test` e compara o **placar do
> JUnit**. É por isso que o `esperado.txt` de hoje tem uma linha só.

---

## 6. Fechar o dia

```bash
jt teste dia24
```

Alvo:

```
Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
```

Quatro testes, zero falha. E repare no que esses quatro cobrem: leitura, erro
404, gravação e validação — as quatro coisas que você construiu do dia 15 ao 23.

Passou? `jt ok "dia24 testes automatizados com @SpringBootTest"`.

---

## 7. O experimento de hoje é o melhor do curso

É aqui que teste automatizado deixa de ser teoria.

Vá no `NovoLead.java` e **comente o `@NotBlank` do whatsapp**:

```java
// @NotBlank(message = "whatsapp e obrigatorio")
@Pattern(regexp = "[0-9]{11}", message = "whatsapp precisa ter 11 digitos")
String whatsapp) {
```

Agora rode `jt teste dia24`.

Três testes continuam verdes. **Um fica vermelho** — e ele diz exatamente qual
promessa o sistema deixou de cumprir. Esta é a saída real, eu rodei:

```
Tests run: 4, Failures: 1, Errors: 0, Skipped: 0

ControladorLeadsTest.criar_lead_sem_whatsapp_devolve_400  <<< FAILURE!
org.opentest4j.AssertionFailedError: expected: <400> but was: <200>
```

Leia a última linha: *esperava 400, veio 200*. Ele não diz só "quebrou" — diz
**o que você prometeu e o que o sistema entregou no lugar**.

Repare no que acabou de acontecer: você quebrou uma regra escrita há sete dias,
num arquivo diferente, e **soube em dez segundos**. Sem abrir navegador, sem
`curl`, sem lembrar que aquela regra existia.

Isso é o que teste compra. Não é "garantir que funciona" — é **poder mexer no
código sem medo**, porque se você quebrar alguma coisa, alguém te avisa na hora.

Descomente antes de commitar.
