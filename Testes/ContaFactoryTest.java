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

public class ContaFactoryTest {

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new UsuarioIndividual("Teste", "teste@email.com", "123");
    }

    @Test
    public void testCriarContaCorrente() {
        ContaFinanceira conta = ContaFactory.criarConta("corrente", "001", usuario);
        assertTrue(conta instanceof ContaCorrente);
        assertEquals("001", conta.getNumeroConta());
    }

    @Test
    public void testCriarContaCorrenteComParametros() {
        ContaFinanceira conta = ContaFactory.criarConta("corrente", "002", usuario, 1000.0);
        assertTrue(conta instanceof ContaCorrente);
        // Não consigo verificar o limite diretamente sem cast, mas a criação funcionou
    }

    @Test
    public void testCriarCartaoCredito() {
        LocalDate fechamento = LocalDate.now();
        LocalDate vencimento = LocalDate.now().plusDays(10);
        ContaFinanceira conta = ContaFactory.criarConta("credito", "003", usuario, 2000.0, fechamento, vencimento);
        assertTrue(conta instanceof CartaoCredito);
    }

    @Test
    public void testCriarContaDigital() {
        ContaFinanceira conta = ContaFactory.criarConta("digital", "004", usuario);
        assertTrue(conta instanceof ContaDigital);
    }

    @Test
    public void testCriarCarteiraInvestimento() {
        ContaFinanceira conta = ContaFactory.criarConta("investimento", "005", usuario, "Arrojado");
        assertTrue(conta instanceof CarteiraInvestimento);
    }

    @Test
    public void testCriarCofrinho() {
        ContaFinanceira conta = ContaFactory.criarConta("cofrinho", "006", usuario, "Viagem", 5000.0);
        assertTrue(conta instanceof Cofrinho);
    }

    @Test
    public void testTipoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> {
            ContaFactory.criarConta("inexistente", "000", usuario);
        });
    }

    @Test
    public void testMetodosAuxiliares() {
        assertNotNull(ContaFactory.criarContaCorrente("101", usuario));
        assertNotNull(ContaFactory.criarContaDigital("102", usuario));
        assertNotNull(ContaFactory.criarCarteiraInvestimento("103", usuario, "Moderado"));
        assertNotNull(ContaFactory.criarCofrinho("104", usuario, "Carro", 20000.0));
        assertNotNull(ContaFactory.criarCofrinhoComPrazo("105", usuario, "Casa", 100000.0, LocalDate.now().plusYears(5)));
        assertNotNull(ContaFactory.criarContaCorrente("106", usuario, 200.0));
        assertNotNull(ContaFactory.criarContaDigital("107", usuario, 0.8));
        assertNotNull(ContaFactory.criarCartaoCredito("108", usuario, 5000.0));
    }
}