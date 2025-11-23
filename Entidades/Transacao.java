import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Classe que representa uma transação financeira
public class Transacao {
    private static int contadorId = 1;
    private int id;
    private TipoTransacao tipo;
    private Categoria categoria;
    private String subcategoria;
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
    private List<String> anexos; // Lista de caminhos de arquivos anexados (simulado)
    private boolean estornada;
    
    // CONSTRUTOR principal
    public Transacao(TipoTransacao tipo, Categoria categoria, double valor, 
                     String descricao, Usuario pagador, ContaFinanceira contaOrigem) {
        this.id = contadorId++;
        this.tipo = tipo;
        this.categoria = categoria;
        this.subcategoria = null;
        this.valor = valor;
        this.data = LocalDate.now();
        this.descricao = descricao;
        this.pagador = pagador;
        this.contaOrigem = contaOrigem;
        this.recorrente = false;
        this.parcelas = 1;
        this.parcelaAtual = 1;
        this.anexos = new ArrayList<>();
        this.estornada = false;
    }
    
    // CONSTRUTOR para transferências
    public Transacao(double valor, String descricao, Usuario pagador, 
                     ContaFinanceira contaOrigem, ContaFinanceira contaDestino) {
        this.id = contadorId++;
        this.tipo = TipoTransacao.TRANSFERENCIA;
        this.categoria = Categoria.OUTROS;
        this.subcategoria = null;
        this.valor = valor;
        this.data = LocalDate.now();
        this.descricao = descricao;
        this.pagador = pagador;
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.recorrente = false;
        this.parcelas = 1;
        this.parcelaAtual = 1;
        this.anexos = new ArrayList<>();
        this.estornada = false;
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
    
    // Estorna a transação (reverte a operação)
    public boolean estornar() throws Exception {
        if (estornada) {
            throw new IllegalStateException("Transação já foi estornada");
        }
        
        boolean sucesso = false;
        
        switch (tipo) {
            case RECEITA:
                // Estornar receita = sacar o valor da conta
                sucesso = contaOrigem.sacar(valor);
                break;
                
            case DESPESA:
                // Estornar despesa = devolver o valor para a conta
                contaOrigem.depositar(valor);
                sucesso = true;
                break;
                
            case TRANSFERENCIA:
                if (contaDestino == null) {
                    throw new IllegalStateException("Conta destino não definida");
                }
                // Estornar transferência = inverter a operação
                boolean sacouDestino = contaDestino.sacar(valor);
                if (sacouDestino) {
                    contaOrigem.depositar(valor);
                    sucesso = true;
                }
                break;
        }
        
        if (sucesso) {
            estornada = true;
        }
        
        return sucesso;
    }
    
    // Adiciona um anexo (simulado - apenas guarda o caminho/nome do arquivo)
    public void adicionarAnexo(String caminhoArquivo) {
        if (caminhoArquivo != null && !caminhoArquivo.trim().isEmpty()) {
            anexos.add(caminhoArquivo);
        }
    }
    
    // Remove um anexo
    public boolean removerAnexo(String caminhoArquivo) {
        return anexos.remove(caminhoArquivo);
    }
    
    // Lista todos os anexos
    public List<String> getAnexos() {
        return new ArrayList<>(anexos);
    }
    
    // Verifica se tem anexos
    public boolean temAnexos() {
        return !anexos.isEmpty();
    }
    
    // Retorna quantidade de anexos
    public int getQuantidadeAnexos() {
        return anexos.size();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append(" | ");
        sb.append(tipo.getNome()).append(" | ");
        sb.append("R$ ").append(String.format("%.2f", valor)).append(" | ");
        sb.append(categoria.getNome());
        
        if (subcategoria != null) {
            sb.append(" (").append(subcategoria).append(")");
        }
        
        sb.append(" | ");
        sb.append(data).append(" | ");
        sb.append(descricao);
        
        if (parcelas > 1) {
            sb.append(" (").append(parcelaAtual).append("/").append(parcelas).append(")");
        }
        
        if (estornada) {
            sb.append(" [ESTORNADA]");
        }
        
        if (temAnexos()) {
            sb.append(" 📎").append(getQuantidadeAnexos());
        }
        
        return sb.toString();
    }
    
    // GETTERS
    public int getId() { return id; }
    public TipoTransacao getTipo() { return tipo; }
    public Categoria getCategoria() { return categoria; }
    public String getSubcategoria() { return subcategoria; }
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
    public boolean isEstornada() { return estornada; }
    
    // SETTERS
    public void setBeneficiario(Usuario beneficiario) { this.beneficiario = beneficiario; }
    public void setContaDestino(ContaFinanceira contaDestino) { this.contaDestino = contaDestino; }
    public void setRecorrente(boolean recorrente) { this.recorrente = recorrente; }
    public void setParcelas(int parcelas) { this.parcelas = parcelas; }
    public void setParcelaAtual(int parcelaAtual) { this.parcelaAtual = parcelaAtual; }
    public void setData(LocalDate data) { this.data = data; }
    public void setSubcategoria(String subcategoria) {
        if (categoria != null && categoria.temSubcategoria(subcategoria)) {
            this.subcategoria = subcategoria;
        } else {
            throw new IllegalArgumentException("Subcategoria inválida para esta categoria");
        }
    }
}
