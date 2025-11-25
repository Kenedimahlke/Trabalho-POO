package Gerenciadores;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

// Singleton
public class GerenciadorFinanceiro {
    private static GerenciadorFinanceiro instancia;
    private List<Usuario> usuarios;
    private List<ContaFinanceira> contas;
    private List<Transacao> transacoes;
    private List<Meta> metas;
    private List<Orcamento> orcamentos;
    
    private GerenciadorFinanceiro() {
        this.usuarios = new ArrayList<>();
        this.contas = new ArrayList<>();
        this.transacoes = new ArrayList<>();
        this.metas = new ArrayList<>();
        this.orcamentos = new ArrayList<>();
    }
    
    public static GerenciadorFinanceiro getInstancia() {
        if (instancia == null) {
            instancia = new GerenciadorFinanceiro();
        }
        return instancia;
    }
    
    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }
    
    public Usuario buscarUsuarioPorId(String id) {
        return usuarios.stream()
            .filter(u -> u.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarios.stream()
            .filter(u -> u.getEmail().equals(email))
            .findFirst()
            .orElse(null);
    }
    
    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }
    
    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
        
        if (transacao.getTipo() == TipoTransacao.DESPESA) {
            atualizarOrcamentos(transacao);
        }
    }
    
    private void atualizarOrcamentos(Transacao transacao) {
        YearMonth transacaoMes = YearMonth.from(transacao.getData());
        
        for (Orcamento o : orcamentos) {
            if (o.getCategoria().equals(transacao.getCategoria()) && 
                o.getMesReferencia().equals(transacaoMes)) {
                o.adicionarGasto(transacao.getValor());
            }
        }
    }
    
    public List<Transacao> getTransacoes() {
        return new ArrayList<>(transacoes);
    }
    
    public List<Transacao> getTransacoesDoUsuario(Usuario usuario) {
        return transacoes.stream()
            .filter(t -> t.getConta().getTitular().equals(usuario))
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
    
    public void adicionarMeta(Meta meta) {
        metas.add(meta);
    }
    
    public List<Meta> getMetas() {
        return new ArrayList<>(metas);
    }
    
    public List<Meta> getMetasDoUsuario(Usuario usuario) {
        return metas.stream()
            .filter(m -> m.getUsuario().equals(usuario))
            .collect(Collectors.toList());
    }
    
    public List<Meta> getMetasAtrasadas() {
        LocalDate hoje = LocalDate.now();
        return metas.stream()
            .filter(m -> m.getPrazo().isBefore(hoje) && !m.isConcluida())
            .collect(Collectors.toList());
    }
    
    public List<Meta> getMetasAlcancadas() {
        return metas.stream()
            .filter(Meta::isConcluida)
            .collect(Collectors.toList());
    }
    
    public void adicionarConta(ContaFinanceira conta) {
        contas.add(conta);
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
    
    public double calcularSaldoTotal() {
        return contas.stream()
            .mapToDouble(ContaFinanceira::consultarSaldo)
            .sum();
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
    
    public void adicionarOrcamento(Orcamento orcamento) {
        orcamentos.add(orcamento);
    }
    
    public List<Orcamento> getOrcamentos() {
        return new ArrayList<>(orcamentos);
    }
    
    public List<Orcamento> getOrcamentosDoUsuario(Usuario usuario) {
        return new ArrayList<>(orcamentos);
    }
    
    public List<Orcamento> getOrcamentosEstourados() {
        return orcamentos.stream()
            .filter(Orcamento::isEstourado)
            .collect(Collectors.toList());
    }
    
    public List<Orcamento> getOrcamentosProximosDoLimite() {
        return orcamentos.stream()
            .filter(o -> o.getPercentualGasto() >= 80.0 && !o.isEstourado())
            .collect(Collectors.toList());
    }
    
    public Orcamento buscarOrcamentoPorCategoria(Usuario usuario, Categoria categoria) {
        return orcamentos.stream()
            .filter(o -> o.getCategoria() == categoria)
            .findFirst()
            .orElse(null);
    }
    
    public void limparDados() {
        usuarios.clear();
        contas.clear();
        transacoes.clear();
        metas.clear();
        orcamentos.clear();
    }
    
    public void carregarEstado(List<Usuario> usuarios, List<ContaFinanceira> contas, 
                             List<Transacao> transacoes, List<Meta> metas, List<Orcamento> orcamentos) {
        limparDados();
        if (usuarios != null) this.usuarios.addAll(usuarios);
        if (contas != null) this.contas.addAll(contas);
        if (transacoes != null) this.transacoes.addAll(transacoes);
        if (metas != null) this.metas.addAll(metas);
        if (orcamentos != null) this.orcamentos.addAll(orcamentos);
    }

    public static void resetarInstancia() {
        instancia = null;
    }
    
    public List<Transacao> processarRecorrencias() {
        List<Transacao> todasNovasTransacoes = new ArrayList<>();
        List<Transacao> novasTransacoes = new ArrayList<>();
        LocalDate hoje = LocalDate.now();
        
        for (Transacao t : transacoes) {
            if (t.isRecorrente()) {
                LocalDate proximaData = t.getData().plusMonths(1);
                
                if (!proximaData.isAfter(hoje)) {
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
            todasNovasTransacoes.add(nova);
        }
        
        if (!novasTransacoes.isEmpty()) {
            todasNovasTransacoes.addAll(processarRecorrencias());
        }
        
        return todasNovasTransacoes;
    }
}
