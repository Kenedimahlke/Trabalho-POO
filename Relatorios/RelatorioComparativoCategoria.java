import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

// Relatório que compara gastos entre diferentes categorias
public class RelatorioComparativoCategoria extends Relatorio {
    
    private List<Transacao> transacoes;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    
    public RelatorioComparativoCategoria(List<Transacao> transacoes, LocalDate dataInicio, LocalDate dataFim) {
        super("RELATÓRIO COMPARATIVO POR CATEGORIA", 
              "Análise de gastos por categoria entre " + dataInicio + " e " + dataFim);
        this.transacoes = transacoes;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }
    
    @Override
    public String gerar() {
        StringBuilder sb = new StringBuilder();
        sb.append(formatarCabecalho());
        
        // Filtra transações no período (apenas despesas)
        List<Transacao> despesasPeriodo = transacoes.stream()
            .filter(t -> !t.getData().isBefore(dataInicio) && !t.getData().isAfter(dataFim))
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .collect(Collectors.toList());
        
        if (despesasPeriodo.isEmpty()) {
            sb.append("Nenhuma despesa encontrada no período.\n");
            sb.append(formatarRodape());
            return sb.toString();
        }
        
        // Agrupa por categoria e calcula totais
        Map<Categoria, Double> gastosPorCategoria = new HashMap<>();
        Map<Categoria, Integer> contagemPorCategoria = new HashMap<>();
        
        for (Transacao t : despesasPeriodo) {
            Categoria cat = t.getCategoria();
            if (cat != null) {
                gastosPorCategoria.put(cat, gastosPorCategoria.getOrDefault(cat, 0.0) + t.getValor());
                contagemPorCategoria.put(cat, contagemPorCategoria.getOrDefault(cat, 0) + 1);
            }
        }
        
        double totalGastos = gastosPorCategoria.values().stream().mapToDouble(Double::doubleValue).sum();
        
        // Ordena por valor (maior para menor)
        List<Map.Entry<Categoria, Double>> listaOrdenada = gastosPorCategoria.entrySet().stream()
            .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()))
            .collect(Collectors.toList());
        
        // Cabeçalho da tabela
        sb.append(String.format("%-25s %15s %10s %15s\n", 
            "Categoria", "Total Gasto", "Qtd", "% do Total"));
        sb.append("-".repeat(80)).append("\n");
        
        // Lista as categorias
        for (Map.Entry<Categoria, Double> entry : listaOrdenada) {
            Categoria cat = entry.getKey();
            double valor = entry.getValue();
            int qtd = contagemPorCategoria.get(cat);
            double percentual = (valor / totalGastos) * 100;
            
            sb.append(String.format("%-25s %15s %10d %14.2f%%\n",
                cat.name(),
                formatarMoeda(valor),
                qtd,
                percentual
            ));
            
            // Adiciona barra de visualização
            int barras = (int) (percentual / 2); // 50 caracteres = 100%
            sb.append("  ").append("█".repeat(Math.max(0, barras))).append("\n");
        }
        
        // Resumo
        sb.append("-".repeat(80)).append("\n");
        sb.append(String.format("Total de Gastos: %15s\n", formatarMoeda(totalGastos)));
        sb.append(String.format("Número de Categorias: %d\n", gastosPorCategoria.size()));
        sb.append(String.format("Total de Transações: %d\n", despesasPeriodo.size()));
        sb.append(String.format("Gasto Médio por Transação: %15s\n", 
            formatarMoeda(totalGastos / despesasPeriodo.size())));
        
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
