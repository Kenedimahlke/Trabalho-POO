import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CarteiraInvestimentoTest {

    private CarteiraInvestimento carteira;
    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new UsuarioIndividual("Maria", "maria@email.com", "654321");
        carteira = new CarteiraInvestimento("INV-001", usuario, "Moderado");
    }

    @Test
    public void testCriacaoCarteira() {
        assertEquals("INV-001", carteira.getNumeroConta());
        assertEquals(usuario, carteira.getTitular());
        assertEquals(0.0, carteira.consultarSaldo());
        assertEquals("CARTEIRA_INVESTIMENTO", carteira.getTipoConta());
        assertTrue(carteira.isAtiva());
    }

    @Test
    public void testDeposito() {
        carteira.depositar(1000.0);
        assertEquals(1000.0, carteira.consultarSaldo());
    }

    @Test
    public void testSaqueComSucesso() throws Exception {
        carteira.depositar(1000.0);
        boolean sucesso = carteira.sacar(500.0);
        assertTrue(sucesso);
        assertEquals(500.0, carteira.consultarSaldo());
    }

    @Test
    public void testSaqueSemSaldo() {
        carteira.depositar(100.0);
        Exception exception = assertThrows(Exception.class, () -> {
            carteira.sacar(200.0);
        });
        assertTrue(exception.getMessage().contains("SaldoInsuficienteException"));
    }

    @Test
    public void testAplicarRentabilidadeModerada() {
        // Moderado = 1.0%
        carteira.depositar(1000.0);
        carteira.aplicarRentabilidade();
        assertEquals(1010.0, carteira.consultarSaldo(), 0.01);
    }

    @Test
    public void testAplicarRentabilidadeConservadora() {
        CarteiraInvestimento conservadora = new CarteiraInvestimento("INV-002", usuario, "Conservador");
        conservadora.depositar(1000.0);
        conservadora.aplicarRentabilidade(); // 0.5%
        assertEquals(1005.0, conservadora.consultarSaldo(), 0.01);
    }

    @Test
    public void testAplicarRentabilidadeArrojada() {
        CarteiraInvestimento arrojada = new CarteiraInvestimento("INV-003", usuario, "Arrojado");
        arrojada.depositar(1000.0);
        arrojada.aplicarRentabilidade(); // 2.0%
        assertEquals(1020.0, arrojada.consultarSaldo(), 0.01);
    }

    @Test
    public void testCarteiraInativa() {
        carteira.setAtiva(false);
        assertThrows(IllegalStateException.class, () -> carteira.depositar(100.0));
        assertThrows(IllegalStateException.class, () -> carteira.sacar(100.0));
    }
}
