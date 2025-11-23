import java.time.LocalDate;
import java.util.List;

// Repositório específico para Metas (SRP)
public class RepositorioMetas extends RepositorioMemoria<Meta> {
    
    @Override
    public Meta buscarPorId(String id) {
        // Meta não tem ID único no momento
        return null;
    }
    
    public List<Meta> buscarPorUsuario(Usuario usuario) {
        return listarTodos().stream()
            .filter(m -> m.getUsuario().equals(usuario))
            .toList();
    }
    
    public List<Meta> buscarConcluidas() {
        return listarTodos().stream()
            .filter(Meta::isConcluida)
            .toList();
    }
    
    public List<Meta> buscarPendentes() {
        return listarTodos().stream()
            .filter(m -> !m.isConcluida())
            .toList();
    }
    
    public List<Meta> buscarAtrasadas() {
        LocalDate hoje = LocalDate.now();
        return listarTodos().stream()
            .filter(m -> !m.isConcluida() && m.getDataLimite().isBefore(hoje))
            .toList();
    }
}
