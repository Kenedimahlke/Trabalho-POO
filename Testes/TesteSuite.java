import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    TransacaoTest.class,
    ContaCorrenteTest.class,
    MetaTest.class,
    OrcamentoTest.class,
    CofrinhoTest.class,
    GerenciadorFinanceiroTest.class,
    SimulacaoCenarioTest.class,
    GerenciadorPersistenciaTest.class,
    RelatoriosTest.class,
    CartaoCreditoTest.class,
    CarteiraInvestimentoTest.class,
    GrupoTest.class,
    StrategyTest.class,
    ContaFactoryTest.class,
    RepositorioMemoriaTest.class
})
public class TesteSuite {
    // Esta classe serve apenas como ponto de entrada para executar todos os testes
}
