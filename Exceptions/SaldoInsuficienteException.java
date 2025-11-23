package Exceptions;

import Entidades.*;
import Enums.*;
import Interfaces.*;
import java.time.*;
import java.util.*;

// Exceção lançada quando não há saldo suficiente para realizar uma operação
public class SaldoInsuficienteException extends Exception {
    
    private double saldoDisponivel;
    private double valorSolicitado;
    
    public SaldoInsuficienteException() {
        super("Saldo insuficiente para realizar a operação.");
    }
    
    public SaldoInsuficienteException(String mensagem) {
        super(mensagem);
    }
    
    public SaldoInsuficienteException(String mensagem, double saldoDisponivel, double valorSolicitado) {
        super(mensagem);
        this.saldoDisponivel = saldoDisponivel;
        this.valorSolicitado = valorSolicitado;
    }
    
    public double getSaldoDisponivel() {
        return saldoDisponivel;
    }
    
    public double getValorSolicitado() {
        return valorSolicitado;
    }
    
    public double getValorFaltante() {
        return valorSolicitado - saldoDisponivel;
    }
}