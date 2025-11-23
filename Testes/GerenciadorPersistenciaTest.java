import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GerenciadorPersistenciaTest {
    
    private GerenciadorPersistencia persistencia;
    private List<Usuario> usuarios;
    private List<ContaFinanceira> contas;
    private List<Transacao> transacoes;
    private List<Meta> metas;
    private List<Orcamento> orcamentos;
    private static final String ARQUIVO_TESTE = "teste_dados.dat";
    
    @BeforeEach
    public void setUp() {
        persistencia = new GerenciadorPersistencia();
        
        // Prepara dados de teste
        usuarios = new ArrayList<>();
        Usuario usuario = new UsuarioIndividual("Teste", "000.000.000-00", "teste@email.com");
        usuarios.add(usuario);
        
        contas = new ArrayList<>();
        ContaCorrente conta = new ContaCorrente("12345", usuario, 500.0);
        conta.depositar(1000.0);
        contas.add(conta);
        
        transacoes = new ArrayList<>();
        Transacao transacao = new Transacao(
            TipoTransacao.RECEITA,
            Categoria.SALARIO,
            500.0,
            "Salário",
            usuario,
            conta
        );
        transacoes.add(transacao);
        
        metas = new ArrayList<>();
        Meta meta = new Meta(
            "Economizar",
            Categoria.OUTROS,
            10000.0,
            java.time.LocalDate.now().plusMonths(6),
            usuario
        );
        metas.add(meta);
        
        orcamentos = new ArrayList<>();
        Orcamento orcamento = new Orcamento(
            "Orçamento Teste",
            Categoria.ALIMENTACAO,
            1000.0,
            java.time.YearMonth.now(),
            usuario
        );
        orcamentos.add(orcamento);
    }
    
    @AfterEach
    public void tearDown() {
        // Limpa arquivo de teste após cada teste
        File arquivo = new File(ARQUIVO_TESTE);
        if (arquivo.exists()) {
            arquivo.delete();
        }
        
        File arquivoBackup = new File("backup_" + ARQUIVO_TESTE);
        if (arquivoBackup.exists()) {
            arquivoBackup.delete();
        }
        
        File arquivoExportado = new File("exportado.txt");
        if (arquivoExportado.exists()) {
            arquivoExportado.delete();
        }
    }
    
    @Test
    @DisplayName("Deve salvar dados corretamente")
    public void testSalvarDados() throws Exception {
        persistencia.salvarDados(usuarios, contas, transacoes, metas, orcamentos, ARQUIVO_TESTE);
        
        File arquivo = new File(ARQUIVO_TESTE);
        assertTrue(arquivo.exists());
        assertTrue(arquivo.length() > 0);
    }
    
    @Test
    @DisplayName("Deve carregar dados corretamente")
    public void testCarregarDados() throws Exception {
        // Primeiro salva
        persistencia.salvarDados(usuarios, contas, transacoes, metas, orcamentos, ARQUIVO_TESTE);
        
        // Depois carrega
        Map<String, List<?>> dadosCarregados = persistencia.carregarDados(ARQUIVO_TESTE);
        
        assertNotNull(dadosCarregados);
        assertEquals(5, dadosCarregados.size());
        
        @SuppressWarnings("unchecked")
        List<Usuario> usuariosCarregados = (List<Usuario>) dadosCarregados.get("usuarios");
        assertEquals(1, usuariosCarregados.size());
        assertEquals("Teste", usuariosCarregados.get(0).getNome());
    }
    
    @Test
    @DisplayName("Deve verificar existência de dados salvos")
    public void testExisteDadosSalvos() throws Exception {
        assertFalse(persistencia.existeDadosSalvos(ARQUIVO_TESTE));
        
        persistencia.salvarDados(usuarios, contas, transacoes, metas, orcamentos, ARQUIVO_TESTE);
        
        assertTrue(persistencia.existeDadosSalvos(ARQUIVO_TESTE));
    }
    
    @Test
    @DisplayName("Deve criar backup dos dados")
    public void testCriarBackup() throws Exception {
        persistencia.salvarDados(usuarios, contas, transacoes, metas, orcamentos, ARQUIVO_TESTE);
        
        persistencia.criarBackup(ARQUIVO_TESTE);
        
        // Verifica se arquivo de backup foi criado no diretório de backups
        File diretorioBackup = new File("backups");
        if (!diretorioBackup.exists()) {
            diretorioBackup = new File(".");
        }
        
        File[] arquivos = diretorioBackup.listFiles((dir, name) -> 
            name.startsWith("backup_" + ARQUIVO_TESTE) || name.contains(ARQUIVO_TESTE) && name.contains("backup"));
        
        assertNotNull(arquivos);
        assertTrue(arquivos.length > 0);
        
        // Limpa backups criados
        for (File backup : arquivos) {
            backup.delete();
        }
        if (diretorioBackup.getName().equals("backups")) {
            diretorioBackup.delete();
        }
    }
    
    @Test
    @DisplayName("Deve exportar para arquivo texto")
    public void testExportarParaTexto() throws Exception {
        persistencia.salvarDados(usuarios, contas, transacoes, metas, orcamentos, ARQUIVO_TESTE);
        
        persistencia.exportarParaTexto(ARQUIVO_TESTE, "exportado.txt");
        
        File arquivoExportado = new File("exportado.txt");
        assertTrue(arquivoExportado.exists());
        assertTrue(arquivoExportado.length() > 0);
    }
    
    @Test
    @DisplayName("Deve limpar dados salvos")
    public void testLimparDadosSalvos() throws Exception {
        persistencia.salvarDados(usuarios, contas, transacoes, metas, orcamentos, ARQUIVO_TESTE);
        assertTrue(persistencia.existeDadosSalvos(ARQUIVO_TESTE));
        
        persistencia.limparDadosSalvos(ARQUIVO_TESTE);
        
        assertFalse(persistencia.existeDadosSalvos(ARQUIVO_TESTE));
    }
    
    @Test
    @DisplayName("Deve retornar mapa vazio ao carregar arquivo inexistente")
    public void testCarregarArquivoInexistente() throws Exception {
        Map<String, List<?>> dados = persistencia.carregarDados("arquivo_que_nao_existe.dat");
        assertNotNull(dados);
        assertTrue(dados.isEmpty());
    }
    
    @Test
    @DisplayName("Deve preservar integridade dos dados após salvar e carregar")
    public void testIntegridadeDados() throws Exception {
        // Salva
        persistencia.salvarDados(usuarios, contas, transacoes, metas, orcamentos, ARQUIVO_TESTE);
        
        // Carrega
        Map<String, List<?>> dadosCarregados = persistencia.carregarDados(ARQUIVO_TESTE);
        
        @SuppressWarnings("unchecked")
        List<Usuario> usuariosCarregados = (List<Usuario>) dadosCarregados.get("usuarios");
        @SuppressWarnings("unchecked")
        List<ContaFinanceira> contasCarregadas = (List<ContaFinanceira>) dadosCarregados.get("contas");
        @SuppressWarnings("unchecked")
        List<Transacao> transacoesCarregadas = (List<Transacao>) dadosCarregados.get("transacoes");
        
        // Verifica integridade
        assertEquals(usuarios.size(), usuariosCarregados.size());
        assertEquals(contas.size(), contasCarregadas.size());
        assertEquals(transacoes.size(), transacoesCarregadas.size());
        
        assertEquals(usuarios.get(0).getNome(), usuariosCarregados.get(0).getNome());
        assertEquals(contas.get(0).getNumeroConta(), contasCarregadas.get(0).getNumeroConta());
        assertEquals(transacoes.get(0).getValor(), transacoesCarregadas.get(0).getValor(), 0.01);
    }
}
