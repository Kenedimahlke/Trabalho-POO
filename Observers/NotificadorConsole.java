package Observers;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Interfaces.*;
import Interfaces.Observer;
import java.time.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Observador que envia notificações para o console
public class NotificadorConsole implements Observer {
    private String nomeDestinatario;
    private boolean ativo;
    
    public NotificadorConsole(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
        this.ativo = true;
    }
    
    @Override
    public void atualizar(String evento, Object dados) {
        if (!ativo) return;
        
        String timestamp = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   NOTIFICAÇÃO                                             ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║ Para: " + ajustarTamanho(nomeDestinatario, 50) + " ║");
        System.out.println("║ Hora: " + ajustarTamanho(timestamp, 50) + " ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.println("║ " + ajustarTamanho(evento, 58) + " ║");
        
        if (dados != null) {
            String dadosStr = dados.toString();
            if (dadosStr.length() > 56) {
                dadosStr = dadosStr.substring(0, 53) + "...";
            }
            System.out.println("║ " + ajustarTamanho(dadosStr, 58) + " ║");
        }
        
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
    }
    
    private String ajustarTamanho(String texto, int tamanho) {
        if (texto.length() > tamanho) {
            return texto.substring(0, tamanho);
        }
        return String.format("%-" + tamanho + "s", texto);
    }
    
    @Override
    public String getNome() {
        return nomeDestinatario;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
}