package Interfaces;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import java.time.*;
import java.util.*;
import java.util.List;

public interface Subject {
    void adicionarObservador(Observer observer);
    void removerObservador(Observer observer);
    void notificarObservadores(String evento, Object dados);
    List<Observer> getObservadores();
}