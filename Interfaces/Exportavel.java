// Interface para entidades que podem ser exportadas
public interface Exportavel {
    String exportarParaTexto();
    void salvarEmArquivo(String caminho);
    String getFormatoExportacao();
}
