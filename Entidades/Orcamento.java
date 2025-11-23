import java.time.LocalDate;
import java.time.YearMonth;

// Classe que representa um orçamento para controlar limites por categoria
public class Orcamento {
    private String nome;
    private Categoria categoria;
    private double limiteValor;
    private double valorGasto;
    private YearMonth mesReferencia;
    private Usuario responsavel;
    private boolean alertaEnviado;
    private double percentualAlerta; // Percentual para enviar alerta (ex: 80% = 0.8)
    
    // CONSTRUTOR
    public Orcamento(String nome, Categoria categoria, double limiteValor, 
                     YearMonth mesReferencia, Usuario responsavel) {
        this.nome = nome;
        this.categoria = categoria;
        this.limiteValor = limiteValor;
        this.valorGasto = 0.0;
        this.mesReferencia = mesReferencia;
        this.responsavel = responsavel;
        this.alertaEnviado = false;
        this.percentualAlerta = 0.8; // 80% por padrão
    }
    
    // CONSTRUTOR simplificado (mês atual)
    public Orcamento(String nome, Categoria categoria, double limiteValor, Usuario responsavel) {
        this(nome, categoria, limiteValor, YearMonth.now(), responsavel);
    }
    
    // Adiciona um gasto ao orçamento
    public void adicionarGasto(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }
        this.valorGasto += valor;
    }
    
    // Remove um gasto (caso de estorno)
    public void removerGasto(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }
        this.valorGasto = Math.max(0, this.valorGasto - valor);
    }
    
    // Retorna o valor disponível no orçamento
    public double getValorDisponivel() {
        return Math.max(limiteValor - valorGasto, 0);
    }
    
    // Retorna o percentual gasto do orçamento
    public double getPercentualGasto() {
        if (limiteValor == 0) return 0;
        return (valorGasto / limiteValor) * 100;
    }
    
    // Verifica se o orçamento está estourado
    public boolean isEstourado() {
        return valorGasto > limiteValor;
    }
    
    // Verifica se o orçamento está próximo do limite
    public boolean isProximoDoLimite() {
        return getPercentualGasto() >= (percentualAlerta * 100);
    }
    
    // Verifica se deve enviar alerta
    public boolean deveEnviarAlerta() {
        return isProximoDoLimite() && !alertaEnviado;
    }
    
    // Marca que o alerta foi enviado
    public void marcarAlertaEnviado() {
        this.alertaEnviado = true;
    }
    
    // Reseta o alerta (para permitir novo envio se necessário)
    public void resetarAlerta() {
        this.alertaEnviado = false;
    }
    
    // Verifica se o orçamento pertence ao mês atual
    public boolean isMesAtual() {
        return mesReferencia.equals(YearMonth.now());
    }
    
    // Verifica se o orçamento está vencido
    public boolean isVencido() {
        return mesReferencia.isBefore(YearMonth.now());
    }
    
    // Retorna quanto foi ultrapassado (se houver)
    public double getValorUltrapassado() {
        return Math.max(valorGasto - limiteValor, 0);
    }
    
    // Zera o orçamento para renovação
    public void renovar() {
        this.valorGasto = 0.0;
        this.alertaEnviado = false;
        this.mesReferencia = YearMonth.now();
    }
    
    // Cria um novo orçamento para o próximo mês baseado neste
    public Orcamento criarProximoMes() {
        return new Orcamento(nome, categoria, limiteValor, 
                           mesReferencia.plusMonths(1), responsavel);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Orçamento: ").append(nome).append("\n");
        sb.append("Categoria: ").append(categoria.getNome()).append("\n");
        sb.append("Mês: ").append(mesReferencia).append("\n");
        sb.append("Gasto: R$ ").append(String.format("%.2f", valorGasto));
        sb.append(" / R$ ").append(String.format("%.2f", limiteValor));
        sb.append(" (").append(String.format("%.1f", getPercentualGasto())).append("%)\n");
        sb.append("Disponível: R$ ").append(String.format("%.2f", getValorDisponivel())).append("\n");
        
        if (isEstourado()) {
            sb.append("ORÇAMENTO ESTOURADO! Ultrapassou em R$ ");
            sb.append(String.format("%.2f", getValorUltrapassado())).append("\n");
        } else if (isProximoDoLimite()) {
            sb.append("Atenção! Próximo do limite (");
            sb.append(String.format("%.0f", percentualAlerta * 100)).append("%)\n");
        } else {
            sb.append("Orçamento sob controle\n");
        }
        
        return sb.toString();
    }
    
    // GETTERS
    public String getNome() { return nome; }
    public Categoria getCategoria() { return categoria; }
    public double getLimiteValor() { return limiteValor; }
    public double getValorGasto() { return valorGasto; }
    public YearMonth getMesReferencia() { return mesReferencia; }
    public Usuario getResponsavel() { return responsavel; }
    public boolean isAlertaEnviado() { return alertaEnviado; }
    public double getPercentualAlerta() { return percentualAlerta; }
    
    // SETTERS
    public void setNome(String nome) { this.nome = nome; }
    public void setLimiteValor(double limiteValor) { 
        if (limiteValor < 0) {
            throw new IllegalArgumentException("Limite deve ser positivo");
        }
        this.limiteValor = limiteValor; 
    }
    public void setPercentualAlerta(double percentualAlerta) {
        if (percentualAlerta < 0 || percentualAlerta > 1) {
            throw new IllegalArgumentException("Percentual deve estar entre 0 e 1");
        }
        this.percentualAlerta = percentualAlerta;
    }
}
