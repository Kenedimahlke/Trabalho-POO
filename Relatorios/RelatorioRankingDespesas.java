import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

// Relatório que mostra um ranking das maiores despesas
public class RelatorioRankingDespesas extends Relatorio {
    
    private List<Transacao> transacoes;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private int topN; // Quantas despesas exibir
    
    public RelatorioRankingDespesas(List<Transacao> transacoes, LocalDate dataInicio, 
                                   LocalDate dataFim, int topN) {
        super("RANKING DE DESPESAS", 
              "Top " + topN + " maiores despesas entre " + dataInicio + " e " + dataFim);
        this.transacoes = transacoes;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.topN = topN;
    }
    
    @Override
    public String gerar() {
        StringBuilder sb = new StringBuilder();
        sb.append(formatarCabecalho());
        
        // Filtra e ordena despesas no período
        List<Transacao> despesasOrdenadas = transacoes.stream()
            .filter(t -> !t.getData().isBefore(dataInicio) && !t.getData().isAfter(dataFim))
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .sorted((t1, t2) -> Double.compare(t2.getValor(), t1.getValor()))
            .limit(topN)
            .collect(Collectors.toList());
        
        if (despesasOrdenadas.isEmpty()) {
            sb.append("Nenhuma despesa encontrada no período.\n");
            sb.append(formatarRodape());
            return sb.toString();
        }
        
        // Cabeçalho da tabela
        sb.append(String.format("%-5s %-12s %-20s %-25s %15s\n", 
            "Rank", "Data", "Categoria", "Descrição", "Valor"));
        sb.append("-".repeat(80)).append("\n");
        
        // Lista as despesas
        int ranking = 1;
        double total = 0;
        
        for (Transacao t : despesasOrdenadas) {
            String categoria = t.getCategoria() != null ? t.getCategoria().name() : "N/A";
            String descricao = t.getDescricao().length() > 25 ? 
                             t.getDescricao().substring(0, 22) + "..." : t.getDescricao();
            
            String emoji = "";
            if (ranking == 1) emoji = "🥇";
            else if (ranking == 2) emoji = "🥈";
            else if (ranking == 3) emoji = "🥉";
            
            sb.append(String.format("%-5s %-12s %-20s %-25s %15s\n",
                emoji + ranking + "º",
                t.getData(),
                categoria,
                descricao,
                formatarMoeda(t.getValor())
            ));
            
            total += t.getValor();
            ranking++;
        }
        
        // Análise adicional por categoria
        sb.append("\n").append("=".repeat(80)).append("\n");
        sb.append(centralizarTexto("ANÁLISE POR CATEGORIA", 80)).append("\n");
        sb.append("=".repeat(80)).append("\n\n");
        
        Map<Categoria, Long> categoriaCount = despesasOrdenadas.stream()
            .filter(t -> t.getCategoria() != null)
            .collect(Collectors.groupingBy(Transacao::getCategoria, Collectors.counting()));
        
        if (!categoriaCount.isEmpty()) {
            sb.append("Categorias mais frequentes no ranking:\n");
            categoriaCount.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .forEach(entry -> {
                    sb.append(String.format("  - %s: %d ocorrência(s)\n", 
                        entry.getKey().name(), entry.getValue()));
                });
        }
        
        // Resumo
        sb.append("\n").append("-".repeat(80)).append("\n");
        sb.append(String.format("Total das %d maiores despesas: %15s\n", 
            despesasOrdenadas.size(), formatarMoeda(total)));
        sb.append(String.format("Valor médio: %15s\n", 
            formatarMoeda(total / despesasOrdenadas.size())));
        
        sb.append(formatarRodape());
        return sb.toString();
    }
    
    @Override
    public void exportarParaArquivo(String caminhoArquivo) throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoArquivo))) {
            writer.print(gerar());
        }
    }
}
