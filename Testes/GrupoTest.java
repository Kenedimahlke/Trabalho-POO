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
        // O método contemMembro é privado. Vou testar indiretamente tentando adicionar de novo.
        assertFalse(grupo.adicionarMembro(membro1)); // Não deve adicionar duplicado
    }

    @Test
    public void testRemoverMembro() {
        grupo.adicionarMembro(membro1);
        boolean removeu = grupo.removerMembro(membro1);
        assertTrue(removeu);
        
        // Tentar remover de novo
        assertFalse(grupo.removerMembro(membro1));
    }

    @Test
    public void testNaoPodeRemoverAdmin() {
        boolean removeu = grupo.removerMembro(admin);
        assertFalse(removeu);
    }

    @Test
    public void testLimiteMembros() {
        // O limite padrão é 10. Já tem 1 (admin).
        // Adicionar mais 9
        for (int i = 0; i < 9; i++) {
            grupo.adicionarMembro(new UsuarioIndividual("User" + i, "user" + i + "@email.com", "123"));
        }
        
        // Tentar adicionar o 11º membro (total)
        boolean adicionou = grupo.adicionarMembro(membro1);
        assertFalse(adicionou);
    }
    
    @Test
    public void testConstrutorSimplificado() {
        Grupo grupoSimples = new Grupo("Amigos", "Grupo de amigos");
        assertEquals("Amigos", grupoSimples.getNome());
        assertEquals("Amigos@grupo.com", grupoSimples.getEmail());
        // Admin é null inicialmente
        assertFalse(grupoSimples.podeCriarLancamento()); // Sem membros ativos
        
        grupoSimples.adicionarMembro(membro1);
        assertTrue(grupoSimples.podeCriarLancamento());
    }
}