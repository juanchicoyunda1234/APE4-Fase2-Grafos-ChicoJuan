package negocio;

import modelo.Arista;
import modelo.Persona;
import modelo.TipoRelacion;
import modelo.Vertice;

public class RedSocial {
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
