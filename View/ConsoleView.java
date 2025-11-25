package View;

public class ConsoleView {
    
    public void exibirTitulo(String titulo) {
        System.out.println("\n" + "=".repeat(65));
        System.out.println("           " + titulo);
        System.out.println("=".repeat(65));
    }
    
    public void exibirLinha(String linha) {
        System.out.println(linha);
    }
    
    public void exibirSeparador() {
        System.out.println("-".repeat(65));
    }
    
    public void exibir(String texto) {
        System.out.print(texto);
    }
    
    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }
    
    public void exibirErro(String erro) {
        System.out.println("ERRO: " + erro);
    }
}
