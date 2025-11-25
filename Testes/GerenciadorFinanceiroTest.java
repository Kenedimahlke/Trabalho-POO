package Testes;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Factory.*;
import Gerenciadores.*;
import java.time.*;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class GerenciadorFinanceiroTest {
    
    private GerenciadorFinanceiro gerenciador;
    private Usuario usuario;
    
    @BeforeEach
    public void setUp() {
        gerenciador = GerenciadorFinanceiro.getInstancia();
        // Limpa os dados antes de cada teste
        gerenciador.limparDados();
        
        usuario = new UsuarioIndividual("Teste User", "000.000.000-00", "teste@email.com");
    }
    
    @Test
    @DisplayName("Deve retornar sempre a mesma instância (Singleton)")
    public void testSingleton() {
        GerenciadorFinanceiro instancia1 = GerenciadorFinanceiro.getInstancia();
        GerenciadorFinanceiro instancia2 = GerenciadorFinanceiro.getInstancia();
        
        assertSame(instancia1, instancia2);
    }
    
    @Test
    @DisplayName("Deve adicionar usuário corretamente")
    public void testAdicionarUsuario() {
        gerenciador.adicionarUsuario(usuario);
        
        assertTrue(gerenciador.getUsuarios().contains(usuario));
        assertEquals(1, gerenciador.getUsuarios().size());
    }
    
    @Test
    @DisplayName("Deve buscar usuário por ID")
    public void testBuscarUsuarioPorId() {
        gerenciador.adicionarUsuario(usuario);
        
        Usuario encontrado = gerenciador.buscarUsuarioPorId(usuario.getId());
        
        assertNotNull(encontrado);
        assertEquals(usuario.getId(), encontrado.getId());
    }
    
    @Test
    @DisplayName("Deve adicionar conta corretamente")
    public void testAdicionarConta() {
        ContaCorrente conta = new ContaCorrente("12345", usuario, 500.0);
        conta.depositar(1000.0);
        gerenciador.adicionarConta(conta);
        
        assertTrue(gerenciador.getContas().contains(conta));
        assertEquals(1, gerenciador.getContas().size());
    }
    
    @Test
    @DisplayName("Deve buscar conta por número")
    public void testBuscarContaPorNumero() {
        ContaCorrente conta = new ContaCorrente("12345", usuario, 500.0);
        conta.depositar(1000.0);
        gerenciador.adicionarConta(conta);
        
        ContaFinanceira encontrada = gerenciador.buscarContaPorNumero("12345");
        
        assertNotNull(encontrada);
        assertEquals("12345", encontrada.getNumeroConta());
    }
    
    @Test
    @DisplayName("Deve adicionar transação corretamente")
    public void testAdicionarTransacao() {
        ContaCorrente conta = new ContaCorrente("12345", usuario, 500.0);
        conta.depositar(1000.0);
        Transacao transacao = new Transacao(
            TipoTransacao.RECEITA,
            Categoria.SALARIO,
            500.0,
            "Salário",
            usuario,
            conta
        );
        
        gerenciador.adicionarTransacao(transacao);
        
        assertTrue(gerenciador.getTransacoes().contains(transacao));
        assertEquals(1, gerenciador.getTransacoes().size());
    }
    
    @Test
    @DisplayName("Deve adicionar meta corretamente")
    public void testAdicionarMeta() {
        Meta meta = new Meta(
            "Economizar",
            Categoria.OUTROS,
            10000.0,
            java.time.LocalDate.now().plusMonths(6),
            usuario
        );
        
        gerenciador.adicionarMeta(meta);
        
        assertTrue(gerenciador.getMetas().contains(meta));
        assertEquals(1, gerenciador.getMetas().size());
    }
    
    @Test
    @DisplayName("Deve adicionar orçamento corretamente")
    public void testAdicionarOrcamento() {
        Orcamento orcamento = new Orcamento(
            "Orçamento Teste",
            Categoria.ALIMENTACAO,
            1000.0,
            java.time.YearMonth.now(),
            usuario
        );
        
        gerenciador.adicionarOrcamento(orcamento);
        
        assertTrue(gerenciador.getOrcamentos().contains(orcamento));
        assertEquals(1, gerenciador.getOrcamentos().size());
    }
    
    @Test
    @DisplayName("Deve calcular saldo total de todas as contas")
    public void testCalcularSaldoTotal() {
        ContaCorrente conta1 = new ContaCorrente("001", usuario, 500.0);
        conta1.depositar(1000.0);
        ContaDigital conta2 = new ContaDigital("002", usuario, 0.5);
        conta2.depositar(2000.0);
        
        gerenciador.adicionarConta(conta1);
        gerenciador.adicionarConta(conta2);
        
        double saldoTotal = gerenciador.calcularSaldoTotal();
        
        assertEquals(3000.0, saldoTotal, 0.01);
    }
}
