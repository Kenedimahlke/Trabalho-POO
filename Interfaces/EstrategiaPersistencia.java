package Interfaces;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import java.time.*;
import java.util.*;
import java.util.List;
import java.util.Map;

// Interface para estratégias de persistência (DIP + OCP)
public interface EstrategiaPersistencia {
    void salvar(Map<String, List<?>> dados, String destino) throws Exception;
    Map<String, List<?>> carregar(String origem) throws Exception;
    boolean existe(String origem);
    void deletar(String origem) throws Exception;
}