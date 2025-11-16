import java.time.LocalDate;

// Classe que representa uma transação financeira
public class Transacao {
    private static int contadorId = 1;
    private int id;
    private TipoTransacao tipo;
    private Categoria categoria;
    private double valor;
    private LocalDate data;
    private String descricao;
    private Usuario pagador;
    private Usuario beneficiario;
    private ContaFinanceira contaOrigem;
    private ContaFinanceira contaDestino;
    private boolean recorrente;
    private int parcelas;
    private int parcelaAtual;
    
    // CONSTRUTOR principal
    public Transacao(TipoTransacao tipo, Categoria categoria, double valor, 
                     String descricao, Usuario pagador, ContaFinanceira contaOrigem) {
        this.id = contadorId++;
        this.tipo = tipo;
        this.categoria = categoria;
        this.valor = valor;
        this.data = LocalDate.now();
        this.descricao = descricao;
        this.pagador = pagador;
        this.contaOrigem = contaOrigem;
        this.recorrente = false;
        this.parcelas = 1;
        this.parcelaAtual = 1;
    }
    
    // CONSTRUTOR para transferências
    public Transacao(double valor, String descricao, Usuario pagador, 
                     ContaFinanceira contaOrigem, ContaFinanceira contaDestino) {
        this.id = contadorId++;
        this.tipo = TipoTransacao.TRANSFERENCIA;
        this.categoria = Categoria.OUTROS;
        this.valor = valor;
        this.data = LocalDate.now();
        this.descricao = descricao;
        this.pagador = pagador;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.recorrente = false;
        this.parcelas = 1;
        this.parcelaAtual = 1;
    }
    
    // Executa a transação
    public boolean executar() throws Exception {
        switch (tipo) {
            case RECEITA:
                contaOrigem.depositar(valor);
                return true;
                
            case DESPESA:
                return contaOrigem.sacar(valor);
                
            case TRANSFERENCIA:
                if (contaDestino == null) {
                    throw new IllegalStateException("Conta destino não definida para transferência");
                }
                boolean sacou = contaOrigem.sacar(valor);
                if (sacou) {
                    contaDestino.depositar(valor);
                    return true;
                }
                return false;
                
            default:
                return false;
        }
    }
    
    // Gera transação recorrente para o próximo período
    public Transacao gerarProximaRecorrencia() {
        if (!recorrente) {
            return null;
        }
        
        Transacao nova = new Transacao(tipo, categoria, valor, descricao, pagador, contaOrigem);
        nova.data = this.data.plusMonths(1);
        nova.recorrente = true;
        nova.beneficiario = this.beneficiario;
        return nova;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(" | ");
        sb.append(tipo.getNome()).append(" | ");
        sb.append("R$ ").append(String.format("%.2f", valor)).append(" | ");
        sb.append(categoria.getNome()).append(" | ");
        sb.append(data).append(" | ");
        sb.append(descricao);
        
        if (parcelas > 1) {
            sb.append(" (").append(parcelaAtual).append("/").append(parcelas).append(")");
        }
        
        return sb.toString();
    }
    
    // GETTERS
    public int getId() { return id; }
    public TipoTransacao getTipo() { return tipo; }
    public Categoria getCategoria() { return categoria; }
    public double getValor() { return valor; }
    public LocalDate getData() { return data; }
    public String getDescricao() { return descricao; }
    public Usuario getPagador() { return pagador; }
    public Usuario getBeneficiario() { return beneficiario; }
    public ContaFinanceira getContaOrigem() { return contaOrigem; }
    public ContaFinanceira getContaDestino() { return contaDestino; }
    public boolean isRecorrente() { return recorrente; }
    public int getParcelas() { return parcelas; }
    public int getParcelaAtual() { return parcelaAtual; }
    
    // SETTERS
    public void setBeneficiario(Usuario beneficiario) { this.beneficiario = beneficiario; }
    public void setContaDestino(ContaFinanceira contaDestino) { this.contaDestino = contaDestino; }
    public void setRecorrente(boolean recorrente) { this.recorrente = recorrente; }
    public void setParcelas(int parcelas) { this.parcelas = parcelas; }
    public void setParcelaAtual(int parcelaAtual) { this.parcelaAtual = parcelaAtual; }
    public void setData(LocalDate data) { this.data = data; }
}
