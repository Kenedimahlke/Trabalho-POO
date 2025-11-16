// Exceção lançada quando uma categoria não é encontrada
public class CategoriaNaoEncontradaException extends Exception {
    public CategoriaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    
    public CategoriaNaoEncontradaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
