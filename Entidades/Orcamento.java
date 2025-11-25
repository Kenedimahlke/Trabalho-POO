package Entidades;

import Enums.*;
import Exceptions.*;
import Interfaces.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

// Classe que representa um orçamento para controlar limites por categoria
public class Orcamento implements Serializable, Calculavel, Exportavel {
    private static final long serialVersionUID = 1L;
    private String nome;
    private Categoria categoria;
    private double limiteValor;
    private double valorGasto;
    private YearMonth mesReferencia;
    private Usuario responsavel;
    private boolean alertaEnviado;
    private double percentualAlerta; // Percentual para enviar alerta (ex: 80% = 0.8)
    
    // CONSTRUTOR
    public Orcamento(String nome, Categoria categoria, double limiteValor, 
                     YearMonth mesReferencia, Usuario responsavel) {
        this.nome = nome;
        this.categoria = categoria;
        this.limiteValor = limiteValor;
        this.valorGasto = 0.0;
        this.mesReferencia = mesReferencia;
        this.responsavel = responsavel;
        this.alertaEnviado = false;
        this.percentualAlerta = 0.8; // 80% por padrão
    }
    
    // CONSTRUTOR simplificado (mês atual)
    public Orcamento(String nome, Categoria categoria, double limiteValor, Usuario responsavel) {
        this(nome, categoria, limiteValor, YearMonth.now(), responsavel);
    }
    
    // Adiciona um gasto ao orçamento
    public void adicionarGasto(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }
        this.valorGasto += valor;
    }
    
    // Remove um gasto (caso de estorno)
    public void removerGasto(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor deve ser positivo");
        }
        this.valorGasto = Math.max(0, this.valorGasto - valor);
    }
    
    // Retorna o valor disponível no orçamento
    public double getValorDisponivel() {
        return Math.max(limiteValor - valorGasto, 0);
    }
    
    // Retorna o percentual gasto do orçamento
    public double getPercentualGasto() {
        if (limiteValor == 0) return 0;
        return (valorGasto / limiteValor) * 100;
    }
    
    // Verifica se o orçamento está estourado
    public boolean isEstourado() {
        return valorGasto > limiteValor;
    }
    
    // Verifica se o orçamento está próximo do limite
    public boolean isProximoDoLimite() {
        return getPercentualGasto() >= (percentualAlerta * 100);
    }
    
    // Verifica se deve enviar alerta
    public boolean deveEnviarAlerta() {
        return isProximoDoLimite() && !alertaEnviado;
    }
    
    // Marca que o alerta foi enviado
    public void marcarAlertaEnviado() {
        this.alertaEnviado = true;
    }
    
    // Reseta o alerta (para permitir novo envio se necessário)
    public void resetarAlerta() {
        this.alertaEnviado = false;
    }
    
    // Verifica se o orçamento pertence ao mês atual
    public boolean isMesAtual() {
        return mesReferencia.equals(YearMonth.now());
    }
    
    // Verifica se o orçamento está vencido
    public boolean isVencido() {
        return mesReferencia.isBefore(YearMonth.now());
    }
    
    // Retorna quanto foi ultrapassado (se houver)
    public double getValorUltrapassado() {
        return Math.max(valorGasto - limiteValor, 0);
    }

    public void resetar() {
        this.valorGasto = 0.0;
        this.alertaEnviado = false;
    }

    public String gerarRelatorio() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO DE ORÇAMENTO ===\n");
        sb.append("Nome: ").append(nome).append("\n");
        sb.append("Categoria: ").append(categoria.getNome()).append("\n");
        sb.append("Limite: R$ ").append(String.format("%.2f", limiteValor)).append("\n");
        sb.append("Gasto: R$ ").append(String.format("%.2f", valorGasto)).append("\n");
        sb.append("Percentual: ").append(String.format("%.1f", getPercentualGasto())).append("%\n");
        sb.append("Disponível: R$ ").append(String.format("%.2f", getValorDisponivel())).append("\n");
        sb.append("Status: ").append(isEstourado() ? "ESTOURADO" : "OK").append("\n");
        return sb.toString();
    }
    
    // GETTERS
    public String getNome() { return nome; }
    public Categoria getCategoria() { return categoria; }
    public double getLimiteValor() { return limiteValor; }
    public double getValorGasto() { return valorGasto; }
    public YearMonth getMesReferencia() { return mesReferencia; }
    public Usuario getResponsavel() { return responsavel; }
    public boolean isAlertaEnviado() { return alertaEnviado; }
    public double getPercentualAlerta() { return percentualAlerta; }
    
    // SETTERS
    public void setNome(String nome) { this.nome = nome; }
    public void setLimiteValor(double limiteValor) { 
        if (limiteValor < 0) {
            throw new IllegalArgumentException("Limite deve ser positivo");
        }
        this.limiteValor = limiteValor; 
    }
    public void setPercentualAlerta(double percentualAlerta) {
        if (percentualAlerta < 0 || percentualAlerta > 1) {
            throw new IllegalArgumentException("Percentual deve estar entre 0 e 1");
        }
        this.percentualAlerta = percentualAlerta;
    }

    // IMPLEMENTAÇÃO DE INTERFACES
    @Override
    public double calcular() {
        return getPercentualGasto();
    }

    @Override
    public String getDescricaoCalculo() {
        return "Percentual gasto do orçamento";
    }

    @Override
    public String exportarParaTexto() {
        return gerarRelatorio();
    }

    @Override
    public void salvarEmArquivo(String caminho) {
        try (FileWriter writer = new FileWriter(caminho)) {
            writer.write(exportarParaTexto());
        } catch (IOException e) {
            System.err.println("Erro ao salvar orçamento em arquivo: " + e.getMessage());
        }
    }

    @Override
    public String getFormatoExportacao() {
        return "TEXTO";
    }
}