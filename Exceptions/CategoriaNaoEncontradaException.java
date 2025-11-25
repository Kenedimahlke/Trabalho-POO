package Exceptions;

import Entidades.*;
import Enums.*;
import Interfaces.*;
import java.time.*;
import java.util.*;

public class CategoriaNaoEncontradaException extends Exception {
    public CategoriaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
    
    public CategoriaNaoEncontradaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}