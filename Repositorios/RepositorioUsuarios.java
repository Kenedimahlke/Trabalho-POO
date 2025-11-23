package Repositorios;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Interfaces.*;
import java.time.*;
import java.util.*;
import java.util.List;

public class RepositorioUsuarios extends RepositorioMemoria<Usuario> {
    
    @Override
    public Usuario buscarPorId(String id) {
        return listarTodos().stream()
            .filter(u -> u.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    public Usuario buscarPorEmail(String email) {
        return listarTodos().stream()
            .filter(u -> u.getEmail().equalsIgnoreCase(email))
            .findFirst()
            .orElse(null);
    }
    
    public Usuario buscarPorNome(String nome) {
        return listarTodos().stream()
            .filter(u -> u.getNome().equalsIgnoreCase(nome))
            .findFirst()
            .orElse(null);
    }
}