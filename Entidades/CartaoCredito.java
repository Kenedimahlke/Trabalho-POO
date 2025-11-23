package Entidades;

import Enums.*;
import Exceptions.*;
import Interfaces.*;
import java.io.Serializable;
import java.time.*;
import java.time.LocalDate;
import java.util.*;

// Classe que representa um Cartão de Crédito
public class CartaoCredito implements ContaFinanceira, Serializable {
    private static final long serialVersionUID = 1L;
    
    private String numeroConta;
    private Usuario titular;
    private double limite;
    private double limiteDisponivel;
    private double faturaAtual;
    private LocalDate dataFechamento;
    private LocalDate dataVencimento;
    private boolean ativa;
    
    // CONSTRUTOR
    public CartaoCredito(String numeroConta, Usuario titular, double limite, 
                         LocalDate dataFechamento, LocalDate dataVencimento) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.limite = limite;
        this.limiteDisponivel = limite;
        this.faturaAtual = 0.0;
        this.dataFechamento = dataFechamento;
        this.dataVencimento = dataVencimento;
        this.ativa = true;
    }
    
    @Override
    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor do pagamento deve ser positivo");
        }
        if (!ativa) {
            throw new IllegalStateException("Cartão inativo. Não é possível realizar pagamentos.");
        }
        
        // Pagamento da fatura libera o limite
        if (valor <= faturaAtual) {
            faturaAtual -= valor;
            limiteDisponivel += valor;
        } else {
            // Pagamento maior que a fatura
            limiteDisponivel += faturaAtual;
            faturaAtual = 0.0;
        }
    }
    
    @Override
    public boolean sacar(double valor) throws Exception {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor da compra deve ser positivo");
        }
        if (!ativa) {
            throw new IllegalStateException("Cartão inativo. Não é possível realizar compras.");
        }
        
        if (valor > limiteDisponivel) {
            throw new Exception("LimiteExcedidoException: Limite do cartão excedido! Disponível: R$ " 
                    + String.format("%.2f", limiteDisponivel));
        }
        
        limiteDisponivel -= valor;
        faturaAtual += valor;
        return true;
    }
    
    @Override
    public double consultarSaldo() {
        return limiteDisponivel;
    }
    
    public double getFaturaAtual() {
        return faturaAtual;
    }
    
    public void fecharFatura() {
        // Lógica para fechar a fatura mensal
        System.out.println("Fatura fechada no valor de R$ " + String.format("%.2f", faturaAtual));
    }
    
    @Override
    public String getNumeroConta() {
        return numeroConta;
    }
    
    @Override
    public String getTipoConta() {
        return "CARTAO_CREDITO";
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
        extrato.append("=== EXTRATO CARTÃO DE CRÉDITO ===\n");
        extrato.append("Número: ").append(numeroConta).append("\n");
        extrato.append("Titular: ").append(titular.getNome()).append("\n");
        extrato.append("Limite Total: R$ ").append(String.format("%.2f", limite)).append("\n");
        extrato.append("Limite Disponível: R$ ").append(String.format("%.2f", limiteDisponivel)).append("\n");
        extrato.append("Fatura Atual: R$ ").append(String.format("%.2f", faturaAtual)).append("\n");
        extrato.append("Data Fechamento: ").append(dataFechamento).append("\n");
        extrato.append("Data Vencimento: ").append(dataVencimento).append("\n");
        extrato.append("Status: ").append(ativa ? "Ativa" : "Inativa").append("\n");
        return extrato.toString();
    }
    
    // GETTERS adicionais
    public double getLimite() { return limite; }
    public double getLimiteDisponivel() { return limiteDisponivel; }
    public LocalDate getDataFechamento() { return dataFechamento; }
    public LocalDate getDataVencimento() { return dataVencimento; }
}