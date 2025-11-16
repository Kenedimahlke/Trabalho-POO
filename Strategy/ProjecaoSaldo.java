import java.util.List;

// Estratégia para projetar o saldo futuro baseado no histórico
public class ProjecaoSaldo implements EstrategiaCalculo {
    private int meses;
    
    public ProjecaoSaldo(int meses) {
        this.meses = meses;
    }
    
    @Override
    public double calcular(List<Transacao> transacoes) {
        if (transacoes.isEmpty()) {
            return 0.0;
        }
        
        double mediaReceitas = transacoes.stream()
            .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
            .mapToDouble(Transacao::getValor)
            .average()
            .orElse(0.0);
            
        double mediaDespesas = transacoes.stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .mapToDouble(Transacao::getValor)
            .average()
            .orElse(0.0);
            
        double saldoMedioMensal = mediaReceitas - mediaDespesas;
        return saldoMedioMensal * meses;
    }
    
    @Override
    public String getDescricao() {
        return "Projeção de saldo para " + meses + " meses baseada no histórico";
    }
    
    public int getMeses() {
        return meses;
    }
    
    public void setMeses(int meses) {
        this.meses = meses;
    }
}
