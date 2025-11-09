// Interface base para todas as contas financeiras do sistema
public interface ContaFinanceira {
    
    // Deposita um valor na conta
    void depositar(double valor);
    
    // Saca um valor da conta
    boolean sacar(double valor) throws Exception;
    
    // Consulta o saldo disponível na conta
    double consultarSaldo();
    
    // Retorna o número identificador da conta
    String getNumeroConta();
    
    // Retorna o tipo da conta
    String getTipoConta();
    
    // Retorna o titular da conta
    Usuario getTitular();
    
    // Verifica se a conta está ativa
    boolean isAtiva();
    
    // Ativa ou desativa a conta
    void setAtiva(boolean ativa);
    
    // Retorna um extrato simplificado da conta
    String getExtrato();
}
