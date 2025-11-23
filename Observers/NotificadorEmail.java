package Observers;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Interfaces.*;
import Interfaces.Observer;
import java.time.*;
import java.util.*;

// Observador que simula envio de email
public class NotificadorEmail implements Observer {
    private String email;
    private boolean ativo;
    
    public NotificadorEmail(String email) {
        this.email = email;
        this.ativo = true;
    }
    
    @Override
    public void atualizar(String evento, Object dados) {
        if (!ativo) return;
        
        System.out.println("\n  [EMAIL ENVIADO]");
        System.out.println("Para: " + email);
        System.out.println("Assunto: " + evento);
        System.out.println("Mensagem: " + (dados != null ? dados.toString() : "Notificação do sistema"));
        System.out.println("────────────────────────────────────\n");
    }
    
    @Override
    public String getNome() {
        return email;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
}