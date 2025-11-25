package Testes;

import Entidades.*;
import Enums.*;
import Factory.*;
import Relatorios.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

public class RelatoriosTest {

    private List<Transacao> transacoes;
    private GerenciadorRelatorios gerenciadorRelatorios;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    @BeforeEach
    public void setUp() {
        transacoes = new ArrayList<>();
        dataInicio = LocalDate.of(2023, 1, 1);
        dataFim = LocalDate.of(2023, 1, 31);

        transacoes.add(criarTransacao("Salário", 5000.0, TipoTransacao.RECEITA, LocalDate.of(2023, 1, 5), Categoria.SALARIO));
        transacoes.add(criarTransacao("Aluguel", 1500.0, TipoTransacao.DESPESA, LocalDate.of(2023, 1, 10), Categoria.MORADIA));
        transacoes.add(criarTransacao("Supermercado", 800.0, TipoTransacao.DESPESA, LocalDate.of(2023, 1, 15), Categoria.ALIMENTACAO));
        transacoes.add(criarTransacao("Cinema", 100.0, TipoTransacao.DESPESA, LocalDate.of(2023, 1, 20), Categoria.LAZER));
        transacoes.add(criarTransacao("Freelance", 1000.0, TipoTransacao.RECEITA, LocalDate.of(2023, 1, 25), Categoria.SALARIO));
        transacoes.add(criarTransacao("Fevereiro", 200.0, TipoTransacao.DESPESA, LocalDate.of(2023, 2, 1), Categoria.LAZER));

        gerenciadorRelatorios = new GerenciadorRelatorios(transacoes);
    }

    private Transacao criarTransacao(String descricao, double valor, TipoTransacao tipo, LocalDate data, Categoria categoria) {
        Transacao t = new Transacao(tipo, categoria, valor, descricao, null, null);
        t.setData(data);
        return t;
    }

    @Test
    public void testGerarRelatorioPorPeriodo() {
        String relatorio = gerenciadorRelatorios.gerarRelatorioPorPeriodo(dataInicio, dataFim);
        assertNotNull(relatorio);
        assertTrue(relatorio.contains("RELATORIO DE GASTOS POR PERIODO"));
        assertTrue(relatorio.contains("Salário"));
        assertTrue(relatorio.contains("Aluguel"));
        assertTrue(relatorio.contains("Supermercado"));
        assertTrue(relatorio.contains("Cinema"));
        assertTrue(relatorio.contains("Freelance"));
        assertFalse(relatorio.contains("Fevereiro")); 
        
        assertTrue(relatorio.contains("Total de Receitas:"));
        assertTrue(relatorio.contains("Total de Despesas:"));
    }

    @Test
    public void testGerarRelatorioComparativoCategoria() {
        String relatorio = gerenciadorRelatorios.gerarRelatorioComparativoCategoria(dataInicio, dataFim);
        assertNotNull(relatorio);
        assertTrue(relatorio.contains("RELATORIO COMPARATIVO POR CATEGORIA"));
        assertTrue(relatorio.contains("Moradia"));
        assertTrue(relatorio.contains("Alimentação"));
        assertTrue(relatorio.contains("Lazer"));
        
        assertFalse(relatorio.contains("Salário"));
    }

    @Test
    public void testGerarRelatorioRankingDespesas() {
        String relatorio = gerenciadorRelatorios.gerarRelatorioRankingDespesas(dataInicio, dataFim, 5);
        assertNotNull(relatorio);
        assertTrue(relatorio.contains("TOP 5 MAIORES DESPESAS"));
        assertTrue(relatorio.contains("Aluguel")); // Maior despesa
        assertTrue(relatorio.contains("Supermercado"));
        assertTrue(relatorio.contains("Cinema"));
    }

    @Test
    public void testGerarRelatorioEvolucaoSaldo() {
        double saldoInicial = 1000.0;
        String relatorio = gerenciadorRelatorios.gerarRelatorioEvolucaoSaldo(dataInicio, dataFim, saldoInicial);
        assertNotNull(relatorio);
        assertTrue(relatorio.contains("RELATORIO DE EVOLUCAO DE SALDO"));
        assertTrue(relatorio.contains("Saldo Inicial:"));
        assertTrue(relatorio.contains("2023")); // Mês
    }

    @Test
    public void testGerarRelatorioCompleto() {
        double saldoInicial = 1000.0;
        String relatorio = gerenciadorRelatorios.gerarRelatorioCompleto(dataInicio, dataFim, saldoInicial);
        assertNotNull(relatorio);
        assertTrue(relatorio.contains("RELATORIO FINANCEIRO COMPLETO"));
        assertTrue(relatorio.contains("RESUMO GERAL"));
        assertTrue(relatorio.contains("Saldo Inicial:"));
        assertTrue(relatorio.contains("Saldo Final:"));
    }

    @Test
    public void testExportarRelatorioPorPeriodo(@TempDir Path tempDir) throws Exception {
        File arquivo = tempDir.resolve("relatorio_periodo.txt").toFile();
        gerenciadorRelatorios.exportarRelatorioPorPeriodo(dataInicio, dataFim, arquivo.getAbsolutePath());
        assertTrue(arquivo.exists());
        assertTrue(arquivo.length() > 0);
        
        String conteudo = Files.readString(arquivo.toPath());
        assertTrue(conteudo.contains("RELATORIO DE GASTOS POR PERIODO"));
    }

    @Test
    public void testExportarRelatorioComparativoCategoria(@TempDir Path tempDir) throws Exception {
        File arquivo = tempDir.resolve("relatorio_categoria.txt").toFile();
        gerenciadorRelatorios.exportarRelatorioComparativoCategoria(dataInicio, dataFim, arquivo.getAbsolutePath());
        assertTrue(arquivo.exists());
        assertTrue(arquivo.length() > 0);
        
        String conteudo = Files.readString(arquivo.toPath());
        assertTrue(conteudo.contains("RELATORIO COMPARATIVO POR CATEGORIA"));
    }

    @Test
    public void testExportarRelatorioRankingDespesas(@TempDir Path tempDir) throws Exception {
        File arquivo = tempDir.resolve("relatorio_ranking.txt").toFile();
        gerenciadorRelatorios.exportarRelatorioRankingDespesas(dataInicio, dataFim, 5, arquivo.getAbsolutePath());
        assertTrue(arquivo.exists());
        assertTrue(arquivo.length() > 0);
        
        String conteudo = Files.readString(arquivo.toPath());
        assertTrue(conteudo.contains("TOP 5 MAIORES DESPESAS"));
    }

    @Test
    public void testExportarRelatorioEvolucaoSaldo(@TempDir Path tempDir) throws Exception {
        File arquivo = tempDir.resolve("relatorio_evolucao.txt").toFile();
        gerenciadorRelatorios.exportarRelatorioEvolucaoSaldo(dataInicio, dataFim, 1000.0, arquivo.getAbsolutePath());
        assertTrue(arquivo.exists());
        assertTrue(arquivo.length() > 0);
        
        String conteudo = Files.readString(arquivo.toPath());
        assertTrue(conteudo.contains("RELATORIO DE EVOLUCAO DE SALDO"));
    }
    
    @Test
    public void testRelatorioVazio() {
        GerenciadorRelatorios gerenciadorVazio = new GerenciadorRelatorios(new ArrayList<>());
        String relatorio = gerenciadorVazio.gerarRelatorioPorPeriodo(dataInicio, dataFim);
        assertTrue(relatorio.contains("Total de Receitas: R$ 0,00"));
    }
}
