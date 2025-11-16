import java.util.List;

// Interface Strategy para diferentes algoritmos de cálculo financeiro
public interface EstrategiaCalculo {
    double calcular(List<Transacao> transacoes);
    String getDescricao();
}
