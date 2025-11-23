package Factory;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Interfaces.*;
import java.time.*;
import java.time.LocalDate;
import java.util.*;

// Factory para criação de diferentes tipos de contas financeiras
public class ContaFactory {
    
    public static ContaFinanceira criarConta(String tipo, String numero, Usuario titular, Object... params) {
        return switch (tipo.toLowerCase()) {
            case "corrente" -> {
                double limiteChequeEspecial = params.length > 0 ? (double) params[0] : 500.0;
                yield new ContaCorrente(numero, titular, limiteChequeEspecial);
            }
            case "credito" -> {
                double limite = (double) params[0];
                LocalDate fechamento = (LocalDate) params[1];
                LocalDate vencimento = (LocalDate) params[2];
                yield new CartaoCredito(numero, titular, limite, fechamento, vencimento);
            }
            case "digital" -> {
                double rendimento = params.length > 0 ? (double) params[0] : 0.5;
                yield new ContaDigital(numero, titular, rendimento);
            }
            case "investimento" -> {
                String tipoInv = params.length > 0 ? (String) params[0] : "Conservador";
                yield new CarteiraInvestimento(numero, titular, tipoInv);
            }
            case "cofrinho" -> {
                String objetivo = params.length > 0 ? (String) params[0] : "Economia";
                double meta = params.length > 1 ? (double) params[1] : 1000.0;
                yield new Cofrinho(numero, titular, objetivo, meta);
            }
            default -> throw new IllegalArgumentException("Tipo de conta inválido: " + tipo);
        };
    }
    
    // Método auxiliar para criar conta corrente básica
    public static ContaCorrente criarContaCorrente(String numero, Usuario titular) {
        return new ContaCorrente(numero, titular, 500.0);
    }
    
    // Método auxiliar para criar conta digital básica
    public static ContaDigital criarContaDigital(String numero, Usuario titular) {
        return new ContaDigital(numero, titular, 0.5);
    }
    
    // Método auxiliar para criar carteira de investimento
    public static CarteiraInvestimento criarCarteiraInvestimento(String numero, Usuario titular, String perfil) {
        return new CarteiraInvestimento(numero, titular, perfil);
    }
    
    // Método auxiliar para criar cofrinho
    public static Cofrinho criarCofrinho(String numero, Usuario titular, String objetivo, double meta) {
        return new Cofrinho(numero, titular, objetivo, meta);
    }
    
    // Método auxiliar para criar cofrinho com prazo
    public static Cofrinho criarCofrinhoComPrazo(String numero, Usuario titular, String objetivo, 
                                                  double meta, LocalDate prazo) {
        return new Cofrinho(numero, titular, objetivo, meta, prazo);
    }
    
    // Método auxiliar para criar conta corrente com limite customizado
    public static ContaCorrente criarContaCorrente(String numero, Usuario titular, double limiteChequeEspecial) {
        return new ContaCorrente(numero, titular, limiteChequeEspecial);
    }
    
    // Método auxiliar para criar conta digital com rendimento customizado
    public static ContaDigital criarContaDigital(String numero, Usuario titular, double rendimento) {
        return new ContaDigital(numero, titular, rendimento);
    }
    
    // Método auxiliar para criar cartão de crédito
    public static CartaoCredito criarCartaoCredito(String numero, Usuario titular, double limite) {
        LocalDate hoje = LocalDate.now();
        LocalDate fechamento = hoje.withDayOfMonth(15);
        LocalDate vencimento = hoje.withDayOfMonth(10).plusMonths(1);
        return new CartaoCredito(numero, titular, limite, fechamento, vencimento);
    }
}