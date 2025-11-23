package Relatorios;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Interfaces.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

// Relatório que mostra a evolução do saldo ao longo do tempo
public class RelatorioEvolucaoSaldo extends Relatorio {
    
    private List<Transacao> transacoes;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private double saldoInicial;
    
    public RelatorioEvolucaoSaldo(List<Transacao> transacoes, LocalDate dataInicio, 
                                 LocalDate dataFim, double saldoInicial) {
        super("EVOLUÇÃO DO SALDO", 
              "Evolução do saldo entre " + dataInicio + " e " + dataFim);
        this.transacoes = transacoes;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.saldoInicial = saldoInicial;
    }
    
    @Override
    public String gerar() {
        StringBuilder sb = new StringBuilder();
        sb.append(formatarCabecalho());
        
        sb.append(String.format("Saldo Inicial: %15s\n\n", formatarMoeda(saldoInicial)));
        
        // Filtra e ordena transações
        List<Transacao> transacoesPeriodo = transacoes.stream()
            .filter(t -> !t.getData().isBefore(dataInicio) && !t.getData().isAfter(dataFim))
            .sorted((t1, t2) -> t1.getData().compareTo(t2.getData()))
            .collect(Collectors.toList());
        
        if (transacoesPeriodo.isEmpty()) {
            sb.append("Nenhuma transação encontrada no período.\n");
            sb.append(formatarRodape());
            return sb.toString();
        }
        
        // Agrupa por mês
        Map<YearMonth, List<Transacao>> transacoesPorMes = transacoesPeriodo.stream()
            .collect(Collectors.groupingBy(t -> YearMonth.from(t.getData())));
        
        // Ordena os meses
        List<YearMonth> mesesOrdenados = transacoesPorMes.keySet().stream()
            .sorted()
            .collect(Collectors.toList());
        
        sb.append(String.format("%-15s %15s %15s %15s %15s\n", 
            "Mês/Ano", "Receitas", "Despesas", "Saldo Período", "Saldo Acumulado"));
        sb.append("-".repeat(90)).append("\n");
        
        double saldoAcumulado = saldoInicial;
        double totalReceitas = 0;
        double totalDespesas = 0;
        
        for (YearMonth mes : mesesOrdenados) {
            List<Transacao> transacoesMes = transacoesPorMes.get(mes);
            
            double receitasMes = transacoesMes.stream()
                .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
                .mapToDouble(Transacao::getValor)
                .sum();
            
            double despesasMes = transacoesMes.stream()
                .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
                .mapToDouble(Transacao::getValor)
                .sum();
            
            double saldoMes = receitasMes - despesasMes;
            saldoAcumulado += saldoMes;
            
            totalReceitas += receitasMes;
            totalDespesas += despesasMes;
            
            sb.append(String.format("%-15s %15s %15s %15s %15s\n",
                mes.toString(),
                formatarMoeda(receitasMes),
                formatarMoeda(despesasMes),
                formatarMoeda(saldoMes),
                formatarMoeda(saldoAcumulado)
            ));
        }
        
        // Gráfico ASCII simplificado de evolução
        sb.append("\n").append("=".repeat(90)).append("\n");
        sb.append(centralizarTexto("GRÁFICO DE EVOLUÇÃO", 90)).append("\n");
        sb.append("=".repeat(90)).append("\n\n");
        
        sb.append(gerarGraficoASCII(mesesOrdenados, transacoesPorMes, saldoInicial));
        
        // Análise estatística
        sb.append("\n").append("=".repeat(90)).append("\n");
        sb.append(centralizarTexto("ANÁLISE ESTATÍSTICA", 90)).append("\n");
        sb.append("=".repeat(90)).append("\n\n");
        
        double mediaReceitas = totalReceitas / mesesOrdenados.size();
        double mediaDespesas = totalDespesas / mesesOrdenados.size();
        double variacao = ((saldoAcumulado - saldoInicial) / saldoInicial) * 100;
        
        sb.append(String.format("Total de Receitas no Período: %15s\n", formatarMoeda(totalReceitas)));
        sb.append(String.format("Total de Despesas no Período: %15s\n", formatarMoeda(totalDespesas)));
        sb.append(String.format("Média de Receitas Mensais: %15s\n", formatarMoeda(mediaReceitas)));
        sb.append(String.format("Média de Despesas Mensais: %15s\n", formatarMoeda(mediaDespesas)));
        sb.append(String.format("Saldo Final: %15s\n", formatarMoeda(saldoAcumulado)));
        sb.append(String.format("Variação no Período: %14.2f%%\n", variacao));
        
        if (variacao > 0) {
            sb.append("\nSeu saldo AUMENTOU no período!\n");
        } else if (variacao < 0) {
            sb.append("\nSeu saldo DIMINUIU no período. Atenção aos gastos!\n");
        } else {
            sb.append("\n○ Seu saldo permaneceu estável no período.\n");
        }
        
        sb.append(formatarRodape());
        return sb.toString();
    }
    
    private String gerarGraficoASCII(List<YearMonth> meses, 
                                    Map<YearMonth, List<Transacao>> transacoesPorMes,
                                    double saldoInicial) {
        StringBuilder sb = new StringBuilder();
        
        // Calcula saldos acumulados
        List<Double> saldos = new ArrayList<>();
        double saldo = saldoInicial;
        saldos.add(saldo);
        
        for (YearMonth mes : meses) {
            List<Transacao> trans = transacoesPorMes.get(mes);
            double receitas = trans.stream()
                .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
                .mapToDouble(Transacao::getValor).sum();
            double despesas = trans.stream()
                .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
                .mapToDouble(Transacao::getValor).sum();
            saldo += (receitas - despesas);
            saldos.add(saldo);
        }
        
        // Encontra min e max para escala
        double maxSaldo = saldos.stream().max(Double::compare).orElse(saldoInicial);
        double minSaldo = saldos.stream().min(Double::compare).orElse(saldoInicial);
        double range = maxSaldo - minSaldo;
        
        if (range == 0) range = 1; // Evita divisão por zero
        
        int altura = 10;
        
        for (int i = 0; i < meses.size(); i++) {
            double saldoMes = saldos.get(i + 1);
            int barras = (int) (((saldoMes - minSaldo) / range) * altura);
            
            sb.append(String.format("%-10s ", meses.get(i).toString()));
            sb.append("│").append("█".repeat(Math.max(0, barras)));
            sb.append(" ").append(formatarMoeda(saldoMes));
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    @Override
    public void exportarParaArquivo(String caminhoArquivo) throws Exception {
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoArquivo))) {
            writer.print(gerar());
        }
    }
}