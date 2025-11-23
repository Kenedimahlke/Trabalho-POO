package Testes;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Factory.*;
import Gerenciadores.*;
import Interfaces.*;
import Observers.*;
import Relatorios.*;
import Repositorios.*;
import Strategy.*;
import java.time.*;
import java.time.LocalDate;
import java.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartaoCreditoTest {

    private CartaoCredito cartao;
    private Usuario usuario;
    private double limite = 1000.0;

    @BeforeEach
    public void setUp() {
        usuario = new UsuarioIndividual("João", "joao@email.com", "123456");
        cartao = new CartaoCredito("1234-CC", usuario, limite, LocalDate.now().plusDays(10), LocalDate.now().plusDays(20));
    }

    @Test
    public void testCriacaoCartao() {
        assertEquals("1234-CC", cartao.getNumeroConta());
        assertEquals(usuario, cartao.getTitular());
        assertEquals(limite, cartao.consultarSaldo()); // Saldo disponível
        assertEquals(0.0, cartao.getFaturaAtual());
        assertEquals("CARTAO_CREDITO", cartao.getTipoConta());
        assertTrue(cartao.isAtiva());
    }

    @Test
    public void testCompraComSucesso() throws Exception {
        boolean sucesso = cartao.sacar(200.0);
        assertTrue(sucesso);
        assertEquals(800.0, cartao.consultarSaldo());
        assertEquals(200.0, cartao.getFaturaAtual());
    }

    @Test
    public void testCompraSemLimite() {
        Exception exception = assertThrows(Exception.class, () -> {
            cartao.sacar(1200.0);
        });
        assertTrue(exception.getMessage().contains("LimiteExcedidoException"));
    }

    @Test
    public void testCompraValorInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            cartao.sacar(-50.0);
        });
    }

    @Test
    public void testPagamentoFaturaParcial() throws Exception {
        cartao.sacar(500.0);
        cartao.depositar(200.0); // Paga 200
        
        assertEquals(700.0, cartao.consultarSaldo()); // 500 disp + 200 pago = 700
        assertEquals(300.0, cartao.getFaturaAtual()); // 500 - 200 = 300
    }

    @Test
    public void testPagamentoFaturaTotal() throws Exception {
        cartao.sacar(500.0);
        cartao.depositar(500.0);
        
        assertEquals(1000.0, cartao.consultarSaldo());
        assertEquals(0.0, cartao.getFaturaAtual());
    }

    @Test
    public void testPagamentoMaiorQueFatura() throws Exception {
        cartao.sacar(500.0);
        cartao.depositar(600.0); // Paga 600 (100 a mais)
        
        // O comportamento atual do código é: limiteDisponivel += faturaAtual; faturaAtual = 0.0;
        // Mas o código diz: limiteDisponivel += faturaAtual;
        // Espera, vamos ver o código:
        // if (valor <= faturaAtual) { ... } else { limiteDisponivel += faturaAtual; faturaAtual = 0.0; }
        // Se eu pago 600 e a fatura é 500:
        // limiteDisponivel (500) += 500 = 1000.
        // O valor excedente (100) é ignorado? O código parece ter um bug ou comportamento simplificado.
        // Vamos testar o comportamento atual.
        
        assertEquals(1000.0, cartao.consultarSaldo());
        assertEquals(0.0, cartao.getFaturaAtual());
    }

    @Test
    public void testCartaoInativo() {
        cartao.setAtiva(false);
        assertThrows(IllegalStateException.class, () -> cartao.sacar(100.0));
        assertThrows(IllegalStateException.class, () -> cartao.depositar(100.0));
    }
    
    @Test
    public void testFecharFatura() {
        // Apenas imprime no console, mas podemos chamar para garantir que não quebra
        cartao.fecharFatura();
    }
}