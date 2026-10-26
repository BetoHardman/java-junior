# Cheatsheet — terminal e Java cru

Imprime ou deixa aberto numa aba. A regra é: **decorar pelo uso, não pela leitura.**
Todo dia, antes dos 40 minutos, escolhe 3 atalhos daqui e usa de propósito.

---

## 1. Terminal — os que economizam mais tempo

| Atalho | O que faz | Por que importa |
|---|---|---|
| `Tab` | completa nome de arquivo/pasta | nunca mais digitar nome inteiro |
| `Tab` `Tab` | lista as opções | descobre sem `ls` |
| `Ctrl+R` | busca no histórico | **o mais importante da lista** — digita 3 letras e acha o comando de ontem |
| `!!` | repete o último comando | `sudo !!` |
| `!$` | último argumento do comando anterior | `mkdir dia07` → `cd !$` |
| `cd -` | volta pra pasta anterior | alterna entre duas pastas |
| `Ctrl+A` / `Ctrl+E` | início / fim da linha | parar de segurar seta |
| `Ctrl+W` | apaga a palavra anterior | |
| `Ctrl+U` | apaga a linha toda | errou? limpa e recomeça |
| `Ctrl+L` | limpa a tela | igual `clear`, mais rápido |
| `Ctrl+C` | mata o processo | |
| `Ctrl+D` | sai do shell / fecha entrada | |

## 2. Terminal — navegação e arquivos

```bash
pwd                 # onde estou
ls -la              # lista tudo, inclusive oculto
cd ..               # sobe um nível
cd ~                # vai pra home
mkdir -p a/b/c      # cria a árvore inteira
cp -r origem destino
mv velho novo       # move e renomeia (é o mesmo comando)
rm -rf pasta        # apaga sem perguntar — cuidado
find . -name "*.java"
grep -rn "public class" .     # procura dentro dos arquivos
cat arquivo         # mostra
head -20 arquivo    # 20 primeiras linhas
tail -f log.txt     # acompanha ao vivo
wc -l arquivo       # conta linhas
```

## 3. Terminal — encanamento (pipes e redirecionamento)

```bash
comando > arquivo       # grava a saída (sobrescreve)
comando >> arquivo      # acrescenta no fim
comando 2> erro.txt     # grava só os erros
comando | grep algo     # manda a saída pro próximo comando
ls -la | grep java | wc -l
history | grep javac
```

**O pipe (`|`) é a ideia mais importante do terminal:** cada programa faz uma coisa e passa o
resultado adiante. É o mesmo raciocínio das Streams do Java — `filter` → `map` → `collect`.

---

## 4. Java sem IDE

```bash
javac Main.java              # compila → gera Main.class
java Main                    # roda (sem o .class no nome)
javac -d build *.java        # compila tudo pra pasta build
java -cp build Main          # roda usando build como classpath
java Main.java               # compila e roda de uma vez (Java 11+)
jshell                       # laboratório interativo
javap -c Main                # mostra o bytecode — vale ver uma vez
```

### jshell (seu laboratório)

```
jshell> int x = 10
jshell> x * 2
jshell> "texto".toUpperCase()
jshell> /vars        lista as variáveis
jshell> /list        mostra o que você digitou
jshell> /edit        abre editor
jshell> /exit        sai
```

Use o jshell pra testar **antes** de escrever no arquivo. Economiza o ciclo compilar-rodar-errar.

---

## 5. Java — abreviações e sinais que confundem

| Escrita | Significa |
|---|---|
| `i++` | usa o valor, depois soma 1 |
| `++i` | soma 1, depois usa |
| `x += 5` | `x = x + 5` |
| `a ? b : c` | se `a`, então `b`, senão `c` |
| `&&` `\|\|` | e / ou (com curto-circuito: para assim que já sabe o resultado) |
| `==` | mesma **referência** (mesmo objeto na memória) |
| `.equals()` | mesmo **conteúdo** — é o que você quer em 95% dos casos |
| `final` | não pode ser reatribuído |
| `static` | pertence à classe, não ao objeto |
| `void` | não devolve nada |
| `null` | referência que não aponta pra nada |
| `->` | lambda: `x -> x * 2` |
| `::` | referência a método: `String::toUpperCase` |
| `<T>` | genérico: "um tipo qualquer, decidido no uso" |
| `@` | anotação: metadado lido pelo compilador ou pelo Spring |

### A pegadinha número 1

```java
String a = "oi";
String b = "oi";
a == b          // true  — pool de strings, mesma referência
new String("oi") == new String("oi")   // false — objetos diferentes
"oi".equals("oi")                      // true  — SEMPRE use isso
```

---

## 5b. Criando ou usando? (a pergunta que resolve metade dos erros)

Quase todo erro meu ate aqui foi o mesmo: **copiei codigo pra um lugar onde ele
nao tinha o que fazer.** Linha do `Main` dentro da classe, declaracao da interface
dentro da implementacao. A causa e sempre nao ter parado pra perguntar:

> **Esse nome ja existe em algum lugar, ou eu estou inventando ele agora?**

- **Ja existe** -> eu **uso**: chamo com `( )`, leio, atribuo.
- **Estou inventando** -> eu **declaro**: digo o tipo antes do nome.

### A regra do que vem depois

| O que aparece | Significa | Exemplo |
|---|---|---|
| `{ }` depois | esta **criando** | `public void salvar(Cliente c) {` |
| `;` depois | esta so **prometendo** | `void salvar(Cliente c);` (interface) |
| `( )` com valor dentro | esta **chamando** | `repo.salvar(c)` |
| `new` | esta **fabricando** um objeto | `new Cliente[10]` |

**`new` e a unica palavra do Java que fabrica.** Onde nao tem `new`, nada nasceu.

### Lendo uma linha de verdade (dia 8)

```java
private Cliente[] itens = new Cliente[10];
   |       |        |    |      |
   |       |        |    |      +- FABRICANDO: cria a caixa de 10
   |       |        |    +- atribuicao: guarda a esquerda o que vem da direita
   |       |        +- NOME NOVO: "itens" nao existia, inventei agora
   |       +- TIPO: "Cliente" ja existe, so aponto pra ele
   +- quem enxerga
```

Uma linha so: **aponto** pra um tipo que existe, **invento** um nome, **fabrico** um objeto.

Tres linhas abaixo, no mesmo arquivo:

```java
itens[qtd] = c;
```

Nada aqui e novo. `itens` e `qtd` eu criei acima, `c` chegou pelo parametro.
Essa linha so **mexe** no que ja esta la.

### Interface x classe — cardapio x cozinha

| | O que e | Como termina o metodo | Precisa de `public`? |
|---|---|---|---|
| **Interface** | o cardapio: lista os pratos | `;` | nao, ja e publico |
| **Classe** | a cozinha: cozinha cada um | `{ ... }` | **sim**, obrigatorio |

Escrever o cardapio duas vezes nao abre a cozinha. Se a classe repetir as
declaracoes da interface, o erro e:

```
attempting to assign weaker access privileges; was public
```

= prometi publico no contrato e entreguei menos aqui.

### A armadilha do objeto que se cria sozinho

```java
public class RepositorioMemoria implements Repositorio {
    Repositorio repo = new RepositorioMemoria();   // NUNCA
}
```

Cada objeto criaria outro, que criaria outro: `StackOverflowError`.
**Classe nenhuma se instancia dentro do proprio corpo.** Quem cria o
repositorio e quem vai **usar** ele (o `Main`, ou o Spring).

### Erros de compilador traduzidos

| O compilador diz | Quer dizer |
|---|---|
| `cannot find symbol` | nome escrito errado, ou falta import. Compare com a linha vizinha que funciona |
| `class, interface, enum, or record expected` | tem codigo solto fora de qualquer classe |
| `X is not abstract and does not override Y` | assinou contrato de N metodos e entregou menos |
| `attempting to assign weaker access privileges` | faltou `public` no metodo da classe |
| `reached end of file while parsing` | falta uma `}` la em cima |

---

## 6. Git — o mínimo que você usa todo dia

```bash
git init
git status                      # use o tempo todo
git add .
git commit -m "mensagem"
git log --oneline               # histórico enxuto
git diff                        # o que mudou e ainda não foi adicionado
git diff --staged               # o que já foi adicionado
git checkout -- arquivo         # desfaz mudança não commitada
git branch minha-feature
git checkout -b minha-feature   # cria e já entra
git push origin main
git pull
```

**Mensagem de commit decente:** o que mudou e por quê, no imperativo.
`adiciona validacao de whatsapp no POST /leads` — não `update` nem `ajustes`.

---

## 7. Maven (semana 4 em diante)

```bash
mvn clean               # apaga target/
mvn compile
mvn test
mvn package             # gera o .jar
mvn spring-boot:run     # sobe a aplicação
java -jar target/app.jar
```

## 8. curl — testar API sem Postman

```bash
curl localhost:8080/leads
curl -i localhost:8080/leads                     # mostra os headers e o status
curl -X POST localhost:8080/leads \
  -H "Content-Type: application/json" \
  -d '{"nome":"Teste","whatsapp":"61999999999"}'
curl -X PUT localhost:8080/leads/1 -H "Content-Type: application/json" -d '{"status":"fechado"}'
curl -s localhost:8080/leads | jq               # formata o JSON
```

---

## 9. Os 3 de hoje

Escolhe três que você **não** usa ainda e força o uso durante os 40 minutos:

- [ ] `Ctrl+R` em vez de seta pra cima
- [ ] `!$` em vez de redigitar o caminho
- [ ] `grep -rn` em vez de abrir arquivo procurando
