// Interface para observadores do sistema
public interface Observer {
    void atualizar(String evento, Object dados);
    String getNome();
}
