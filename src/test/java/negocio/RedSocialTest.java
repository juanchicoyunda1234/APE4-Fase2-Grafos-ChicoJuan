package negocio;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RedSocialTest {
    private RedSocial red;

    @BeforeEach
    void prepararFase1() {
        red = new RedSocial();
        red.registrarPersona("A01", "Sofía");
        red.registrarPersona("A02", "Mateo");
        red.registrarPersona("A03", "Valeria");
        red.registrarPersona("A04", "Andrés");
        red.registrarPersona("A05", "Camila");
        red.registrarPersona("A06", "Diego");
        red.agregarAmistad("A01", "A02");
        red.agregarAmistad("A02", "A03");
        red.agregarAmistad("A03", "A04");
        red.agregarAmistad("A04", "A01");
        red.agregarAmistad("A04", "A05");
    }

    @Test
    @DisplayName("Paso 1: 6 usuarios, 5 amistades y grados 2,2,2,3,1,0")
    void estadoInicial() {
        assertEquals(6, red.contarPersonas());
        assertEquals(5, red.contarAmistades());
        assertEquals(10, red.contarAristasInternas());
        assertEquals(2, red.contarAmigos("A01"));
        assertEquals(2, red.contarAmigos("A02"));
        assertEquals(2, red.contarAmigos("A03"));
        assertEquals(3, red.contarAmigos("A04"));
        assertEquals(1, red.contarAmigos("A05"));
        assertEquals(0, red.contarAmigos("A06"));
    }

    @Test
    @DisplayName("registrarPersona rechaza vacío, null, espacios, comas, nombre vacío y duplicados")
    void validacionesRegistro() {
        assertFalse(red.registrarPersona("", "Ana"));
        assertFalse(red.registrarPersona("   ", "Ana"));
        assertFalse(red.registrarPersona(null, "Ana"));
        assertFalse(red.registrarPersona("A 07", "Ana"));
        assertFalse(red.registrarPersona("A,07", "Ana"));
        assertFalse(red.registrarPersona("A07", ""));
        assertFalse(red.registrarPersona("A07", null));
        assertFalse(red.registrarPersona("A02", "Otro"));
        assertTrue(red.registrarPersona("A07", "Ana"));
        assertEquals(7, red.contarPersonas());
    }

    @Test
    @DisplayName("Paso 7: A02 y a02 son la misma cuenta")
    void usuarioNoDistingueMayusculas() {
        assertFalse(red.registrarPersona("a02", "Otro"));
        assertFalse(red.registrarPersona("  A02  ", "Otro"));
        assertEquals(red.listarAmigos("A04"), red.listarAmigos("a04"));
        assertEquals(3, red.contarAmigos(" a04 "));
    }

    @Test
    @DisplayName("Paso 8: agregarAmistad rechaza lazos, duplicados y usuarios inexistentes")
    void validacionesAmistad() {
        assertFalse(red.agregarAmistad("A03", "A03"));
        assertFalse(red.agregarAmistad("A01", "A02"));
        assertFalse(red.agregarAmistad("A02", "A01"));
        assertFalse(red.agregarAmistad("A01", "X99"));
        assertEquals(5, red.contarAmistades());
    }

    @Test
    @DisplayName("La amistad es recíproca: crea dos aristas")
    void amistadReciproca() {
        assertTrue(red.agregarAmistad("A06", "A05"));
        assertTrue(red.listarAmigos("A06").contains("Camila"));
        assertTrue(red.listarAmigos("A05").contains("Diego"));
        assertEquals(12, red.contarAristasInternas());
    }

    @Test
    @DisplayName("Pasos 2 y 3: seguir es dirigido y crea una sola arista")
    void seguirDirigido() {
        assertTrue(red.seguir("A02", "A01"));
        assertTrue(red.seguir("A06", "A01"));
        assertEquals("Sofía", red.listarSeguidos("A02"));
        assertEquals("Sofía", red.listarSeguidos("A06"));
        assertEquals("", red.listarSeguidos("A01"));
        assertEquals("Diego, Mateo", red.listarSeguidores("A01"));
        assertEquals(2, red.contarSeguimientos());
        assertEquals(12, red.contarAristasInternas());
    }

    @Test
    @DisplayName("seguir rechaza lazos, duplicados e inexistentes")
    void validacionesSeguir() {
        assertTrue(red.seguir("A02", "A01"));
        assertFalse(red.seguir("A02", "A01"));
        assertFalse(red.seguir("A02", "A02"));
        assertFalse(red.seguir("A02", "X99"));
        assertEquals(1, red.contarSeguimientos());
    }

    @Test
    @DisplayName("Amistad y seguimiento entre las mismas personas pueden coexistir")
    void amistadYSeguirCoexisten() {
        assertTrue(red.seguir("A01", "A02"));
        assertEquals(2, red.contarAmigos("A01"));
        assertEquals("Mateo", red.listarSeguidos("A01"));
    }

    @Test
    @DisplayName("Paso 4: listarAmigos(A04) devuelve Valeria, Sofía y Camila (inserción al inicio invierte el orden)")
    void listarAmigosAndres() {
        assertEquals("Camila, Sofía, Valeria", red.listarAmigos("A04"));
        assertEquals("", red.listarAmigos("A06"));
    }

    @Test
    @DisplayName("Usuario inexistente: listar devuelve null y contarAmigos devuelve -1")
    void usuarioInexistente() {
        assertNull(red.listarAmigos("X99"));
        assertNull(red.listarSeguidos("X99"));
        assertNull(red.listarSeguidores("X99"));
        assertEquals(-1, red.contarAmigos("X99"));
        assertEquals(-1, red.contarAmigos(null));
    }

    @Test
    @DisplayName("Paso 5: eliminarAmistad(A04, A05) quita ambas aristas")
    void eliminarAmistad() {
        assertTrue(red.eliminarAmistad("A04", "A05"));
        assertEquals(2, red.contarAmigos("A04"));
        assertEquals(0, red.contarAmigos("A05"));
        assertFalse(red.listarAmigos("A04").contains("Camila"));
        assertFalse(red.eliminarAmistad("A04", "A05"));
        assertEquals(8, red.contarAristasInternas());
    }

    @Test
    @DisplayName("dejarDeSeguir quita solo la arista SEGUIR")
    void dejarDeSeguir() {
        red.seguir("A01", "A02");
        assertTrue(red.dejarDeSeguir("A01", "A02"));
        assertEquals("", red.listarSeguidos("A01"));
        assertEquals(2, red.contarAmigos("A01"));
        assertFalse(red.dejarDeSeguir("A01", "A02"));
    }

    @Test
    @DisplayName("Paso 6: eliminarPersona(A01) borra a Sofía y todas las referencias hacia ella")
    void eliminarPersona() {
        red.seguir("A02", "A01");
        red.seguir("A06", "A01");
        assertTrue(red.eliminarPersona("A01"));
        assertFalse(red.existePersona("A01"));
        assertEquals(5, red.contarPersonas());
        assertEquals(3, red.contarAmistades());
        assertEquals(0, red.contarSeguimientos());
        assertFalse(red.listarAmigos("A02").contains("Sofía"));
        assertFalse(red.listarAmigos("A04").contains("Sofía"));
        assertEquals("", red.listarSeguidos("A06"));
        assertFalse(red.eliminarPersona("A01"));
    }

    @Test
    @DisplayName("eliminarPersona funciona con la cabeza, el medio y el último vértice")
    void eliminarEnDistintasPosiciones() {
        assertTrue(red.eliminarPersona("A06"));
        assertTrue(red.eliminarPersona("A03"));
        assertTrue(red.eliminarPersona("A01"));
        assertEquals(3, red.contarPersonas());
        assertEquals(1, red.contarAmistades());
        assertEquals("Camila", red.listarAmigos("A04"));
    }

    @Test
    @DisplayName("Red vacía")
    void redVacia() {
        RedSocial vacia = new RedSocial();
        assertEquals(0, vacia.contarPersonas());
        assertFalse(vacia.eliminarPersona("A01"));
        assertEquals("La red está vacía.", vacia.mostrarRed());
    }
}
