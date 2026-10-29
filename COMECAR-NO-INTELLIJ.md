# Começar no IntelliJ

Dez minutos, uma vez só. Depois disso todo dia abre direto no exercício.

O que já está conferido nesta máquina:

| | |
|---|---|
| JDK | Temurin **21**, com `JAVA_HOME` configurado |
| IntelliJ | **Community Edition 2024.3.3** |
| git | `Beto Hardman` |

---

## 1. Abrir a pasta **do dia**, não o projeto inteiro

No IntelliJ: **Open** e escolhe

```
D:\DELL TI CL\Claude\java-junior\dias\dia11
```

**Não abra a pasta `java-junior`.** Cada pasta `diaNN` tem o seu próprio
`Main.java` e o seu próprio `Cliente.java`. Se você abrir o projeto todo, o IntelliJ
vê onze `Main` e pinta tudo de vermelho reclamando de classe duplicada — e nada
está errado, é só ele tentando compilar os onze dias de uma vez.

Uma pasta de dia por vez: um `Main`, um `Cliente`, zero confusão.

Quando ele perguntar se você confia no projeto, responde **Trust Project**.

## 2. Conferir o JDK

`Ctrl+Alt+Shift+S` abre o **Project Structure**. Em **Project**, o campo **SDK**
tem que mostrar o **21**.

Se estiver vazio ou com `<No SDK>`: abre a listinha, vai em **Add SDK → JDK**, e
aponta pra

```
C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot
```

Se o SDK estiver errado, **todo o resto vai parecer quebrado**. É o primeiro lugar
pra olhar quando o IntelliJ reclamar de algo que você sabe que está certo.

## 3. Quatro ajustes antes da primeira linha

O IntelliJ, se você deixar, escreve o código por você. Isso é ótimo pra quem já sabe
e péssimo pra quem está aprendendo. Três ajustes e uma regra.

### a) Desliga o import automático

`Ctrl+Alt+S` abre o **Settings**. Vai em
**Editor → General → Auto Import**, e na parte de Java **desmarca**:

- `Add unambiguous imports on the fly`
- `Optimize imports on the fly`

> Se o caminho do menu estiver um pouco diferente na sua versão, usa a **busca do
> Settings** — tem uma lupa no canto. Procura por `auto import`.

**Por quê:** você aprendeu no dia 9 que `import` é endereço. Se o IntelliJ passar a
escrever sozinho, em duas semanas você não lembra mais o que é `java.util`. Ele
ainda vai **sugerir** com `Alt+Enter` — e aí você confirma sabendo o que está fazendo.

### b) Nada de "Generate"

O menu `Alt+Insert` gera getter, setter e construtor prontos. **Não usa por enquanto.**
Você aprendeu encapsulamento no dia 6 digitando getter na mão. Usa quando digitar
virar perda de tempo — não antes.

### c) Git continua no terminal

Você ainda está aprendendo o que é commit, push e histórico — semana passada a gente
mexeu em commit antigo na mão. A interface gráfica de git esconde exatamente a parte
que você precisa enxergar. **Continua com `jt ok`.**

### d) A regra mais importante: lê o erro antes de aceitar a correção

O IntelliJ sublinha de vermelho e oferece `Alt+Enter` pra consertar sozinho.

**Lê o que está escrito antes de apertar.** Passa o mouse em cima do sublinhado e
lê a mensagem — é a mesma mensagem que o `javac` te dava, só que agora ela aparece
enquanto você digita, em vez de depois.

Se você apertar `Alt+Enter` no automático, você desaprende em um mês o que levou
onze dias pra aprender. Lê, entende, **aí** conserta — com `Alt+Enter` se quiser.

## 4. O ciclo de trabalho

Muda o editor. **Não muda mais nada.**

| Onde | O quê |
|---|---|
| IntelliJ | escrever o código |
| Git Bash | `jt rodar`, `jt teste`, `jt ok` |

O `jt` continua funcionando igual, da pasta `java-junior`. Você pode rodar pelo
botão verde do IntelliJ também (`Ctrl+Shift+F10` em cima do `Main`), mas **quem diz
se passou é o `jt teste`**, porque é ele que compara com o `esperado.txt`.

Dica: deixa o IntelliJ numa metade da tela e o Git Bash na outra.

## 5. O que muda de verdade

As quatro coisas que te derrubaram no dia 10, e o que acontece com elas agora:

| Dia 10, no nano | No IntelliJ |
|---|---|
| faltou um `)` | ele pinta o parêntese que falta na hora |
| faltou um `}` | ele mostra a chave que combina com cada chave |
| `Lis` em vez de `List` | sublinha vermelho enquanto você digita |
| o `resumo()` sumiu sem aviso | `Ctrl+Z` desfaz, e o git mostra o que mudou |

Nenhum desses você teria perdido tempo com.

## 6. Spring Boot — o que a sua versão não faz

A sua é a **Community 2024.3.3**, anterior à fusão das edições da JetBrains. Ela é
completa pra Java puro, mas **não tem o assistente de Spring** — na época isso era
só na Ultimate.

Isso **não te impede** de fazer a API de leads. O caminho gratuito:

1. gera o projeto em [start.spring.io](https://start.spring.io) pelo navegador
2. baixa o `.zip` e descompacta
3. abre a pasta no IntelliJ — ele reconhece o Maven sozinho
4. roda pelo método `main` da classe da aplicação, como qualquer Java

Você não precisa instalar o Maven: **o IntelliJ traz o dele embutido.**

O que você perde é conveniência (assistente, navegação entre beans, console de
banco), não capacidade. Quando chegar a hora, a gente vê se vale assinar — e se
você estiver matriculado em alguma instituição de ensino, a JetBrains dá a licença
completa de graça pra estudante.
