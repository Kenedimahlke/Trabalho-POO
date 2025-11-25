package Strategy;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import java.time.*;
import java.util.*;
import java.util.List;

// Estrategia para projetar o saldo futuro baseado no historico
public class ProjecaoSaldo {
    private int meses;
    
    public ProjecaoSaldo(int meses) {
        this.meses = meses;
    }
    
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
    
    public String getDescricao() {
        return "Projecao de saldo para " + meses + " meses baseada no historico";
    }
    
    public int getMeses() {
        return meses;
    }
    
    public void setMeses(int meses) {
        this.meses = meses;
    }
}