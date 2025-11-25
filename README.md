# Sistema de Gerenciamento Financeiro Pessoal

Este é um sistema completo de gestão financeira desenvolvido em Java, focado na aplicação de conceitos de Orientação a Objetos, Padrões de Projeto (Design Patterns) e princípios SOLID. O sistema permite o gerenciamento de contas, transações, metas, orçamentos e cofrinhos, tanto para usuários individuais quanto para grupos.

## Funcionalidades

*   **Gerenciamento de Usuários**: Cadastro de usuários individuais e grupos (família, amigos, etc.).
*   **Contas Financeiras**:
    *   Conta Corrente (com cheque especial).
    *   Conta Digital (com rendimento automático).
    *   Cartão de Crédito (controle de limite e faturas).
    *   Carteira de Investimento (perfis conservador, moderado e agressivo).
    *   Cofrinhos (poupança com objetivos específicos).
*   **Transações**:
    *   Receitas e Despesas.
    *   Transferências entre contas.
    *   Transações recorrentes e parceladas.
    *   Anexos em transações.
    *   Estorno de lançamentos.
*   **Planejamento**:
    *   **Metas**: Definição de objetivos financeiros com acompanhamento de progresso.
    *   **Orçamentos**: Definição de limites de gastos por categoria com alertas.
*   **Algoritmos Inteligentes**:
    *   **Projeção de Saldo**: Estimativa futura baseada no histórico.
    *   **Rateio Automático**: Divisão de despesas entre pessoas com pesos diferentes.
    *   **Sugestão de Economia**: Análise de gastos e recomendações personalizadas.
*   **Relatórios**:
    *   Extratos por período.
    *   Comparativos por categoria.
    *   Ranking de despesas.
    *   Evolução patrimonial.
    *   Exportação para arquivo de texto.
*   **Persistência**: Salvamento automático dos dados em arquivo binário (`dados_sistema.ser`).

## Tecnologias e Aspectos Utilizados

O projeto foi construído utilizando Java puro (versão 17+), sem frameworks externos para a lógica de negócios, focando na robustez da linguagem e arquitetura de software.

### Padrões de Projeto (Design Patterns)
*   **Singleton**: Utilizado no `GerenciadorFinanceiro` para garantir uma única instância controladora do estado do sistema.
*   **Factory Method**: Utilizado na `ContaFactory` para encapsular a lógica de criação dos diferentes tipos de contas financeiras.
*   **Strategy**: Utilizado para implementar os diferentes algoritmos inteligentes (`ProjecaoSaldo`, `RateioAutomatico`, `SugestaoEconomia`), permitindo a troca e extensão fácil das estratégias de cálculo.

### Princípios SOLID
*   **Single Responsibility Principle (SRP)**: Separação clara de responsabilidades (Entidades apenas armazenam dados e lógica básica, Gerenciadores controlam o fluxo,  Main cuida da interação).
*   **Open/Closed Principle (OCP)**: O sistema é extensível para novos tipos de contas e transações sem modificar o código base drasticamente.
*   **Interface Segregation Principle (ISP)**: Interfaces específicas como `Calculavel` e `Exportavel` implementadas apenas pelas classes que necessitam.

### Outros Aspectos
*   **Java Core**: Uso extensivo de Collections API, Stream API e Lambdas para manipulação de dados.
*   **Java Time API**: Manipulação precisa de datas com `LocalDate` e `YearMonth`.
*   **Serialização**: Persistência nativa de objetos Java para salvar o estado do sistema.
*   **Tratamento de Exceções**: Hierarquia de exceções personalizadas 

## Como Compilar e Executar

### Pré-requisitos
*   JDK 17 ou superior instalado.

### Execução Manual (Terminal)

1.  **Compilar o projeto:**
    Abra o terminal na raiz do projeto e execute:
    ```powershell
    javac -encoding UTF-8 Entidades/*.java Enums/*.java Exceptions/*.java Factory/*.java Gerenciadores/*.java Interfaces/*.java Relatorios/*.java Strategy/*.java Main.java
    ```

2.  **Executar o sistema:**
    ```powershell
    java Main
    ```

## Como Rodar os Testes Automatizados

O projeto utiliza **JUnit 5** para testes unitários. Existem duas formas de executá-los:

### Opção 1: Via Maven (Recomendado)
Se você tiver o Maven instalado:
```powershell
mvn test
```

### Opção 2: Manualmente (Console Standalone)
O projeto inclui a biblioteca do JUnit na pasta `lib/`.

1.  **Compilar as classes do projeto e os testes:**
    ```powershell
    javac -cp "lib/junit-platform-console-standalone-1.10.1.jar;." Entidades/*.java Enums/*.java Exceptions/*.java Factory/*.java Gerenciadores/*.java Interfaces/*.java Relatorios/*.java Strategy/*.java Testes/*.java
    ```

2.  **Executar os testes:**
    ```powershell
    java -jar lib/junit-platform-console-standalone-1.10.1.jar -cp "." --scan-classpath
    ```
    *Isso irá varrer o classpath em busca de classes de teste e executá-las, exibindo o relatório no terminal.*

---
**Autor:** Kenedi Mahlke
**Disciplina:** Programação Orientada a Objetos
