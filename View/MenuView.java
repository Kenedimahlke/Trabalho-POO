package View;

/**
 * Responsa�vel por exibir menus do sistema
 * Segue o princa�pio Single Responsibility
 */
public class MenuView {
    
    private ConsoleView view;
    
    public MenuView(ConsoleView view) {
        this.view = view;
    }
    
    public void exibirMenuPrincipal() {
        view.exibirTitulo("MENU PRINCIPAL");
        view.exibirLinha(" 1. Gerenciar Usuarios");
        view.exibirLinha(" 2. Gerenciar Contas");
        view.exibirLinha(" 3. Gerenciar Transacoes");
        view.exibirLinha(" 4. Gerenciar Metas");
        view.exibirLinha(" 5. Gerenciar Orcamentos");
        view.exibirLinha(" 6. Gerenciar Cofrinhos");
        view.exibirLinha(" 7. Algoritmos Inteligentes");
        view.exibirLinha(" 8. Relatorios");
        view.exibirLinha(" 9. Salvar Dados");
        view.exibirLinha(" 0. Sair");
        view.exibirSeparador();
        view.exibir("Escolha uma opa�a�o: ");
    }
    
    public void exibirMenuUsuarios() {
        view.exibirTitulo("GERENCIAR USUARIOS");
        view.exibirLinha(" 1. Cadastrar Usuario Individual");
        view.exibirLinha(" 2. Cadastrar Grupo");
        view.exibirLinha(" 3. Listar Usuarios");
        view.exibirLinha(" 0. Voltar");
        view.exibirSeparador();
        view.exibir("Escolha uma opa�a�o: ");
    }
    
    public void exibirMenuContas() {
        view.exibirTitulo("GERENCIAR CONTAS");
        view.exibirLinha(" 1. Criar Conta Corrente");
        view.exibirLinha(" 2. Criar Conta Digital");
        view.exibirLinha(" 3. Criar Cartao de Credito");
        view.exibirLinha(" 4. Criar Carteira de Investimento");
        view.exibirLinha(" 5. Criar Cofrinho");
        view.exibirLinha(" 6. Listar Contas");
        view.exibirLinha(" 0. Voltar");
        view.exibirSeparador();
        view.exibir("Escolha uma opa�a�o: ");
    }
    
    public void exibirMenuTransacoes() {
        view.exibirTitulo("GERENCIAR TRANSACOES");
        view.exibirLinha(" 1. Registrar Receita");
        view.exibirLinha(" 2. Registrar Despesa");
        view.exibirLinha(" 3. Listar Transacoes");
        view.exibirLinha(" 4. Estornar Transacao");
        view.exibirLinha(" 5. Adicionar Anexo a Transacao");
        view.exibirLinha(" 6. Dividir Despesa (Rateio)");
        view.exibirLinha(" 0. Voltar");
        view.exibirSeparador();
        view.exibir("Escolha uma opa�a�o: ");
    }
    
    public void exibirMenuMetas() {
        view.exibirTitulo("GERENCIAR METAS");
        view.exibirLinha(" 1. Criar Meta");
        view.exibirLinha(" 2. Listar Metas");
        view.exibirLinha(" 3. Adicionar valor a� Meta");
        view.exibirLinha(" 0. Voltar");
        view.exibirSeparador();
        view.exibir("Escolha uma opa�a�o: ");
    }
    
    public void exibirMenuOrcamentos() {
        view.exibirTitulo("GERENCIAR ORCAMENTOS");
        view.exibirLinha(" 1. Criar Orcamento");
        view.exibirLinha(" 2. Listar Orcamentos");
        view.exibirLinha(" 0. Voltar");
        view.exibirSeparador();
        view.exibir("Escolha uma opa�a�o: ");
    }
    
    public void exibirMenuCofrinhos() {
        view.exibirTitulo("GERENCIAR COFRINHOS");
        view.exibirLinha(" 1. Depositar no Cofrinho");
        view.exibirLinha(" 2. Sacar do Cofrinho");
        view.exibirLinha(" 3. Ver Progresso do Cofrinho");
        view.exibirLinha(" 0. Voltar");
        view.exibirSeparador();
        view.exibir("Escolha uma opa�a�o: ");
    }
    
    public void exibirMenuAlgoritmos() {
        view.exibirTitulo("ALGORITMOS INTELIGENTES");
        view.exibirLinha(" 1. Simulaa�a�o de Cena�rio");
        view.exibirLinha(" 2. Deteca�a�o de Gastos Anormais");
        view.exibirLinha(" 3. Sugesta�es de Economia");
        view.exibirLinha(" 4. Projea�a�o de Saldo");
        view.exibirLinha(" 5. Rateio Automa�tico");
        view.exibirLinha(" 0. Voltar");
        view.exibirSeparador();
        view.exibir("Escolha uma opa�a�o: ");
    }
    
    public void exibirMenuSimulacao() {
        view.exibirTitulo("SIMULACAO DE CENARIO");
        view.exibirLinha(" 1. Simular mudanca em categoria especifica");
        view.exibirLinha(" 2. Simular mudanca global de gastos");
        view.exibirLinha(" 3. Simular nova despesa recorrente");
        view.exibirLinha(" 0. Voltar");
        view.exibirSeparador();
        view.exibir("Escolha uma opa�a�o: ");
    }
    
    public void exibirMenuRelatorios() {
        view.exibirTitulo("RELATORIOS");
        view.exibirLinha(" 1. Relatorio por Periodo");
        view.exibirLinha(" 2. Comparativo por Categoria");
        view.exibirLinha(" 3. Ranking de Despesas");
        view.exibirLinha(" 4. Evolucao de Saldo");
        view.exibirLinha(" 5. Relatorio Resumo de Grupo");
        view.exibirLinha(" 6. Relatorio Completo");
        view.exibirLinha(" 7. Exportar Relatorio");
        view.exibirLinha(" 0. Voltar");
        view.exibirSeparador();
        view.exibir("Escolha uma opa�a�o: ");
    }
}
