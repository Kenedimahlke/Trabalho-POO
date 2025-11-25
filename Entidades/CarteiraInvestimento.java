package Entidades;

import Enums.*;
import Exceptions.*;
import Interfaces.*;
import java.io.Serializable;
import java.time.*;
import java.util.*;

// Classe que representa uma Carteira de Investimentos
// Com diferentes perfis de risco e rentabilidade
public class CarteiraInvestimento implements ContaFinanceira, Serializable {
    private static final long serialVersionUID = 1L;
    
    private String numeroConta;
    private Usuario titular;
    private double saldo;
    private String tipo; // "Conservador", "Moderado", "Arrojado"
    private double rentabilidade;
    private boolean ativa;
    
    // CONSTRUTOR
    public CarteiraInvestimento(String numeroConta, Usuario titular, String tipo) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldo = 0.0;
        this.tipo = tipo;
        this.rentabilidade = calcularRentabilidade(tipo);
        this.ativa = true;
    }
    
    private double calcularRentabilidade(String tipo) {
        return switch (tipo.toLowerCase()) {
            case "conservador" -> 0.5;
            case "moderado" -> 1.0;
            case "arrojado" -> 2.0;
            default -> 0.0;
        };
    }
    
    @Override
    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do aporte deve ser positivo");
        }
        if (!ativa) {
            throw new IllegalStateException("Carteira inativa. Não é possível realizar aportes.");
        }
        this.saldo += valor;
    }
    
    @Override
    public boolean sacar(double valor) throws Exception {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do resgate deve ser positivo");
        }
        if (!ativa) {
            throw new IllegalStateException("Carteira inativa. Não é possível realizar resgates.");
        }
        
        if (valor > saldo) {
            throw new Exception("SaldoInsuficienteException: Saldo insuficiente. Disponível: R$ " 
                    + String.format("%.2f", saldo));
        }
        
        this.saldo -= valor;
        return true;
    }
    
    @Override
    public double consultarSaldo() {
        return this.saldo;
    }
    
    public void aplicarRentabilidade() {
        if (ativa && saldo > 0) {
            double valorRentabilidade = saldo * (rentabilidade / 100);
            saldo += valorRentabilidade;
            System.out.println("Rentabilidade aplicada: R$ " + String.format("%.2f", valorRentabilidade));
        }
    }
    
    @Override
    public String getNumeroConta() {
        return numeroConta;
    }
    
    @Override
    public String getTipoConta() {
        return "CARTEIRA_INVESTIMENTO";
    }
    
    @Override
    public Usuario getTitular() {
        return titular;
    }
    
    @Override
    public boolean isAtiva() {
        return ativa;
    }
    
    @Override
    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
    
    @Override
    public String getExtrato() {
        StringBuilder extrato = new StringBuilder();
        extrato.append("=== EXTRATO CARTEIRA DE INVESTIMENTO ===\n");
        extrato.append("Número: ").append(numeroConta).append("\n");
        extrato.append("Titular: ").append(titular.getNome()).append("\n");
        extrato.append("Tipo: ").append(tipo).append("\n");
        extrato.append("Saldo Investido: R$ ").append(String.format("%.2f", saldo)).append("\n");
        extrato.append("Rentabilidade: ").append(String.format("%.2f", rentabilidade)).append("% a.m.\n");
        extrato.append("Status: ").append(ativa ? "Ativa" : "Inativa").append("\n");
        return extrato.toString();
    }
    
    public String getTipo() { return tipo; }
    public double getRentabilidade() { return rentabilidade; }
}