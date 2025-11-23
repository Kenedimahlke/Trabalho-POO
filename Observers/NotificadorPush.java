// Observador que simula push notifications
public class NotificadorPush implements Observer {
    private String dispositivoId;
    private boolean ativo;
    
    public NotificadorPush(String dispositivoId) {
        this.dispositivoId = dispositivoId;
        this.ativo = true;
    }
    
    @Override
    public void atualizar(String evento, Object dados) {
        if (!ativo) return;
        
        System.out.println("\n  [PUSH NOTIFICATION]");
        System.out.println("Dispositivo: " + dispositivoId);
        System.out.println("▸ " + evento);
        if (dados != null) {
            System.out.println("  " + dados.toString());
        }
        System.out.println("────────────────────────────────────\n");
    }
    
    @Override
    public String getNome() {
        return "Dispositivo: " + dispositivoId;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
}
