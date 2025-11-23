package Interfaces;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import java.time.*;
import java.util.*;

// Interface para entidades que realizam cálculos
public interface Calculavel {
    double calcular();
    String getDescricaoCalculo();
}