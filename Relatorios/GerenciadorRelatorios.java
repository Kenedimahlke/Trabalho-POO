package Relatorios;

import Entidades.*;
import Enums.*;
import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class GerenciadorRelatorios {
    private List<Transacao> transacoes;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public GerenciadorRelatorios(List<Transacao> transacoes) {
        this.transacoes = new ArrayList<>(transacoes);
    }
    
    public void atualizarTransacoes(List<Transacao> transacoes) {
        this.transacoes = new ArrayList<>(transacoes);
    }
    
    public String gerarRelatorioPorPeriodo(LocalDate inicio, LocalDate fim) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=".repeat(65)).append("\n");
        sb.append("           RELATORIO DE GASTOS POR PERIODO\n");
        sb.append("=".repeat(65)).append("\n");
        sb.append("Periodo: ").append(inicio.format(formatter))
          .append(" ate ").append(fim.format(formatter)).append("\n");
        sb.append("=".repeat(65)).append("\n\n");
        
        List<Transacao> transacoesPeriodo = filtrarPorPeriodo(inicio, fim);
        
        double totalReceitas = transacoesPeriodo.stream()
            .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
            .mapToDouble(Transacao::getValor)
            .sum();
            
        double totalDespesas = transacoesPeriodo.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .mapToDouble(Transacao::getValor)
            .sum();
        
        sb.append(String.format("Total de Receitas: R$ %.2f\n", totalReceitas));
        sb.append(String.format("Total de Despesas: R$ %.2f\n", totalDespesas));
        sb.append(String.format("Saldo do Periodo:  R$ %.2f\n\n", totalReceitas - totalDespesas));
        
        sb.append("Transacoes do Periodo:\n");
        sb.append("-".repeat(65)).append("\n");
        for (Transacao t : transacoesPeriodo) {
            sb.append(String.format("%s | %s | R$ %.2f | %s\n",
                t.getData().format(formatter),
                t.getTipo(),
                t.getValor(),
                t.getDescricao()));
        }
        
        return sb.toString();
    }
    
    public String gerarRelatorioComparativoCategoria(LocalDate inicio, LocalDate fim) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=".repeat(65)).append("\n");
        sb.append("        RELATORIO COMPARATIVO POR CATEGORIA\n");
        sb.append("=".repeat(65)).append("\n");
        sb.append("Periodo: ").append(inicio.format(formatter))
          .append(" ate ").append(fim.format(formatter)).append("\n");
        sb.append("=".repeat(65)).append("\n\n");
        
        List<Transacao> transacoesPeriodo = filtrarPorPeriodo(inicio, fim);
        
        Map<Categoria, Double> gastosPorCategoria = transacoesPeriodo.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .collect(Collectors.groupingBy(
                Transacao::getCategoria,
                Collectors.summingDouble(Transacao::getValor)
            ));
        
        double totalDespesas = gastosPorCategoria.values().stream()
            .mapToDouble(Double::doubleValue)
            .sum();
        
        sb.append(String.format("%-25s | %-12s | %-10s\n", "Categoria", "Valor", "Percentual"));
        sb.append("-".repeat(65)).append("\n");
        
        gastosPorCategoria.entrySet().stream()
            .sorted(Map.Entry.<Categoria, Double>comparingByValue().reversed())
            .forEach(entry -> {
                double percentual = (entry.getValue() / totalDespesas) * 100;
                sb.append(String.format("%-25s | R$ %9.2f | %6.2f%%\n",
                    entry.getKey().getNome(),
                    entry.getValue(),
                    percentual));
            });
        
        sb.append("-".repeat(65)).append("\n");
        sb.append(String.format("TOTAL: R$ %.2f\n", totalDespesas));
        
        return sb.toString();
    }
    
    public String gerarRelatorioRankingDespesas(LocalDate inicio, LocalDate fim, int topN) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=".repeat(65)).append("\n");
        sb.append("          TOP ").append(topN).append(" MAIORES DESPESAS\n");
        sb.append("=".repeat(65)).append("\n");
        sb.append("Periodo: ").append(inicio.format(formatter))
          .append(" ate ").append(fim.format(formatter)).append("\n");
        sb.append("=".repeat(65)).append("\n\n");
        
        List<Transacao> despesas = filtrarPorPeriodo(inicio, fim).stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .sorted(Comparator.comparingDouble(Transacao::getValor).reversed())
            .limit(topN)
            .collect(Collectors.toList());
        
        sb.append(String.format("%-5s | %-12s | %-12s | %s\n", "Pos", "Data", "Valor", "Descricao"));
        sb.append("-".repeat(65)).append("\n");
        
        int posicao = 1;
        for (Transacao t : despesas) {
            sb.append(String.format("%-5d | %-12s | R$ %9.2f | %s\n",
                posicao++,
                t.getData().format(formatter),
                t.getValor(),
                t.getDescricao()));
        }
        
        return sb.toString();
    }
    
    public String gerarRelatorioEvolucaoSaldo(LocalDate inicio, LocalDate fim, double saldoInicial) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=".repeat(65)).append("\n");
        sb.append("           RELATORIO DE EVOLUCAO DE SALDO\n");
        sb.append("=".repeat(65)).append("\n");
        sb.append("Periodo: ").append(inicio.format(formatter))
          .append(" ate ").append(fim.format(formatter)).append("\n");
        sb.append("=".repeat(65)).append("\n\n");
        
        List<Transacao> transacoesPeriodo = filtrarPorPeriodo(inicio, fim);
        transacoesPeriodo.sort(Comparator.comparing(Transacao::getData));
        
        sb.append(String.format("Saldo Inicial: R$ %.2f\n\n", saldoInicial));
        sb.append(String.format("%-12s | %-12s | %-12s | %s\n", "Data", "Tipo", "Valor", "Saldo"));
        sb.append("-".repeat(65)).append("\n");
        
        double saldoAtual = saldoInicial;
        for (Transacao t : transacoesPeriodo) {
            if (t.getTipo() == TipoTransacao.RECEITA) {
                saldoAtual += t.getValor();
            } else {
                saldoAtual -= t.getValor();
            }
            
            sb.append(String.format("%-12s | %-12s | R$ %9.2f | R$ %9.2f\n",
                t.getData().format(formatter),
                t.getTipo(),
                t.getValor(),
                saldoAtual));
        }
        
        sb.append("-".repeat(65)).append("\n");
        sb.append(String.format("Saldo Final: R$ %.2f\n", saldoAtual));
        sb.append(String.format("Variacao:    R$ %.2f\n", saldoAtual - saldoInicial));
        
        return sb.toString();
    }
    
    public String gerarRelatorioCompleto(LocalDate inicio, LocalDate fim, double saldoInicial) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=".repeat(65)).append("\n");
        sb.append("              RELATORIO FINANCEIRO COMPLETO\n");
        sb.append("=".repeat(65)).append("\n");
        sb.append("Periodo: ").append(inicio.format(formatter))
          .append(" ate ").append(fim.format(formatter)).append("\n");
        sb.append("=".repeat(65)).append("\n\n");
        
        List<Transacao> transacoesPeriodo = filtrarPorPeriodo(inicio, fim);
        
        double totalReceitas = transacoesPeriodo.stream()
            .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
            .mapToDouble(Transacao::getValor)
            .sum();
            
        double totalDespesas = transacoesPeriodo.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .mapToDouble(Transacao::getValor)
            .sum();
        
        sb.append("RESUMO GERAL\n");
        sb.append("-".repeat(65)).append("\n");
        sb.append(String.format("Saldo Inicial:     R$ %.2f\n", saldoInicial));
        sb.append(String.format("Total Receitas:    R$ %.2f\n", totalReceitas));
        sb.append(String.format("Total Despesas:    R$ %.2f\n", totalDespesas));
        sb.append(String.format("Saldo Final:       R$ %.2f\n\n", saldoInicial + totalReceitas - totalDespesas));
        
        Map<Categoria, Double> gastosPorCategoria = transacoesPeriodo.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .collect(Collectors.groupingBy(
                Transacao::getCategoria,
                Collectors.summingDouble(Transacao::getValor)
            ));
        
        sb.append("DESPESAS POR CATEGORIA\n");
        sb.append("-".repeat(65)).append("\n");
        gastosPorCategoria.entrySet().stream()
            .sorted(Map.Entry.<Categoria, Double>comparingByValue().reversed())
            .forEach(entry -> {
                double percentual = (entry.getValue() / totalDespesas) * 100;
                sb.append(String.format("%-25s: R$ %9.2f (%5.2f%%)\n",
                    entry.getKey().getNome(),
                    entry.getValue(),
                    percentual));
            });
        
        return sb.toString();
    }
    
    public String gerarRelatorioResumoGrupo(Grupo grupo, LocalDate inicio, LocalDate fim) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("=".repeat(65)).append("\n");
        sb.append("           RELATORIO RESUMO DO GRUPO\n");
        sb.append("=".repeat(65)).append("\n");
        sb.append("Grupo: ").append(grupo.getNome()).append("\n");
        sb.append("Periodo: ").append(inicio.format(formatter))
          .append(" ate ").append(fim.format(formatter)).append("\n");
        sb.append("=".repeat(65)).append("\n\n");
        
        UsuarioIndividual[] membros = grupo.getMembros();
        sb.append("Membros do Grupo:\n");
        for (UsuarioIndividual u : membros) {
            sb.append("- ").append(u.getNome()).append("\n");
        }
        sb.append("\n");
        
        List<Transacao> transacoesGrupo = filtrarPorPeriodo(inicio, fim).stream()
            .filter(t -> {
                Usuario titular = t.getConta().getTitular();
                for (UsuarioIndividual m : membros) {
                    if (m.equals(titular)) return true;
                }
                return false;
            })
            .collect(Collectors.toList());
        
        double totalReceitas = transacoesGrupo.stream()
            .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
            .mapToDouble(Transacao::getValor)
            .sum();
            
        double totalDespesas = transacoesGrupo.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .mapToDouble(Transacao::getValor)
            .sum();
        
        sb.append(String.format("Total de Receitas: R$ %.2f\n", totalReceitas));
        sb.append(String.format("Total de Despesas: R$ %.2f\n", totalDespesas));
        sb.append(String.format("Saldo do Grupo:    R$ %.2f\n", totalReceitas - totalDespesas));
        
        return sb.toString();
    }
    
    public void exportarRelatorioPorPeriodo(LocalDate inicio, LocalDate fim, String caminhoArquivo) {
        exportarParaArquivo(gerarRelatorioPorPeriodo(inicio, fim), caminhoArquivo);
    }
    
    public void exportarRelatorioComparativoCategoria(LocalDate inicio, LocalDate fim, String caminhoArquivo) {
        exportarParaArquivo(gerarRelatorioComparativoCategoria(inicio, fim), caminhoArquivo);
    }
    
    public void exportarRelatorioRankingDespesas(LocalDate inicio, LocalDate fim, int topN, String caminhoArquivo) {
        exportarParaArquivo(gerarRelatorioRankingDespesas(inicio, fim, topN), caminhoArquivo);
    }
    
    public void exportarRelatorioEvolucaoSaldo(LocalDate inicio, LocalDate fim, double saldoInicial, String caminhoArquivo) {
        exportarParaArquivo(gerarRelatorioEvolucaoSaldo(inicio, fim, saldoInicial), caminhoArquivo);
    }
    
    public void exportarRelatorioCompleto(LocalDate inicio, LocalDate fim, double saldoInicial, String caminhoArquivo) {
        exportarParaArquivo(gerarRelatorioCompleto(inicio, fim, saldoInicial), caminhoArquivo);
    }
    
    private void exportarParaArquivo(String conteudo, String caminhoArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(caminhoArquivo))) {
            writer.println(conteudo);
            System.out.println("\nRelatorio exportado com sucesso para: " + caminhoArquivo);
        } catch (IOException e) {
            System.out.println("\nErro ao exportar relatorio: " + e.getMessage());
        }
    }
    
    private List<Transacao> filtrarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return transacoes.stream()
            .filter(t -> !t.getData().isBefore(inicio) && !t.getData().isAfter(fim))
            .collect(Collectors.toList());
    }
}
