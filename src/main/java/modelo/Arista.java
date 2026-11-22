package modelo;

public class Arista {
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
