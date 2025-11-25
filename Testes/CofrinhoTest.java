package Testes;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Factory.*;
import Gerenciadores.*;


import Relatorios.*;

import Strategy.*;
import java.time.*;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CofrinhoTest {
    
    private Usuario usuario;
    private Cofrinho cofrinho;
    
    @BeforeEach
    public void setUp() {
        usuario = new UsuarioIndividual("Ana Costa", "555.666.777-88", "ana@email.com");
        cofrinho = new Cofrinho(
            "COF001",
            usuario,
            "Comprar carro",
            50000.0,
            java.time.LocalDate.now().plusDays(365)
        );
    }
    
    @Test
    @DisplayName("Deve criar cofrinho com valores corretos")
    public void testCriarCofrinho() {
        assertEquals("COF001", cofrinho.getNumeroConta());
        assertEquals("Comprar carro", cofrinho.getObjetivo());
        assertEquals(50000.0, cofrinho.getMetaValor(), 0.01);
        assertEquals(0.0, cofrinho.consultarSaldo(), 0.01);
    }
    
    @Test
    @DisplayName("Deve depositar no cofrinho")
    public void testDepositar() {
        cofrinho.depositar(5000.0);
        
        assertEquals(5000.0, cofrinho.consultarSaldo(), 0.01);
    }
    
    @Test
    @DisplayName("Deve calcular progresso da meta corretamente")
    public void testCalcularProgressoMeta() {
        cofrinho.depositar(25000.0);
        
        assertEquals(50.0, cofrinho.calcularProgressoMeta(), 0.01);
    }
    
    @Test
    @DisplayName("Deve sacar do cofrinho quando há saldo")
    public void testSacar() throws Exception {
        cofrinho.depositar(10000.0);
        boolean sucesso = cofrinho.sacar(3000.0);
        
        assertTrue(sucesso);
        assertEquals(7000.0, cofrinho.consultarSaldo(), 0.01);
    }
    
    @Test
    @DisplayName("Não deve permitir saque maior que saldo")
    public void testSacarSaldoInsuficiente() throws Exception {
        cofrinho.depositar(1000.0);
        
        assertThrows(Exception.class, () -> {
            cofrinho.sacar(2000.0);
        });
    }
    
    @Test
    @DisplayName("Deve calcular dias restantes")
    public void testCalcularDiasRestantes() {
        long diasRestantes = cofrinho.calcularDiasRestantes();
        
        assertTrue(diasRestantes > 0);
        assertTrue(diasRestantes <= 366);
    }
    
    @Test
    @DisplayName("Deve verificar se meta foi atingida")
    public void testMetaAtingida() {
        cofrinho.depositar(50000.0);
        
        assertTrue(cofrinho.metaAtingida());
        assertEquals(100.0, cofrinho.calcularProgressoMeta(), 0.01);
    }
    
    @Test
    @DisplayName("Deve calcular quanto falta para meta")
    public void testCalcularFaltaParaMeta() {
        cofrinho.depositar(20000.0);
        
        assertEquals(30000.0, cofrinho.calcularFaltaParaMeta(), 0.01);
    }
    
    @Test
    @DisplayName("Deve retornar tipo de conta correto")
    public void testGetTipoConta() {
        assertEquals("Cofrinho", cofrinho.getTipoConta());
    }
    
    @Test
    @DisplayName("Deve permitir múltiplos depósitos")
    public void testMultiplosDepositos() {
        cofrinho.depositar(10000.0);
        cofrinho.depositar(5000.0);
        cofrinho.depositar(3000.0);
        
        assertEquals(18000.0, cofrinho.consultarSaldo(), 0.01);
        assertEquals(36.0, cofrinho.calcularProgressoMeta(), 0.01);
    }
    
    @Test
    @DisplayName("Não deve permitir depósito negativo")
    public void testDepositarValorNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            cofrinho.depositar(-500.0);
        });
    }
    
    @Test
    @DisplayName("Deve gerar relatório do cofrinho")
    public void testGerarRelatorio() {
        cofrinho.depositar(15000.0);
        
        String relatorio = cofrinho.gerarRelatorio();
        
        assertNotNull(relatorio);
        assertTrue(relatorio.contains("Comprar carro"));
        assertTrue(relatorio.contains("30"));
    }
}
