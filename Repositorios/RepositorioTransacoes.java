package Repositorios;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Interfaces.*;
import java.time.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class RepositorioTransacoes extends RepositorioMemoria<Transacao> {
    
    @Override
    public Transacao buscarPorId(String id) {
        // Transação não tem ID único no momento, mas pode ser adicionado
        return null;
    }
    
    public List<Transacao> buscarPorConta(ContaFinanceira conta) {
        return listarTodos().stream()
            .filter(t -> t.getConta().equals(conta))
            .toList();
    }
    
    public List<Transacao> buscarPorCategoria(Categoria categoria) {
        return listarTodos().stream()
            .filter(t -> t.getCategoria() == categoria)
            .toList();
    }
    
    public List<Transacao> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return listarTodos().stream()
            .filter(t -> !t.getData().isBefore(inicio) && !t.getData().isAfter(fim))
            .toList();
    }
    
    public List<Transacao> buscarPorTipo(TipoTransacao tipo) {
        return listarTodos().stream()
            .filter(t -> t.getTipo() == tipo)
            .toList();
    }
    
    public double calcularTotalReceitas() {
        return listarTodos().stream()
            .filter(t -> t.getTipo() == TipoTransacao.RECEITA)
            .mapToDouble(Transacao::getValor)
            .sum();
    }
    
    public double calcularTotalDespesas() {
        return listarTodos().stream()
            .filter(t -> t.getTipo() == TipoTransacao.DESPESA)
            .mapToDouble(Transacao::getValor)
            .sum();
    }
}