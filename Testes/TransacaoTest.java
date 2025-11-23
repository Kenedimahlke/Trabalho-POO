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
import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TransacaoTest {
    
    private ContaCorrente conta;
    private Usuario usuario;
    
    @BeforeEach
    public void setUp() {
        usuario = new UsuarioIndividual("João Silva", "123.456.789-00", "joao@email.com");
        conta = new ContaCorrente("12345", usuario, 500.0);
        conta.depositar(1000.0);
    }
    
    @Test
    @DisplayName("Deve criar transação de receita corretamente")
    public void testCriarTransacaoReceita() {
        Transacao transacao = new Transacao(
            TipoTransacao.RECEITA,
            Categoria.SALARIO,
            500.0,
            "Salário",
            usuario,
            conta
        );
        
        assertEquals(TipoTransacao.RECEITA, transacao.getTipo());
        assertEquals(500.0, transacao.getValor());
        assertEquals("Salário", transacao.getDescricao());
        assertFalse(transacao.isEstornada());
    }
    
    @Test
    @DisplayName("Deve criar transação de despesa com categoria")
    public void testCriarTransacaoDespesa() {
        Transacao transacao = new Transacao(
            TipoTransacao.DESPESA,
            Categoria.ALIMENTACAO,
            200.0,
            "Supermercado",
            usuario,
            conta
        );
        
        assertEquals(TipoTransacao.DESPESA, transacao.getTipo());
        assertEquals(Categoria.ALIMENTACAO, transacao.getCategoria());
        assertEquals(200.0, transacao.getValor());
    }
    
    @Test
    @DisplayName("Deve adicionar anexo à transação")
    public void testAdicionarAnexo() {
        Transacao transacao = new Transacao(
            TipoTransacao.DESPESA,
            Categoria.OUTROS,
            100.0,
            "Compra",
            usuario,
            conta
        );
        
        transacao.adicionarAnexo("/path/to/nota_fiscal.pdf");
        
        assertEquals(1, transacao.getAnexos().size());
        assertTrue(transacao.getAnexos().contains("/path/to/nota_fiscal.pdf"));
    }
    
    @Test
    @DisplayName("Deve remover anexo da transação")
    public void testRemoverAnexo() {
        Transacao transacao = new Transacao(
            TipoTransacao.DESPESA,
            Categoria.OUTROS,
            100.0,
            "Compra",
            usuario,
            conta
        );
        
        String anexo = "/path/to/nota.pdf";
        transacao.adicionarAnexo(anexo);
        transacao.removerAnexo(anexo);
        
        assertEquals(0, transacao.getAnexos().size());
    }
    
    @Test
    @DisplayName("Deve estornar transação de receita corretamente")
    public void testEstornarReceita() throws Exception {
        double saldoInicial = conta.consultarSaldo();
        
        Transacao transacao = new Transacao(
            TipoTransacao.RECEITA,
            Categoria.SALARIO,
            500.0,
            "Salário",
            usuario,
            conta
        );
        
        transacao.estornar();
        
        assertTrue(transacao.isEstornada());
        assertEquals(saldoInicial - 500.0, conta.consultarSaldo(), 0.01);
    }
    
    @Test
    @DisplayName("Deve estornar transação de despesa corretamente")
    public void testEstornarDespesa() throws Exception {
        conta.sacar(200.0); // Remove 200 do saldo
        double saldoAposSaque = conta.consultarSaldo();
        
        Transacao transacao = new Transacao(
            TipoTransacao.DESPESA,
            Categoria.OUTROS,
            200.0,
            "Compra",
            usuario,
            conta
        );
        
        transacao.estornar();
        
        assertTrue(transacao.isEstornada());
        assertEquals(saldoAposSaque + 200.0, conta.consultarSaldo(), 0.01);
    }
    
    @Test
    @DisplayName("Não deve permitir estornar transação já estornada")
    public void testNaoPermitirEstornarDuasVezes() throws Exception {
        Transacao transacao = new Transacao(
            TipoTransacao.RECEITA,
            Categoria.SALARIO,
            500.0,
            "Salário",
            usuario,
            conta
        );
        
        transacao.estornar();
        
        assertThrows(IllegalStateException.class, () -> {
            transacao.estornar();
        });
    }
    
    @Test
    @DisplayName("Deve definir e obter subcategoria")
    public void testSubcategoria() {
        Transacao transacao = new Transacao(
            TipoTransacao.DESPESA,
            Categoria.ALIMENTACAO,
            50.0,
            "Restaurante",
            usuario,
            conta
        );
        
        transacao.setSubcategoria("Restaurante");
        
        assertEquals("Restaurante", transacao.getSubcategoria());
    }
}