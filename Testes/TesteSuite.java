package Testes;

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
    RelatoriosTest.class,
    CartaoCreditoTest.class,
    CarteiraInvestimentoTest.class,
    GrupoTest.class,
    StrategyTest.class,
    ContaFactoryTest.class
})
public class TesteSuite {
   
}
