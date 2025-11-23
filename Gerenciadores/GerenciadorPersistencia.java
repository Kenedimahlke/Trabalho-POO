package Gerenciadores;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Interfaces.*;
import Interfaces.Observer;
import Observers.*;
import Relatorios.*;
import Repositorios.*;
import Strategy.*;
import java.io.*;
import java.time.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Classe responsável pela persistência de dados do sistema
// REFATORADO: Aplicando DIP - Depende de EstrategiaPersistencia (interface)
public class GerenciadorPersistencia {
    private static final String ARQUIVO_DADOS = "dados_financeiros.dat";
    private static final String DIRETORIO_BACKUP = "backups";
    
    private EstrategiaPersistencia estrategia;
    
    // Construtor com estratégia (DIP - Dependency Injection)
    public GerenciadorPersistencia(EstrategiaPersistencia estrategia) {
        this.estrategia = estrategia;
    }
    
    // Construtor padrão usa serialização
    public GerenciadorPersistencia() {
        this.estrategia = new PersistenciaSerializacao();
    }
    
    // Permite trocar estratégia em tempo de execução (OCP)
    public void setEstrategia(EstrategiaPersistencia estrategia) {
        this.estrategia = estrategia;
    }
    
    // Salva todos os dados usando a estratégia configurada
    public void salvarDados(List<Usuario> usuarios, List<ContaFinanceira> contas,
                           List<Transacao> transacoes, List<Meta> metas,
                           List<Orcamento> orcamentos) throws Exception {
        salvarDados(usuarios, contas, transacoes, metas, orcamentos, ARQUIVO_DADOS);
    }
    
    // Salva dados em arquivo específico
    public void salvarDados(List<Usuario> usuarios, List<ContaFinanceira> contas,
                           List<Transacao> transacoes, List<Meta> metas,
                           List<Orcamento> orcamentos, String arquivo) throws Exception {
        Map<String, List<?>> dados = new HashMap<>();
        dados.put("usuarios", usuarios);
        dados.put("contas", contas);
        dados.put("transacoes", transacoes);
        dados.put("metas", metas);
        dados.put("orcamentos", orcamentos);
        
        estrategia.salvar(dados, arquivo);
        System.out.println("Dados salvos com sucesso em " + arquivo);
    }
    
    // Carrega dados usando a estratégia configurada
    public Map<String, List<?>> carregarDados() throws Exception {
        return carregarDados(ARQUIVO_DADOS);
    }
    
    // Carrega dados de arquivo específico
    public Map<String, List<?>> carregarDados(String arquivo) throws Exception {
        if (!estrategia.existe(arquivo)) {
            System.out.println("Nenhum arquivo de dados encontrado. Iniciando sistema vazio.");
            return new HashMap<>();
        }
        
        Map<String, List<?>> dados = estrategia.carregar(arquivo);
        System.out.println("Dados carregados com sucesso de " + arquivo);
        return dados;
    }
    
    // Verifica se existem dados salvos
    public boolean existeDadosSalvos() {
        return existeDadosSalvos(ARQUIVO_DADOS);
    }
    
    public boolean existeDadosSalvos(String arquivo) {
        return estrategia.existe(arquivo);
    }
    
    // Limpa dados salvos
    public void limparDadosSalvos() throws Exception {
        limparDadosSalvos(ARQUIVO_DADOS);
    }
    
    public void limparDadosSalvos(String arquivo) throws Exception {
        estrategia.deletar(arquivo);
        System.out.println("Dados removidos com sucesso de " + arquivo);
    }
    
    // Cria um backup dos dados
    public void criarBackup() throws Exception {
        criarBackup(ARQUIVO_DADOS);
    }
    
    public void criarBackup(String arquivoOrigem) throws Exception {
        File diretorio = new File(DIRETORIO_BACKUP);
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
        
        if (!estrategia.existe(arquivoOrigem)) {
            System.out.println("Nenhum arquivo para backup.");
            return;
        }
        
        String timestamp = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String arquivoBackup = DIRETORIO_BACKUP + File.separator + "backup_" + arquivoOrigem + "_" + timestamp + ".dat";
        
        // Carrega e salva no backup
        Map<String, List<?>> dados = estrategia.carregar(arquivoOrigem);
        estrategia.salvar(dados, arquivoBackup);
        
        System.out.println("Backup criado: backup_" + timestamp + ".dat");
    }
    
    // Exporta dados em formato texto
    public void exportarParaTexto(String arquivoOrigem, String nomeArquivo) throws Exception {
        Map<String, List<?>> dados = estrategia.carregar(arquivoOrigem);
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            writer.println("=================================================");
            writer.println("   EXPORTAÇÃO DE DADOS - SISTEMA FINANCEIRO");
            writer.println("=================================================");
            writer.println("Data: " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            writer.println();
            
            @SuppressWarnings("unchecked")
            List<Usuario> usuarios = (List<Usuario>) dados.get("usuarios");
            writer.println("--- USUÁRIOS ---");
            if (usuarios != null) {
                for (Usuario u : usuarios) {
                    writer.println(u.getNome() + " (" + u.getEmail() + ")");
                }
            }
            writer.println();
            
            @SuppressWarnings("unchecked")
            List<ContaFinanceira> contas = (List<ContaFinanceira>) dados.get("contas");
            writer.println("--- CONTAS ---");
            if (contas != null) {
                for (ContaFinanceira c : contas) {
                    writer.println(c.getTipoConta() + " " + c.getNumeroConta() + 
                                 " - Titular: " + c.getTitular().getNome() + 
                                 " - Saldo: R$ " + String.format("%.2f", c.consultarSaldo()));
                }
            }
            writer.println();
            
            @SuppressWarnings("unchecked")
            List<Transacao> transacoes = (List<Transacao>) dados.get("transacoes");
            writer.println("--- TRANSAÇÕES ---");
            if (transacoes != null) {
                writer.println("Total: " + transacoes.size());
            }
            writer.println();
            
            @SuppressWarnings("unchecked")
            List<Meta> metas = (List<Meta>) dados.get("metas");
            writer.println("--- METAS ---");
            if (metas != null) {
                writer.println("Total: " + metas.size());
            }
            writer.println();
            
            @SuppressWarnings("unchecked")
            List<Orcamento> orcamentos = (List<Orcamento>) dados.get("orcamentos");
            writer.println("--- ORÇAMENTOS ---");
            if (orcamentos != null) {
                writer.println("Total: " + orcamentos.size());
            }
            writer.println();
            
            writer.println("=================================================");
            
            System.out.println("Dados exportados para " + nomeArquivo);
        }
    }
}