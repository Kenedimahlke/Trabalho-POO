package Gerenciadores;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Interfaces.*;
import Interfaces.Observer;
import Observers.*;
import Relatorios.*;
import Repositorios.*;
import Strategy.*;
import java.time.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Singleton - Gerenciador central do sistema financeiro
public class GerenciadorFinanceiro implements Subject {
    private static GerenciadorFinanceiro instancia;
    
    // Aplicando DIP: Dependência de abstrações (interfaces)
    private final RepositorioDados<Usuario> repositorioUsuarios;
    private final RepositorioDados<ContaFinanceira> repositorioContas;
    private final RepositorioDados<Transacao> repositorioTransacoes;
    private final RepositorioDados<Meta> repositorioMetas;
    private final RepositorioDados<Orcamento> repositorioOrcamentos;
    
    private List<Observer> observadores;
    
    // CONSTRUTOR privado (Singleton)
    private GerenciadorFinanceiro() {
        // Injeção de dependência via construtor (pode ser via parâmetro também)
        this.repositorioUsuarios = new RepositorioUsuarios();
        this.repositorioContas = new RepositorioContas();
        this.repositorioTransacoes = new RepositorioTransacoes();
        this.repositorioMetas = new RepositorioMetas();
        this.repositorioOrcamentos = new RepositorioOrcamentos();
        this.observadores = new ArrayList<>();
    }
    
    // Método para obter a instância única
    public static GerenciadorFinanceiro getInstancia() {
        if (instancia == null) {
            instancia = new GerenciadorFinanceiro();
        }
        return instancia;
    }
    
    // MÉTODOS DE USUÁRIO 
    public void adicionarUsuario(Usuario usuario) {
        repositorioUsuarios.adicionar(usuario);
        notificarObservadores("USUARIO_CADASTRADO", usuario);
    }
    
    public Usuario buscarUsuarioPorId(String id) {
        return repositorioUsuarios.buscarPorId(id);
    }
    
    public Usuario buscarUsuarioPorEmail(String email) {
        if (repositorioUsuarios instanceof RepositorioUsuarios) {
            return ((RepositorioUsuarios) repositorioUsuarios).buscarPorEmail(email);
        }
        return null;
    }
    
    public List<Usuario> getUsuarios() {
        return repositorioUsuarios.listarTodos();
    }
    
    // MÉTODOS DO PADRÃO OBSERVER
    @Override
    public void adicionarObservador(Observer observer) {
        if (observer != null && !observadores.contains(observer)) {
            observadores.add(observer);
        }
    }
    
    @Override
    public void removerObservador(Observer observer) {
        observadores.remove(observer);
    }
    
    @Override
    public void notificarObservadores(String evento, Object dados) {
        for (Observer observer : observadores) {
            observer.atualizar(evento, dados);
        }
    }
    
    @Override
    public List<Observer> getObservadores() {
        return new ArrayList<>(observadores);
    }
    
    // MÉTODOS DE TRANSAÇÃO 
    public void adicionarTransacao(Transacao transacao) {
        repositorioTransacoes.adicionar(transacao);
        
        // Atualiza orçamentos se for despesa
        if (transacao.getTipo() == TipoTransacao.DESPESA) {
            atualizarOrcamentos(transacao);
        }
        
        // Notificar observadores
        notificarObservadores("Nova Transação: " + transacao.getTipo(),
            String.format("R$ %.2f - %s", transacao.getValor(), transacao.getDescricao()));
    }
    
    private void atualizarOrcamentos(Transacao transacao) {
        if (repositorioOrcamentos == null) return;
        
        List<Orcamento> orcamentos = repositorioOrcamentos.listarTodos();
        YearMonth transacaoMes = YearMonth.from(transacao.getData());
        
        for (Orcamento o : orcamentos) {
            if (o.getCategoria().equals(transacao.getCategoria()) && 
                o.getMesReferencia().equals(transacaoMes)) {
                
                o.adicionarGasto(transacao.getValor());
                
                if (o.deveEnviarAlerta()) {
                     notificarObservadores("ALERTA_ORCAMENTO", 
                        "Atenção! Orçamento de " + o.getCategoria().getNome() + " atingiu " + String.format("%.1f", o.getPercentualGasto()) + "%");
                     o.marcarAlertaEnviado();
                }
                
                if (o.isEstourado()) {
                     notificarObservadores("ALERTA_ORCAMENTO", 
                        "URGENTE! Orçamento de " + o.getCategoria().getNome() + " ESTOURADO!");
                }
            }
        }
    }
    
    public List<Transacao> getTransacoes() {
        return repositorioTransacoes.listarTodos();
    }
    
    public List<Transacao> getTransacoesDoUsuario(Usuario usuario) {
        if (repositorioTransacoes instanceof RepositorioTransacoes) {
            // Busca por conta do usuário
            return repositorioTransacoes.listarTodos().stream()
                .filter(t -> t.getConta().getTitular().equals(usuario))
                .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
    
    public List<Transacao> getTransacoesPorTipo(TipoTransacao tipo) {
        if (repositorioTransacoes instanceof RepositorioTransacoes) {
            return ((RepositorioTransacoes) repositorioTransacoes).buscarPorTipo(tipo);
        }
        return new ArrayList<>();
    }
    
    public List<Transacao> getTransacoesPorCategoria(Categoria categoria) {
        if (repositorioTransacoes instanceof RepositorioTransacoes) {
            return ((RepositorioTransacoes) repositorioTransacoes).buscarPorCategoria(categoria);
        }
        return new ArrayList<>();
    }
    
    // MÉTODOS DE META 
    public void adicionarMeta(Meta meta) {
        repositorioMetas.adicionar(meta);
        // Notificar observadores
        notificarObservadores("Nova Meta Criada",
            meta.getDescricao() + " - R$ " + String.format("%.2f", meta.getValorAlvo()));
    }
    
    public List<Meta> getMetas() {
        return repositorioMetas.listarTodos();
    }
    
    public List<Meta> getMetasDoUsuario(Usuario usuario) {
        if (repositorioMetas instanceof RepositorioMetas) {
            return ((RepositorioMetas) repositorioMetas).buscarPorUsuario(usuario);
        }
        return new ArrayList<>();
    }
    
    public List<Meta> getMetasAtrasadas() {
        if (repositorioMetas instanceof RepositorioMetas) {
            return ((RepositorioMetas) repositorioMetas).buscarAtrasadas();
        }
        return new ArrayList<>();
    }
    
    public List<Meta> getMetasAlcancadas() {
        if (repositorioMetas instanceof RepositorioMetas) {
            return ((RepositorioMetas) repositorioMetas).buscarConcluidas();
        }
        return new ArrayList<>();
    }
    
    // MÉTODOS DE CONTA 
    public void adicionarConta(ContaFinanceira conta) {
        repositorioContas.adicionar(conta);
        // Notificar observadores
        notificarObservadores("Nova Conta Criada",
            conta.getTipoConta() + " - " + conta.getNumeroConta());
    }
    
    public List<ContaFinanceira> getContas() {
        return repositorioContas.listarTodos();
    }
    
    public List<ContaFinanceira> getContasDoUsuario(Usuario usuario) {
        if (repositorioContas instanceof RepositorioContas) {
            return ((RepositorioContas) repositorioContas).buscarPorTitular(usuario);
        }
        return new ArrayList<>();
    }
    
    public ContaFinanceira buscarContaPorNumero(String numeroConta) {
        return repositorioContas.buscarPorId(numeroConta);
    }
    
    // MÉTODOS DE ANÁLISE
    public double calcularSaldoTotal() {
        if (repositorioContas instanceof RepositorioContas) {
            return ((RepositorioContas) repositorioContas).calcularSaldoTotal();
        }
        return 0.0;
    }
    
    public double calcularSaldoTotal(Usuario usuario) {
        return getContasDoUsuario(usuario).stream()
            .mapToDouble(ContaFinanceira::consultarSaldo)
            .sum();
    }
    
    public double calcularTotalReceitas(Usuario usuario) {
        return getTransacoesDoUsuario(usuario).stream()
            .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
            .mapToDouble(Transacao::getValor)
            .sum();
    }
    
    public double calcularTotalDespesas(Usuario usuario) {
        return getTransacoesDoUsuario(usuario).stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .mapToDouble(Transacao::getValor)
            .sum();
    }
    
    // MÉTODOS DE ORÇAMENTO 
    public void adicionarOrcamento(Orcamento orcamento) {
        repositorioOrcamentos.adicionar(orcamento);
        notificarObservadores("Novo Orçamento Criado", orcamento.getNome());
    }
    
    public List<Orcamento> getOrcamentos() {
        return repositorioOrcamentos.listarTodos();
    }
    
    public List<Orcamento> getOrcamentosDoUsuario(Usuario usuario) {
        // Orçamento não tem usuário diretamente - filtrar por outro critério se necessário
        return repositorioOrcamentos.listarTodos();
    }
    
    public List<Orcamento> getOrcamentosEstourados() {
        if (repositorioOrcamentos instanceof RepositorioOrcamentos) {
            return ((RepositorioOrcamentos) repositorioOrcamentos).buscarEstourados();
        }
        return new ArrayList<>();
    }
    
    public List<Orcamento> getOrcamentosProximosDoLimite() {
        if (repositorioOrcamentos instanceof RepositorioOrcamentos) {
            return ((RepositorioOrcamentos) repositorioOrcamentos).buscarProximosDoLimite(80.0);
        }
        return new ArrayList<>();
    }
    
    public Orcamento buscarOrcamentoPorCategoria(Usuario usuario, Categoria categoria) {
        if (repositorioOrcamentos instanceof RepositorioOrcamentos) {
            return ((RepositorioOrcamentos) repositorioOrcamentos)
                .buscarPorCategoria(categoria).stream()
                .findFirst()
                .orElse(null);
        }
        return null;
    }
    
    // Verifica e notifica alertas automáticos
    public void verificarAlertas() {
        // Alertar sobre metas atrasadas
        List<Meta> metasAtrasadas = getMetasAtrasadas();
        for (Meta meta : metasAtrasadas) {
            notificarObservadores("⚠️ Meta Atrasada",
                meta.getDescricao() + " - Falta: R$ " + 
                String.format("%.2f", meta.getValorAlvo() - meta.getValorAtual()));
        }
        
        // Alertar sobre orçamentos estourados
        List<Orcamento> orcamentosEstourados = getOrcamentosEstourados();
        for (Orcamento orcamento : orcamentosEstourados) {
            if (orcamento.deveEnviarAlerta()) {
                notificarObservadores("⚠️ Orçamento Estourado",
                    orcamento.getNome() + " - Excedido em R$ " + 
                    String.format("%.2f", orcamento.getValorUltrapassado()));
                orcamento.marcarAlertaEnviado();
            }
        }
    }

    public void limparDados() {
        repositorioUsuarios.limpar();
        repositorioContas.limpar();
        repositorioTransacoes.limpar();
        repositorioMetas.limpar();
        repositorioOrcamentos.limpar();
    }
    // Método para resetar o singleton 
    public static void resetarInstancia() {
        instancia = null;
    }
    
    // Processa transações recorrentes
    public void processarRecorrencias() {
        List<Transacao> transacoes = repositorioTransacoes.listarTodos();
        List<Transacao> novasTransacoes = new ArrayList<>();
        LocalDate hoje = LocalDate.now();
        
        for (Transacao t : transacoes) {
            if (t.isRecorrente()) {
                LocalDate proximaData = t.getData().plusMonths(1);
                
                // Se a próxima data já passou ou é hoje
                if (!proximaData.isAfter(hoje)) {
                    // Verifica se já existe a recorrência
                    boolean existe = transacoes.stream().anyMatch(existente -> 
                        existente.getDescricao().equals(t.getDescricao()) &&
                        existente.getValor() == t.getValor() &&
                        existente.getData().equals(proximaData) &&
                        existente.getTipo() == t.getTipo()
                    );
                    
                    if (!existe) {
                        Transacao nova = t.gerarProximaRecorrencia();
                        if (nova != null) {
                            novasTransacoes.add(nova);
                        }
                    }
                }
            }
        }
        
        for (Transacao nova : novasTransacoes) {
            adicionarTransacao(nova);
            System.out.println("Recorrência gerada: " + nova.getDescricao() + " para " + nova.getData());
        }
        
        // Se gerou novas, chama recursivamente para garantir meses subsequentes
        if (!novasTransacoes.isEmpty()) {
            processarRecorrencias();
        }
    }
}