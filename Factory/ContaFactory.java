package Factory;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Interfaces.*;
import java.time.*;
import java.time.LocalDate;
import java.util.*;

public class ContaFactory {
    
    public static ContaFinanceira criarConta(String tipo, String numero, Usuario titular, Object... params) {
        return switch (tipo.toLowerCase()) {
            case "corrente" -> {
                double limiteChequeEspecial = (double) params[0];
                yield new ContaCorrente(numero, titular, limiteChequeEspecial);
            }
            case "credito" -> {
                double limite = (double) params[0];
                LocalDate fechamento = (LocalDate) params[1];
                LocalDate vencimento = (LocalDate) params[2];
                yield new CartaoCredito(numero, titular, limite, fechamento, vencimento);
            }
            case "digital" -> {
                double rendimento = (double) params[0];
                yield new ContaDigital(numero, titular, rendimento);
            }
            case "investimento" -> {
                String tipoInv = (String) params[0];
                yield new CarteiraInvestimento(numero, titular, tipoInv);
            }
            case "cofrinho" -> {
                String objetivo = (String) params[0];
                double meta = (double) params[1];
                yield new Cofrinho(numero, titular, objetivo, meta);
            }
            default -> throw new IllegalArgumentException("Tipo de conta inválido: " + tipo);
        };
    }
}