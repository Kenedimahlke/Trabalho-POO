package Relatorios;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Interfaces.*;
import java.time.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RelatorioResumoGrupo extends Relatorio {
    private List<Transacao> transacoes;
    private Grupo grupo;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public RelatorioResumoGrupo(List<Transacao> transacoes, Grupo grupo, LocalDate dataInicio, LocalDate dataFim) {
        super("RESUMO DE GRUPO: " + grupo.getNome(), 
              "Período: " + dataInicio + " a " + dataFim);
        this.transacoes = transacoes;
        this.grupo = grupo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    @Override
    public String gerar() {
        StringBuilder sb = new StringBuilder();
        sb.append(formatarCabecalho());
        
        List<Transacao> transacoesGrupo = transacoes.stream()
            .filter(t -> t.getPagador().equals(grupo))
            .filter(t -> !t.getData().isBefore(dataInicio) && !t.getData().isAfter(dataFim))
            .collect(Collectors.toList());

        double totalReceitas = transacoesGrupo.stream()
            .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
            .mapToDouble(Transacao::getValor)
            .sum();

        double totalDespesas = transacoesGrupo.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .mapToDouble(Transacao::getValor)
            .sum();

        sb.append(String.format("Total Receitas: %15s\n", formatarMoeda(totalReceitas)));
        sb.append(String.format("Total Despesas: %15s\n", formatarMoeda(totalDespesas)));
        sb.append(String.format("Saldo do Período: %13s\n\n", formatarMoeda(totalReceitas - totalDespesas)));

        sb.append("--- Despesas por Categoria ---\n");
        Map<String, Double> porCategoria = transacoesGrupo.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .collect(Collectors.groupingBy(
                t -> t.getCategoria().getNome(),
                Collectors.summingDouble(Transacao::getValor)
            ));

        porCategoria.forEach((cat, val) -> {
            sb.append(String.format("%-20s: %15s\n", cat, formatarMoeda(val)));
        });

        sb.append("\n--- Membros do Grupo ---\n");
        
        sb.append("Membros: " + grupo.getQuantidadeMembros() + "\n");
        
        sb.append(formatarRodape());
        return sb.toString();
    }

    @Override
    public void exportarParaArquivo(String caminhoArquivo) throws Exception {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(caminhoArquivo))) {
            writer.print(gerar());
        }
    }
}