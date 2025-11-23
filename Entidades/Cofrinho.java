import java.time.LocalDate;
import java.io.Serializable;

// Classe que representa um cofrinho ou poupança virtual
public class Cofrinho implements ContaFinanceira, Serializable {
    private static final long serialVersionUID = 1L;
    private String numeroConta;
    private Usuario titular;
    private double saldo;
    private String objetivo;
    private double metaValor;
    private LocalDate dataCriacao;
    private LocalDate dataMetaPrazo;
    private boolean ativa;
    
    // CONSTRUTOR
    public Cofrinho(String numeroConta, Usuario titular, String objetivo, double metaValor) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldo = 0.0;
        this.objetivo = objetivo;
        this.metaValor = metaValor;
        this.dataCriacao = LocalDate.now();
        this.ativa = true;
    }
    
    // CONSTRUTOR com prazo
    public Cofrinho(String numeroConta, Usuario titular, String objetivo, 
                    double metaValor, LocalDate dataMetaPrazo) {
        this(numeroConta, titular, objetivo, metaValor);
        this.dataMetaPrazo = dataMetaPrazo;
    }
    
    @Override
    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de depósito deve ser positivo");
        }
        if (!ativa) {
            throw new IllegalStateException("Cofrinho está inativo");
        }
        this.saldo += valor;
    }
    
    @Override
    public boolean sacar(double valor) throws Exception {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de saque deve ser positivo");
        }
        if (!ativa) {
            throw new IllegalStateException("Cofrinho está inativo");
        }
        if (valor > saldo) {
            throw new Exception("Saldo insuficiente no cofrinho");
        }
        
        this.saldo -= valor;
        return true;
    }
    
    @Override
    public double consultarSaldo() {
        return saldo;
    }
    
    @Override
    public String getNumeroConta() {
        return numeroConta;
    }
    
    @Override
    public String getTipoConta() {
        return "Cofrinho";
    }
    
    @Override
    public Usuario getTitular() {
        return titular;
    }
    
    @Override
    public boolean isAtiva() {
        return ativa;
    }
    
    @Override
    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
    
    @Override
    public String getExtrato() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== EXTRATO DO COFRINHO ===\n");
        sb.append("Número: ").append(numeroConta).append("\n");
        sb.append("Titular: ").append(titular.getNome()).append("\n");
        sb.append("Objetivo: ").append(objetivo).append("\n");
        sb.append("Saldo Atual: R$ ").append(String.format("%.2f", saldo)).append("\n");
        sb.append("Meta: R$ ").append(String.format("%.2f", metaValor)).append("\n");
        sb.append("Progresso: ").append(String.format("%.1f", getPercentualMeta())).append("%\n");
        sb.append("Status: ").append(ativa ? "Ativo" : "Inativo").append("\n");
        
        if (dataMetaPrazo != null) {
            sb.append("Prazo: ").append(dataMetaPrazo).append("\n");
            if (isMetaAtingida()) {
                sb.append("META ATINGIDA!\n");
            } else if (isPrazoVencido()) {
                sb.append("Prazo vencido\n");
            }
        } else if (isMetaAtingida()) {
            sb.append("META ATINGIDA!\n");
        }
        
        return sb.toString();
    }
    
    // Métodos específicos do Cofrinho
    
    public double getPercentualMeta() {
        if (metaValor == 0) return 0;
        return Math.min((saldo / metaValor) * 100, 100);
    }
    
    public boolean isMetaAtingida() {
        return saldo >= metaValor;
    }
    
    public double getValorFaltante() {
        return Math.max(metaValor - saldo, 0);
    }
    
    public boolean isPrazoVencido() {
        if (dataMetaPrazo == null) return false;
        return LocalDate.now().isAfter(dataMetaPrazo);
    }
    
    public long getDiasRestantes() {
        if (dataMetaPrazo == null) return -1;
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), dataMetaPrazo);
    }
    
    // "Quebrar" o cofrinho - saca todo o valor
    public double quebrarCofrinho() throws Exception {
        if (!ativa) {
            throw new IllegalStateException("Cofrinho já está inativo");
        }
        
        double valorTotal = saldo;
        saldo = 0;
        ativa = false;
        return valorTotal;
    }
    
    @Override
    public String toString() {
        return String.format("Cofrinho [%s] - %s: R$ %.2f / R$ %.2f (%.1f%%)", 
                           numeroConta, objetivo, saldo, metaValor, getPercentualMeta());
    }
    
    // Métodos de compatibilidade com Main
    public double calcularProgressoMeta() { return getPercentualMeta(); }
    public long calcularDiasRestantes() { return getDiasRestantes(); }
    public boolean metaAtingida() { return isMetaAtingida(); }
    public double calcularFaltaParaMeta() { return getValorFaltante(); }
    public String gerarRelatorio() { return getExtrato(); }
    
    // GETTERS e SETTERS adicionais
    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }
    
    public double getMetaValor() { return metaValor; }
    public void setMetaValor(double metaValor) { this.metaValor = metaValor; }
    
    public LocalDate getDataCriacao() { return dataCriacao; }
    public LocalDate getDataMetaPrazo() { return dataMetaPrazo; }
    public void setDataMetaPrazo(LocalDate dataMetaPrazo) { this.dataMetaPrazo = dataMetaPrazo; }
}
