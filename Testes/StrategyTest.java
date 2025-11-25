package Testes;

import Entidades.*;
import Enums.*;
import Factory.*;
import Strategy.*;
import java.time.*;
import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class StrategyTest {

    private List<Transacao> transacoes;
    private Usuario usuario1;
    private Usuario usuario2;
    private ContaFinanceira conta;

    @BeforeEach
    public void setUp() {
        transacoes = new ArrayList<>();
        usuario1 = new UsuarioIndividual("User1", "111.111.111-11", "user1@email.com");
        usuario2 = new UsuarioIndividual("User2", "222.222.222-22", "user2@email.com");
        conta = ContaFactory.criarConta("CORRENTE", "123-4", usuario1, 10000.0);

        transacoes.add(criarTransacao("Salario", 5000.0, TipoTransacao.RECEITA, Categoria.SALARIO));
        transacoes.add(criarTransacao("Aluguel", 1500.0, TipoTransacao.DESPESA, Categoria.MORADIA));
        transacoes.add(criarTransacao("Supermercado", 800.0, TipoTransacao.DESPESA, Categoria.ALIMENTACAO));
        transacoes.add(criarTransacao("Cinema", 100.0, TipoTransacao.DESPESA, Categoria.LAZER));
        transacoes.add(criarTransacao("Restaurante", 200.0, TipoTransacao.DESPESA, Categoria.ALIMENTACAO));
    }

    private Transacao criarTransacao(String descricao, double valor, TipoTransacao tipo, Categoria categoria) {
        Transacao t = new Transacao(tipo, categoria, valor, descricao, usuario1, conta);
        t.setData(LocalDate.now());
        return t;
    }

    // --- Testes ProjecaoSaldo ---
    @Test
    @DisplayName("Deve calcular projecao de saldo corretamente")
    public void testProjecaoSaldo() {
        ProjecaoSaldo projecao = new ProjecaoSaldo(6);
        
        double resultado = projecao.calcular(transacoes);
        assertEquals(26100.0, resultado, 0.01);
        assertEquals(6, projecao.getMeses());
        assertTrue(projecao.getDescricao().contains("6 meses"));
    }
    
    @Test
    @DisplayName("Deve retornar zero para lista vazia")
    public void testProjecaoSaldoVazio() {
        ProjecaoSaldo projecao = new ProjecaoSaldo(12);
        assertEquals(0.0, projecao.calcular(new ArrayList<>()));
    }
    
    @Test
    @DisplayName("Deve permitir alterar meses")
    public void testProjecaoSaldoSetMeses() {
        ProjecaoSaldo projecao = new ProjecaoSaldo(3);
        projecao.setMeses(12);
        assertEquals(12, projecao.getMeses());
    }

    // --- Testes RateioAutomatico ---
    @Test
    @DisplayName("Deve calcular rateio com pesos corretamente")
    public void testRateioAutomatico() {
        RateioAutomatico rateio = new RateioAutomatico();
        rateio.adicionarMembro(usuario1, 2.0);
        rateio.adicionarMembro(usuario2, 1.0);
        
        assertEquals(2600.0, rateio.calcular(transacoes));
        
        Map<Usuario, Double> resultado = rateio.calcularRateio(300.0);
        
        assertEquals(200.0, resultado.get(usuario1), 0.01);
        assertEquals(100.0, resultado.get(usuario2), 0.01);
        assertEquals(2, rateio.getPesos().size());
    }
    
    @Test
    @DisplayName("Deve retornar vazio quando nao ha membros")
    public void testRateioSemPesos() {
        RateioAutomatico rateio = new RateioAutomatico();
        assertTrue(rateio.calcularRateio(100.0).isEmpty());
    }
    
    @Test
    @DisplayName("Deve distribuir igualmente com pesos iguais")
    public void testRateioIgual() {
        RateioAutomatico rateio = new RateioAutomatico();
        rateio.adicionarMembro(usuario1, 1.0);
        rateio.adicionarMembro(usuario2, 1.0);
        
        Map<Usuario, Double> resultado = rateio.calcularRateio(1000.0);
        assertEquals(500.0, resultado.get(usuario1), 0.01);
        assertEquals(500.0, resultado.get(usuario2), 0.01);
    }

    // --- Testes SugestaoEconomia ---
    @Test
    @DisplayName("Deve sugerir economia baseada na maior categoria")
    public void testSugestaoEconomia() {
        SugestaoEconomia sugestao = new SugestaoEconomia();
        
        assertEquals(225.0, sugestao.calcular(transacoes), 0.01);
        
        Map<Categoria, Double> analise = sugestao.analisarGastosPorCategoria(transacoes);
        assertEquals(1500.0, analise.get(Categoria.MORADIA));
        assertEquals(1000.0, analise.get(Categoria.ALIMENTACAO));
        assertEquals(100.0, analise.get(Categoria.LAZER));
    }
    
    @Test
    @DisplayName("Deve retornar zero para lista vazia")
    public void testSugestaoEconomiaVazio() {
        SugestaoEconomia sugestao = new SugestaoEconomia();
        assertEquals(0.0, sugestao.calcular(new ArrayList<>()));
        assertTrue(sugestao.analisarGastosPorCategoria(new ArrayList<>()).isEmpty());
    }
    
    @Test
    @DisplayName("Deve ignorar receitas na analise")
    public void testSugestaoEconomiaSomenteDespesas() {
        SugestaoEconomia sugestao = new SugestaoEconomia();
        
        List<Transacao> somenteReceitas = new ArrayList<>();
        somenteReceitas.add(criarTransacao("Salario", 5000.0, TipoTransacao.RECEITA, Categoria.SALARIO));
        
        assertEquals(0.0, sugestao.calcular(somenteReceitas));
    }
}
