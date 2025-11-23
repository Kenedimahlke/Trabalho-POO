package Interfaces;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import java.time.*;
import java.util.*;

// Interface para observadores do sistema
public interface Observer {
    void atualizar(String evento, Object dados);
    String getNome();
}