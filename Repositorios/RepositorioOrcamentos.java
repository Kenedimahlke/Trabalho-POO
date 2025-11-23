import java.time.YearMonth;
import java.util.List;

// Repositório específico para Orçamentos (SRP)
public class RepositorioOrcamentos extends RepositorioMemoria<Orcamento> {
    
    @Override
    public Orcamento buscarPorId(String id) {
        return listarTodos().stream()
            .filter(o -> o.getNome().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    public List<Orcamento> buscarPorCategoria(Categoria categoria) {
        return listarTodos().stream()
            .filter(o -> o.getCategoria() == categoria)
            .toList();
    }
    
    public List<Orcamento> buscarPorMes(YearMonth mes) {
        return listarTodos().stream()
            .filter(o -> o.getMesReferencia().equals(mes))
            .toList();
    }
    
    public List<Orcamento> buscarEstourados() {
        return listarTodos().stream()
            .filter(Orcamento::isEstourado)
            .toList();
    }
    
    public List<Orcamento> buscarProximosDoLimite(double percentual) {
        return listarTodos().stream()
            .filter(o -> !o.isEstourado() && o.getPercentualGasto() >= percentual)
            .toList();
    }
}
