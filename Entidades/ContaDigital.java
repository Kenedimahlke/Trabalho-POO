import java.io.Serializable;

// Classe que representa uma Conta Digital (como Nubank, Inter, etc)
// Possui rendimento automático no saldo
public class ContaDigital implements ContaFinanceira, Serializable {
    private static final long serialVersionUID = 1L;
    
    private String numeroConta;
    private Usuario titular;
    private double saldo;
    private double rendimento; // Percentual de rendimento mensal
    private boolean ativa;
    
    // CONSTRUTOR
    public ContaDigital(String numeroConta, Usuario titular, double rendimento) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldo = 0.0;
        this.rendimento = rendimento;
        this.ativa = true;
    }
    
    @Override
    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do depósito deve ser positivo");
        }
        if (!ativa) {
            throw new IllegalStateException("Conta inativa. Não é possível realizar depósitos.");
        }
        this.saldo += valor;
    }
    
    @Override
    public boolean sacar(double valor) throws Exception {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do saque deve ser positivo");
        }
        if (!ativa) {
            throw new IllegalStateException("Conta inativa. Não é possível realizar saques.");
        }
        
        if (valor > saldo) {
            throw new Exception("SaldoInsuficienteException: Saldo insuficiente. Disponível: R$ " 
                    + String.format("%.2f", saldo));
        }
        
        this.saldo -= valor;
        return true;
    }
    
    @Override
    public double consultarSaldo() {
        return this.saldo;
    }
    
    // Aplica o rendimento mensal no saldo
    public void aplicarRendimento() {
        if (ativa && saldo > 0) {
            double valorRendimento = saldo * (rendimento / 100);
            saldo += valorRendimento;
            System.out.println("Rendimento aplicado: R$ " + String.format("%.2f", valorRendimento));
        }
    }
    
    @Override
    public String getNumeroConta() {
        return numeroConta;
    }
    
    @Override
    public String getTipoConta() {
        return "CONTA_DIGITAL";
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
        StringBuilder extrato = new StringBuilder();
        extrato.append("=== EXTRATO CONTA DIGITAL ===\n");
        extrato.append("Número: ").append(numeroConta).append("\n");
        extrato.append("Titular: ").append(titular.getNome()).append("\n");
        extrato.append("Saldo: R$ ").append(String.format("%.2f", saldo)).append("\n");
        extrato.append("Rendimento: ").append(String.format("%.2f", rendimento)).append("% a.m.\n");
        extrato.append("Status: ").append(ativa ? "Ativa" : "Inativa").append("\n");
        return extrato.toString();
    }
    
    // GETTERS adicionais
    public double getRendimento() { return rendimento; }
}
