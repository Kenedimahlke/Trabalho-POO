package Strategy;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Interfaces.*;
import java.time.*;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Estratégia para rateio automático de despesas entre membros de um grupo
public class RateioAutomatico implements EstrategiaCalculo {
    private Map<Usuario, Double> pesos;
    
    public RateioAutomatico() {
        this.pesos = new HashMap<>();
    }
    
    public void adicionarMembro(Usuario usuario, double peso) {
        pesos.put(usuario, peso);
    }
    
    @Override
    public double calcular(List<Transacao> transacoes) {
        // Calcula o total de despesas compartilhadas
        return transacoes.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .mapToDouble(Transacao::getValor)
            .sum();
    }
    
    @Override
    public String getDescricao() {
        return "Rateio automático de despesas com pesos personalizados";
    }
    
    // Calcula quanto cada membro deve pagar
    public Map<Usuario, Double> calcularRateio(double valorTotal) {
        Map<Usuario, Double> rateio = new HashMap<>();
        
        if (pesos.isEmpty()) {
            return rateio;
        }
        
        double somaPesos = pesos.values().stream()
            .mapToDouble(Double::doubleValue)
            .sum();
        
        for (Map.Entry<Usuario, Double> entry : pesos.entrySet()) {
            double proporcao = entry.getValue() / somaPesos;
            double valorMembro = valorTotal * proporcao;
            rateio.put(entry.getKey(), valorMembro);
        }
        
        return rateio;
    }
    
    public Map<Usuario, Double> getPesos() {
        return new HashMap<>(pesos);
    }
}