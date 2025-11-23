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
import static org.junit.jupiter.api.Assertions.*;

public class MetaTest {
    
    private Usuario usuario;
    private Meta meta;
    
    @BeforeEach
    public void setUp() {
        usuario = new UsuarioIndividual("Carlos Lima", "111.222.333-44", "carlos@email.com");
        meta = new Meta(
            "Viagem para Europa",
            Categoria.LAZER,
            10000.0,
            LocalDate.now().plusMonths(6),
            usuario
        );
    }
    
    @Test
    @DisplayName("Deve criar meta com valores corretos")
    public void testCriarMeta() {
        assertEquals("Viagem para Europa", meta.getDescricao());
        assertEquals(10000.0, meta.getValorAlvo(), 0.01);
        assertEquals(0.0, meta.getValorAtual(), 0.01);
        assertFalse(meta.isConcluida());
    }
    
    @Test
    @DisplayName("Deve contribuir para meta corretamente")
    public void testContribuir() {
        meta.contribuir(2000.0);
        
        assertEquals(2000.0, meta.getValorAtual(), 0.01);
        assertEquals(20.0, meta.calcularProgresso(), 0.01);
    }
    
    @Test
    @DisplayName("Deve calcular progresso corretamente")
    public void testCalcularProgresso() {
        meta.contribuir(5000.0);
        
        assertEquals(50.0, meta.calcularProgresso(), 0.01);
    }
    
    @Test
    @DisplayName("Deve marcar meta como concluída ao atingir 100%")
    public void testMetaConcluida() {
        meta.contribuir(10000.0);
        
        assertTrue(meta.isConcluida());
        assertEquals(100.0, meta.calcularProgresso(), 0.01);
    }
    
    @Test
    @DisplayName("Deve permitir contribuição acima do valor alvo")
    public void testContribuirAcimaValorAlvo() {
        meta.contribuir(12000.0);
        
        assertEquals(12000.0, meta.getValorAtual(), 0.01);
        assertEquals(100.0, meta.calcularProgresso(), 0.01);
    }
    
    @Test
    @DisplayName("Não deve permitir contribuição negativa")
    public void testContribuirValorNegativo() {
        double valorAntes = meta.getValorAtual();
        
        meta.contribuir(-500.0);
        
        assertEquals(valorAntes, meta.getValorAtual(), 0.01);
    }
    
    @Test
    @DisplayName("Deve verificar status 'PENDENTE' corretamente")
    public void testStatusNoPrazo() {
        meta.contribuir(3000.0);
        
        String status = meta.verificarStatus();
        
        assertTrue(status.contains("PENDENTE"));
    }
    
    @Test
    @DisplayName("Deve verificar status 'ATRASADA' para meta vencida")
    public void testStatusAtrasada() {
        Meta metaVencida = new Meta(
            "Meta antiga",
            Categoria.LAZER,
            5000.0,
            LocalDate.now().minusDays(10),
            usuario
        );
        
        String status = metaVencida.verificarStatus();
        
        assertTrue(status.contains("ATRASADA"));
    }
    
    @Test
    @DisplayName("Deve verificar status 'CONCLUIDA' para meta atingida")
    public void testStatusConcluida() {
        meta.contribuir(10000.0);
        
        String status = meta.verificarStatus();
        
        assertTrue(status.contains("CONCLUIDA"));
    }
    
    @Test
    @DisplayName("Deve retornar dias restantes corretos")
    public void testDiasRestantes() {
        long dias = meta.getDataLimite().toEpochDay() - LocalDate.now().toEpochDay();
        
        assertTrue(dias > 0);
    }
    
    @Test
    @DisplayName("Deve permitir múltiplas contribuições")
    public void testMultiplasContribuicoes() {
        meta.contribuir(1000.0);
        meta.contribuir(2000.0);
        meta.contribuir(1500.0);
        
        assertEquals(4500.0, meta.getValorAtual(), 0.01);
        assertEquals(45.0, meta.calcularProgresso(), 0.01);
    }
}