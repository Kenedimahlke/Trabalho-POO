package Testes;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Factory.*;
import Gerenciadores.*;
import Interfaces.*;
import Observers.*;
import Relatorios.*;
import Repositorios.*;
import Strategy.*;
import java.time.*;
import java.util.*;
import java.util.List;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RepositorioMemoriaTest {

    private RepositorioMemoria<String> repositorio;

    @BeforeEach
    public void setUp() {
        repositorio = new RepositorioMemoria<>();
    }

    @Test
    public void testAdicionar() {
        repositorio.adicionar("Item 1");
        List<String> lista = repositorio.listarTodos();
        assertEquals(1, lista.size());
        assertTrue(lista.contains("Item 1"));
    }

    @Test
    public void testAdicionarDuplicado() {
        repositorio.adicionar("Item 1");
        repositorio.adicionar("Item 1");
        assertEquals(1, repositorio.listarTodos().size());
    }

    @Test
    public void testAdicionarNull() {
        repositorio.adicionar(null);
        assertEquals(0, repositorio.listarTodos().size());
    }

    @Test
    public void testRemover() {
        repositorio.adicionar("Item 1");
        repositorio.remover("Item 1");
        assertTrue(repositorio.listarTodos().isEmpty());
    }

    @Test
    public void testLimpar() {
        repositorio.adicionar("Item 1");
        repositorio.adicionar("Item 2");
        repositorio.limpar();
        assertTrue(repositorio.listarTodos().isEmpty());
    }
    
    @Test
    public void testBuscarPorId() {
        // A implementação base retorna null
        assertNull(repositorio.buscarPorId("1"));
    }
}