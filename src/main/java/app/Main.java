package app;

import java.util.Scanner;
import negocio.RedSocial;

public class Main {
    private static final Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        RedSocial red = new RedSocial();
        cargarFase1(red);
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Opción: ");
            switch (opcion) {
                case 1 -> ejecutarSecuencia();
                case 2 -> System.out.println(red.mostrarRed());
                case 3 -> resultado(red.registrarPersona(leer("Usuario: "), leer("Nombre: ")));
                case 4 -> resultado(red.eliminarPersona(leer("Usuario a eliminar: ")));
                case 5 -> resultado(red.agregarAmistad(leer("Usuario A: "), leer("Usuario B: ")));
                case 6 -> resultado(red.seguir(leer("Quién sigue: "), leer("A quién sigue: ")));
                case 7 -> resultado(red.eliminarAmistad(leer("Usuario A: "), leer("Usuario B: ")));
                case 8 -> resultado(red.dejarDeSeguir(leer("Quién deja de seguir: "), leer("A quién: ")));
                case 9 -> imprimirLista("Amigos", red.listarAmigos(leer("Usuario: ")));
                case 10 -> imprimirLista("Seguidos", red.listarSeguidos(leer("Usuario: ")));
                case 11 -> imprimirLista("Seguidores", red.listarSeguidores(leer("Usuario: ")));
                case 12 -> imprimirConteo(red.contarAmigos(leer("Usuario: ")));
                case 0 -> System.out.println("Fin del programa.");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== CONEXIÓN AMBATO - APE 4 FASE 2 =====");
        System.out.println(" 1. Ejecutar secuencia de comprobación (Tabla 3)");
        System.out.println(" 2. Mostrar red");
        System.out.println(" 3. Registrar persona");
        System.out.println(" 4. Eliminar persona");
        System.out.println(" 5. Agregar amistad");
        System.out.println(" 6. Seguir");
        System.out.println(" 7. Eliminar amistad");
        System.out.println(" 8. Dejar de seguir");
        System.out.println(" 9. Listar amigos");
        System.out.println("10. Listar seguidos");
        System.out.println("11. Listar seguidores");
        System.out.println("12. Contar amigos");
        System.out.println(" 0. Salir");
    }

    static void cargarFase1(RedSocial red) {
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

    static void ejecutarSecuencia() {
        RedSocial red = new RedSocial();

        titulo("ANEXO 1 - Paso 1: registrarPersona A01-A06, cinco amistades y mostrarRed");
        String[][] personas = {
                {"A01", "Sofía"}, {"A02", "Mateo"}, {"A03", "Valeria"},
                {"A04", "Andrés"}, {"A05", "Camila"}, {"A06", "Diego"}
        };
        for (String[] p : personas) {
            System.out.println("registrarPersona(" + p[0] + ", " + p[1] + ") -> " + red.registrarPersona(p[0], p[1]));
        }
        String[][] amistades = {{"A01", "A02"}, {"A02", "A03"}, {"A03", "A04"}, {"A04", "A01"}, {"A04", "A05"}};
        for (String[] a : amistades) {
            System.out.println("agregarAmistad(" + a[0] + ", " + a[1] + ") -> " + red.agregarAmistad(a[0], a[1]));
        }
        System.out.println(red.mostrarRed());

        titulo("ANEXO 2 - Pasos 2 y 3: seguimientos");
        System.out.println("seguir(A02, A01) -> " + red.seguir("A02", "A01"));
        System.out.println("seguir(A06, A01) -> " + red.seguir("A06", "A01"));
        System.out.println("Seguidores de Sofía (A01): " + lista(red.listarSeguidores("A01")));
        System.out.println(red.mostrarRed());

        titulo("ANEXO 4 - Paso 4: listarAmigos, listarSeguidos, listarSeguidores y contarAmigos");
        System.out.println("listarAmigos(A04)      -> " + lista(red.listarAmigos("A04")));
        System.out.println("contarAmigos(A04)      -> " + red.contarAmigos("A04"));
        System.out.println("listarSeguidos(A02)    -> " + lista(red.listarSeguidos("A02")));
        System.out.println("listarSeguidos(A06)    -> " + lista(red.listarSeguidos("A06")));
        System.out.println("listarSeguidores(A01)  -> " + lista(red.listarSeguidores("A01")));
        System.out.println("contarAmigos(A06)      -> " + red.contarAmigos("A06"));
        System.out.println("listarAmigos(X99)      -> " + lista(red.listarAmigos("X99")));
        System.out.println("contarAmigos(X99)      -> " + red.contarAmigos("X99"));

        titulo("ANEXO 3 - Paso 5: eliminarAmistad(A04, A05)");
        System.out.println("eliminarAmistad(A04, A05) -> " + red.eliminarAmistad("A04", "A05"));
        System.out.println("listarAmigos(A04) -> " + lista(red.listarAmigos("A04")));
        System.out.println("contarAmigos(A05) -> " + red.contarAmigos("A05"));

        titulo("ANEXO 3 - Paso 6: eliminarPersona(A01)");
        System.out.println("eliminarPersona(A01) -> " + red.eliminarPersona("A01"));
        System.out.println("existePersona(A01)   -> " + red.existePersona("A01"));
        System.out.println("listarAmigos(A02)    -> " + lista(red.listarAmigos("A02")));
        System.out.println("listarSeguidos(A06)  -> " + lista(red.listarSeguidos("A06")));
        System.out.println(red.mostrarRed());

        titulo("ANEXO 3 - Pasos 7 y 8: validaciones");
        System.out.println("registrarPersona(A02, Martín) -> " + red.registrarPersona("A02", "Martín") + "  (usuario duplicado)");
        System.out.println("registrarPersona(a02, Martín) -> " + red.registrarPersona("a02", "Martín") + "  (A02 y a02 son la misma cuenta)");
        System.out.println("agregarAmistad(A03, A03)      -> " + red.agregarAmistad("A03", "A03") + "  (lazo no permitido)");
        System.out.println("agregarAmistad(A02, A03)      -> " + red.agregarAmistad("A02", "A03") + "  (amistad duplicada)");
        System.out.println("registrarPersona(\"\", Ana)     -> " + red.registrarPersona("", "Ana") + "  (usuario vacío)");
        System.out.println("registrarPersona(A 07, Ana)   -> " + red.registrarPersona("A 07", "Ana") + "  (usuario con espacio)");
        System.out.println("registrarPersona(A,07, Ana)   -> " + red.registrarPersona("A,07", "Ana") + "  (usuario con coma)");
    }

    private static String lista(String texto) {
        if (texto == null) return "null";
        return texto.isEmpty() ? "(vacía)" : texto;
    }

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("------------------------------------------------------------");
        System.out.println(texto);
        System.out.println("------------------------------------------------------------");
    }

    private static void resultado(boolean ok) {
        System.out.println(ok ? "Operación realizada." : "Operación rechazada (revise usuario, duplicados o lazos).");
    }

    private static void imprimirLista(String etiqueta, String lista) {
        if (lista == null) System.out.println("El usuario no existe.");
        else System.out.println(etiqueta + ": " + (lista.isEmpty() ? "ninguno" : lista));
    }

    private static void imprimirConteo(int total) {
        if (total == -1) System.out.println("El usuario no existe.");
        else System.out.println("Amigos: " + total);
    }

    private static String leer(String mensaje) {
        System.out.print(mensaje);
        return entrada.nextLine();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leer(mensaje).trim());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número.");
            }
        }
    }
}
