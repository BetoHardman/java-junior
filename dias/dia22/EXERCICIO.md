# Dia 22 — banco de dados

Até ontem os seus leads moravam num `ArrayList`. Desligou o programa, acabaram.

Nenhuma landing page pode mandar lead pra uma API assim. Hoje entra banco.

E hoje o dia 14 cobra o juro: **o `Repositorio` é uma interface.** O serviço só
conhece ela. Então a troca acontece num canto só, e o resto do sistema não fica
sabendo.

> Eu rodei os dois e comparei: as respostas da API de hoje são **idênticas, byte
> a byte**, às do dia 21. Mesma API, armazenamento completamente trocado.

---

## 1. As duas dependências novas

**`spring-boot-starter-data-jpa`** traz o **Hibernate**: o tradutor que vira
objeto Java em linha de tabela e vice-versa. Sem ele você escreveria SQL na mão
e preencheria objeto campo a campo.

**`h2`** é o banco. Ele roda **dentro do próprio programa** — não tem servidor
pra instalar, não tem senha, não tem nada. Perfeito pra aprender.

```properties
spring.datasource.url=jdbc:h2:mem:leads
spring.jpa.hibernate.ddl-auto=create-drop
```

`ddl-auto=create-drop` quer dizer: **o Hibernate cria as tabelas sozinho** ao
subir, olhando as suas classes, e apaga ao desligar.

> **Sendo honesto com você:** `mem` quer dizer memória. Os dados ainda somem ao
> desligar. O que mudou hoje não foi a permanência — foi a **estrutura**: agora
> existe tabela, chave primária, SQL de verdade. Trocar por Postgres é mudar
> essa linha e a dependência. O código Java não muda.

---

## 2. `@Entity` — a classe vira tabela

```java
@Entity
public class Cliente {
```

Essa anotação diz ao Hibernate: *"crie uma tabela com o nome desta classe, e uma
coluna pra cada campo."* `nome`, `whatsapp`, `status` viram colunas.

### `@Id` e `@GeneratedValue`

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

`@Id` marca a **chave primária** — a coluna que identifica a linha.

`@GeneratedValue` muda quem gera o número. No dia 19 era o seu código:

```java
c.setId(proximoId);
proximoId = proximoId + 1;
```

Agora é **o banco**. E isso não é detalhe: com dois programas gravando ao mesmo
tempo, o seu contador em Java entregaria o mesmo número pros dois. O banco não.

### Duas coisas que já vêm prontas

**`@Inheritance`** no `Cliente`. Como `ClienteVip` herda dele, o Hibernate
precisa saber onde guardar os dois. `SINGLE_TABLE` põe na mesma tabela e cria
sozinho uma coluna `DTYPE` dizendo quem é quem.

**Os construtores `protected` vazios**, no `Cliente` e no `ClienteVip`. O
Hibernate monta o objeto vazio e depois preenche campo a campo com o que veio do
banco — pra isso precisa de um construtor sem argumento. `protected` porque
ninguém de fora deve usar.

---

## 3. A interface que escreve SQL sozinha

Abra o `repositorio/ClientesJpa.java`. Ela é uma **interface sem nenhuma
implementação** — e funciona.

```java
public interface ClientesJpa extends JpaRepository<Cliente, Long> {
}
```

Só com essa linha você já ganha `save`, `findById`, `findAll`, `count`,
`deleteById` e mais uns quinze. O Spring Data cria a classe em tempo de execução.

E tem a parte que parece mágica e não é:

```java
List<Cliente> findByStatus(String status);
```

**O Spring Data lê o nome do método e escreve o SQL.** Ele quebra em pedaços:

```
find  By  Status
 │     │    └── o campo "status" da entidade
 │     └─────── "onde"
 └───────────── "selecione"

→  SELECT * FROM cliente WHERE status = ?
```

Por isso o nome **não é livre**. `buscarPorStatus` não funcionaria: o Spring Data
não fala português. Tem que ser `findBy` + o nome do campo, com a primeira letra
maiúscula.

---

## 4. A peça-chave: `RepositorioBanco`

Olhe o começo do arquivo:

```java
@Repository
public class RepositorioBanco implements Repositorio {
```

**Implementa o mesmo `Repositorio` de sempre.** A interface não mudou uma letra
desde o dia 8. Por dentro, em vez de `ArrayList`, ela conversa com o banco.

E é por isso que o `ServicoDeLeads` e o `ControladorLeads` **não mudam**. Eles
pedem um `Repositorio`; o Spring agora entrega esta implementação em vez da
antiga.

> O `RepositorioMemoria` saiu do projeto hoje. Se duas classes anotadas com
> `@Repository` implementassem a mesma interface, o Spring não saberia qual
> entregar e se recusaria a subir. Ele continua no `dias/dia21` pra comparar.

### Um tipo novo: `Optional`

```java
return jpa.findById(id).orElse(null);
```

`findById` não devolve `Cliente` — devolve `Optional<Cliente>`, **uma caixinha
que pode estar vazia**. É o jeito do Java moderno dizer "pode não ter nada aqui"
sem usar `null` solto.

`orElse(null)` abre a caixinha: *"me dá o que tem dentro, ou `null` se estiver
vazia"*. Assim o resto do seu código, que já trata `null`, continua igual.

---

## 5. A pegadinha do dia: salvar não é opcional

Esta é a parte que todo mundo erra ao sair da memória, e eu tropecei nela
montando o exercício.

No dia 21, a `Carteira` fazia assim:

```java
repo.salvar(luna);
luna.mudarStatus("fechado");    // ← funcionava
```

Funcionava porque o `ArrayList` guardava **o mesmo objeto** que estava na mão.
Mexeu num, mexeu no outro.

**Com banco isso acaba.** Depois do `salvar`, o que você tem na mão é uma cópia;
a linha da tabela é outra coisa. Mudar o objeto não muda a tabela.

Por isso a `Carteira` mudou de ordem hoje — monta o objeto inteiro primeiro,
salva depois. E por isso o TODO-6 existe.

> **A régua:** em memória, alterar o objeto basta. Com banco, **alterou tem que
> salvar.**

---

## 6. O que você escreve hoje

Seis TODOs, em cinco arquivos. Todos curtos.

| TODO | Arquivo | O que é |
|---|---|---|
| 1 | `modelo/Cliente.java` | a anotação que vira tabela |
| 2 | `modelo/Cliente.java` | as duas do id |
| 3 | `modelo/ClienteVip.java` | a mesma do TODO-1 |
| 4 | `repositorio/ClientesJpa.java` | o método que vira SQL |
| 5 | `repositorio/RepositorioBanco.java` | dois corpos de uma linha |
| 6 | `servico/ServicoDeLeads.java` | a linha que salva |

Os imports já estão todos nos arquivos.

### Antes de escrever, roda

```bash
jt teste dia22
```

O aplicativo nem sobe, e no meio do texto está a causa:

```
Not a managed type: class br.com.betohardman.leads.modelo.Cliente
```

*"Tipo não gerenciado"*: o Hibernate foi procurar a tabela do `Cliente` e não
achou classe nenhuma marcada como entidade. É o TODO-1 faltando.

---

## 7. Fechar o dia

```bash
jt teste dia22
```

As mesmas sete requisições do dia 21, com as mesmas sete respostas. **A prova do
dia é essa: nada mudou por fora.**

Passou? `jt ok "dia22 banco de dados com JPA e H2"`.

---

## 8. Experimento de 2 minutos

Com tudo passando, comente a linha do TODO-6 — a do `repo.salvar(c)` no serviço:

```java
c.mudarStatus(novoStatus);
// repo.salvar(c);
return c;
```

Antes de rodar: o `PUT /leads/6` vai responder **200 ou erro**? E o `GET` do fim
vai mostrar o lead alterado?

Roda `jt teste dia22`.

O PUT responde **200**, com o lead já mudado no corpo. Parece perfeito. Mas o
`GET` do fim mostra o lead **do jeito que estava** — a alteração nunca chegou na
tabela.

Guarde essa: a resposta estava certa, o banco estava errado, e nada reclamou. É
o sexto erro mudo do curso, e o mais caro de todos — porque em produção você só
descobre quando o cliente liga.

Descomente antes de commitar.
