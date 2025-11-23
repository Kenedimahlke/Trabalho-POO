import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StrategyTest {

    private List<Transacao> transacoes;
    private Usuario usuario1;
    private Usuario usuario2;

    @BeforeEach
    public void setUp() {
        transacoes = new ArrayList<>();
        usuario1 = new UsuarioIndividual("User1", "user1@email.com", "123");
        usuario2 = new UsuarioIndividual("User2", "user2@email.com", "123");

        // Adicionando transações
        transacoes.add(criarTransacao("Salário", 5000.0, TipoTransacao.RECEITA, Categoria.SALARIO));
        transacoes.add(criarTransacao("Aluguel", 1500.0, TipoTransacao.DESPESA, Categoria.MORADIA));
        transacoes.add(criarTransacao("Supermercado", 800.0, TipoTransacao.DESPESA, Categoria.ALIMENTACAO));
        transacoes.add(criarTransacao("Cinema", 100.0, TipoTransacao.DESPESA, Categoria.LAZER));
        transacoes.add(criarTransacao("Restaurante", 200.0, TipoTransacao.DESPESA, Categoria.ALIMENTACAO));
    }

    private Transacao criarTransacao(String descricao, double valor, TipoTransacao tipo, Categoria categoria) {
        Transacao t = new Transacao(tipo, categoria, valor, descricao, null, null);
        t.setData(LocalDate.now());
        return t;
    }

    // --- Testes DetectorGastosAnormais ---
    @Test
    public void testDetectorGastosAnormais() {
        DetectorGastosAnormais detector = new DetectorGastosAnormais();
        
        // Média de despesas: (1500 + 800 + 100 + 200) / 4 = 650
        // Limite padrão 1.5 * média = 975
        
        // Aluguel (1500) > 975 -> Anormal
        // Supermercado (800) < 975 -> Normal
        
        // O método calcular retorna o limite de desvio * média
        assertEquals(975.0, detector.calcular(transacoes), 0.01);
        
        List<Transacao> anormais = detector.detectarAnormalidades(transacoes);
        // O detector compara com a média DA CATEGORIA
        // Média Moradia: 1500. Limite: 2250. Aluguel (1500) -> Normal
        // Média Alimentação: (800+200)/2 = 500. Limite: 750. Supermercado (800) -> Anormal. Restaurante (200) -> Normal.
        // Média Lazer: 100. Limite: 150. Cinema (100) -> Normal.
        
        // Vamos verificar a lógica do detector:
        // "double media = mediaPorCategoria.getOrDefault(t.getCategoria(), 0.0);"
        // "if (t.getValor() > media * limiteDesvio)"
        
        // Alimentação: Média 500. Limite 750.
        // Supermercado 800 > 750 -> Anormal.
        
        assertEquals(1, anormais.size());
        assertEquals("Supermercado", anormais.get(0).getDescricao());
    }
    
    @Test
    public void testDetectorGastosAnormaisVazio() {
        DetectorGastosAnormais detector = new DetectorGastosAnormais();
        assertEquals(0.0, detector.calcular(new ArrayList<>()));
        assertTrue(detector.detectarAnormalidades(new ArrayList<>()).isEmpty());
    }

    // --- Testes ProjecaoSaldo ---
    @Test
    public void testProjecaoSaldo() {
        ProjecaoSaldo projecao = new ProjecaoSaldo(6); // 6 meses
        
        // Média Receitas: 5000
        // Média Despesas: 650
        // Saldo Médio Mensal: 4350
        // Projeção 6 meses: 4350 * 6 = 26100
        
        assertEquals(26100.0, projecao.calcular(transacoes), 0.01);
        assertEquals(6, projecao.getMeses());
        assertEquals("Projeção de saldo para 6 meses baseada no histórico", projecao.getDescricao());
    }
    
    @Test
    public void testProjecaoSaldoVazio() {
        ProjecaoSaldo projecao = new ProjecaoSaldo(12);
        assertEquals(0.0, projecao.calcular(new ArrayList<>()));
    }

    // --- Testes RateioAutomatico ---
    @Test
    public void testRateioAutomatico() {
        RateioAutomatico rateio = new RateioAutomatico();
        rateio.adicionarMembro(usuario1, 2.0); // Peso 2
        rateio.adicionarMembro(usuario2, 1.0); // Peso 1
        
        // Total despesas: 2600
        assertEquals(2600.0, rateio.calcular(transacoes));
        
        Map<Usuario, Double> resultado = rateio.calcularRateio(300.0);
        // Soma pesos: 3
        // User1: 300 * (2/3) = 200
        // User2: 300 * (1/3) = 100
        
        assertEquals(200.0, resultado.get(usuario1), 0.01);
        assertEquals(100.0, resultado.get(usuario2), 0.01);
        
        assertEquals(2, rateio.getPesos().size());
    }
    
    @Test
    public void testRateioSemPesos() {
        RateioAutomatico rateio = new RateioAutomatico();
        assertTrue(rateio.calcularRateio(100.0).isEmpty());
    }

    // --- Testes SugestaoEconomia ---
    @Test
    public void testSugestaoEconomia() {
        SugestaoEconomia sugestao = new SugestaoEconomia();
        
        // Gastos por categoria:
        // Moradia: 1500
        // Alimentação: 1000
        // Lazer: 100
        
        // Maior gasto: 1500 (Moradia)
        // Sugestão: 15% de 1500 = 225
        
        assertEquals(225.0, sugestao.calcular(transacoes), 0.01);
        
        Map<Categoria, Double> analise = sugestao.analisarGastosPorCategoria(transacoes);
        assertEquals(1500.0, analise.get(Categoria.MORADIA));
        assertEquals(1000.0, analise.get(Categoria.ALIMENTACAO));
    }
    
    @Test
    public void testSugestaoEconomiaVazio() {
        SugestaoEconomia sugestao = new SugestaoEconomia();
        assertEquals(0.0, sugestao.calcular(new ArrayList<>()));
    }
}
