package Enums;

import Entidades.*;
import Exceptions.*;
import Interfaces.*;
import java.time.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

// Categorias de despesas e receitas com subcategorias
public enum Categoria {
    // Despesas
    ALIMENTACAO("Alimentação", "DESPESA", new String[]{"Supermercado", "Restaurante", "Delivery", "Lanche"}),
    TRANSPORTE("Transporte", "DESPESA", new String[]{"Combustível", "Uber/Taxi", "Transporte Público", "Manutenção"}),
    MORADIA("Moradia", "DESPESA", new String[]{"Aluguel", "Condomínio", "Água", "Luz", "Internet", "Gás"}),
    SAUDE("Saúde", "DESPESA", new String[]{"Médico", "Medicamentos", "Plano de Saúde", "Academia"}),
    LAZER("Lazer", "DESPESA", new String[]{"Cinema", "Viagem", "Shopping", "Streaming", "Hobbies"}),
    
    // Receitas
    SALARIO("Salário", "RECEITA", new String[]{"Salário Fixo", "Hora Extra", "Bônus", "Comissão"}),
    FREELANCE("Freelance", "RECEITA", new String[]{"Projeto", "Consultoria", "Serviço Pontual"}),
    OUTROS("Outros", "RECEITA", new String[]{"Presente", "Venda", "Reembolso", "Investimento"});
    
    private final String nome;
    private final String tipo;
    private final List<String> subcategorias;
    
    Categoria(String nome, String tipo, String[] subcategorias) {
        this.nome = nome;
        this.tipo = tipo;
        this.subcategorias = new ArrayList<>();
        for (String sub : subcategorias) {
            this.subcategorias.add(sub);
        }
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public List<String> getSubcategorias() {
        return new ArrayList<>(subcategorias);
    }
    
    public boolean temSubcategoria(String subcategoria) {
        return subcategorias.stream()
            .anyMatch(sub -> sub.equalsIgnoreCase(subcategoria));
    }
    
    public boolean isDespesa() {
        return tipo.equals("DESPESA");
    }
    
    public boolean isReceita() {
        return tipo.equals("RECEITA");
    }
    
    public String listarSubcategorias() {
        return String.join(", ", subcategorias);
    }
}