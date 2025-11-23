package Relatorios;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Interfaces.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

// Relatório que mostra todas as transações em um período específico
public class RelatorioPorPeriodo extends Relatorio {
    
    private List<Transacao> transacoes;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    
    public RelatorioPorPeriodo(List<Transacao> transacoes, LocalDate dataInicio, LocalDate dataFim) {
        super("RELATÓRIO POR PERÍODO", 
              "Transações entre " + dataInicio + " e " + dataFim);
        this.transacoes = transacoes;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }
    
    @Override
    public String gerar() {
        StringBuilder sb = new StringBuilder();
        sb.append(formatarCabecalho());
        
        // Filtra transações no período
        List<Transacao> transacoesPeriodo = transacoes.stream()
            .filter(t -> !t.getData().isBefore(dataInicio) && !t.getData().isAfter(dataFim))
            .sorted((t1, t2) -> t1.getData().compareTo(t2.getData()))
            .collect(Collectors.toList());
        
        if (transacoesPeriodo.isEmpty()) {
            sb.append("Nenhuma transação encontrada no período.\n");
            sb.append(formatarRodape());
            return sb.toString();
        }
        
        // Cabeçalho da tabela
        sb.append(String.format("%-12s %-10s %-20s %-15s %15s\n", 
            "Data", "Tipo", "Categoria", "Descrição", "Valor"));
        sb.append("-".repeat(80)).append("\n");
        
        double totalReceitas = 0;
        double totalDespesas = 0;
        
        // Lista as transações
        for (Transacao t : transacoesPeriodo) {
            String tipo = t.getTipo() == TipoTransacao.RECEITA ? "RECEITA" : "DESPESA";
            String categoria = t.getCategoria() != null ? t.getCategoria().name() : "N/A";
            String descricao = t.getDescricao().length() > 15 ? 
                             t.getDescricao().substring(0, 12) + "..." : t.getDescricao();
            
            sb.append(String.format("%-12s %-10s %-20s %-15s %15s\n",
                t.getData(),
                tipo,
                categoria,
                descricao,
                formatarMoeda(t.getValor())
            ));
            
            if (t.getTipo() == TipoTransacao.RECEITA) {
                totalReceitas += t.getValor();
            } else {
                totalDespesas += t.getValor();
            }
        }
        
        // Resumo
        sb.append("-".repeat(80)).append("\n");
        sb.append(String.format("Total de Receitas: %15s\n", formatarMoeda(totalReceitas)));
        sb.append(String.format("Total de Despesas: %15s\n", formatarMoeda(totalDespesas)));
        sb.append(String.format("Saldo do Período:  %15s\n", formatarMoeda(totalReceitas - totalDespesas)));
        sb.append(String.format("Total de Transações: %d\n", transacoesPeriodo.size()));
        
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