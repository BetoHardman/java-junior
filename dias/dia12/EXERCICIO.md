# Dia 12 — Maven: o `pom.xml` e a arrumação da casa

Hoje **o seu código não muda em nada.** São os mesmos cinco arquivos do dia 11,
no mesmo estado em que você os deixou.

O que muda é tudo em volta: onde eles moram, quem compila e de onde vem o que não é seu.

Três lacunas pra preencher, marcadas com `TODO` no `pom.xml`.

---

## 0. Primeiro: `mvn` no terminal

Você **já tem** Maven nessa máquina — ele veio dentro do IntelliJ. Só não está no PATH.

No Git Bash:

```bash
echo 'export PATH="$PATH:/c/Program Files/JetBrains/IntelliJ IDEA Community Edition 2024.3.3/plugins/maven/lib/maven3/bin"' >> ~/.bashrc
```

```bash
source ~/.bashrc
```

Confere:

```bash
mvn -version
```

Tem que aparecer `Apache Maven 3.9.9` e, embaixo, `Java version: 21.0.12.1`.
Se o `mvn` não for achado, o `~/.bashrc` não foi lido — fecha e abre o Git Bash.

> `>>` acrescenta no fim do arquivo. Com um `>` só você **apagaria** o `.bashrc` inteiro.
> É o mesmo sinal que você usou no dia 3, no encanamento.

## 1. Pra que serve o Maven

Não é pra compilar. `javac` já compila.

Olha a primeira linha do `Main.java` de hoje:

```java
import org.apache.commons.lang3.StringUtils;
```

Isso **não é do Java** e **não é seu**. É uma biblioteca de terceiros, o commons-lang3.
Sem Maven, pra usar ela você teria que: achar o site, baixar o `.jar` certo, guardar
numa pasta, e lembrar de passar `-cp` apontando pra ele em toda compilação. E repetir
isso pra cada biblioteca, e pras bibliotecas que *elas* usam.

**Maven faz isso por você a partir de um arquivo de texto.** Você escreve o nome e a
versão; ele baixa, guarda e monta o classpath.

Hoje é uma biblioteca. Um projeto Spring Boot tem umas quarenta.

## 2. A arrumação: `src/main/java`

Repara onde os `.java` estão agora:

```
dia12/
├── pom.xml                 <- a receita
└── src/
    └── main/
        └── java/           <- o codigo fica AQUI
            ├── Cliente.java
            └── ...
```

Essa pasta não é escolha minha, é **convenção do Maven**. Ele procura o código em
`src/main/java` e pronto — por isso o `pom.xml` não precisa dizer onde o código está.

Chama-se *convenção sobre configuração*: se você segue o padrão, não configura nada.
Todo projeto Java do mundo tem essa mesma árvore. Quando você abrir o repositório de
qualquer biblioteca no GitHub, vai saber onde olhar.

## 3. `TODO-1` — o endereço do seu projeto

```xml
<groupId>br.com.betohardman</groupId>
<artifactId>TODO-1</artifactId>
<version>1.0-SNAPSHOT</version>
```

Esses três juntos são a **identidade** do projeto, o jeito que o mundo chamaria ele
se você publicasse:

| | O que é |
|---|---|
| `groupId` | quem fez — domínio ao contrário, igual a pacote |
| `artifactId` | o nome do projeto |
| `version` | a versão. `SNAPSHOT` quer dizer "em desenvolvimento, ainda muda" |

Troca o `TODO-1` por `dia12`.

## 4. `TODO-2` — a versão do Java

```xml
<maven.compiler.release>TODO-2</maven.compiler.release>
```

Qual Java compila e pra qual Java o bytecode é gerado. O seu é o **21** — é o que o
`mvn -version` te mostrou, e o mesmo que você pôs no SDK do IntelliJ ontem.

Se esse número não bater com o seu JDK, você vai ver `invalid target release`.

## 5. `TODO-3` — a dependência

Dentro de `<dependencies>`, no lugar do comentário, entra **um bloco `<dependency>`**
com os mesmos três campos da seção 3 — só que agora é o endereço da biblioteca
**dos outros**, não o seu:

```
groupId     org.apache.commons
artifactId  commons-lang3
version     3.17.0
```

Esses três campos têm nome: são as **coordenadas** da biblioteca. É assim que todo
mundo publica — você acha num site, copia as coordenadas, cola no pom.

Cuidado com XML: toda tag que abre `<groupId>` tem que fechar `</groupId>`, com a
barra. Errou uma e o Maven recusa o arquivo inteiro.

## 6. Compilar

```bash
cd dias/dia12
```

```bash
mvn compile
```

**Na primeira vez ele vai baixar um monte de coisa** e cuspir dezenas de linhas de
`Downloading...`. Isso é normal e acontece uma vez só: ele guarda tudo em
`C:\Users\Betim\.m2\repository`, o seu repositório local. Da segunda vez em diante é
instantâneo e funciona offline.

No fim tem que aparecer `BUILD SUCCESS`.

Apareceu uma pasta `target/` nova — é onde o Maven joga o resultado. **Ela não vai
pro git** (já está no `.gitignore`): é gerada, não é fonte. Deu problema estranho,
`mvn clean` apaga ela e você começa do zero.

## 7. Rodar

Volta pra raiz e usa o `jt` de sempre:

```bash
cd ../..
```

```bash
jt teste
```

O `jt` aprendeu Maven hoje: quando ele acha um `pom.xml` na pasta do dia, ele chama
o Maven em vez do `javac`, e monta o classpath com os `.jar` das dependências.
Pra você, o comando é o mesmo de sempre.

---

## Saída esperada

```
========== CARTEIRA ==========
novo........1
contato.....2
fechado.....2
------------------------------
Luna Toys (61993687124) - fechado
...
```

Os pontinhos e o `=` em volta de `CARTEIRA` são o `StringUtils` trabalhando. Se a
dependência não estivesse no pom, nada disso compilaria.

## Fechar

```bash
jt ok "dia12 Maven, pom.xml e dependencias"
```

## Prova do dia

1. Pra que serve o Maven, se o `javac` já compila?
2. Por que o `pom.xml` não diz onde o código está?
3. O que são `groupId`, `artifactId` e `version` juntos?
4. Onde o Maven guarda o que baixou, e por que a segunda compilação é rápida?
5. Por que a pasta `target/` não entra no git?
