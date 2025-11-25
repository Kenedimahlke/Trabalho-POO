package Gerenciadores;

import Entidades.*;
import java.io.*;
import java.util.List;

public class GerenciadorPersistencia {
    
    private static final String ARQUIVO_DADOS = "dados_sistema.ser";
    
    public void salvarDados(GerenciadorFinanceiro gerenciador) throws IOException {
        DadosSistema dados = new DadosSistema(
            gerenciador.getUsuarios(),
            gerenciador.getContas(),
            gerenciador.getTransacoes(),
            gerenciador.getMetas(),
            gerenciador.getOrcamentos()
        );
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            oos.writeObject(dados);
        }
    }
    
    @SuppressWarnings("unchecked")
    public void carregarDados(GerenciadorFinanceiro gerenciador) throws IOException, ClassNotFoundException {
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) {
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            DadosSistema dados = (DadosSistema) ois.readObject();
            
            // Limpar dados atuais
            // Como o GerenciadorFinanceiro não tem métodos "set" para as listas e nem "clear",
            // precisaremos adicionar métodos para limpar ou setar os dados no GerenciadorFinanceiro.
            // Por enquanto, vamos assumir que o gerenciador está vazio ao iniciar ou vamos adicionar os métodos necessários.
            
            // Vamos precisar modificar o GerenciadorFinanceiro para permitir a carga desses dados
            // ou expor métodos para limpar e adicionar em lote.
            // A melhor abordagem para manter o encapsulamento é adicionar um método 'carregarEstado' no GerenciadorFinanceiro
            // ou métodos 'setUsuarios', 'setContas', etc.
            
            // Como não posso editar o GerenciadorFinanceiro neste passo (apenas criar arquivo),
            // vou assumir que vou editar o GerenciadorFinanceiro em seguida.
            
            gerenciador.carregarEstado(
                dados.usuarios,
                dados.contas,
                dados.transacoes,
                dados.metas,
                dados.orcamentos
            );
        }
    }
    
    // Classe interna para encapsular os dados
    private static class DadosSistema implements Serializable {
        private static final long serialVersionUID = 1L;
        
        List<Usuario> usuarios;
        List<ContaFinanceira> contas;
        List<Transacao> transacoes;
        List<Meta> metas;
        List<Orcamento> orcamentos;
        
        public DadosSistema(List<Usuario> usuarios, List<ContaFinanceira> contas, 
                           List<Transacao> transacoes, List<Meta> metas, List<Orcamento> orcamentos) {
            this.usuarios = usuarios;
            this.contas = contas;
            this.transacoes = transacoes;
            this.metas = metas;
            this.orcamentos = orcamentos;
        }
    }
}
