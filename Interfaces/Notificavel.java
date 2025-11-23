package Interfaces;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import java.time.*;
import java.util.*;

// Interface para entidades que podem receber notificações
public interface Notificavel {
    void enviarNotificacao(String mensagem);
    void configurarNotificacoes(boolean ativo);
    boolean isNotificacoesAtivas();
}