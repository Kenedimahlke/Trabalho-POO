import java.util.List;

// Repositório específico para Contas (SRP)
public class RepositorioContas extends RepositorioMemoria<ContaFinanceira> {
    
    @Override
    public ContaFinanceira buscarPorId(String id) {
        return listarTodos().stream()
            .filter(c -> c.getNumeroConta().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    public List<ContaFinanceira> buscarPorTitular(Usuario titular) {
        return listarTodos().stream()
            .filter(c -> c.getTitular().equals(titular))
            .toList();
    }
    
    public List<ContaFinanceira> buscarPorTipo(String tipo) {
        return listarTodos().stream()
            .filter(c -> c.getTipoConta().equals(tipo))
            .toList();
    }
    
    public double calcularSaldoTotal() {
        return listarTodos().stream()
            .mapToDouble(ContaFinanceira::consultarSaldo)
            .sum();
    }
}
