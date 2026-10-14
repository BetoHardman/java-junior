# Avaliação do curso que já estava no PC

**Arquivo:** `C:\Users\Betim\Downloads\roteiro-java-backend.md`
**Criado:** 01/09/2026 · **Meta original:** 15/11/2026 · **75 dias, 2h/dia**

---

## O dado que importa

**2 dias concluídos de 75.** Último código escrito em `treino-java/Main.java`: **03/09/2026**.
Última anotação em `Documents/JAVA/JAVA.txt`: **15/09/2026**.

Hoje é 14/10. O curso está parado há **um mês**.

Isso não é preguiça sua. É um problema de projeto do curso, e eu vou ser específico sobre qual.

---

## O que o curso tem de bom (e eu vou manter)

| | |
|---|---|
| **Sequência** | A ordem dos assuntos está certa: fundamentos → OOP → coleções → Java moderno → backend → Spring → entrevista. Não tem buraco. |
| **Perguntas de entrevista embutidas** | `==` vs `.equals()`, `HashMap` por dentro, `ArrayList` vs `LinkedList`. São exatamente as que caem. |
| **Prova diária** | A ideia de "como saber que aprendi" em cada dia é boa. |
| **Regra de ouro** | "Você nunca copia código sem entender cada linha." Mantida. |

O conteúdo não é o problema. Se você tivesse feito os 75 dias, estaria pronto.

---

## O que está errado

### 1. É grande demais para quem você é hoje
75 dias × 2h = **150 horas**. Você fez 4. Um plano que você não executa vale zero, por melhor que
seja o conteúdo. E 2h/dia não cabe em alguém que roda duas contas de conteúdo e atende clientes.

### 2. Não tem terminal em lugar nenhum
O curso assume IDE desde o dia 1. Não tem `javac`, não tem `java`, não tem `jshell`, não tem shell,
não tem atalho. É exatamente o oposto do que você está pedindo agora. Você aprende a clicar em
"Run", não a entender o que acontece quando o botão é apertado.

### 3. Git só no dia 43, nuvem em lugar nenhum
Git aparece na metade do caminho e deploy aparece no dia 68, como detalhe. Para quem quer trabalhar
"igual você faz aí", isso está invertido: terminal, git e nuvem são o **ambiente**, não um módulo.

### 4. Não tem automação
Os checkboxes são marcados na mão num arquivo markdown. Dá pra marcar sem ter feito. Não existe
nada que rode o seu código e diga "passou" ou "não passou". Sem retorno automático, não tem jogo.

### 5. Sete dias de LeetCode que você não precisa agora
Dias 35 a 41 são Big-O, estruturas de dados e desafios estilo entrevista de big tech. Isso é caro em
tempo e serve pra Nubank e Itaú. Para a sua meta — virar empregável e usar Java no seu próprio
negócio — o retorno é baixo. Cortei para 1 dia de noção e devolvi o tempo pro que te paga.

### 6. Os projetos são genéricos
"Sistema bancário" e "gerenciador de tarefas" são projetos de curso. Todo mundo tem. Você tem três
sites no ar e leads reais se perdendo no WhatsApp — isso é um projeto que ninguém mais tem.

### 7. Ritmo sem folga vira culpa
2h todo dia, incluindo fim de semana, sem nenhuma margem. No primeiro dia perdido vem a culpa, e
depois da culpa vem o abandono. Foi o que aconteceu.

---

## O que eu mudei

| Antes | Agora |
|---|---|
| 2h/dia, 75 dias, sem folga | **40 min/dia obrigatórios**, 6 dias/semana, domingo livre |
| IDE desde o início | **Terminal puro**: `javac`, `java`, `jshell`, `vim` — IDE só na semana 4 |
| Git no dia 43 | **Git no dia 1**, commit todo dia |
| Nuvem no dia 68 | **Nuvem a partir da semana 4**, deploy e CI |
| Checkbox na mão | **Script `jt`** que compila, roda, testa e commita por você |
| 7 dias de LeetCode | 1 dia de noção de complexidade |
| Projeto de curso | **API de leads do Alon Warm**, seu problema real |
| Nenhum atalho ensinado | **Drill de atalhos todo dia**, terminal e código |

**Por que 40 minutos e não 2 horas:** porque 40 minutos você faz num dia ruim. O curso antigo era
bom demais para ser feito. Este é pequeno o bastante para não ter desculpa. Se der vontade de
continuar depois dos 40, continua — mas a meta do dia já foi batida aos 40.

---

## Veredito

**O curso antigo era um bom livro e um péssimo plano.** Conteúdo sólido, execução impossível.

Aproveitei a sequência e as perguntas de entrevista, joguei fora o tamanho e a dependência de IDE,
e reconstruí em volta de três coisas que você pediu: **terminal cru, nuvem e automação.**

O curso novo está em `README.md`, nesta mesma pasta.
