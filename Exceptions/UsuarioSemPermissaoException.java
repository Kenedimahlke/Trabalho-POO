// Exceção lançada quando um usuário tenta realizar uma ação sem permissão
public class UsuarioSemPermissaoException extends Exception {
    public UsuarioSemPermissaoException(String mensagem) {
        super(mensagem);
    }
    
    public UsuarioSemPermissaoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
