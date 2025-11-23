import java.util.Map;
import java.util.List;

// Interface para estratégias de persistência (DIP + OCP)
public interface EstrategiaPersistencia {
    void salvar(Map<String, List<?>> dados, String destino) throws Exception;
    Map<String, List<?>> carregar(String origem) throws Exception;
    boolean existe(String origem);
    void deletar(String origem) throws Exception;
}
