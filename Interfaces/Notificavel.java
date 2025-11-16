// Interface para entidades que podem receber notificações
public interface Notificavel {
    void enviarNotificacao(String mensagem);
    void configurarNotificacoes(boolean ativo);
    boolean isNotificacoesAtivas();
}
