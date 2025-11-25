import Entidades.*;
import Enums.*;
import Exceptions.*;
import Factory.*;
import Gerenciadores.*;
import Relatorios.*;
import Strategy.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    private static GerenciadorFinanceiro gerenciador = GerenciadorFinanceiro.getInstancia();
    private static GerenciadorRelatorios geradorRelatorios;
    private static GerenciadorPersistencia persistencia = new GerenciadorPersistencia();
    
    public static void main(String[] args) {
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
        System.out.println("                    MENU USUARIOS");
        System.out.println("═".repeat(65));
        System.out.println(" 1. Cadastrar Usuario Individual");
        System.out.println(" 2. Cadastrar Grupo");
        System.out.println(" 3. Listar Usuarios");
        System.out.println(" 0. Voltar");
        System.out.println("═".repeat(65));
        System.out.print("Escolha uma opcao: ");
        
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
                System.out.println("\nOpcao invalida!");
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
                criarContaCorrente();
                break;
            case 2:
                criarContaDigital();
                break;
            case 3:
                criarCartaoCredito();
                break;
            case 4:
                criarCarteiraInvestimento();
                break;
            case 5:
                criarCofrinho();
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
        System.out.println(" 1. Projecao de Saldo");
        System.out.println(" 2. Rateio Automatico");
        System.out.println(" 3. Sugestoes de Economia");
        System.out.println(" 0. Voltar");
        System.out.println("═".repeat(65));
        System.out.print("Escolha uma opcao: ");
        
        int opcao = lerOpcao();
        
        switch (opcao) {
            case 1:
                projetarSaldo();
                break;
            case 2:
                executarRateioAutomatico();
                break;
            case 3:
                sugerirEconomias();
                break;
            case 0:
                return;
            default:
                System.out.println("\nOpcao invalida!");
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
    
    private static void cadastrarUsuarioIndividual() {
        System.out.print("\nNome: ");
        String nome = scanner.nextLine();
        
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        UsuarioIndividual usuario = new UsuarioIndividual(nome, cpf, email);
        gerenciador.adicionarUsuario(usuario);
        
        System.out.println("\nUsuário cadastrado com sucesso!");
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
    
    private static void criarContaCorrente() {
        Usuario titular = selecionarUsuario();
        if (titular == null) return;
        
        System.out.print("\nNúmero da Conta: ");
        String numero = scanner.nextLine();
        
        System.out.print("Limite do Cheque Especial [Enter para R$ 500]: ");
        String limiteStr = scanner.nextLine();
        double limite = limiteStr.isEmpty() ? 500.0 : Double.parseDouble(limiteStr);
        
        try {
            ContaFinanceira conta = ContaFactory.criarConta("corrente", numero, titular, limite);
            gerenciador.adicionarConta(conta);
            System.out.println("\nConta criada com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao criar conta: " + e.getMessage());
        }
    }
    
    private static void criarContaDigital() {
        Usuario titular = selecionarUsuario();
        if (titular == null) return;
        
        System.out.print("\nNúmero da Conta: ");
        String numero = scanner.nextLine();
        
        System.out.print("Rendimento mensal (%) [Enter para 0.5]: ");
        String rendStr = scanner.nextLine();
        double rendimento = rendStr.isEmpty() ? 0.5 : Double.parseDouble(rendStr);
        
        try {
            ContaFinanceira conta = ContaFactory.criarConta("digital", numero, titular, rendimento);
            gerenciador.adicionarConta(conta);
            System.out.println("\nConta criada com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao criar conta: " + e.getMessage());
        }
    }
    
    private static void criarCartaoCredito() {
        Usuario titular = selecionarUsuario();
        if (titular == null) return;
        
        System.out.print("\nNúmero do Cartão: ");
        String numero = scanner.nextLine();
        
        System.out.print("Limite (R$): ");
        double limite = lerDouble();
        
        System.out.print("Dia do fechamento (1-28): ");
        int diaFechamento = lerOpcao();
        
        System.out.print("Dia do vencimento (1-28): ");
        int diaVencimento = lerOpcao();
        
        LocalDate fechamento = LocalDate.now().withDayOfMonth(diaFechamento);
        LocalDate vencimento = LocalDate.now().withDayOfMonth(diaVencimento);
        
        try {
            ContaFinanceira conta = ContaFactory.criarConta("credito", numero, titular, limite, fechamento, vencimento);
            gerenciador.adicionarConta(conta);
            System.out.println("\nCartão criado com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao criar cartão: " + e.getMessage());
        }
    }
    
    private static void criarCarteiraInvestimento() {
        Usuario titular = selecionarUsuario();
        if (titular == null) return;
        
        System.out.print("\nNúmero da Carteira: ");
        String numero = scanner.nextLine();
        
        System.out.println("\nTipo de Investimento:");
        System.out.println("1. Conservador (0.5% a.m.)");
        System.out.println("2. Moderado (1.0% a.m.)");
        System.out.println("3. Agressivo (2.0% a.m.)");
        System.out.print("Escolha: ");
        
        int tipo = lerOpcao();
        String tipoInv = switch(tipo) {
            case 1 -> "Conservador";
            case 2 -> "Moderado";
            case 3 -> "Agressivo";
            default -> "Conservador";
        };
        
        try {
            ContaFinanceira conta = ContaFactory.criarConta("investimento", numero, titular, tipoInv);
            gerenciador.adicionarConta(conta);
            System.out.println("\nCarteira criada com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao criar carteira: " + e.getMessage());
        }
    }
    
    private static void criarCofrinho() {
        Usuario titular = selecionarUsuario();
        if (titular == null) return;
        
        System.out.print("\nNúmero do Cofrinho: ");
        String numero = scanner.nextLine();
        
        System.out.print("Objetivo: ");
        String objetivo = scanner.nextLine();
        
        System.out.print("Meta (R$): ");
        double meta = lerDouble();
        
        try {
            ContaFinanceira conta = ContaFactory.criarConta("cofrinho", numero, titular, objetivo, meta);
            gerenciador.adicionarConta(conta);
            System.out.println("\nCofrinho criado com sucesso!");
        } catch (Exception e) {
            System.out.println("\nErro ao criar cofrinho: " + e.getMessage());
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
        System.out.println("\nAnexo adicionado com sucesso!");
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
        List<Meta> metasAtrasadas = gerenciador.getMetasAtrasadas();
        if (metasAtrasadas.isEmpty()) {
            System.out.println("\nNenhuma meta atrasada!");
        } else {
            System.out.println("\n=== METAS ATRASADAS ===");
            for (Meta meta : metasAtrasadas) {
                System.out.printf("%s - Falta: R$ %.2f\n",
                    meta.getDescricao(),
                    meta.getValorAlvo() - meta.getValorAtual());
            }
        }
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
        List<Orcamento> orcamentosEstourados = gerenciador.getOrcamentosEstourados();
        if (orcamentosEstourados.isEmpty()) {
            System.out.println("\nNenhum orcamento estourado!");
        } else {
            System.out.println("\n=== ORCAMENTOS ESTOURADOS ===");
            for (Orcamento o : orcamentosEstourados) {
                System.out.printf("%s - Excedido em R$ %.2f\n",
                    o.getNome(),
                    o.getValorUltrapassado());
            }
        }
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
            System.out.printf("%d. [Conta: %s] %s | Saldo: R$ %.2f | Progresso: %.2f%%\n",
                i + 1,
                c.getNumeroConta(),
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
        
        System.out.println("\nOrigem do depósito:");
        System.out.println("1. Depósito em Espécie (Dinheiro)");
        System.out.println("2. Transferir de outra Conta");
        System.out.print("Escolha: ");
        int opcao = lerOpcao();
        
        try {
            if (opcao == 2) {
                System.out.println("\nSelecione a conta de origem:");
                ContaFinanceira origem = selecionarConta();
                
                if (origem != null) {
                    // Tenta sacar da conta origem
                    boolean sacou = origem.sacar(valor);
                    if (sacou) {
                        cofrinho.depositar(valor);
                        System.out.println("Valor transferido com sucesso da conta " + origem.getNumeroConta());
                        
                        // Registrar a transação
                        Transacao t = new Transacao(TipoTransacao.TRANSFERENCIA, null, valor, 
                            "Aplicação em Cofrinho: " + cofrinho.getObjetivo(), 
                            origem.getTitular(), origem);
                        t.setContaDestino(cofrinho);
                        gerenciador.adicionarTransacao(t);
                    }
                } else {
                    System.out.println("Conta origem inválida!");
                }
            } else {
                // Depósito direto (espécie)
                cofrinho.depositar(valor);
                System.out.println("\nDepósito realizado com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("\nErro: " + e.getMessage());
        }
    }
    
    private static void sacarCofrinho() {
        Cofrinho cofrinho = selecionarCofrinho();
        if (cofrinho == null) return;
        
        System.out.print("\nValor: R$ ");
        double valor = lerDouble();
        
        System.out.println("\nDestino do saque:");
        System.out.println("1. Saque em Espécie (Dinheiro)");
        System.out.println("2. Transferir para outra Conta");
        System.out.print("Escolha: ");
        int opcao = lerOpcao();
        
        try {
            // Tenta realizar o saque do cofrinho primeiro
            boolean sucesso = cofrinho.sacar(valor);
            
            if (sucesso) {
                if (opcao == 2) {
                    System.out.println("\nSelecione a conta de destino:");
                    ContaFinanceira destino = selecionarConta();
                    
                    if (destino != null) {
                        destino.depositar(valor);
                        System.out.println("Valor transferido com sucesso para a conta " + destino.getNumeroConta());
                        
                        // Opcional: Registrar a transação de transferência no histórico
                        Transacao t = new Transacao(TipoTransacao.TRANSFERENCIA, null, valor, 
                            "Resgate Cofrinho: " + cofrinho.getObjetivo(), 
                            cofrinho.getTitular(), cofrinho);
                        t.setContaDestino(destino);
                        gerenciador.adicionarTransacao(t);
                        
                    } else {
                        System.out.println("Conta destino inválida! O valor foi mantido como saque em espécie.");
                    }
                }
                System.out.println("\nOperação realizada com sucesso!");
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
        
        long dias = cofrinho.calcularDiasRestantes();
        if (dias >= 0) {
            System.out.println("Dias Restantes: " + dias);
        } else {
            System.out.println("Dias Restantes: Sem prazo definido");
        }
        System.out.println("═".repeat(65));
    }
    

    
    private static void sugerirEconomias() {
        SugestaoEconomia sugestao = new SugestaoEconomia();
        List<Transacao> transacoes = gerenciador.getTransacoes();
        
        double economiaSugerida = sugestao.calcular(transacoes);
        
        if (economiaSugerida <= 0) {
             System.out.println("\nNão há dados suficientes de despesas para sugerir economias.");
             return;
        }
        
        java.util.Map<Categoria, Double> gastos = sugestao.analisarGastosPorCategoria(transacoes);
        java.util.Map.Entry<Categoria, Double> maiorGasto = gastos.entrySet().stream()
            .max(java.util.Map.Entry.comparingByValue())
            .orElse(null);
            
        System.out.println("\n" + "═".repeat(65));
        System.out.println("                  SUGESTÃO DE ECONOMIA");
        System.out.println("═".repeat(65));
        
        if (maiorGasto != null) {
             System.out.println("Categoria com maior gasto: " + maiorGasto.getKey().getNome());
             System.out.println(String.format("Total gasto nesta categoria: R$ %.2f", maiorGasto.getValue()));
             System.out.println("═".repeat(65));
             System.out.println(String.format("SUGESTÃO: Se você reduzir 15%% destes gastos,\nvocê economizará R$ %.2f.", economiaSugerida));
        }
        System.out.println("═".repeat(65));
    }
    
    private static void projetarSaldo() {
        System.out.println("\nSelecione a conta para projeção (ou 0 para todas):");
        ContaFinanceira conta = selecionarConta();
        
        List<Transacao> transacoes;
        if (conta != null) {
            transacoes = gerenciador.getTransacoes().stream()
                .filter(t -> t.getContaOrigem().equals(conta))
                .collect(java.util.stream.Collectors.toList());
            System.out.println("Projetando para a conta: " + conta.getNumeroConta());
        } else {
            transacoes = gerenciador.getTransacoes();
            System.out.println("Projetando para TODAS as contas (Geral)");
        }
        
        System.out.print("\nMeses para projetar: ");
        int meses = lerOpcao();
        
        ProjecaoSaldo projecao = new ProjecaoSaldo(meses);
        double resultado = projecao.calcular(transacoes);
        
        double saldoAtual = (conta != null) ? conta.consultarSaldo() : 
                           gerenciador.getContas().stream().mapToDouble(ContaFinanceira::consultarSaldo).sum();
                           
        System.out.println(String.format("\nSaldo Atual: R$ %.2f", saldoAtual));
        System.out.println(String.format("Saldo Projetado em %d meses: R$ %.2f", meses, saldoAtual + resultado));
        System.out.println("(Baseado na média histórica de receitas e despesas)");
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
    
    private static void carregarDados() {
        try {
            persistencia.carregarDados(gerenciador);
            System.out.println("\nDados carregados com sucesso.");
            
            // Processar recorrencias apos carregar
            List<Transacao> recorrencias = gerenciador.processarRecorrencias();
            if (!recorrencias.isEmpty()) {
                System.out.println("\n--- Recorrencias Processadas ---");
                for (Transacao t : recorrencias) {
                    System.out.println("Gerada: " + t.getDescricao() + " - " + t.getData());
                }
            }
        } catch (Exception e) {
            System.out.println("\nNenhum dado salvo encontrado ou erro ao carregar: " + e.getMessage());
            System.out.println("Iniciando com base de dados vazia.");
        }
    }
    
    private static void salvarDados() {
        try {
            persistencia.salvarDados(gerenciador);
            System.out.println("\nDados salvos com sucesso.");
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
        
        System.out.print("\nSelecione o número da lista: ");
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