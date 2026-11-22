package modelo;

public class Persona {
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
