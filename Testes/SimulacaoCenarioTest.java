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
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SimulacaoCenarioTest {
    
    private SimulacaoCenario simulador;
    private List<Transacao> transacoes;
    private ContaCorrente conta;
    private Usuario usuario;
    
    @BeforeEach
    public void setUp() {
        simulador = new SimulacaoCenario();
        usuario = new UsuarioIndividual("Teste", "000.000.000-00", "teste@email.com");
        conta = new ContaCorrente("12345", usuario, 1000.0);
        conta.depositar(5000.0);
        
        transacoes = new ArrayList<>();
        
        // Adiciona algumas transações de teste
        transacoes.add(new Transacao(
            TipoTransacao.DESPESA, Categoria.ALIMENTACAO, 500.0, "Supermercado",
            usuario, conta
        ));
        
        transacoes.add(new Transacao(
            TipoTransacao.DESPESA, Categoria.ALIMENTACAO, 300.0, "Restaurante",
            usuario, conta
        ));
        
        transacoes.add(new Transacao(
            TipoTransacao.DESPESA, Categoria.LAZER, 200.0, "Cinema",
            usuario, conta
        ));
        
        transacoes.add(new Transacao(
            TipoTransacao.RECEITA, Categoria.SALARIO, 3000.0, "Salário",
            usuario, conta
        ));
    }
    
    @Test
    @DisplayName("Deve simular aumento de gastos em categoria específica")
    public void testSimularAumentoGastos() {
        var resultado = simulador.simularMudancaGastos(transacoes, conta.consultarSaldo(), Categoria.ALIMENTACAO, 20.0, 1);
        
        assertNotNull(resultado);
        assertEquals(80.0, resultado.getImpactoMensal(), 0.01);
        assertTrue(resultado.toString().contains(Categoria.ALIMENTACAO.getNome()));
    }
    
    @Test
    @DisplayName("Deve simular redução de gastos em categoria específica")
    public void testSimularReducaoGastos() {
        var resultado = simulador.simularMudancaGastos(transacoes, conta.consultarSaldo(), Categoria.ALIMENTACAO, -10.0, 1);
        
        assertNotNull(resultado);
        assertTrue(resultado.getImpactoMensal() < 0);
    }
    
    @Test
    @DisplayName("Deve simular mudança global positiva")
    public void testSimularMudancaGlobalPositiva() {
        var resultado = simulador.simularMudancaGlobal(transacoes, conta.consultarSaldo(), 15.0, 1);
        
        assertNotNull(resultado);
        assertTrue(resultado.getImpactoMensal() > 0);
    }
    
    @Test
    @DisplayName("Deve simular mudança global negativa")
    public void testSimularMudancaGlobalNegativa() {
        var resultado = simulador.simularMudancaGlobal(transacoes, conta.consultarSaldo(), -20.0, 1);
        
        assertNotNull(resultado);
        assertTrue(resultado.getImpactoMensal() < 0);
    }
    
    @Test
    @DisplayName("Deve simular nova despesa recorrente")
    public void testSimularNovaDespesa() {
        var resultado = simulador.simularNovaDespesa(
            transacoes,
            conta.consultarSaldo(),
            50.0,
            1
        );
        
        assertNotNull(resultado);
        assertEquals(50.0, resultado.getImpactoMensal(), 0.01);
    }
    
    @Test
    @DisplayName("Deve comparar dois cenários")
    public void testCompararCenarios() {
        var cenario1 = simulador.simularMudancaGastos(transacoes, conta.consultarSaldo(), Categoria.ALIMENTACAO, 10.0, 1);
        var cenario2 = simulador.simularMudancaGastos(transacoes, conta.consultarSaldo(), Categoria.ALIMENTACAO, -10.0, 1);
        
        String comparacao = simulador.compararCenarios(cenario1, cenario2);
        
        assertNotNull(comparacao);
        assertTrue(comparacao.contains("Cenário 1"));
        assertTrue(comparacao.contains("Cenário 2"));
    }
    
    @Test
    @DisplayName("Deve calcular impacto anual corretamente")
    public void testImpactoAnual() {
        var resultado = simulador.simularNovaDespesa(
            transacoes,
            conta.consultarSaldo(),
            100.0,
            12
        );
        
        assertEquals(1200.0, resultado.getImpactoAnual(), 0.01); // 100 * 12
    }
    
    @Test
    @DisplayName("Deve lidar com categoria sem transações")
    public void testCategoriaSemTransacoes() {
        var resultado = simulador.simularMudancaGastos(transacoes, conta.consultarSaldo(), Categoria.SAUDE, 50.0, 1);
        
        assertNotNull(resultado);
        assertEquals(0.0, resultado.getImpactoMensal(), 0.01);
    }
    
    @Test
    @DisplayName("Deve lidar com lista vazia de transações")
    public void testListaVaziaTransacoes() {
        List<Transacao> listaVazia = new ArrayList<>();
        
        var resultado = simulador.simularMudancaGlobal(listaVazia, conta.consultarSaldo(), 10.0, 1);
        
        assertNotNull(resultado);
        assertEquals(0.0, resultado.getImpactoMensal(), 0.01);
    }
}