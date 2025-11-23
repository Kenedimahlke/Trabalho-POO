import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Classe que representa uma meta financeira
public class Meta implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    private Categoria categoria;
    private double valorAlvo;
    private double valorAtual;
    private LocalDate dataInicio;
    private LocalDate prazo;
    private Usuario responsavel;
    private boolean alcancada;
    
    // CONSTRUTOR
    public Meta(String nome, Categoria categoria, double valorAlvo, 
                LocalDate prazo, Usuario responsavel) {
        this.nome = nome;
        this.categoria = categoria;
        this.valorAlvo = valorAlvo;
        this.valorAtual = 0.0;
        this.dataInicio = LocalDate.now();
        this.prazo = prazo;
        this.responsavel = responsavel;
        this.alcancada = false;
    }
    
    // Atualiza o progresso da meta
    public void atualizarProgresso(double valor) {
        if (valor < 0) {
            return; // Ignora valor negativo ou lança exceção? Teste espera que saldo não mude.
        }
        this.valorAtual += valor;
        
        if (valorAtual >= valorAlvo) {
            alcancada = true;
        }
    }
    
    // Retorna o percentual de conclusão da meta
    public double getPercentualConcluido() {
        if (valorAlvo == 0) return 0;
        return Math.min((valorAtual / valorAlvo) * 100, 100);
    }
    
    // Verifica se a meta foi alcançada
    public boolean isAlcancada() {
        return alcancada || valorAtual >= valorAlvo;
    }
    
    // Verifica se a meta está atrasada
    public boolean isAtrasada() {
        return LocalDate.now().isAfter(prazo) && !isAlcancada();
    }
    
    // Retorna quantos dias faltam para o prazo
    public long getDiasRestantes() {
        return ChronoUnit.DAYS.between(LocalDate.now(), prazo);
    }
    
    // Retorna quanto falta para alcançar a meta
    public double getValorRestante() {
        return Math.max(valorAlvo - valorAtual, 0);
    }
    
    // Calcula quanto precisa economizar por dia para alcançar a meta
    public double getEconomiaDiariaNecessaria() {
        long diasRestantes = getDiasRestantes();
        if (diasRestantes <= 0) return 0;
        
        return getValorRestante() / diasRestantes;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Meta: ").append(nome).append("\n");
        sb.append("Categoria: ").append(categoria.getNome()).append("\n");
        sb.append("Progresso: R$ ").append(String.format("%.2f", valorAtual));
        sb.append(" / R$ ").append(String.format("%.2f", valorAlvo));
        sb.append(" (").append(String.format("%.1f", getPercentualConcluido())).append("%)\n");
        sb.append("Prazo: ").append(prazo);
        
        if (isAlcancada()) {
            sb.append(" - ✓ ALCANÇADA");
        } else if (isAtrasada()) {
            sb.append(" - ✗ ATRASADA");
        } else {
            sb.append(" - ").append(getDiasRestantes()).append(" dias restantes");
        }
        
        return sb.toString();
    }
    
    // Métodos para compatibilidade com Main
    public String getDescricao() { return nome; }
    public double calcularProgresso() { return getPercentualConcluido(); }
    public void contribuir(double valor) { atualizarProgresso(valor); }
    public boolean isConcluida() { return isAlcancada(); }
    public String verificarStatus() {
        if (isAlcancada()) return "CONCLUIDA";
        if (isAtrasada()) return "ATRASADA";
        return "PENDENTE";
    }
    public Usuario getUsuario() { return responsavel; }
    public LocalDate getDataLimite() { return prazo; }

    // GETTERS
    public String getNome() { return nome; }
    public Categoria getCategoria() { return categoria; }
    public double getValorAlvo() { return valorAlvo; }
    public double getValorAtual() { return valorAtual; }
    public LocalDate getDataInicio() { return dataInicio; }
    public LocalDate getPrazo() { return prazo; }
    public Usuario getResponsavel() { return responsavel; }
    
    // SETTERS
    public void setNome(String nome) { this.nome = nome; }
    public void setValorAlvo(double valorAlvo) { this.valorAlvo = valorAlvo; }
    public void setPrazo(LocalDate prazo) { this.prazo = prazo; }
}
