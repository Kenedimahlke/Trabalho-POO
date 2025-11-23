import java.util.ArrayList;
import java.util.List;

// Repositório genérico em memória (SRP + DIP)
public class RepositorioMemoria<T> implements RepositorioDados<T> {
    private List<T> dados;
    
    public RepositorioMemoria() {
        this.dados = new ArrayList<>();
    }
    
    @Override
    public void adicionar(T item) {
        if (item != null && !dados.contains(item)) {
            dados.add(item);
        }
    }
    
    @Override
    public void remover(T item) {
        dados.remove(item);
    }
    
    @Override
    public T buscarPorId(String id) {
        // Implementação básica - pode ser sobrescrita
        return null;
    }
    
    @Override
    public List<T> listarTodos() {
        return new ArrayList<>(dados);
    }
    
    @Override
    public void limpar() {
        dados.clear();
    }
}
