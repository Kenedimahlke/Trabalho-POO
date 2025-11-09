// Tipos de transações financeiras do sistema
public enum TipoTransacao {
    RECEITA("Receita"),
    DESPESA("Despesa"),
    TRANSFERENCIA("Transferência");
    
    private final String nome;
    
    TipoTransacao(String nome) {
        this.nome = nome;
    }
    
    public String getNome() {
        return nome;
    }
}
