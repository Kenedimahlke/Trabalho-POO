import java.time.LocalDate;

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
}
