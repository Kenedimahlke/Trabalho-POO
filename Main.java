import Entidades.*;
import Enums.*;
import Exceptions.*;
import Factory.*;
import Gerenciadores.*;
import Interfaces.*;
import Observers.*;
import Relatorios.*;
import Repositorios.*;
import Strategy.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe principal com interface de console para o sistema financeiro
public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    private static GerenciadorFinanceiro gerenciador = GerenciadorFinanceiro.getInstancia();
    private static GerenciadorPersistencia persistencia = new GerenciadorPersistencia();
    private static GerenciadorRelatorios geradorRelatorios;
    
    public static void main(String[] args) {
        configurarObservadores();
        carregarDados();
        
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║         SISTEMA DE GERENCIAMENTO FINANCEIRO PESSOAL          ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        
        boolean executando = true;
        
        while (executando) {
            try {
                exibirMenuPrincipal();
                int opcao = lerOpcao();
                
                switch (opcao) {
                    case 1:
                        menuUsuarios();
                        break;
                    case 2:
                        menuContas();
                        break;
                    case 3:
                        menuTransacoes();
                        break;
                    case 4:
                        menuMetas();
                        break;
                    case 5:
                        menuOrcamentos();
                        break;
                    case 6:
                        menuCofrinho();
                        break;
                    case 7:
                        menuAlgoritmos();
                        break;
                    case 8:
                        menuRelatorios();
                        break;
                    case 9:
                        salvarDados();
                        break;
                    case 0:
                        executando = false;
                        salvarDados();
                        System.out.println("\nSistema encerrado com sucesso!");
                        break;
                    default:
                        System.out.println("\nOpção inválida!");
                }
                
            } catch (Exception e) {
                System.out.println("\nErro: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
    
    private static void exibirMenuPrincipal() {
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                         MENU PRINCIPAL");
        System.out.println("═".repeat(65));
        System.out.println(" 1. Gerenciar Usuários");
        System.out.println(" 2. Gerenciar Contas");
        System.out.println(" 3. Gerenciar Transações");
        System.out.println(" 4. Gerenciar Metas");
        System.out.println(" 5. Gerenciar Orçamentos");
        System.out.println(" 6. Gerenciar Cofrinhos");
        System.out.println(" 7. Algoritmos Inteligentes");
        System.out.println(" 8. Relatórios");
        System.out.println(" 9. Salvar Dados");
        System.out.println(" 0. Sair");
        System.out.println("═".repeat(65));
        System.out.print("Escolha uma opção: ");
    }
    
    private static void menuUsuarios() {
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                     GERENCIAR USUÁRIOS");
        System.out.println("═".repeat(65));
        System.out.println(" 1. Cadastrar Usuário Individual");
        System.out.println(" 2. Cadastrar Grupo");
        System.out.println(" 3. Listar Usuários");
        System.out.println(" 0. Voltar");
        System.out.println("═".repeat(65));
        System.out.print("Escolha uma opção: ");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                cadastrarUsuarioIndividual();
                break;
            case 2:
                cadastrarGrupo();
                break;
            case 3:
                listarUsuarios();
                break;
            case 0:
                return;
            default:
                System.out.println("\n✗ Opção inválida!");
        }
    }
    
    private static void menuContas() {
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                      GERENCIAR CONTAS");
        System.out.println("═".repeat(65));
        System.out.println(" 1. Criar Conta Corrente");
        System.out.println(" 2. Criar Conta Digital");
        System.out.println(" 3. Criar Cartão de Crédito");
        System.out.println(" 4. Criar Carteira de Investimento");
        System.out.println(" 5. Criar Cofrinho");
        System.out.println(" 6. Listar Contas");
        System.out.println(" 0. Voltar");
        System.out.println("═".repeat(65));
        System.out.print("Escolha uma opção: ");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                criarConta("corrente");
                break;
            case 2:
                criarConta("digital");
                break;
            case 3:
                criarConta("credito");
                break;
            case 4:
                criarConta("investimento");
                break;
            case 5:
                criarConta("cofrinho");
                break;
            case 6:
                listarContas();
                break;
            case 0:
                return;
            default:
                System.out.println("\nOpção inválida!");
        }
    }
    
    private static void menuTransacoes() {
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                    GERENCIAR TRANSAÇÕES");
        System.out.println("═".repeat(65));
        System.out.println(" 1. Registrar Receita");
        System.out.println(" 2. Registrar Despesa");
        System.out.println(" 3. Listar Transações");
        System.out.println(" 4. Estornar Transação");
        System.out.println(" 5. Adicionar Anexo à Transação");
        System.out.println(" 6. Dividir Despesa (Rateio)");
        System.out.println(" 0. Voltar");
        System.out.println("═".repeat(65));
        System.out.print("Escolha uma opção: ");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                registrarReceita();
                break;
            case 2:
                registrarDespesa();
                break;
            case 3:
                listarTransacoes();
                break;
            case 4:
                estornarTransacao();
                break;
            case 5:
                adicionarAnexoTransacao();
                break;
            case 6:
                dividirDespesaRateio();
                break;
            case 0:
                return;
            default:
                System.out.println("\nOpção inválida!");
        }
    }
    
    private static void menuMetas() {
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                      GERENCIAR METAS");
        System.out.println("═".repeat(65));
        System.out.println(" 1. Criar Meta");
        System.out.println(" 2. Listar Metas");
        System.out.println(" 3. Contribuir para Meta");
        System.out.println(" 4. Verificar Status das Metas");
        System.out.println(" 0. Voltar");
        System.out.println("═".repeat(65));
        System.out.print("Escolha uma opção: ");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                criarMeta();
                break;
            case 2:
                listarMetas();
                break;
            case 3:
                contribuirMeta();
                break;
            case 4:
                verificarStatusMetas();
                break;
            case 0:
                return;
            default:
                System.out.println("\nOpção inválida!");
        }
    }
    
    private static void menuOrcamentos() {
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                    GERENCIAR ORÇAMENTOS");
        System.out.println("═".repeat(65));
        System.out.println(" 1. Criar Orçamento");
        System.out.println(" 2. Listar Orçamentos");
        System.out.println(" 3. Verificar Status dos Orçamentos");
        System.out.println(" 0. Voltar");
        System.out.println("═".repeat(65));
        System.out.print("Escolha uma opção: ");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                criarOrcamento();
                break;
            case 2:
                listarOrcamentos();
                break;
            case 3:
                verificarStatusOrcamentos();
                break;
            case 0:
                return;
            default:
                System.out.println("\nOpção inválida!");
        }
    }
    
    private static void menuCofrinho() {
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                    GERENCIAR COFRINHOS");
        System.out.println("═".repeat(65));
        System.out.println(" 1. Criar Cofrinho");
        System.out.println(" 2. Listar Cofrinhos");
        System.out.println(" 3. Depositar no Cofrinho");
        System.out.println(" 4. Sacar do Cofrinho");
        System.out.println(" 5. Verificar Progresso do Cofrinho");
        System.out.println(" 0. Voltar");
        System.out.println("═".repeat(65));
        System.out.print("Escolha uma opção: ");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                criarCofrinho();
                break;
            case 2:
                listarCofrinhos();
                break;
            case 3:
                depositarCofrinho();
                break;
            case 4:
                sacarCofrinho();
                break;
            case 5:
                verificarProgressoCofrinho();
                break;
            case 0:
                return;
            default:
                System.out.println("\nOpção inválida!");
        }
    }
    
    private static void menuAlgoritmos() {
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                  ALGORITMOS INTELIGENTES");
        System.out.println("═".repeat(65));
        System.out.println(" 1. Simulação de Cenário");
        System.out.println(" 2. Detecção de Gastos Anormais");
        System.out.println(" 3. Sugestões de Economia");
        System.out.println(" 4. Projeção de Saldo");
        System.out.println(" 5. Rateio Automático");
        System.out.println(" 0. Voltar");
        System.out.println("═".repeat(65));
        System.out.print("Escolha uma opção: ");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                executarSimulacaoCenario();
                break;
            case 2:
                detectarGastosAnormais();
                break;
            case 3:
                sugerirEconomias();
                break;
            case 4:
                projetarSaldo();
                break;
            case 5:
                executarRateioAutomatico();
                break;
            case 0:
                return;
            default:
                System.out.println("\nOpção inválida!");
        }
    }
    
    private static void menuRelatorios() {
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                        RELATÓRIOS");
        System.out.println("═".repeat(65));
        System.out.println(" 1. Relatório por Período");
        System.out.println(" 2. Comparativo por Categoria");
        System.out.println(" 3. Ranking de Despesas");
        System.out.println(" 4. Evolução de Saldo");
        System.out.println(" 5. Relatório Completo");
        System.out.println(" 6. Exportar Relatório");
        System.out.println(" 7. Resumo de Grupo");
        System.out.println(" 0. Voltar");
        System.out.println("═".repeat(65));
        System.out.print("Escolha uma opção: ");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                gerarRelatorioPorPeriodo();
                break;
            case 2:
                gerarRelatorioComparativoCategoria();
                break;
            case 3:
                gerarRelatorioRankingDespesas();
                break;
            case 4:
                gerarRelatorioEvolucaoSaldo();
                break;
            case 5:
                gerarRelatorioCompleto();
                break;
            case 6:
                exportarRelatorio();
                break;
            case 7:
                gerarRelatorioResumoGrupo();
                break;
            case 0:
                return;
            default:
                System.out.println("\nOpção inválida!");
        }
    }
    
    // ==================== IMPLEMENTAÇÃO DAS FUNCIONALIDADES ====================
    
    private static void cadastrarUsuarioIndividual() {
        System.out.print("\nNome: ");
        String nome = scanner.nextLine();
        
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        UsuarioIndividual usuario = new UsuarioIndividual(nome, cpf, email);
        gerenciador.adicionarUsuario(usuario);
        
        System.out.println("\n✓ Usuário cadastrado com sucesso!");
    }
    
    private static void cadastrarGrupo() {
        System.out.print("\nNome do Grupo: ");
        String nome = scanner.nextLine();
        
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        
        Grupo grupo = new Grupo(nome, descricao);
        gerenciador.adicionarUsuario(grupo);
        
        System.out.println("\nGrupo cadastrado com sucesso!");
    }
    
    private static void listarUsuarios() {
        List<Usuario> usuarios = gerenciador.getUsuarios();
        
        if (usuarios.isEmpty()) {
            System.out.println("\nNenhum usuário cadastrado.");
            return;
        }
        
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                    LISTA DE USUÁRIOS");
        System.out.println("═".repeat(65));
        
        for (Usuario u : usuarios) {
            System.out.println("ID: " + u.getId() + " | Nome: " + u.getNome());
        }
    }
    
    private static void criarConta(String tipo) {
        Usuario titular = selecionarUsuario();
        if (titular == null) return;
        
        System.out.print("\nNúmero da Conta: ");
        String numero = scanner.nextLine();
        
        try {
            ContaFinanceira conta = ContaFactory.criarConta(tipo, numero, titular);
            gerenciador.adicionarConta(conta);
            System.out.println("\nConta criada com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao criar conta: " + e.getMessage());
        }
    }
    
    private static void listarContas() {
        List<ContaFinanceira> contas = gerenciador.getContas();
        
        if (contas.isEmpty()) {
            System.out.println("\nNenhuma conta cadastrada.");
            return;
        }
        
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                     LISTA DE CONTAS");
        System.out.println("═".repeat(65));
        
        for (ContaFinanceira c : contas) {
            System.out.println("Número: " + c.getNumeroConta() + 
                             " | Tipo: " + c.getTipoConta() + 
                             " | Saldo: R$ " + String.format("%.2f", c.consultarSaldo()));
        }
    }
    
    private static void registrarReceita() {
        ContaFinanceira conta = selecionarConta();
        if (conta == null) return;
        
        System.out.print("\nDescrição: ");
        String descricao = scanner.nextLine();
        
        System.out.print("Valor: R$ ");
        double valor = lerDouble();
        
        System.out.print("Data (dd/MM/yyyy) [Enter para hoje]: ");
        LocalDate data = lerData();
        
        System.out.print("É uma receita recorrente (mensal)? (S/N): ");
        String respRecorrente = scanner.nextLine();
        boolean recorrente = respRecorrente.equalsIgnoreCase("S");

        Transacao transacao = new Transacao(TipoTransacao.RECEITA, null, valor, descricao, 
                                           conta.getTitular(), conta);
        transacao.setData(data);
        transacao.setRecorrente(recorrente);
        gerenciador.adicionarTransacao(transacao);
        
        System.out.println("\nReceita registrada com sucesso!");
    }
    
    private static void registrarDespesa() {
        ContaFinanceira conta = selecionarConta();
        if (conta == null) return;
        
        System.out.print("\nDescrição: ");
        String descricao = scanner.nextLine();
        
        System.out.print("Valor: R$ ");
        double valor = lerDouble();
        
        Categoria categoria = selecionarCategoria();
        if (categoria == null) return;
        
        System.out.print("Data (dd/MM/yyyy) [Enter para hoje]: ");
        LocalDate data = lerData();

        System.out.print("É uma compra parcelada? (S/N): ");
        String respParcela = scanner.nextLine();
        boolean parcelado = respParcela.equalsIgnoreCase("S");

        if (parcelado) {
            System.out.print("Número de parcelas: ");
            int numParcelas = lerOpcao();
            
            double valorParcela = valor / numParcelas;
            
            for (int i = 1; i <= numParcelas; i++) {
                Transacao transacao = new Transacao(TipoTransacao.DESPESA, categoria, valorParcela, descricao, 
                                                   conta.getTitular(), conta);
                transacao.setData(data.plusMonths(i - 1));
                transacao.setParcelas(numParcelas);
                transacao.setParcelaAtual(i);
                gerenciador.adicionarTransacao(transacao);
            }
            System.out.println("\nDespesa parcelada em " + numParcelas + "x registrada com sucesso!");
        } else {
            System.out.print("É uma despesa recorrente (mensal)? (S/N): ");
            String respRecorrente = scanner.nextLine();
            boolean recorrente = respRecorrente.equalsIgnoreCase("S");

            Transacao transacao = new Transacao(TipoTransacao.DESPESA, categoria, valor, descricao, 
                                               conta.getTitular(), conta);
            transacao.setData(data);
            transacao.setRecorrente(recorrente);
            gerenciador.adicionarTransacao(transacao);
            
            System.out.println("\nDespesa registrada com sucesso!");
        }
    }
    
    private static void listarTransacoes() {
        List<Transacao> transacoes = gerenciador.getTransacoes();
        
        if (transacoes.isEmpty()) {
            System.out.println("\nNenhuma transação registrada.");
            return;
        }
        
        System.out.println("\n" + "═".repeat(80));
        System.out.println("                        LISTA DE TRANSAÇÕES");
        System.out.println("═".repeat(80));
        
        for (int i = 0; i < transacoes.size(); i++) {
            Transacao t = transacoes.get(i);
            System.out.printf("%d. %s | %s | %s | R$ %.2f%s\n", 
                i + 1,
                t.getData(),
                t.getTipo(),
                t.getDescricao(),
                t.getValor(),
                t.isEstornada() ? " [ESTORNADA]" : "");
        }
    }
    
    private static void estornarTransacao() {
        listarTransacoes();
        
        System.out.print("\nNúmero da transação a estornar: ");
        int indice = lerOpcao() - 1;
        
        List<Transacao> transacoes = gerenciador.getTransacoes();
        
        if (indice < 0 || indice >= transacoes.size()) {
            System.out.println("\nTransação inválida!");
            return;
        }
        
        try {
            Transacao t = transacoes.get(indice);
            t.estornar();
            System.out.println("\nTransação estornada com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }
    
    private static void adicionarAnexoTransacao() {
        listarTransacoes();
        
        System.out.print("\nNúmero da transação: ");
        int indice = lerOpcao() - 1;
        
        List<Transacao> transacoes = gerenciador.getTransacoes();
        
        if (indice < 0 || indice >= transacoes.size()) {
            System.out.println("\nTransação inválida!");
            return;
        }
        
        System.out.print("Caminho do anexo: ");
        String caminho = scanner.nextLine();
        
        transacoes.get(indice).adicionarAnexo(caminho);
        System.out.println("\nnexo adicionado com sucesso!");
    }
    
    private static void criarMeta() {
        Usuario usuario = selecionarUsuario();
        if (usuario == null) return;
        
        System.out.print("\nDescrição da Meta: ");
        String descricao = scanner.nextLine();
        
        System.out.print("Valor Alvo: R$ ");
        double valorAlvo = lerDouble();
        
        System.out.print("Data Limite (dd/MM/yyyy): ");
        LocalDate dataLimite = lerData();
        
        Categoria categoria = selecionarCategoria();
        if (categoria == null) return;
        
        Meta meta = new Meta(descricao, categoria, valorAlvo, dataLimite, usuario);
        gerenciador.adicionarMeta(meta);
        
        System.out.println("\nMeta criada com sucesso!");
    }
    
    private static void listarMetas() {
        List<Meta> metas = gerenciador.getMetas();
        
        if (metas.isEmpty()) {
            System.out.println("\nNenhuma meta cadastrada.");
            return;
        }
        
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                      LISTA DE METAS");
        System.out.println("═".repeat(65));
        
        for (int i = 0; i < metas.size(); i++) {
            Meta m = metas.get(i);
            System.out.printf("%d. %s | Progresso: %.2f%% | Status: %s\n",
                i + 1,
                m.getDescricao(),
                m.calcularProgresso(),
                m.verificarStatus());
        }
    }
    
    private static void contribuirMeta() {
        listarMetas();
        
        System.out.print("\nNúmero da meta: ");
        int indice = lerOpcao() - 1;
        
        List<Meta> metas = gerenciador.getMetas();
        
        if (indice < 0 || indice >= metas.size()) {
            System.out.println("\nMeta inválida!");
            return;
        }
        
        System.out.print("Valor da contribuição: R$ ");
        double valor = lerDouble();
        
        metas.get(indice).contribuir(valor);
        System.out.println("\nContribuição registrada com sucesso!");
    }
    
    private static void verificarStatusMetas() {
        gerenciador.verificarAlertas();
    }
    
    private static void criarOrcamento() {
        System.out.print("\nNome do Orçamento: ");
        String nome = scanner.nextLine();
        
        Categoria categoria = selecionarCategoria();
        if (categoria == null) return;
        
        System.out.print("Limite de Valor: R$ ");
        double limite = lerDouble();
        
        System.out.print("Mês/Ano (MM/yyyy): ");
        YearMonth mesRef = lerMesAno();
        
        Usuario usuario = selecionarUsuario();
        if (usuario == null) return;
        
        Orcamento orcamento = new Orcamento(nome, categoria, limite, mesRef, usuario);
        gerenciador.adicionarOrcamento(orcamento);
        
        System.out.println("\nOrçamento criado com sucesso!");
    }
    
    private static void listarOrcamentos() {
        List<Orcamento> orcamentos = gerenciador.getOrcamentos();
        
        if (orcamentos.isEmpty()) {
            System.out.println("\nNenhum orçamento cadastrado.");
            return;
        }
        
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                   LISTA DE ORÇAMENTOS");
        System.out.println("═".repeat(65));
        
        for (Orcamento o : orcamentos) {
            System.out.printf("%s | Categoria: %s | %.2f%% usado%s\n",
                o.getNome(),
                o.getCategoria(),
                o.getPercentualGasto(),
                o.isEstourado() ? " [ESTOURADO!]" : "");
        }
    }
    
    private static void verificarStatusOrcamentos() {
        gerenciador.verificarAlertas();
    }
    
    private static void criarCofrinho() {
        Usuario titular = selecionarUsuario();
        if (titular == null) return;
        
        System.out.print("\nNúmero da Conta: ");
        String numero = scanner.nextLine();
        
        System.out.print("Objetivo: ");
        String objetivo = scanner.nextLine();
        
        System.out.print("Meta de Valor: R$ ");
        double meta = lerDouble();
        
        System.out.print("Prazo (dias): ");
        int dias = lerOpcao();
        LocalDate prazo = LocalDate.now().plusDays(dias);
        
        Cofrinho cofrinho = new Cofrinho(numero, titular, objetivo, meta, prazo);
        gerenciador.adicionarConta(cofrinho);
        
        System.out.println("\nCofrinho criado com sucesso!");
    }
    
    private static void listarCofrinhos() {
        List<ContaFinanceira> contas = gerenciador.getContas();
        List<Cofrinho> cofrinhos = new ArrayList<>();
        
        for (ContaFinanceira c : contas) {
            if (c instanceof Cofrinho) {
                cofrinhos.add((Cofrinho) c);
            }
        }
        
        if (cofrinhos.isEmpty()) {
            System.out.println("\nNenhum cofrinho cadastrado.");
            return;
        }
        
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                   LISTA DE COFRINHOS");
        System.out.println("═".repeat(65));
        
        for (int i = 0; i < cofrinhos.size(); i++) {
            Cofrinho c = cofrinhos.get(i);
            System.out.printf("%d. %s | Saldo: R$ %.2f | Progresso: %.2f%%\n",
                i + 1,
                c.getObjetivo(),
                c.consultarSaldo(),
                c.calcularProgressoMeta());
        }
    }
    
    private static void depositarCofrinho() {
        Cofrinho cofrinho = selecionarCofrinho();
        if (cofrinho == null) return;
        
        System.out.print("\nValor: R$ ");
        double valor = lerDouble();
        
        cofrinho.depositar(valor);
        System.out.println("\nDepósito realizado com sucesso!");
    }
    
    private static void sacarCofrinho() {
        Cofrinho cofrinho = selecionarCofrinho();
        if (cofrinho == null) return;
        
        System.out.print("\nValor: R$ ");
        double valor = lerDouble();
        
        try {
            boolean sucesso = cofrinho.sacar(valor);
            if (sucesso) {
                System.out.println("\nSaque realizado com sucesso!");
            } else {
                System.out.println("\nSaldo insuficiente!");
            }
        } catch (Exception e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }
    
    private static void verificarProgressoCofrinho() {
        Cofrinho cofrinho = selecionarCofrinho();
        if (cofrinho == null) return;
        
        System.out.println("\n" + "═".repeat(65));
        System.out.println("Objetivo: " + cofrinho.getObjetivo());
        System.out.println("Saldo Atual: R$ " + String.format("%.2f", cofrinho.consultarSaldo()));
        System.out.println("Meta: R$ " + String.format("%.2f", cofrinho.getMetaValor()));
        System.out.println("Progresso: " + String.format("%.2f%%", cofrinho.calcularProgressoMeta()));
        System.out.println("Dias Restantes: " + cofrinho.calcularDiasRestantes());
        System.out.println("═".repeat(65));
    }
    
    private static void executarSimulacaoCenario() {
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                 SIMULAÇÃO DE CENÁRIO");
        System.out.println("═".repeat(65));
        System.out.println(" 1. Simular mudança em categoria específica");
        System.out.println(" 2. Simular mudança global de gastos");
        System.out.println(" 3. Simular nova despesa recorrente");
        System.out.println(" 0. Voltar");
        System.out.println("═".repeat(65));
        System.out.print("Escolha uma opção: ");
        
        int opcao = lerOpcao();
        
        SimulacaoCenario simulador = new SimulacaoCenario();
        List<Transacao> transacoes = gerenciador.getTransacoes();
        
        switch (opcao) {
            case 1:
                Categoria cat = selecionarCategoria();
                if (cat == null) return;
                
                System.out.print("Saldo atual: R$ ");
                double saldoAtual = lerDouble();
                
                System.out.print("Percentual de mudança (ex: 10 para +10%, -20 para -20%): ");
                double perc = lerDouble();
                
                System.out.print("Meses para simular: ");
                int meses = lerOpcao();
                
                var resultado1 = simulador.simularMudancaGastos(transacoes, saldoAtual, cat, perc, meses);
                System.out.println(resultado1);
                break;
                
            case 2:
                System.out.print("Saldo atual: R$ ");
                double saldo = lerDouble();
                
                System.out.print("Percentual de mudança global: ");
                double percGlobal = lerDouble();
                
                System.out.print("Meses para simular: ");
                int mesesGlobal = lerOpcao();
                
                var resultado2 = simulador.simularMudancaGlobal(transacoes, saldo, percGlobal, mesesGlobal);
                System.out.println(resultado2);
                break;
                
            case 3:
                System.out.print("Saldo atual: R$ ");
                double saldoNovo = lerDouble();
                
                System.out.print("Valor da nova despesa mensal: R$ ");
                double valorMensal = lerDouble();
                
                System.out.print("Meses para simular: ");
                int mesesNovo = lerOpcao();
                
                var resultado3 = simulador.simularNovaDespesa(transacoes, saldoNovo, valorMensal, mesesNovo);
                System.out.println(resultado3);
                break;
        }
    }
    
    private static void detectarGastosAnormais() {
        DetectorGastosAnormais detector = new DetectorGastosAnormais();
        List<Transacao> transacoes = gerenciador.getTransacoes();
        
        var resultado = detector.calcular(transacoes);
        System.out.println("\n" + resultado);
    }
    
    private static void sugerirEconomias() {
        SugestaoEconomia sugestao = new SugestaoEconomia();
        List<Transacao> transacoes = gerenciador.getTransacoes();
        
        var resultado = sugestao.calcular(transacoes);
        System.out.println("\n" + resultado);
    }
    
    private static void projetarSaldo() {
        List<Transacao> transacoes = gerenciador.getTransacoes();
        
        System.out.print("\nMeses para projetar: ");
        int meses = lerOpcao();
        
        ProjecaoSaldo projecao = new ProjecaoSaldo(meses);
        double resultado = projecao.calcular(transacoes);
        System.out.println(String.format("\nSaldo projetado em %d meses: R$ %.2f", meses, resultado));
    }
    
    private static void executarRateioAutomatico() {
        RateioAutomatico rateio = new RateioAutomatico();
        
        System.out.print("\nValor total a ratear: R$ ");
        double valorTotal = lerDouble();
        
        System.out.print("Número de pessoas: ");
        int numPessoas = lerOpcao();
        
        List<Double> pesos = new ArrayList<>();
        for (int i = 0; i < numPessoas; i++) {
            System.out.print("Peso da pessoa " + (i + 1) + ": ");
            pesos.add(lerDouble());
        }
        
        System.out.println("\n=== RATEIO AUTOMÁTICO ===");
        double pesoTotal = pesos.stream().mapToDouble(Double::doubleValue).sum();
        for (int i = 0; i < numPessoas; i++) {
            double valorPessoa = valorTotal * (pesos.get(i) / pesoTotal);
            System.out.println(String.format("Pessoa %d (peso %.1f): R$ %.2f", 
                i + 1, pesos.get(i), valorPessoa));
        }
    }
    
    private static void gerarRelatorioPorPeriodo() {
        inicializarGeradorRelatorios();
        
        System.out.print("\nData Início (dd/MM/yyyy): ");
        LocalDate inicio = lerData();
        
        System.out.print("Data Fim (dd/MM/yyyy): ");
        LocalDate fim = lerData();
        
        String relatorio = geradorRelatorios.gerarRelatorioPorPeriodo(inicio, fim);
        System.out.println("\n" + relatorio);
        
        pausar();
    }
    
    private static void gerarRelatorioComparativoCategoria() {
        inicializarGeradorRelatorios();
        
        System.out.print("\nData Início (dd/MM/yyyy): ");
        LocalDate inicio = lerData();
        
        System.out.print("Data Fim (dd/MM/yyyy): ");
        LocalDate fim = lerData();
        
        String relatorio = geradorRelatorios.gerarRelatorioComparativoCategoria(inicio, fim);
        System.out.println("\n" + relatorio);
        
        pausar();
    }
    
    private static void gerarRelatorioRankingDespesas() {
        inicializarGeradorRelatorios();
        
        System.out.print("\nData Início (dd/MM/yyyy): ");
        LocalDate inicio = lerData();
        
        System.out.print("Data Fim (dd/MM/yyyy): ");
        LocalDate fim = lerData();
        
        System.out.print("Quantas despesas exibir (Top N): ");
        int topN = lerOpcao();
        
        String relatorio = geradorRelatorios.gerarRelatorioRankingDespesas(inicio, fim, topN);
        System.out.println("\n" + relatorio);
        
        pausar();
    }
    
    private static void gerarRelatorioEvolucaoSaldo() {
        inicializarGeradorRelatorios();
        
        System.out.print("\nData Início (dd/MM/yyyy): ");
        LocalDate inicio = lerData();
        
        System.out.print("Data Fim (dd/MM/yyyy): ");
        LocalDate fim = lerData();
        
        System.out.print("Saldo Inicial: R$ ");
        double saldoInicial = lerDouble();
        
        String relatorio = geradorRelatorios.gerarRelatorioEvolucaoSaldo(inicio, fim, saldoInicial);
        System.out.println("\n" + relatorio);
        
        pausar();
    }
    
    private static void gerarRelatorioCompleto() {
        inicializarGeradorRelatorios();
        
        System.out.print("\nData Início (dd/MM/yyyy): ");
        LocalDate inicio = lerData();
        
        System.out.print("Data Fim (dd/MM/yyyy): ");
        LocalDate fim = lerData();
        
        System.out.print("Saldo Inicial: R$ ");
        double saldoInicial = lerDouble();
        
        String relatorio = geradorRelatorios.gerarRelatorioCompleto(inicio, fim, saldoInicial);
        System.out.println("\n" + relatorio);
        
        pausar();
    }
    
    private static void exportarRelatorio() {
        inicializarGeradorRelatorios();
        
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                  EXPORTAR RELATÓRIO");
        System.out.println("═".repeat(65));
        System.out.println(" 1. Por Período");
        System.out.println(" 2. Comparativo por Categoria");
        System.out.println(" 3. Ranking de Despesas");
        System.out.println(" 4. Evolução de Saldo");
        System.out.println(" 5. Completo");
        System.out.println(" 0. Voltar");
        System.out.println("═".repeat(65));
        System.out.print("Escolha uma opção: ");
        
        int opcao = lerOpcao();
        
        if (opcao == 0) return;
        
        System.out.print("\nData Início (dd/MM/yyyy): ");
        LocalDate inicio = lerData();
        
        System.out.print("Data Fim (dd/MM/yyyy): ");
        LocalDate fim = lerData();
        
        System.out.print("Nome do arquivo (sem extensão): ");
        String nomeArquivo = scanner.nextLine();
        String caminhoArquivo = nomeArquivo + ".txt";
        
        try {
            switch (opcao) {
                case 1:
                    geradorRelatorios.exportarRelatorioPorPeriodo(inicio, fim, caminhoArquivo);
                    break;
                case 2:
                    geradorRelatorios.exportarRelatorioComparativoCategoria(inicio, fim, caminhoArquivo);
                    break;
                case 3:
                    System.out.print("Top N despesas: ");
                    int topN = lerOpcao();
                    geradorRelatorios.exportarRelatorioRankingDespesas(inicio, fim, topN, caminhoArquivo);
                    break;
                case 4:
                    System.out.print("Saldo Inicial: R$ ");
                    double saldoInicial = lerDouble();
                    geradorRelatorios.exportarRelatorioEvolucaoSaldo(inicio, fim, saldoInicial, caminhoArquivo);
                    break;
                case 5:
                    System.out.print("Saldo Inicial: R$ ");
                    double saldo = lerDouble();
                    geradorRelatorios.exportarRelatorioCompleto(inicio, fim, saldo, caminhoArquivo);
                    break;
            }
            
            System.out.println("\nRelatório exportado com sucesso para: " + caminhoArquivo);
        } catch (Exception e) {
            System.out.println("\nErro ao exportar relatório: " + e.getMessage());
        }
    }
    
    // ==================== MÉTODOS AUXILIARES ====================
    
    private static void configurarObservadores() {
        gerenciador.adicionarObservador(new NotificadorConsole("Sistema"));
        gerenciador.adicionarObservador(new NotificadorEmail("admin@sistema.com"));
        gerenciador.adicionarObservador(new NotificadorPush("Usuario Principal"));
    }
    
    private static void carregarDados() {
        if (persistencia.existeDadosSalvos()) {
            System.out.println("\nCarregando dados salvos...");
            try {
                var dados = persistencia.carregarDados();
                
                // Carrega dados no gerenciador
                @SuppressWarnings("unchecked")
                List<Usuario> usuarios = (List<Usuario>) dados.get("usuarios");
                for (Usuario u : usuarios) {
                    gerenciador.adicionarUsuario(u);
                }
                for (ContaFinanceira c : (List<ContaFinanceira>) dados.get("contas")) {
                    gerenciador.adicionarConta(c);
                }
                for (Transacao t : (List<Transacao>) dados.get("transacoes")) {
                    gerenciador.adicionarTransacao(t);
                }
                for (Meta m : (List<Meta>) dados.get("metas")) {
                    gerenciador.adicionarMeta(m);
                }
                for (Orcamento o : (List<Orcamento>) dados.get("orcamentos")) {
                    gerenciador.adicionarOrcamento(o);
                }
                
                System.out.println("Dados carregados com sucesso!");
                
                // Processar recorrências após carregar
                gerenciador.processarRecorrencias();
                
            } catch (Exception e) {
                System.out.println("Erro ao carregar dados: " + e.getMessage());
            }
        }
    }
    
    private static void salvarDados() {
        try {
            persistencia.salvarDados(
                gerenciador.getUsuarios(),
                gerenciador.getContas(),
                gerenciador.getTransacoes(),
                gerenciador.getMetas(),
                gerenciador.getOrcamentos()
            );
            System.out.println("\nDados salvos com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao salvar dados: " + e.getMessage());
        }
    }
    
    private static void inicializarGeradorRelatorios() {
        if (geradorRelatorios == null) {
            geradorRelatorios = new GerenciadorRelatorios(gerenciador.getTransacoes());
        } else {
            geradorRelatorios.atualizarTransacoes(gerenciador.getTransacoes());
        }
    }
    
    private static Usuario selecionarUsuario() {
        listarUsuarios();
        
        List<Usuario> usuarios = gerenciador.getUsuarios();
        if (usuarios.isEmpty()) return null;
        
        System.out.print("\nSelecione o ID do usuário: ");
        String id = scanner.nextLine();
        
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        
        System.out.println("\nUsuário não encontrado!");
        return null;
    }
    
    private static ContaFinanceira selecionarConta() {
        listarContas();
        
        List<ContaFinanceira> contas = gerenciador.getContas();
        if (contas.isEmpty()) return null;
        
        System.out.print("\nNúmero da conta: ");
        String numero = scanner.nextLine();
        
        for (ContaFinanceira c : contas) {
            if (c.getNumeroConta().equals(numero)) {
                return c;
            }
        }
        
        System.out.println("\nConta não encontrada!");
        return null;
    }
    
    private static Cofrinho selecionarCofrinho() {
        listarCofrinhos();
        
        System.out.print("\nNúmero do cofrinho: ");
        int indice = lerOpcao() - 1;
        
        List<ContaFinanceira> contas = gerenciador.getContas();
        List<Cofrinho> cofrinhos = new ArrayList<>();
        
        for (ContaFinanceira c : contas) {
            if (c instanceof Cofrinho) {
                cofrinhos.add((Cofrinho) c);
            }
        }
        
        if (indice < 0 || indice >= cofrinhos.size()) {
            System.out.println("\nCofrinho inválido!");
            return null;
        }
        
        return cofrinhos.get(indice);
    }
    
    private static Categoria selecionarCategoria() {
        Categoria[] categorias = Categoria.values();
        
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                     CATEGORIAS");
        System.out.println("═".repeat(65));
        
        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + ". " + categorias[i].name());
        }
        
        System.out.print("\nSelecione a categoria: ");
        int opcao = lerOpcao() - 1;
        
        if (opcao < 0 || opcao >= categorias.length) {
            System.out.println("\nCategoria inválida!");
            return null;
        }
        
        return categorias[opcao];
    }
    
    private static int lerOpcao() {
        try {
            int opcao = Integer.parseInt(scanner.nextLine());
            return opcao;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private static double lerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().replace(",", "."));
        } catch (NumberFormatException e) {
            System.out.println("\nValor inválido!");
            return 0.0;
        }
    }
    
    private static LocalDate lerData() {
        String input = scanner.nextLine().trim();
        
        if (input.isEmpty()) {
            return LocalDate.now();
        }
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(input, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("\nData inválida! Usando data atual.");
            return LocalDate.now();
        }
    }
    
    private static YearMonth lerMesAno() {
        try {
            String input = scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
            return YearMonth.parse(input, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("\nFormato inválido! Usando mês atual.");
            return YearMonth.now();
        }
    }
    
    private static void pausar() {
        System.out.print("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    private static void dividirDespesaRateio() {
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                  DIVIDIR DESPESA (RATEIO)");
        System.out.println("═".repeat(65));
        
        List<Usuario> usuarios = gerenciador.getUsuarios();
        List<Grupo> grupos = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u instanceof Grupo) {
                grupos.add((Grupo) u);
            }
        }
        
        if (grupos.isEmpty()) {
            System.out.println("\nNenhum grupo cadastrado.");
            return;
        }
        
        for (int i = 0; i < grupos.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, grupos.get(i).getNome());
        }
        
        System.out.print("Escolha o grupo: ");
        int indice = lerOpcao() - 1;
        
        if (indice < 0 || indice >= grupos.size()) {
            System.out.println("\nGrupo inválido!");
            return;
        }
        
        Grupo grupo = grupos.get(indice);
        
        System.out.print("Valor total da despesa: R$ ");
        double valor = lerDouble();
        
        UsuarioIndividual[] membros = grupo.getMembros();
        if (membros.length == 0) {
             System.out.println("Grupo sem membros.");
             return;
        }
        
        double valorPorPessoa = valor / membros.length;
        
        System.out.println("\n--- Resultado do Rateio (Igualitário) ---");
        for (UsuarioIndividual membro : membros) {
            System.out.printf("%s: R$ %.2f\n", membro.getNome(), valorPorPessoa);
        }
        
        System.out.println("\n(Nota: Esta é uma simulação. Nenhuma transação foi criada.)");
    }

    private static void gerarRelatorioResumoGrupo() {
        inicializarGeradorRelatorios();
        
        List<Usuario> usuarios = gerenciador.getUsuarios();
        List<Grupo> grupos = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u instanceof Grupo) {
                grupos.add((Grupo) u);
            }
        }
        
        if (grupos.isEmpty()) {
            System.out.println("\nNenhum grupo cadastrado.");
            return;
        }
        
        for (int i = 0; i < grupos.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, grupos.get(i).getNome());
        }
        
        System.out.print("Escolha o grupo: ");
        int indice = lerOpcao() - 1;
        
        if (indice < 0 || indice >= grupos.size()) {
            System.out.println("\nGrupo inválido!");
            return;
        }
        
        Grupo grupo = grupos.get(indice);
        
        System.out.print("\nData Início (dd/MM/yyyy): ");
        LocalDate inicio = lerData();
        
        System.out.print("Data Fim (dd/MM/yyyy): ");
        LocalDate fim = lerData();
        
        String relatorio = geradorRelatorios.gerarRelatorioResumoGrupo(grupo, inicio, fim);
        System.out.println("\n" + relatorio);
        
        pausar();
    }
}