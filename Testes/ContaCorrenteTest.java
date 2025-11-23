import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ContaCorrenteTest {
    
    private Usuario usuario;
    private ContaCorrente conta;
    
    @BeforeEach
    public void setUp() {
        usuario = new UsuarioIndividual("Maria Santos", "987.654.321-00", "maria@email.com");
        conta = new ContaCorrente("54321", usuario, 500.0);
        conta.depositar(1000.0);
    }
    
    @Test
    @DisplayName("Deve criar conta corrente com saldo inicial")
    public void testCriarContaCorrente() {
        assertEquals("54321", conta.getNumeroConta());
        assertEquals(1000.0, conta.consultarSaldo(), 0.01);
        assertEquals(500.0, conta.getLimiteChequeEspecial(), 0.01);
        assertTrue(conta.isAtiva());
    }
    
    @Test
    @DisplayName("Deve depositar valor corretamente")
    public void testDepositar() {
        conta.depositar(500.0);
        
        assertEquals(1500.0, conta.consultarSaldo(), 0.01);
    }
    
    @Test
    @DisplayName("Deve sacar valor dentro do saldo")
    public void testSacarDentroSaldo() throws Exception {
        boolean sucesso = conta.sacar(300.0);
        
        assertTrue(sucesso);
        assertEquals(700.0, conta.consultarSaldo(), 0.01);
    }
    
    @Test
    @DisplayName("Deve permitir saque com cheque especial")
    public void testSacarComChequeEspecial() throws Exception {
        boolean sucesso = conta.sacar(1200.0); // 1000 saldo + 200 cheque especial
        
        assertTrue(sucesso);
        assertEquals(-200.0, conta.consultarSaldo(), 0.01);
    }
    
    @Test
    @DisplayName("Deve bloquear saque acima do limite com cheque especial")
    public void testSacarAcimaLimiteChequeEspecial() {
        assertThrows(Exception.class, () -> {
            conta.sacar(1600.0); // Excede saldo + limite
        });
    }
    
    @Test
    @DisplayName("Não deve permitir depósito de valor negativo")
    public void testDepositarValorNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            conta.depositar(-100.0);
        });
    }
    
    @Test
    @DisplayName("Deve cobrar tarifa mensal corretamente")
    public void testCobrarTarifaMensal() throws Exception {
        conta.cobrarTarifaMensal();
        
        assertEquals(984.1, conta.consultarSaldo(), 0.01); // 1000 - 15.90
    }
    
    @Test
    @DisplayName("Deve desativar e reativar conta")
    public void testDesativarReativarConta() {
        conta.desativar();
        assertFalse(conta.isAtiva());
        
        conta.reativar();
        assertTrue(conta.isAtiva());
    }
    
    @Test
    @DisplayName("Não deve permitir saque em conta inativa")
    public void testSaqueContaInativa() {
        conta.desativar();
        
        assertThrows(Exception.class, () -> {
            conta.sacar(100.0);
        });
    }
    
    @Test
    @DisplayName("Deve retornar tipo de conta correto")
    public void testGetTipoConta() {
        assertEquals("CONTA_CORRENTE", conta.getTipoConta());
    }
    
    @Test
    @DisplayName("Deve retornar titular correto")
    public void testGetTitular() {
        assertEquals(usuario, conta.getTitular());
        assertEquals("Maria Santos", conta.getTitular().getNome());
    }
}
