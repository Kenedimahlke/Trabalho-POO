// Exceção lançada quando o limite de crédito ou operação é excedido
public class LimiteExcedidoException extends Exception {
    
    private double limiteDisponivel;
    private double valorSolicitado;
    
    public LimiteExcedidoException() {
        super("Limite excedido para esta operação.");
    }
    
    public LimiteExcedidoException(String mensagem) {
        super(mensagem);
    }
    
    public LimiteExcedidoException(String mensagem, double limiteDisponivel, double valorSolicitado) {
        super(mensagem);
        this.limiteDisponivel = limiteDisponivel;
        this.valorSolicitado = valorSolicitado;
    }
    
    public double getLimiteDisponivel() {
        return limiteDisponivel;
    }
    
    public double getValorSolicitado() {
        return valorSolicitado;
    }
    
    public double getValorExcedido() {
        return valorSolicitado - limiteDisponivel;
    }
}
