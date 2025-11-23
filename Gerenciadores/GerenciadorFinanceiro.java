import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Singleton - Gerenciador central do sistema financeiro
// REFATORADO: Aplicando SRP - responsabilidade única de coordenar repositórios
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
    
    // MÉTODOS DE USUÁRIO - Delegando para o repositório (SRP)
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
    
    // MÉTODOS DE TRANSAÇÃO - Delegando para o repositório (SRP)
    public void adicionarTransacao(Transacao transacao) {
        repositorioTransacoes.adicionar(transacao);
        // Notificar observadores
        notificarObservadores("Nova Transação: " + transacao.getTipo(),
            String.format("R$ %.2f - %s", transacao.getValor(), transacao.getDescricao()));
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
    
    // MÉTODOS DE META - Delegando para o repositório (SRP)
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
    
    // MÉTODOS DE CONTA - Delegando para o repositório (SRP)
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
    
    // MÉTODOS DE ORÇAMENTO - Delegando para o repositório (SRP)
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
    // Método para resetar o singleton (útil em testes)
    public static void resetarInstancia() {
        instancia = null;
    }
}
