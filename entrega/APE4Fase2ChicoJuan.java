import java.util.Scanner;


public class APE4Fase2ChicoJuan {
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

    private static void ejecutarSecuencia() {
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

class RedSocial {
    private Vertice cabeza;

    public RedSocial() {
        this.cabeza = null;
    }

    private String normalizar(String usuario) {
        if (usuario == null) return null;
        return usuario.trim().toLowerCase();
    }

    private boolean esUsuarioValido(String clave) {
        if (clave == null || clave.isEmpty()) return false;
        for (int i = 0; i < clave.length(); i++) {
            char c = clave.charAt(i);
            if (Character.isWhitespace(c) || c == ',') return false;
        }
        return true;
    }

    private Vertice buscar(String usuario) {
        String clave = normalizar(usuario);
        if (clave == null) return null;
        for (Vertice v = cabeza; v != null; v = v.getSig()) {
            if (v.getPersona().getUsuario().equals(clave)) return v;
        }
        return null;
    }

    private boolean existe(Vertice origen, Vertice destino, TipoRelacion tipo) {
        for (Arista a = origen.getAristas(); a != null; a = a.getSig()) {
            if (a.getDestino() == destino && a.getTipo() == tipo) return true;
        }
        return false;
    }

    private void insertar(Vertice origen, Vertice destino, TipoRelacion tipo) {
        Arista nueva = new Arista(destino, tipo);
        nueva.setSig(origen.getAristas());
        origen.setAristas(nueva);
    }

    private boolean quitar(Vertice origen, Vertice destino, TipoRelacion tipo) {
        Arista anterior = null;
        Arista actual = origen.getAristas();
        while (actual != null) {
            if (actual.getDestino() == destino && actual.getTipo() == tipo) {
                if (anterior == null) origen.setAristas(actual.getSig());
                else anterior.setSig(actual.getSig());
                return true;
            }
            anterior = actual;
            actual = actual.getSig();
        }
        return false;
    }

    private void quitarHacia(Vertice origen, Vertice destino) {
        Arista anterior = null;
        Arista actual = origen.getAristas();
        while (actual != null) {
            if (actual.getDestino() == destino) {
                if (anterior == null) origen.setAristas(actual.getSig());
                else anterior.setSig(actual.getSig());
            } else {
                anterior = actual;
            }
            actual = actual.getSig();
        }
    }

    public boolean registrarPersona(String usuario, String nombre) {
        String clave = normalizar(usuario);
        if (!esUsuarioValido(clave)) return false;
        if (nombre == null || nombre.trim().isEmpty()) return false;
        if (buscar(clave) != null) return false;
        Vertice nuevo = new Vertice(new Persona(clave, nombre.trim()));
        nuevo.setSig(cabeza);
        cabeza = nuevo;
        return true;
    }

    public boolean eliminarPersona(String usuario) {
        Vertice objetivo = buscar(usuario);
        if (objetivo == null) return false;
        for (Vertice w = cabeza; w != null; w = w.getSig()) {
            quitarHacia(w, objetivo);
        }
        Vertice anterior = null;
        Vertice actual = cabeza;
        while (actual != objetivo) {
            anterior = actual;
            actual = actual.getSig();
        }
        if (anterior == null) cabeza = objetivo.getSig();
        else anterior.setSig(objetivo.getSig());
        return true;
    }

    public boolean agregarAmistad(String a, String b) {
        Vertice va = buscar(a);
        Vertice vb = buscar(b);
        if (va == null || vb == null || va == vb || existe(va, vb, TipoRelacion.AMISTAD)) return false;
        insertar(va, vb, TipoRelacion.AMISTAD);
        insertar(vb, va, TipoRelacion.AMISTAD);
        return true;
    }

    public boolean seguir(String a, String b) {
        Vertice va = buscar(a);
        Vertice vb = buscar(b);
        if (va == null || vb == null || va == vb || existe(va, vb, TipoRelacion.SEGUIR)) return false;
        insertar(va, vb, TipoRelacion.SEGUIR);
        return true;
    }

    public boolean eliminarAmistad(String a, String b) {
        Vertice va = buscar(a);
        Vertice vb = buscar(b);
        if (va == null || vb == null || va == vb || !existe(va, vb, TipoRelacion.AMISTAD)) return false;
        quitar(va, vb, TipoRelacion.AMISTAD);
        quitar(vb, va, TipoRelacion.AMISTAD);
        return true;
    }

    public boolean dejarDeSeguir(String a, String b) {
        Vertice va = buscar(a);
        Vertice vb = buscar(b);
        if (va == null || vb == null || va == vb) return false;
        return quitar(va, vb, TipoRelacion.SEGUIR);
    }

    private String listarSalientes(String usuario, TipoRelacion tipo) {
        Vertice v = buscar(usuario);
        if (v == null) return null;
        StringBuilder sb = new StringBuilder();
        for (Arista a = v.getAristas(); a != null; a = a.getSig()) {
            if (a.getTipo() == tipo) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(a.getDestino().getPersona().getNombre());
            }
        }
        return sb.toString();
    }

    public String listarAmigos(String usuario) {
        return listarSalientes(usuario, TipoRelacion.AMISTAD);
    }

    public String listarSeguidos(String usuario) {
        return listarSalientes(usuario, TipoRelacion.SEGUIR);
    }

    public String listarSeguidores(String usuario) {
        Vertice objetivo = buscar(usuario);
        if (objetivo == null) return null;
        StringBuilder sb = new StringBuilder();
        for (Vertice w = cabeza; w != null; w = w.getSig()) {
            if (existe(w, objetivo, TipoRelacion.SEGUIR)) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(w.getPersona().getNombre());
            }
        }
        return sb.toString();
    }

    public int contarAmigos(String usuario) {
        Vertice v = buscar(usuario);
        if (v == null) return -1;
        int total = 0;
        for (Arista a = v.getAristas(); a != null; a = a.getSig()) {
            if (a.getTipo() == TipoRelacion.AMISTAD) total++;
        }
        return total;
    }

    public boolean existePersona(String usuario) {
        return buscar(usuario) != null;
    }

    public int contarPersonas() {
        int total = 0;
        for (Vertice v = cabeza; v != null; v = v.getSig()) total++;
        return total;
    }

    private int contarAristas(TipoRelacion tipo) {
        int total = 0;
        for (Vertice v = cabeza; v != null; v = v.getSig()) {
            for (Arista a = v.getAristas(); a != null; a = a.getSig()) {
                if (a.getTipo() == tipo) total++;
            }
        }
        return total;
    }

    public int contarAmistades() {
        return contarAristas(TipoRelacion.AMISTAD) / 2;
    }

    public int contarSeguimientos() {
        return contarAristas(TipoRelacion.SEGUIR);
    }

    public int contarAristasInternas() {
        return contarAristas(TipoRelacion.AMISTAD) + contarAristas(TipoRelacion.SEGUIR);
    }

    public String mostrarRed() {
        if (cabeza == null) return "La red está vacía.";
        StringBuilder sb = new StringBuilder();
        for (Vertice v = cabeza; v != null; v = v.getSig()) {
            String amigos = listarAmigos(v.getPersona().getUsuario());
            String seguidos = listarSeguidos(v.getPersona().getUsuario());
            sb.append(String.format("%-16s grado %d | Amigos: %-26s | Sigue a: %s%n",
                    v.getPersona(),
                    contarAmigos(v.getPersona().getUsuario()),
                    amigos.isEmpty() ? "ninguno" : amigos,
                    seguidos.isEmpty() ? "nadie" : seguidos));
        }
        sb.append(String.format("Personas: %d | Amistades: %d | Seguimientos: %d | Aristas internas: %d",
                contarPersonas(), contarAmistades(), contarSeguimientos(), contarAristasInternas()));
        return sb.toString();
    }
}

enum TipoRelacion {
    AMISTAD,
    SEGUIR
}

class Persona {
    private String usuario;
    private String nombre;

    public Persona(String usuario, String nombre) {
        this.usuario = usuario;
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre + " (" + usuario.toUpperCase() + ")";
    }
}

class Vertice {
    private Persona persona;
    private Arista aristas;
    private Vertice sig;

    public Vertice(Persona persona) {
        this.persona = persona;
        this.aristas = null;
        this.sig = null;
    }

    public Persona getPersona() {
        return persona;
    }

    public Arista getAristas() {
        return aristas;
    }

    public void setAristas(Arista aristas) {
        this.aristas = aristas;
    }

    public Vertice getSig() {
        return sig;
    }

    public void setSig(Vertice sig) {
        this.sig = sig;
    }
}

class Arista {
    private Vertice destino;
    private TipoRelacion tipo;
    private Arista sig;

    public Arista(Vertice destino, TipoRelacion tipo) {
        this.destino = destino;
        this.tipo = tipo;
        this.sig = null;
    }

    public Vertice getDestino() {
        return destino;
    }

    public TipoRelacion getTipo() {
        return tipo;
    }

    public Arista getSig() {
        return sig;
    }

    public void setSig(Arista sig) {
        this.sig = sig;
    }
}
