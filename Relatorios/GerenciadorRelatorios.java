import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

// Classe responsável por gerenciar a criação e exportação de diferentes tipos de relatórios
public class GerenciadorRelatorios {
    
    private List<Transacao> transacoes;
    
    public GerenciadorRelatorios(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }
    
    // Gera relatório por período
    public String gerarRelatorioPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        RelatorioPorPeriodo relatorio = new RelatorioPorPeriodo(transacoes, dataInicio, dataFim);
        return relatorio.gerar();
    }
    
    // Gera relatório comparativo por categoria
    public String gerarRelatorioComparativoCategoria(LocalDate dataInicio, LocalDate dataFim) {
        RelatorioComparativoCategoria relatorio = 
            new RelatorioComparativoCategoria(transacoes, dataInicio, dataFim);
        return relatorio.gerar();
    }
    
    // Gera relatório de ranking de despesas
    public String gerarRelatorioRankingDespesas(LocalDate dataInicio, LocalDate dataFim, int topN) {
        RelatorioRankingDespesas relatorio = 
            new RelatorioRankingDespesas(transacoes, dataInicio, dataFim, topN);
        return relatorio.gerar();
    }
    
    // Gera relatório de evolução de saldo
    public String gerarRelatorioEvolucaoSaldo(LocalDate dataInicio, LocalDate dataFim, 
                                             double saldoInicial) {
        RelatorioEvolucaoSaldo relatorio = 
            new RelatorioEvolucaoSaldo(transacoes, dataInicio, dataFim, saldoInicial);
        return relatorio.gerar();
    }
    
    // Exporta relatório por período para arquivo
    public void exportarRelatorioPorPeriodo(LocalDate dataInicio, LocalDate dataFim, 
                                           String caminhoArquivo) throws Exception {
        RelatorioPorPeriodo relatorio = new RelatorioPorPeriodo(transacoes, dataInicio, dataFim);
        relatorio.exportarParaArquivo(caminhoArquivo);
    }
    
    // Exporta relatório comparativo para arquivo
    public void exportarRelatorioComparativoCategoria(LocalDate dataInicio, LocalDate dataFim,
                                                     String caminhoArquivo) throws Exception {
        RelatorioComparativoCategoria relatorio = 
            new RelatorioComparativoCategoria(transacoes, dataInicio, dataFim);
        relatorio.exportarParaArquivo(caminhoArquivo);
    }
    
    // Exporta ranking de despesas para arquivo
    public void exportarRelatorioRankingDespesas(LocalDate dataInicio, LocalDate dataFim,
                                                int topN, String caminhoArquivo) throws Exception {
        RelatorioRankingDespesas relatorio = 
            new RelatorioRankingDespesas(transacoes, dataInicio, dataFim, topN);
        relatorio.exportarParaArquivo(caminhoArquivo);
    }
    
    // Exporta evolução de saldo para arquivo
    public void exportarRelatorioEvolucaoSaldo(LocalDate dataInicio, LocalDate dataFim,
                                              double saldoInicial, String caminhoArquivo) throws Exception {
        RelatorioEvolucaoSaldo relatorio = 
            new RelatorioEvolucaoSaldo(transacoes, dataInicio, dataFim, saldoInicial);
        relatorio.exportarParaArquivo(caminhoArquivo);
    }
    
    // Gera relatório resumido de todas as informações
    public String gerarRelatorioCompleto(LocalDate dataInicio, LocalDate dataFim, 
                                        double saldoInicial) {
        StringBuilder sb = new StringBuilder();
        
        sb.append("═".repeat(80)).append("\n");
        sb.append(centralizarTexto("RELATÓRIO FINANCEIRO COMPLETO", 80)).append("\n");
        sb.append("═".repeat(80)).append("\n\n");
        
        // Relatório por período
        sb.append("\n\n");
        sb.append(gerarRelatorioPorPeriodo(dataInicio, dataFim));
        
        // Comparativo por categoria
        sb.append("\n\n");
        sb.append(gerarRelatorioComparativoCategoria(dataInicio, dataFim));
        
        // Ranking de despesas (top 10)
        sb.append("\n\n");
        sb.append(gerarRelatorioRankingDespesas(dataInicio, dataFim, 10));
        
        // Evolução de saldo
        sb.append("\n\n");
        sb.append(gerarRelatorioEvolucaoSaldo(dataInicio, dataFim, saldoInicial));
        
        return sb.toString();
    }
    
    // Exporta relatório completo para arquivo
    public void exportarRelatorioCompleto(LocalDate dataInicio, LocalDate dataFim,
                                         double saldoInicial, String caminhoArquivo) throws Exception {
        String conteudo = gerarRelatorioCompleto(dataInicio, dataFim, saldoInicial);
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoArquivo))) {
            writer.print(conteudo);
        }
    }
    
    // Atualiza a lista de transações
    public void atualizarTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }
    
    // Método auxiliar para centralizar texto
    private String centralizarTexto(String texto, int largura) {
        int espacos = (largura - texto.length()) / 2;
        return " ".repeat(Math.max(0, espacos)) + texto;
    }
}
