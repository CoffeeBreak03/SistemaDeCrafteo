package Objetos;

public class RegistroCrafteo {
    private final Objeto objetoCrafteado;
    private final int cantCrafteada;

    public RegistroCrafteo(Objeto objetoCrafteado, int cantCrafteada) {
        this.objetoCrafteado = objetoCrafteado;
        this.cantCrafteada = cantCrafteada;
    }

    public Objeto getObjetoCrafteado() {
        return objetoCrafteado;
    }

    public int getCantCrafteada() {
        return cantCrafteada;
    }

    @Override
    public String toString() {
        return "objetoCrafteado: " + objetoCrafteado.getNombre() +
                ", cantCrafteada: " + cantCrafteada;
    }
}
