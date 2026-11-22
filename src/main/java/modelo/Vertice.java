package modelo;

public class Vertice {
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
