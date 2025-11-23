import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Singleton - Gerenciador central do sistema financeiro
public class GerenciadorFinanceiro {
    private static GerenciadorFinanceiro instancia;
    private List<Usuario> usuarios;
    private List<Transacao> transacoes;
    private List<Meta> metas;
    private List<ContaFinanceira> contas;
    private List<Orcamento> orcamentos;
    
    // CONSTRUTOR privado (Singleton)
    private GerenciadorFinanceiro() {
        usuarios = new ArrayList<>();
        transacoes = new ArrayList<>();
        metas = new ArrayList<>();
        contas = new ArrayList<>();
        orcamentos = new ArrayList<>();
    }
    
    // Método para obter a instância única
    public static GerenciadorFinanceiro getInstancia() {
        if (instancia == null) {
            instancia = new GerenciadorFinanceiro();
        }
        return instancia;
    }
    
    // === MÉTODOS DE USUÁRIO ===
    public void adicionarUsuario(Usuario usuario) {
        if (usuario != null && !usuarios.contains(usuario)) {
            usuarios.add(usuario);
        }
    }
    
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarios.stream()
            .filter(u -> u.getEmail().equalsIgnoreCase(email))
            .findFirst()
            .orElse(null);
    }
    
    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }
    
    // === MÉTODOS DE TRANSAÇÃO ===
    public void adicionarTransacao(Transacao transacao) {
        if (transacao != null) {
            transacoes.add(transacao);
        }
    }
    
    public List<Transacao> getTransacoes() {
        return new ArrayList<>(transacoes);
    }
    
    public List<Transacao> getTransacoesDoUsuario(Usuario usuario) {
        return transacoes.stream()
            .filter(t -> t.getPagador().equals(usuario))
            .collect(Collectors.toList());
    }
    
    public List<Transacao> getTransacoesPorTipo(TipoTransacao tipo) {
        return transacoes.stream()
            .filter(t -> t.getTipo() == tipo)
            .collect(Collectors.toList());
    }
    
    public List<Transacao> getTransacoesPorCategoria(Categoria categoria) {
        return transacoes.stream()
            .filter(t -> t.getCategoria() == categoria)
            .collect(Collectors.toList());
    }
    
    // === MÉTODOS DE META ===
    public void adicionarMeta(Meta meta) {
        if (meta != null) {
            metas.add(meta);
        }
    }
    
    public List<Meta> getMetas() {
        return new ArrayList<>(metas);
    }
    
    public List<Meta> getMetasDoUsuario(Usuario usuario) {
        return metas.stream()
            .filter(m -> m.getResponsavel().equals(usuario))
            .collect(Collectors.toList());
    }
    
    public List<Meta> getMetasAtrasadas() {
        return metas.stream()
            .filter(Meta::isAtrasada)
            .collect(Collectors.toList());
    }
    
    public List<Meta> getMetasAlcancadas() {
        return metas.stream()
            .filter(Meta::isAlcancada)
            .collect(Collectors.toList());
    }
    
    // === MÉTODOS DE CONTA ===
    public void adicionarConta(ContaFinanceira conta) {
        if (conta != null && !contas.contains(conta)) {
            contas.add(conta);
        }
    }
    
    public List<ContaFinanceira> getContas() {
        return new ArrayList<>(contas);
    }
    
    public List<ContaFinanceira> getContasDoUsuario(Usuario usuario) {
        return contas.stream()
            .filter(c -> c.getTitular().equals(usuario))
            .collect(Collectors.toList());
    }
    
    public ContaFinanceira buscarContaPorNumero(String numeroConta) {
        return contas.stream()
            .filter(c -> c.getNumeroConta().equals(numeroConta))
            .findFirst()
            .orElse(null);
    }
    
    // === MÉTODOS DE ANÁLISE ===
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
    
    // === MÉTODOS DE ORÇAMENTO ===
    public void adicionarOrcamento(Orcamento orcamento) {
        if (orcamento != null) {
            orcamentos.add(orcamento);
        }
    }
    
    public List<Orcamento> getOrcamentos() {
        return new ArrayList<>(orcamentos);
    }
    
    public List<Orcamento> getOrcamentosDoUsuario(Usuario usuario) {
        return orcamentos.stream()
            .filter(o -> o.getResponsavel().equals(usuario))
            .collect(Collectors.toList());
    }
    
    public List<Orcamento> getOrcamentosEstourados() {
        return orcamentos.stream()
            .filter(Orcamento::isEstourado)
            .collect(Collectors.toList());
    }
    
    public List<Orcamento> getOrcamentosProximosDoLimite() {
        return orcamentos.stream()
            .filter(Orcamento::isProximoDoLimite)
            .collect(Collectors.toList());
    }
    
    public Orcamento buscarOrcamentoPorCategoria(Usuario usuario, Categoria categoria) {
        return orcamentos.stream()
            .filter(o -> o.getResponsavel().equals(usuario) && 
                        o.getCategoria() == categoria &&
                        o.isMesAtual())
            .findFirst()
            .orElse(null);
    }
    
    // === MÉTODO DE RESET (para testes) ===
    public void limparDados() {
        usuarios.clear();
        transacoes.clear();
        metas.clear();
        contas.clear();
        orcamentos.clear();
    }
    
    // Método para resetar o singleton (útil em testes)
    public static void resetarInstancia() {
        instancia = null;
    }
}
