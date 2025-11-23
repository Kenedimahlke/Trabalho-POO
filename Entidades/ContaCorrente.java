package Entidades;

import Enums.*;
import Exceptions.*;
import Interfaces.*;
import java.io.Serializable;
import java.time.*;
import java.util.*;

// Classe que representa uma Conta Corrente tradicional
// Possui saldo e limite de cheque especial
public class ContaCorrente implements ContaFinanceira, Serializable {
    private static final long serialVersionUID = 1L;
    
    private String numeroConta;
    private Usuario titular;
    private double saldo;
    private double limiteChequeEspecial;
    private boolean ativa;
    private double tarifaMensal;
    
    // CONSTRUTOR
    public ContaCorrente(String numeroConta, Usuario titular, double limiteChequeEspecial) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldo = 0.0;
        this.limiteChequeEspecial = limiteChequeEspecial;
        this.ativa = true;
        this.tarifaMensal = 15.90;
    }
    
    @Override
    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do depósito deve ser positivo");
        }
        if (!ativa) {
            throw new IllegalStateException("Conta inativa. Não é possível realizar depósitos.");
        }
        this.saldo += valor;
    }
    
    @Override
    public boolean sacar(double valor) throws Exception {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do saque deve ser positivo");
        }
        if (!ativa) {
            throw new IllegalStateException("Conta inativa. Não é possível realizar saques.");
        }
        
        // Verifica se há saldo disponível (saldo + cheque especial)
        double saldoDisponivel = saldo + limiteChequeEspecial;
        
        if (valor > saldoDisponivel) {
            throw new Exception("SaldoInsuficienteException: Saldo insuficiente. Disponível: R$ " 
                    + String.format("%.2f", saldoDisponivel));
        }
        
        this.saldo -= valor;
        return true;
    }
    
    @Override
    public double consultarSaldo() {
        return this.saldo;
    }
    
    // Retorna o saldo disponível total (saldo + limite)
    public double getSaldoDisponivel() {
        return this.saldo + this.limiteChequeEspecial;
    }
    
    // Cobra a tarifa mensal da conta
    public void cobrarTarifaMensal() throws Exception {
        if (ativa) {
            sacar(tarifaMensal);
        }
    }
    
    @Override
    public String getNumeroConta() {
        return this.numeroConta;
    }
    
    @Override
    public String getTipoConta() {
        return "CONTA_CORRENTE";
    }
    
    @Override
    public Usuario getTitular() {
        return this.titular;
    }
    
    @Override
    public boolean isAtiva() {
        return this.ativa;
    }
    
    @Override
    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
    
    @Override
    public String getExtrato() {
        StringBuilder extrato = new StringBuilder();
        extrato.append("=== EXTRATO CONTA CORRENTE ===\n");
        extrato.append("Número: ").append(numeroConta).append("\n");
        extrato.append("Titular: ").append(titular.getNome()).append("\n");
        extrato.append("Saldo: R$ ").append(String.format("%.2f", saldo)).append("\n");
        extrato.append("Limite Cheque Especial: R$ ").append(String.format("%.2f", limiteChequeEspecial)).append("\n");
        extrato.append("Saldo Disponível: R$ ").append(String.format("%.2f", getSaldoDisponivel())).append("\n");
        extrato.append("Status: ").append(ativa ? "ATIVA" : "INATIVA").append("\n");
        extrato.append("Tarifa Mensal: R$ ").append(String.format("%.2f", tarifaMensal)).append("\n");
        extrato.append("==============================");
        return extrato.toString();
    }
    
    // Getters e Setters adicionais
    public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }
    
    public void setLimiteChequeEspecial(double limiteChequeEspecial) {
        if (limiteChequeEspecial >= 0) {
            this.limiteChequeEspecial = limiteChequeEspecial;
        }
    }
    
    public double getTarifaMensal() {
        return tarifaMensal;
    }
    
    public void setTarifaMensal(double tarifaMensal) {
        if (tarifaMensal >= 0) {
            this.tarifaMensal = tarifaMensal;
        }
    }

    public void desativar() {
        this.ativa = false;
    }

    public void reativar() {
        this.ativa = true;
    }
    
    // Verifica se a conta está usando o cheque especial
    public boolean estaUsandoChequeEspecial() {
        return saldo < 0;
    }
}