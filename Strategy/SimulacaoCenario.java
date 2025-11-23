package Strategy;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Interfaces.*;
import java.time.*;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Estratégia para simulação de cenários financeiros
public class SimulacaoCenario implements EstrategiaCalculo {
    
    @Override
    public double calcular(List<Transacao> transacoes) {
        // Retorna 0 - este método não é usado diretamente
        return 0;
    }
    
    @Override
    public String getDescricao() {
        return "Simulação de Cenários Financeiros";
    }
    
    // Simula cenário de mudança nos gastos
    public ResultadoSimulacao simularMudancaGastos(List<Transacao> transacoes, 
                                                    double saldoAtual,
                                                    Categoria categoria,
                                                    double percentualMudanca,
                                                    int meses) {
        
        // Calcula média mensal de gastos na categoria
        double mediaGastosCategoria = transacoes.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .filter(t -> t.getCategoria() == categoria)
            .mapToDouble(Transacao::getValor)
            .average()
            .orElse(0);
        
        // Calcula total de receitas e despesas médias
        double mediaReceitas = transacoes.stream()
            .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
            .mapToDouble(Transacao::getValor)
            .average()
            .orElse(0);
            
        double mediaDespesas = transacoes.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .mapToDouble(Transacao::getValor)
            .average()
            .orElse(0);
        
        // Aplica a mudança percentual na categoria específica
        double mudancaValor = mediaGastosCategoria * (percentualMudanca / 100.0);
        double novaMediaDespesas = mediaDespesas + mudancaValor;
        
        // Projeta saldo futuro
        double saldoMensalMedio = mediaReceitas - mediaDespesas;
        double novoSaldoMensal = mediaReceitas - novaMediaDespesas;
        
        double saldoProjetadoAtual = saldoAtual + (saldoMensalMedio * meses);
        double saldoProjetadoNovo = saldoAtual + (novoSaldoMensal * meses);
        
        return new ResultadoSimulacao(
            saldoAtual,
            saldoProjetadoAtual,
            saldoProjetadoNovo,
            saldoProjetadoNovo - saldoProjetadoAtual,
            categoria,
            percentualMudanca,
            mudancaValor,
            meses
        );
    }
    
    // Simula cenário de mudança global nos gastos
    public ResultadoSimulacao simularMudancaGlobal(List<Transacao> transacoes,
                                                    double saldoAtual,
                                                    double percentualMudanca,
                                                    int meses) {
        
        // Calcula médias mensais
        double mediaReceitas = transacoes.stream()
            .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
            .mapToDouble(Transacao::getValor)
            .average()
            .orElse(0);
            
        double mediaDespesas = transacoes.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .mapToDouble(Transacao::getValor)
            .average()
            .orElse(0);
        
        // Aplica mudança percentual em todas as despesas
        double mudancaValor = mediaDespesas * (percentualMudanca / 100.0);
        double novaMediaDespesas = mediaDespesas + mudancaValor;
        
        // Projeta cenários
        double saldoMensalAtual = mediaReceitas - mediaDespesas;
        double saldoMensalNovo = mediaReceitas - novaMediaDespesas;
        
        double saldoProjetadoAtual = saldoAtual + (saldoMensalAtual * meses);
        double saldoProjetadoNovo = saldoAtual + (saldoMensalNovo * meses);
        
        return new ResultadoSimulacao(
            saldoAtual,
            saldoProjetadoAtual,
            saldoProjetadoNovo,
            saldoProjetadoNovo - saldoProjetadoAtual,
            null,
            percentualMudanca,
            mudancaValor,
            meses
        );
    }
    
    // Simula cenário de nova despesa recorrente
    public ResultadoSimulacao simularNovaDespesa(List<Transacao> transacoes,
                                                  double saldoAtual,
                                                  double valorNovaDespesa,
                                                  int meses) {
        
        double mediaReceitas = transacoes.stream()
            .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
            .mapToDouble(Transacao::getValor)
            .average()
            .orElse(0);
            
        double mediaDespesas = transacoes.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .mapToDouble(Transacao::getValor)
            .average()
            .orElse(0);
        
        // Cenário atual
        double saldoMensalAtual = mediaReceitas - mediaDespesas;
        double saldoProjetadoAtual = saldoAtual + (saldoMensalAtual * meses);
        
        // Cenário com nova despesa
        double saldoMensalNovo = mediaReceitas - (mediaDespesas + valorNovaDespesa);
        double saldoProjetadoNovo = saldoAtual + (saldoMensalNovo * meses);
        
        return new ResultadoSimulacao(
            saldoAtual,
            saldoProjetadoAtual,
            saldoProjetadoNovo,
            saldoProjetadoNovo - saldoProjetadoAtual,
            null,
            0,
            valorNovaDespesa,
            meses
        );
    }
    
    // Simula múltiplos cenários e retorna o melhor
    public String compararCenarios(List<Transacao> transacoes, double saldoAtual, int meses) {
        StringBuilder resultado = new StringBuilder();
        resultado.append("=== COMPARAÇÃO DE CENÁRIOS ===\n\n");
        
        // Cenário 1: Reduzir 10% dos gastos
        ResultadoSimulacao result1 = simularMudancaGlobal(transacoes, saldoAtual, -10, meses);
        double cenario1 = result1.getImpactoTotal();
        resultado.append("Cenário 1: Reduzir 10% dos gastos\n");
        resultado.append(String.format("  Impacto: R$ %.2f (%.1f%% melhor)\n\n", 
            cenario1, (cenario1 / saldoAtual) * 100));
        
        // Cenário 2: Aumentar 10% dos gastos
        ResultadoSimulacao result2 = simularMudancaGlobal(transacoes, saldoAtual, 10, meses);
        double cenario2 = result2.getImpactoTotal();
        resultado.append("Cenário 2: Aumentar 10% dos gastos\n");
        resultado.append(String.format("  Impacto: R$ %.2f (%.1f%% pior)\n\n", 
            cenario2, (cenario2 / saldoAtual) * 100));
        
        // Cenário 3: Reduzir gastos com lazer
        ResultadoSimulacao result3 = simularMudancaGastos(transacoes, saldoAtual, Categoria.LAZER, -50, meses);
        double cenario3 = result3.getImpactoTotal();
        resultado.append("Cenário 3: Reduzir 50% dos gastos com lazer\n");
        resultado.append(String.format("  Impacto: R$ %.2f\n\n", cenario3));
        
        return resultado.toString();
    }

    public String compararCenarios(ResultadoSimulacao cenario1, ResultadoSimulacao cenario2) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== COMPARAÇÃO DE CENÁRIOS ===\n\n");
        sb.append("Cenário 1: ").append(cenario1.getSaldoProjetadoNovo()).append("\n");
        sb.append("Cenário 2: ").append(cenario2.getSaldoProjetadoNovo()).append("\n");
        return sb.toString();
    }
    
    // Classe interna para representar resultado da simulação
    public static class ResultadoSimulacao {
        private double saldoAtual;
        private double saldoProjetadoAtual;
        private double saldoProjetadoNovo;
        private double impactoTotal;
        private Categoria categoria;
        private double percentualMudanca;
        private double valorMudanca;
        private int meses;
        
        public ResultadoSimulacao(double saldoAtual, double saldoProjetadoAtual, 
                                 double saldoProjetadoNovo, double impactoTotal,
                                 Categoria categoria, double percentualMudanca,
                                 double valorMudanca, int meses) {
            this.saldoAtual = saldoAtual;
            this.saldoProjetadoAtual = saldoProjetadoAtual;
            this.saldoProjetadoNovo = saldoProjetadoNovo;
            this.impactoTotal = impactoTotal;
            this.categoria = categoria;
            this.percentualMudanca = percentualMudanca;
            this.valorMudanca = valorMudanca;
            this.meses = meses;
        }
        
        public double getImpactoTotal() { return impactoTotal; }
        public double getSaldoProjetadoNovo() { return saldoProjetadoNovo; }
        public double getImpactoMensal() { return valorMudanca; }
        public double getImpactoAnual() { return valorMudanca * 12; }
        
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== SIMULAÇÃO DE CENÁRIO ===\n\n");
            
            if (categoria != null) {
                sb.append("Categoria: ").append(categoria.getNome()).append("\n");
            } else {
                sb.append("Categoria: Todas\n");
            }
            
            sb.append(String.format("Mudança: %.1f%% (R$ %.2f/mês)\n", 
                percentualMudanca, valorMudanca));
            sb.append(String.format("Período: %d meses\n\n", meses));
            
            sb.append("--- CENÁRIO ATUAL ---\n");
            sb.append(String.format("Saldo Atual: R$ %.2f\n", saldoAtual));
            sb.append(String.format("Saldo em %d meses: R$ %.2f\n\n", meses, saldoProjetadoAtual));
            
            sb.append("--- CENÁRIO SIMULADO ---\n");
            sb.append(String.format("Saldo em %d meses: R$ %.2f\n\n", meses, saldoProjetadoNovo));
            
            sb.append("--- IMPACTO ---\n");
            sb.append(String.format("Diferença: R$ %.2f ", impactoTotal));
            
            if (impactoTotal > 0) {
                sb.append("✓ (Melhor)\n");
            } else if (impactoTotal < 0) {
                sb.append("✗ (Pior)\n");
            } else {
                sb.append("= (Igual)\n");
            }
            
            return sb.toString();
        }
    }
}