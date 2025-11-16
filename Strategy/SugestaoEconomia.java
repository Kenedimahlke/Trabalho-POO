import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Estratégia para sugerir economia baseada nos gastos por categoria
public class SugestaoEconomia implements EstrategiaCalculo {
    
    @Override
    public double calcular(List<Transacao> transacoes) {
        if (transacoes.isEmpty()) {
            return 0.0;
        }
        
        Map<Categoria, Double> gastosPorCategoria = transacoes.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .collect(Collectors.groupingBy(
                Transacao::getCategoria,
                Collectors.summingDouble(Transacao::getValor)
            ));
        
        if (gastosPorCategoria.isEmpty()) {
            return 0.0;
        }
            
        // Retorna 15% do maior gasto como sugestão de economia
        return gastosPorCategoria.values().stream()
            .max(Double::compare)
            .orElse(0.0) * 0.15;
    }
    
    @Override
    public String getDescricao() {
        return "Sugestão de economia de 15% da categoria com maior gasto";
    }
    
    public Map<Categoria, Double> analisarGastosPorCategoria(List<Transacao> transacoes) {
        return transacoes.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .collect(Collectors.groupingBy(
                Transacao::getCategoria,
                Collectors.summingDouble(Transacao::getValor)
            ));
    }
}
