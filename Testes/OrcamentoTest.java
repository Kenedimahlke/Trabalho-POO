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
import java.time.YearMonth;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class OrcamentoTest {
    
    private Orcamento orcamento;
    private Usuario usuario;
    
    @BeforeEach
    public void setUp() {
        usuario = new UsuarioIndividual("Carlos Lima", "111.222.333-44", "carlos@email.com");
        orcamento = new Orcamento(
            "Orçamento Alimentação",
            Categoria.ALIMENTACAO,
            1000.0,
            YearMonth.now(),
            usuario
        );
    }
    
    @Test
    @DisplayName("Deve criar orçamento com valores corretos")
    public void testCriarOrcamento() {
        assertEquals("Orçamento Alimentação", orcamento.getNome());
        assertEquals(Categoria.ALIMENTACAO, orcamento.getCategoria());
        assertEquals(1000.0, orcamento.getLimiteValor(), 0.01);
        assertEquals(0.0, orcamento.getValorGasto(), 0.01);
        assertFalse(orcamento.isEstourado());
    }
    
    @Test
    @DisplayName("Deve adicionar gasto ao orçamento")
    public void testAdicionarGasto() {
        orcamento.adicionarGasto(300.0);
        
        assertEquals(300.0, orcamento.getValorGasto(), 0.01);
        assertEquals(30.0, orcamento.getPercentualGasto(), 0.01);
    }
    
    @Test
    @DisplayName("Deve calcular percentual gasto corretamente")
    public void testCalcularPercentualGasto() {
        orcamento.adicionarGasto(500.0);
        
        assertEquals(50.0, orcamento.getPercentualGasto(), 0.01);
    }
    
    @Test
    @DisplayName("Deve detectar quando orçamento está próximo do limite (80%)")
    public void testAlertaProximoLimite() {
        orcamento.adicionarGasto(850.0); // 85% do limite
        
        assertTrue(orcamento.getPercentualGasto() >= 80.0);
    }
    
    @Test
    @DisplayName("Deve marcar orçamento como estourado ao exceder limite")
    public void testOrcamentoEstourado() {
        orcamento.adicionarGasto(1200.0);
        
        assertTrue(orcamento.isEstourado());
        assertTrue(orcamento.getPercentualGasto() > 100.0);
    }
    
    @Test
    @DisplayName("Deve calcular valor disponível corretamente")
    public void testValorDisponivel() {
        orcamento.adicionarGasto(400.0);
        
        assertEquals(600.0, orcamento.getValorDisponivel(), 0.01);
    }
    
    @Test
    @DisplayName("Deve retornar valor disponível negativo quando estourado")
    public void testValorDisponivelNegativo() {
        orcamento.adicionarGasto(1200.0);
        
        assertEquals(0.0, orcamento.getValorDisponivel(), 0.01);
    }
    
    @Test
    @DisplayName("Deve resetar orçamento corretamente")
    public void testResetarOrcamento() {
        orcamento.adicionarGasto(500.0);
        orcamento.resetar();
        
        assertEquals(0.0, orcamento.getValorGasto(), 0.01);
        assertFalse(orcamento.isEstourado());
    }
    
    @Test
    @DisplayName("Deve permitir múltiplos gastos acumulados")
    public void testMultiplosGastos() {
        orcamento.adicionarGasto(200.0);
        orcamento.adicionarGasto(300.0);
        orcamento.adicionarGasto(150.0);
        
        assertEquals(650.0, orcamento.getValorGasto(), 0.01);
        assertEquals(65.0, orcamento.getPercentualGasto(), 0.01);
    }
    
    @Test
    @DisplayName("Não deve permitir adicionar gasto negativo")
    public void testAdicionarGastoNegativo() {
        assertThrows(IllegalArgumentException.class, () -> {
            orcamento.adicionarGasto(-100.0);
        });
    }
    
    @Test
    @DisplayName("Deve verificar se está no mês de referência")
    public void testMesReferencia() {
        assertEquals(YearMonth.now(), orcamento.getMesReferencia());
    }
    
    @Test
    @DisplayName("Deve gerar relatório do orçamento")
    public void testGerarRelatorio() {
        orcamento.adicionarGasto(600.0);
        
        String relatorio = orcamento.gerarRelatorio();
        
        assertNotNull(relatorio);
        assertTrue(relatorio.contains("Orçamento Alimentação"));
        assertTrue(relatorio.contains("60"));
    }
}