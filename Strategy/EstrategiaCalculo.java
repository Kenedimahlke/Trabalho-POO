package Strategy;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Interfaces.*;
import java.time.*;
import java.util.*;
import java.util.List;

// Interface Strategy para diferentes algoritmos de cálculo financeiro
public interface EstrategiaCalculo {
    double calcular(List<Transacao> transacoes);
    String getDescricao();
}