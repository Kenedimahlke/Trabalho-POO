import java.util.List;

// Interface para repositórios de dados (DIP)
public interface RepositorioDados<T> {
    void adicionar(T item);
    void remover(T item);
    T buscarPorId(String id);
    List<T> listarTodos();
    void limpar();
}
