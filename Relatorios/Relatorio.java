import java.time.LocalDate;
import java.util.List;

// Classe abstrata base para todos os relatórios do sistema
public abstract class Relatorio {
    
    protected String titulo;
    protected LocalDate dataGeracao;
    protected String descricao;
    
    public Relatorio(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataGeracao = LocalDate.now();
    }
    
    // Método abstrato que cada tipo de relatório deve implementar
    public abstract String gerar();
    
    // Método para exportar o relatório para arquivo
    public abstract void exportarParaArquivo(String caminhoArquivo) throws Exception;
    
    // Getters
    public String getTitulo() {
        return titulo;
    }
    
    public LocalDate getDataGeracao() {
        return dataGeracao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    // Método auxiliar para formatar cabeçalho do relatório
    protected String formatarCabecalho() {
        StringBuilder sb = new StringBuilder();
        sb.append("=" .repeat(80)).append("\n");
        sb.append(centralizarTexto(titulo, 80)).append("\n");
        sb.append("=" .repeat(80)).append("\n");
        sb.append("Data de Geração: ").append(dataGeracao).append("\n");
        sb.append("Descrição: ").append(descricao).append("\n");
        sb.append("=" .repeat(80)).append("\n\n");
        return sb.toString();
    }
    
    // Método auxiliar para centralizar texto
    protected String centralizarTexto(String texto, int largura) {
        int espacos = (largura - texto.length()) / 2;
        return " ".repeat(Math.max(0, espacos)) + texto;
    }
    
    // Método auxiliar para formatar valores monetários
    protected String formatarMoeda(double valor) {
        return String.format("R$ %.2f", valor);
    }
    
    // Método auxiliar para formatar rodapé
    protected String formatarRodape() {
        return "\n" + "=" .repeat(80) + "\n" + 
               centralizarTexto("Fim do Relatório", 80) + "\n" + 
               "=" .repeat(80) + "\n";
    }
}
