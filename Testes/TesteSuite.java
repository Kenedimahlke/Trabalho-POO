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
import java.util.*;
import org.junit.jupiter.api.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import static org.junit.jupiter.api.Assertions.*;

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