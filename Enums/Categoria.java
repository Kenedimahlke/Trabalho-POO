// Categorias de despesas e receitas
public enum Categoria {
    // Despesas
    ALIMENTACAO("Alimentação", "DESPESA"),
    TRANSPORTE("Transporte", "DESPESA"),
    MORADIA("Moradia", "DESPESA"),
    SAUDE("Saúde", "DESPESA"),
    LAZER("Lazer", "DESPESA"),
    
    // Receitas
    SALARIO("Salário", "RECEITA"),
    FREELANCE("Freelance", "RECEITA"),
    OUTROS("Outros", "RECEITA");
    
    private final String nome;
    private final String tipo;
    
    Categoria(String nome, String tipo) {
        this.nome = nome;
        this.tipo = tipo;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public boolean isDespesa() {
        return tipo.equals("DESPESA");
    }
    
    public boolean isReceita() {
        return tipo.equals("RECEITA");
    }
}
