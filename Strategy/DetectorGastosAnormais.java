import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Estratégia para detectar gastos fora do padrão
public class DetectorGastosAnormais implements EstrategiaCalculo {
    private double limiteDesvio = 1.5; // 50% acima da média
    
    @Override
    public double calcular(List<Transacao> transacoes) {
        if (transacoes.isEmpty()) {
            return 0.0;
        }
        
        double mediaDespesas = transacoes.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .mapToDouble(Transacao::getValor)
            .average()
            .orElse(0.0);
        
        return mediaDespesas * limiteDesvio;
    }
    
    @Override
    public String getDescricao() {
        return "Detector de gastos anormais (acima de " + (limiteDesvio * 100) + "% da média)";
    }
    
    // Detecta transações que estão fora do padrão
    public List<Transacao> detectarAnormalidades(List<Transacao> transacoes) {
        List<Transacao> anormais = new ArrayList<>();
        
        if (transacoes.isEmpty()) {
            return anormais;
        }
        
        Map<Categoria, Double> mediaPorCategoria = transacoes.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .collect(Collectors.groupingBy(
                Transacao::getCategoria,
                Collectors.averagingDouble(Transacao::getValor)
            ));
        
        for (Transacao t : transacoes) {
            if (t.getTipo() == TipoTransacao.DESPESA) {
                double media = mediaPorCategoria.getOrDefault(t.getCategoria(), 0.0);
                if (t.getValor() > media * limiteDesvio) {
                    anormais.add(t);
                }
            }
        }
        
        return anormais;
    }
    
    public void setLimiteDesvio(double limiteDesvio) {
        this.limiteDesvio = limiteDesvio;
    }
    
    public double getLimiteDesvio() {
        return limiteDesvio;
    }
}
