package Entidades;

import Enums.*;
import Exceptions.*;
import Interfaces.*;
import java.io.Serializable;
import java.time.*;
import java.util.*;
import java.util.UUID;

public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String nome;
    private String email;

// CONSTRUTOR
    protected Usuario(String nome, String email) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.email = email;
    }

// GETTERS
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

// SETTERS
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

// MÉTODOS ABSTRATOS
    public abstract boolean podeCriarLancamento();

    public abstract String getTipoPerfil();
}