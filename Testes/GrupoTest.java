package Testes;

import Entidades.*;
import Enums.*;
import Exceptions.*;
import Factory.*;
import Gerenciadores.*;


import Relatorios.*;

import Strategy.*;
import java.time.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GrupoTest {

    private Grupo grupo;
    private UsuarioIndividual admin;
    private UsuarioIndividual membro1;
    private UsuarioIndividual membro2;

    @BeforeEach
    public void setUp() {
        admin = new UsuarioIndividual("Admin", "admin@email.com", "123");
        membro1 = new UsuarioIndividual("Membro1", "membro1@email.com", "123");
        membro2 = new UsuarioIndividual("Membro2", "membro2@email.com", "123");
        grupo = new Grupo("Família", "familia@email.com", admin);
    }

    @Test
    public void testCriacaoGrupo() {
        assertEquals("Família", grupo.getNome());
        assertEquals("familia@email.com", grupo.getEmail());
        assertEquals("GRUPO", grupo.getTipoPerfil());
        assertTrue(grupo.podeCriarLancamento());
    }

    @Test
    public void testAdicionarMembro() {
        boolean adicionou = grupo.adicionarMembro(membro1);
        assertTrue(adicionou);
        assertFalse(grupo.adicionarMembro(membro1)); 
    }

    @Test
    public void testRemoverMembro() {
        grupo.adicionarMembro(membro1);
        boolean removeu = grupo.removerMembro(membro1);
        assertTrue(removeu);
        
        assertFalse(grupo.removerMembro(membro1));
    }

    @Test
    public void testNaoPodeRemoverAdmin() {
        boolean removeu = grupo.removerMembro(admin);
        assertFalse(removeu);
    }

    @Test
    public void testLimiteMembros() {

        for (int i = 0; i < 9; i++) {
            grupo.adicionarMembro(new UsuarioIndividual("User" + i, "user" + i + "@email.com", "123"));
        }
        
        boolean adicionou = grupo.adicionarMembro(membro1);
        assertFalse(adicionou);
    }
    
    @Test
    public void testConstrutorSimplificado() {
        Grupo grupoSimples = new Grupo("Amigos", "Grupo de amigos");
        assertEquals("Amigos", grupoSimples.getNome());
        assertEquals("Amigos@grupo.com", grupoSimples.getEmail());
        assertFalse(grupoSimples.podeCriarLancamento());
        
        grupoSimples.adicionarMembro(membro1);
        assertTrue(grupoSimples.podeCriarLancamento());
    }
}
